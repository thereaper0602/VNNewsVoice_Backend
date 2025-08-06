package com.pmq.vnnewsvoice.helpers;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;


@Component
public class CloudinaryHelper {
    @Autowired
    private Cloudinary cloudinary;

    public Map<String, String> uploadMultipartFile(MultipartFile file) throws IOException {
        try{
            Map res = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap("resource_type","auto"));

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("url", res.get("secure_url").toString());
            resultMap.put("publicId", res.get("public_id").toString());
            return resultMap;
        }
        catch (IOException e){
            throw new IOException("Lỗi upload file lên cloud");
        }
    }

    public Map<String, String> uploadFile(File file) throws IOException {
        try {
            Map res = cloudinary.uploader().upload(file,
                    ObjectUtils.asMap("resource_type", "auto"));

            Map<String, String> resultMap = new HashMap<>();
            resultMap.put("url", res.get("secure_url").toString());
            resultMap.put("publicId", res.get("public_id").toString());

            return resultMap;
        } catch (IOException ex) {
            throw  new IOException("Lỗi upload lên cloud");
        }
    }

    public boolean deleteFile(String publicId) throws IOException {
        try {
            Map res = cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());

            boolean result = res.get("result").toString().equals("not found");

            return !result;
        } catch (IOException ex) {
            throw new IOException("Xóa file không thành công");
        }
    }

}
