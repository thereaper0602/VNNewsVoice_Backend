-- Thêm trường public_id vào bảng user_info để lưu public_id của ảnh trên Cloudinary
ALTER TABLE userinfo
    ADD COLUMN avatar_public_id VARCHAR(255);

-- Cập nhật comment cho trường mới
COMMENT ON COLUMN userinfo.avatar_public_id IS 'Public ID của ảnh avatar trên Cloudinary';