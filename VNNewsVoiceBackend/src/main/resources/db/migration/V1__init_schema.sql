CREATE TABLE Role (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE UserInfo (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    avatar_url VARCHAR(255),
    email VARCHAR(100) NOT NULL UNIQUE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    birthday DATE,
    is_active BOOLEAN DEFAULT TRUE,
    address TEXT,
    phone_number VARCHAR(20),
    gender VARCHAR(10),
    role_id BIGINT,
    FOREIGN KEY (role_id) REFERENCES Role(id)
);

CREATE TABLE UserProvider (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    provider_id VARCHAR(100) NOT NULL,
    provider_type VARCHAR(50) NOT NULL,
    provider_data JSONB,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES UserInfo(id)
);

CREATE TABLE Admin (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES UserInfo(id)
);

CREATE TABLE Editor (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES UserInfo(id)
);

CREATE TABLE Reader (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL UNIQUE,
    FOREIGN KEY (user_id) REFERENCES UserInfo(id)
);

CREATE TABLE Notification (
    id BIGSERIAL PRIMARY KEY,
    user_id BIGINT NOT NULL,
    title VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    is_read BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES UserInfo(id)
);

CREATE TABLE NotificationRole (
    id BIGSERIAL PRIMARY KEY,
    notification_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    FOREIGN KEY (notification_id) REFERENCES Notification(id),
    FOREIGN KEY (role_id) REFERENCES Role(id)
);

CREATE TABLE Generator (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    logo_url VARCHAR(255),
    url VARCHAR(255) NOT NULL UNIQUE,
    last_time_crawled TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE Category (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE Article (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    author VARCHAR(255),
    generator_id BIGINT NOT NULL,
    published_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    audio_url VARCHAR(255),
    summary TEXT,
    is_active BOOLEAN DEFAULT TRUE,
    editor_id BIGINT,
    category_id BIGINT,
    slug VARCHAR(255) NOT NULL UNIQUE,
    FOREIGN KEY (category_id) REFERENCES Category(id) ON DELETE SET NULL,
    FOREIGN KEY (editor_id) REFERENCES Editor(id) ON DELETE SET NULL,
    FOREIGN KEY (generator_id) REFERENCES Generator(id) ON DELETE CASCADE
);

CREATE TABLE ArticleBlock (
    id BIGSERIAL PRIMARY KEY,
    article_id BIGINT NOT NULL,
    order_index INT NOT NULL,
    type varchar(50) NOT NULL,
    content TEXT,
    text TEXT,
    tag TEXT,
    src TEXT,
    alt TEXT,
    caption TEXT,
    FOREIGN KEY (article_id) REFERENCES Article(id) ON DELETE CASCADE
);

CREATE TABLE Comment (
    id BIGSERIAL PRIMARY KEY,
    article_id BIGINT NOT NULL,
    reader_id BIGINT NOT NULL,
    content TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    comment_reply_id BIGINT,
    FOREIGN KEY (comment_reply_id) REFERENCES Comment(id) ON DELETE CASCADE,
    FOREIGN KEY (article_id) REFERENCES Article(id) ON DELETE CASCADE,
    FOREIGN KEY (reader_id) REFERENCES Reader(id) ON DELETE CASCADE
);

CREATE TABLE CommentLike (
    id BIGSERIAL PRIMARY KEY,
    comment_id BIGINT NOT NULL,
    reader_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (comment_id) REFERENCES Comment(id) ON DELETE CASCADE,
    FOREIGN KEY (reader_id) REFERENCES Reader(id) ON DELETE CASCADE
);

CREATE TABLE ArticleLike (
    id BIGSERIAL PRIMARY KEY,
    article_id BIGINT NOT NULL,
    reader_id BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (article_id) REFERENCES Article(id) ON DELETE CASCADE,
    FOREIGN KEY (reader_id) REFERENCES Reader(id) ON DELETE CASCADE
)



