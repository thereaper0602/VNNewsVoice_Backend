-- Thêm cột top_image để lưu URL ảnh đại diện cho bài viết
ALTER TABLE Article ADD COLUMN IF NOT EXISTS top_image_url VARCHAR(500);

-- Tạo index cho cột mới
CREATE INDEX IF NOT EXISTS idx_article_top_image ON Article(top_image_url);