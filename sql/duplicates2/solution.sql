DELETE FROM users u1
WHERE EXISTS (
    SELECT 1 
    FROM users u2 
    WHERE u2.email = u1.email 
    AND u2.id < u1.id
