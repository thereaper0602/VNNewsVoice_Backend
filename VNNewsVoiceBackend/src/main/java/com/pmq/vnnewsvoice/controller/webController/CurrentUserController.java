package com.pmq.vnnewsvoice.controller.webController;


import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;
import java.util.Optional;

@Controller
public class CurrentUserController {
    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/profile")
    public String profile(
            Model model,
            Principal principal)
    {
        Optional<UserInfo> currentUser = userInfoService.getUserByUsername(principal.getName());

        if(currentUser.isEmpty()){
            model.addAttribute("error", "Không tìm thấy người dùng");
            return "redirect:/";
        }

        model.addAttribute("user", currentUser.get());
        return "dashboard/current-user/profile";
    }

    @PostMapping("/current-user/update")
    public String updateLecturer(
            @ModelAttribute("user") UserInfo updateUser,
            Principal principal,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes
    ){
        if (bindingResult.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Dữ liệu không hợp lệ");
            return "redirect:/profile";
        }

        Optional<UserInfo> existingUser = userInfoService.getUserById(updateUser.getId());

        if(existingUser.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng hợp lệ");
            return "redirect:/profile";
        }

        if(!principal.getName().equals(existingUser.get().getUsername())){
            redirectAttributes.addFlashAttribute("msg_error", "Lỗi cập nhật người dùng");
            return "redirect:/profile";
        }

        try{
            boolean isUpdateUser = userInfoService.updateUser(updateUser);
            if(isUpdateUser){
                redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin người dùng thành công!!!");
            }
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra trong quá trình cập nhật " + e.getMessage());
            return "redirect:/profile";
        }
        return "redirect:/profile";
    }

    @PostMapping("current-user/updatePassword")
    public String updateUserPassword(
            @ModelAttribute("user") UserInfo newUser,
            RedirectAttributes redirectAttributes,
            BindingResult bindingResult,
            Principal principal
    ){
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("error", "Thông tin không hợp lệ !!!");
            return "redirect:/profile";
        }

        Optional<UserInfo> existingUser = userInfoService.getUserById(newUser.getId());

        if(existingUser.isEmpty()){
            redirectAttributes.addFlashAttribute("error", "Không tìm thấy người dùng hợp lệ");
            return "redirect:/profile";
        }

        if(!principal.getName().equals(existingUser.get().getUsername())){
            redirectAttributes.addFlashAttribute("msg_error", "Lỗi cập nhật người dùng");
            return "redirect:/profile";
        }

        try{
            boolean isUpdateUser = userInfoService.updateUser(newUser);
            if(isUpdateUser){
                redirectAttributes.addFlashAttribute("success", "Cập nhật thông tin người dùng thành công!!!");
            }
        }
        catch (Exception e){
            redirectAttributes.addFlashAttribute("error", "Có lỗi xảy ra trong quá trình cập nhật " + e.getMessage());
            return "redirect:/profile";
        }
        return "redirect:/profile";

    }

}
