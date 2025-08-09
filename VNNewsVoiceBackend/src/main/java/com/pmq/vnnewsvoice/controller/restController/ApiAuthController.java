package com.pmq.vnnewsvoice.controller.restController;

import com.pmq.vnnewsvoice.dto.JwtResponseDto;
import com.pmq.vnnewsvoice.dto.LoginDto;
import com.pmq.vnnewsvoice.dto.ReaderDto;
import com.pmq.vnnewsvoice.dto.RegisterReaderDto;
import com.pmq.vnnewsvoice.mapper.ReaderMapper;
import com.pmq.vnnewsvoice.mapper.RegisterReaderMapper;
import com.pmq.vnnewsvoice.pojo.Reader;
import com.pmq.vnnewsvoice.pojo.UserInfo;
import com.pmq.vnnewsvoice.service.ReaderService;
import com.pmq.vnnewsvoice.service.UserDetailService;
import com.pmq.vnnewsvoice.service.UserInfoService;
import com.pmq.vnnewsvoice.service.UserRoleService;
import com.pmq.vnnewsvoice.utils.JwtUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Collections;

@RestController
@RequestMapping("/api")
public class ApiAuthController {
    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private UserDetailService userDetailService;

    @Autowired
    private ReaderService readerService;

    @Autowired
    private ReaderMapper readerMapper;
    
    @Autowired
    private RegisterReaderMapper registerReaderMapper;

    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private JwtUtils jwtUtils;


    @PostMapping("/user/register")
    public ResponseEntity<ReaderDto> create(
            @ModelAttribute RegisterReaderDto registerReaderDto,
            BindingResult result
    ) throws IOException {
        if(result.hasErrors()){
            return ResponseEntity.badRequest().body(null);
        }
        
        // Tạo đối tượng UserInfo từ RegisterReaderDto
        UserInfo userInfo = new UserInfo();
        userInfo.setUsername(registerReaderDto.getUserIdUsername());
        userInfo.setPassword(registerReaderDto.getUserIdPassword()); // Lấy mật khẩu từ RegisterReaderDto
        userInfo.setEmail(registerReaderDto.getUserIdEmail());
        userInfo.setAvatarUrl(registerReaderDto.getUserIdAvatarUrl());
        userInfo.setBirthday(registerReaderDto.getUserIdBirthday());
        userInfo.setAddress(registerReaderDto.getUserIdAddress());
        userInfo.setPhoneNumber(registerReaderDto.getUserIdPhoneNumber());
        userInfo.setGender(registerReaderDto.getUserIdGender());
        userInfo.setAvatarPublicId(registerReaderDto.getUserIdAvatarPublicId());
        userInfo.setRoleId(userRoleService.getUserRoleByName("ROLE_READER"));
        
        // Lưu UserInfo vào database
        UserInfo savedUserInfo = userInfoService.addUser(userInfo);
        
        // Tạo đối tượng Reader và liên kết với UserInfo đã lưu
        Reader reader = new Reader();
        reader.setUserId(savedUserInfo);
        
        // Lưu Reader vào database
        Reader savedReader = readerService.addReader(reader);
        
        // Chuyển đổi Reader thành ReaderDto để trả về
        ReaderDto readerDto = readerMapper.toDto(savedReader);
        
        return ResponseEntity.ok(readerDto);
    }

    @PostMapping("/user/login")
    public ResponseEntity<?> login(
            @RequestBody LoginDto loginDto
    ){
        if(userDetailService.authenticateUser(loginDto.getUsername(), loginDto.getPassword())){
            // Lấy thông tin người dùng từ username
            UserDetails userDetails = userDetailService.loadUserByUsername(loginDto.getUsername());
            
            // Tạo đối tượng Authentication
            Authentication authentication = new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
            
            // Tạo token
            String token = jwtUtils.generatJwtToken(authentication);
            
            // Chỉ trả về token
            return ResponseEntity.ok().body(Collections.singletonMap("token", token));
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
    }





}
