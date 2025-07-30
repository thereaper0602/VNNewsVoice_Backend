-- Thêm cột status để lưu trạng thái chi tiết của bài báo
ALTER TABLE Article ADD COLUMN IF NOT EXISTS status VARCHAR(20) DEFAULT 'PENDING';

-- Tạo index cho cột mới
CREATE INDEX IF NOT EXISTS idx_article_status ON Article(status);