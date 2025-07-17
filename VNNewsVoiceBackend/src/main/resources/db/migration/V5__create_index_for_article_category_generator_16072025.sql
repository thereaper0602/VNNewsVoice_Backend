-- Foreign Keys (PostgreSQL không tự tạo index cho FK)
CREATE INDEX IF NOT EXISTS idx_article_generator_id ON Article(generator_id);
CREATE INDEX IF NOT EXISTS idx_article_category_id ON Article(category_id);
CREATE INDEX IF NOT EXISTS idx_article_editor_id ON Article(editor_id);
CREATE INDEX IF NOT EXISTS idx_articleblock_article_id ON ArticleBlock(article_id);

-- Columns thường được filter/sort
CREATE INDEX IF NOT EXISTS idx_article_published_date ON Article(published_date);
CREATE INDEX IF NOT EXISTS idx_article_is_active ON Article(is_active);
CREATE INDEX IF NOT EXISTS idx_article_created_at ON Article(created_at);

-- Composite index cho query phổ biến
CREATE INDEX IF NOT EXISTS idx_article_active_published ON Article(is_active, published_date DESC);