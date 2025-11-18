create table t_user(
    id bigserial PK,
    name text,
    email text,
    created bigint,
    updated bigint
);

--- 1. Найти дубликаты аккаунтов по email
--- 2. Удалить дубликаты по email, оставить  записи созданные раньше