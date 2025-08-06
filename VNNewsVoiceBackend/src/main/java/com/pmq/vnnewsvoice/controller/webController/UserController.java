package com.pmq.vnnewsvoice.controller.webController;


import com.pmq.vnnewsvoice.helpers.PaginationHelper;
import com.pmq.vnnewsvoice.pojo.*;
import com.pmq.vnnewsvoice.service.*;
import com.pmq.vnnewsvoice.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/admin")
public class UserController {

    @Autowired
    private PaginationHelper paginationHelper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AdminService adminService;

    @Autowired
    private EditorService editorService;

    @Autowired
    private ReaderService readerService;

    @GetMapping("/users")
    public String users(
            Model model,
            @RequestParam Map<String, String> params,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal
            ){
        // Lọc danh sách người dùng chỉ hiển thị reader và editor nếu là admin
        if(principal.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"))){
            params.put("roleFilter", "READER,EDITOR");
        }

        
        long totalUsers = userInfoService.countSearchUsers(params, new HashMap<>());

        Pagination pagination = paginationHelper.createPagination(params, totalUsers, 10);

        List<UserInfo> users = userInfoService.getUsers(params);
        
        // Lọc danh sách vai trò chỉ hiển thị Editor và Reader để thêm mới
        List<Role> allRoles = userRoleService.getAllUserRoles();

        // Kiểm tra về người dùng có phải là super admin không

        List<Role> filteredRoles = allRoles.stream()
                .filter(role -> role.getName().contains("ROLE_EDITOR") || role.getName().contains("ROLE_READER"))
                .collect(Collectors.toList());

        UserInfo newUser = new UserInfo();

        model.addAttribute("users", users);
        model.addAttribute("newUser", newUser);
        model.addAttribute("roles", filteredRoles);

        model.addAttribute("currentPage", pagination.getCurrentPage());
        model.addAttribute("totalPages", pagination.getTotalPages() > 0 ? pagination.getTotalPages() : 1);
        model.addAttribute("startIndex", pagination.getStartIndex());
        model.addAttribute("endIndex", pagination.getEndIndex());
        model.addAttribute("totalUsers", totalUsers);
        model.addAttribute("filterName", params.getOrDefault("name", " "));
        return "dashboard/admin/users/users_list";
    }

    @PostMapping("users/add")
    public String addUser(
            @ModelAttribute UserInfo newUser,
            @RequestParam Map<String, String> params,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal
    )
    {
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error while adding user. Please check the input data.");
            return "redirect:/admin/users";
        }

        try{
            // Kiểm tra vai trò được chọn
            Role userRole = userRoleService.getUserRoleById(newUser.getRoleId().getId());

            // Chỉ cho phép thêm Editor hoặc Reader
            if (!userRole.getName().equals("ROLE_EDITOR") && !userRole.getName().equals("ROLE_READER")) {
                redirectAttributes.addFlashAttribute("error", "Admin chỉ có thể thêm người dùng với vai trò Editor hoặc Reader");
                return "redirect:/admin/users";
            }

            UserInfo createdUser = userInfoService.addUser(newUser);

            switch (userRole.getName())
            {
                case "ROLE_EDITOR":
                    Editor editor = new Editor();
                    editor.setUserId(createdUser);
                    editorService.addEditor(editor);
                    break;
                case "ROLE_READER":
                    Reader reader = new Reader();
                    reader.setUserId(createdUser);
                    readerService.addReader(reader);
                    break;
                default:
                    redirectAttributes.addFlashAttribute("error", "Lỗi khi tạo người dùng!!!!");
            }
            redirectAttributes.addFlashAttribute("success", "Tạo người dùng mới thành công");
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Lỗi khi thêm người dùng!!: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("users/{userId}/toggle-active")
    public String toggleUserActive(
            @PathVariable Long userId,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal
    ) {
        try {
            // Kiểm tra xem người dùng cần thay đổi có phải là admin không
            Optional<UserInfo> targetUser = userInfoService.getUserById(userId);
            if (targetUser.isPresent() && targetUser.get().getRoleId().getName().equals("ROLE_ADMIN")
                    && !userId.equals(principal.getUserInfo().getId())) {
                redirectAttributes.addFlashAttribute("error", "Không thể thay đổi trạng thái của admin khác");
                return "redirect:/admin/users";
            }

            boolean result = userInfoService.toggleUserActive(userId);
            if (result) {
                redirectAttributes.addFlashAttribute("success", "Đã thay đổi trạng thái người dùng thành công");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể thay đổi trạng thái người dùng");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    @PostMapping("users/{userId}/delete")
    public String deleteUser(
            @PathVariable Long userId,
            RedirectAttributes redirectAttributes,
            @AuthenticationPrincipal CustomUserDetails principal
    ) {
        try {
            // Kiểm tra xem người dùng cần xóa có phải là admin không
            Optional<UserInfo> targetUser = userInfoService.getUserById(userId);
            if (targetUser.isPresent() && targetUser.get().getRoleId().getName().equals("ROLE_ADMIN")) {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa tài khoản admin");
                return "redirect:/admin/users";
            }

            String roleName = targetUser.get().getRoleId().getName();

            boolean roleDelete = switch (roleName){
                case "ROLE_EDITOR" -> deleteEditor(targetUser.get());
                case "ROLE_READER" -> deleteReader(targetUser.get());
                default -> {
                    redirectAttributes.addFlashAttribute("error", "Không thể xóa tài khoản !!!");
                    yield false;
                }
            };

            if(!roleDelete){
                redirectAttributes.addFlashAttribute("error", "Không thể xóa tài khoản !!!");
                return "redirect:/admin/users";
            }

            boolean result = userInfoService.deleteUser(userId);
            if (result) {
                redirectAttributes.addFlashAttribute("success", "Đã xóa người dùng thành công");
            } else {
                redirectAttributes.addFlashAttribute("error", "Không thể xóa người dùng");
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Lỗi: " + e.getMessage());
        }
        return "redirect:/admin/users";
    }

    private boolean deleteEditor(UserInfo userInfo){
        Optional<Editor> editor = editorService.getEditorByUserId(userInfo.getId());
        if(editor.isEmpty()){
            return false;
        }
        return editorService.deleteEditor(editor.get().getId());
    }

    private boolean deleteReader(UserInfo userInfo){
        Optional<Reader> reader = readerService.getReaderById(userInfo.getId());
        if(reader.isEmpty()){
            return false;
        }
        return readerService.deleteReader(reader.get().getId());
    }
}
