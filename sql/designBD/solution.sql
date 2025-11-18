-- Таблица авторов
CREATE TABLE author (
    id BIGSERIAL PRIMARY KEY,
    last_name VARCHAR(100) NOT NULL,
    first_name VARCHAR(100) NOT NULL,
    middle_name VARCHAR(100),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Таблица книг
CREATE TABLE book (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(500) NOT NULL,
    publication_year INT,
    isbn VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Связующая таблика (многие-ко-многим)
CREATE TABLE book_author (
    book_id BIGINT REFERENCES book(id) ON DELETE CASCADE,
    author_id BIGINT REFERENCES author(id) ON DELETE CASCADE,
    author_order INT NOT NULL DEFAULT 1, -- порядок авторов на обложке
    PRIMARY KEY (book_id, author_id)
);

SELECT
    b.id,
    b.title,
    b.publication_year,
    b.isbn,
    a.last_name,
    a.first_name,
    a.middle_name
FROM book b
JOIN book_author ba ON b.id = ba.book_id
JOIN author a ON ba.author_id = a.id
WHERE a.last_name = 'Иванов'
ORDER BY b.title;