SELECT
    p.*,
    prof.nickname as author_nickname,
    sc.followers_count
FROM post p
JOIN profile prof ON p.owner_id = prof.id
JOIN subscription_count sc ON prof.id = sc.profile_id
WHERE sc.followers_count > 500
ORDER BY p.inserted_at DESC;