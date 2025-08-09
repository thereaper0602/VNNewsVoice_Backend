package com.pmq.vnnewsvoice.controller.restController;

import com.pmq.vnnewsvoice.dto.CommentDto;
import com.pmq.vnnewsvoice.helpers.PaginationHelper;
import com.pmq.vnnewsvoice.mapper.CommentMapper;
import com.pmq.vnnewsvoice.pojo.Comment;
import com.pmq.vnnewsvoice.service.CommentService;
import com.pmq.vnnewsvoice.utils.Pagination;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class ApiCommentController {
    
    @Autowired
    private CommentService commentService;
    
    @Autowired
    private CommentMapper commentMapper;
    
    @Autowired
    private PaginationHelper paginationHelper;
    
    @GetMapping("/articles/{slug}_{id}/comments")
    public ResponseEntity<Map<String, Object>> getCommentsByArticleId(
            @PathVariable("slug") String slug,
            @PathVariable("id") Long id,
            @RequestParam Map<String, String> params
    ) {
        // Kiểm tra id
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        
        // Lấy danh sách bình luận theo articleId với phân trang
        List<Comment> comments = commentService.getCommentsByArticleId(id, params);
        
        // Đếm tổng số bình luận để phân trang
        long totalComments = commentService.countCommentsByArticleId(id);
        
        // Tạo đối tượng phân trang
        Pagination pagination = paginationHelper.createPagination(params, totalComments, 10);
        
        // Chuyển đổi sang DTO
        List<CommentDto> commentDtos = comments.stream().map(commentMapper::toDto).toList();
        
        // Tạo response
        Map<String, Object> response = new HashMap<>();
        response.put("comments", commentDtos);
        response.put("pagination", pagination);
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/comments/{id}")
    public ResponseEntity<CommentDto> getCommentById(
            @PathVariable("id") Long id
    ) {
        // Kiểm tra id
        if (id == null || id <= 0) {
            return ResponseEntity.badRequest().build();
        }
        
        // Lấy bình luận theo id
        Comment comment = commentService.getCommentById(id);
        if (comment == null) {
            return ResponseEntity.notFound().build();
        }
        
        // Chuyển đổi sang DTO
        CommentDto commentDto = commentMapper.toDto(comment);
        
        return ResponseEntity.ok(commentDto);
    }
    
    @GetMapping("/users/{userId}/comments")
    public ResponseEntity<Map<String, Object>> getCommentsByUserId(
            @PathVariable("userId") Long userId,
            @RequestParam Map<String, String> params
    ) {
        // Kiểm tra userId
        if (userId == null || userId <= 0) {
            return ResponseEntity.badRequest().build();
        }
        
        // Lấy danh sách bình luận theo userId với phân trang
        List<Comment> comments = commentService.getCommentsByUserId(userId, params);
        
        // Tạo đối tượng phân trang (giả sử có 100 bình luận tổng cộng)
        // Trong thực tế, bạn nên có một phương thức để đếm tổng số bình luận theo userId
        Pagination pagination = paginationHelper.createPagination(params, 100, 10);
        
        // Chuyển đổi sang DTO
        List<CommentDto> commentDtos = comments.stream().map(commentMapper::toDto).toList();
        
        // Tạo response
        Map<String, Object> response = new HashMap<>();
        response.put("comments", commentDtos);
        response.put("pagination", pagination);
        
        return ResponseEntity.ok(response);
    }
}