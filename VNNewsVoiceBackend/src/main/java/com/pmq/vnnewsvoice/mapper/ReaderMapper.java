package com.pmq.vnnewsvoice.mapper;

import com.pmq.vnnewsvoice.dto.ReaderDto;
import com.pmq.vnnewsvoice.pojo.Reader;
import com.pmq.vnnewsvoice.pojo.Role;
import com.pmq.vnnewsvoice.pojo.UserInfo;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ReaderMapper implements Serializable {
    public ReaderDto toDto(Reader reader) {
        if (reader == null) {
            return null;
        }
        
        ReaderDto readerDto = new ReaderDto();
        readerDto.setId(reader.getId());
        
        // Xử lý trường hợp userId có thể null
        if (reader.getUserId() != null) {
            readerDto.setUserIdId(reader.getUserId().getId());
            readerDto.setUserIdUsername(reader.getUserId().getUsername());
            readerDto.setUserIdAvatarUrl(reader.getUserId().getAvatarUrl());
            readerDto.setUserIdEmail(reader.getUserId().getEmail());
            readerDto.setUserIdBirthday(reader.getUserId().getBirthday());
            readerDto.setUserIdAddress(reader.getUserId().getAddress());
            readerDto.setUserIdPhoneNumber(reader.getUserId().getPhoneNumber());
            readerDto.setUserIdGender(reader.getUserId().getGender());
            readerDto.setUserIdAvatarPublicId(reader.getUserId().getAvatarPublicId());
            
            // Xử lý trường hợp roleId có thể null
            if (reader.getUserId().getRoleId() != null) {
                readerDto.setUserIdRoleIdId(reader.getUserId().getRoleId().getId());
                readerDto.setUserIdRoleIdName(reader.getUserId().getRoleId().getName());
            }
        }
        
        return readerDto;
    }

    public Reader toEntity(ReaderDto readerDto) {
        if (readerDto == null) {
            return null;
        }

        Reader reader = new Reader();
        reader.setId(readerDto.getId());

        // Xử lý thông tin UserInfo
        if (readerDto.getUserIdId() != null) {
            UserInfo userInfo = new UserInfo();
            userInfo.setId(readerDto.getUserIdId());
            userInfo.setUsername(readerDto.getUserIdUsername());
            userInfo.setAvatarUrl(readerDto.getUserIdAvatarUrl());
            userInfo.setEmail(readerDto.getUserIdEmail());
            userInfo.setBirthday(readerDto.getUserIdBirthday());
            userInfo.setAddress(readerDto.getUserIdAddress());
            userInfo.setPhoneNumber(readerDto.getUserIdPhoneNumber());
            userInfo.setGender(readerDto.getUserIdGender());
            userInfo.setAvatarPublicId(readerDto.getUserIdAvatarPublicId());

            // Xử lý thông tin Role
            if (readerDto.getUserIdRoleIdId() != null) {
                Role role = new Role();
                role.setId(readerDto.getUserIdRoleIdId());
                role.setName(readerDto.getUserIdRoleIdName());
                userInfo.setRoleId(role);
            }

            reader.setUserId(userInfo);
        }

        return reader;
    }



}