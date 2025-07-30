package com.pmq.vnnewsvoice.controller;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    
    /**
     * Xử lý AccessDeniedException và chuyển hướng người dùng dựa trên URL hiện tại
     */
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException ex, 
                                             HttpServletRequest request,
                                             RedirectAttributes redirectAttributes) {
        // Lấy URI hiện tại để xác định vai trò của người dùng
        String requestURI = request.getRequestURI();
        String redirectPath;
        
        // Xác định trang chuyển hướng dựa trên URI
        if (requestURI.startsWith("/admin")) {
            redirectPath = "/admin/articles";
        } else if (requestURI.startsWith("/editor")) {
            redirectPath = "/editor/articles";
        } else {
            // Mặc định chuyển hướng về trang chính
            redirectPath = "/";
        }
        
        // Thêm thông báo lỗi
        redirectAttributes.addFlashAttribute("error", "Bạn không có quyền truy cập: " + ex.getMessage());
        
        return "redirect:" + redirectPath;
    }
    
    /**
     * Xử lý các ngoại lệ IllegalArgumentException
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public String handleIllegalArgumentException(IllegalArgumentException ex,
                                               HttpServletRequest request,
                                               RedirectAttributes redirectAttributes) {
        // Tương tự như trên, xác định trang chuyển hướng dựa trên URI
        String requestURI = request.getRequestURI();
        String redirectPath;
        
        if (requestURI.startsWith("/admin")) {
            redirectPath = "/admin/articles";
        } else if (requestURI.startsWith("/editor")) {
            redirectPath = "/editor/articles";
        } else {
            redirectPath = "/";
        }
        
        redirectAttributes.addFlashAttribute("error", "Dữ liệu không hợp lệ: " + ex.getMessage());
        
        return "redirect:" + redirectPath;
    }
    
    /**
     * Xử lý các ngoại lệ cho REST API endpoints
     */
    @ExceptionHandler({Exception.class})
    @ResponseBody
    public ResponseEntity<Map<String, Object>> handleApiException(Exception ex, HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        Map<String, Object> errorResponse = new HashMap<>();
        
        // Kiểm tra nếu request là AJAX hoặc API call
        if (requestURI.contains("/api/") || 
            request.getHeader("X-Requested-With") != null || 
            requestURI.contains("/save-all-changes") || 
            requestURI.contains("/save-audio-url")) {
            
            errorResponse.put("success", false);
            errorResponse.put("message", "Đã xảy ra lỗi: " + ex.getMessage());
            
            return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        
        // Nếu không phải API call, xử lý như các exception khác
        errorResponse.put("error", "Đã xảy ra lỗi: " + ex.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
    
    /**
     * Xử lý lỗi tài nguyên không tìm thấy
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNoResourceFoundException(NoResourceFoundException ex) {
        Map<String, Object> errorResponse = new HashMap<>();
        errorResponse.put("success", false);
        errorResponse.put("message", "Không tìm thấy tài nguyên: " + ex.getMessage());
        
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}