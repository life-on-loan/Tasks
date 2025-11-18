--- Простой вариант - подсчет дубликатов
SELECT
    email,
    COUNT(*) as duplicate_count
FROM t_user
GROUP BY email
HAVING COUNT(*) > 1
ORDER BY duplicate_count DESC;

--- Детальный вариант - показ всех дублирующихся записей
SELECT
    u1.id,
    u1.email,
    u1.name,
    u1.created
FROM t_user u1
INNER JOIN (
    SELECT email
    FROM t_user
    GROUP BY email
    HAVING COUNT(*) > 1
) u2 ON u1.email = u2.email
ORDER BY u1.email, u1.created;

--- Удаление дубликатов
WITH duplicates AS (
    SELECT
        id,
        email,
        created,
        ROW_NUMBER() OVER (PARTITION BY email ORDER BY created) as row_num
    FROM t_user
)
DELETE FROM t_user
WHERE id IN (
    SELECT id FROM duplicates WHERE row_num > 1
);