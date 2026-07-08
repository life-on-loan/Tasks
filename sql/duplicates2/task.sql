-- Удаление дублей. Реализовать запрос на удаление повторяющихся значений. 
-- Например есть таблица emails(id, email), в какой-то момент email'ы стали дублироваться, 
-- необходимо написать запрос на удаление дублей (какие id останутся не принципиально)

create table email(
id bigserial,
email text
);

insert into email(id, email) values (1, 'ivanov@mail.ru');
insert into email(id, email) values (2, 'petrov@mail.ru');
insert into email(id, email) values (3, 'ivanov@mail.ru');
insert into email(id, email) values (4, 'sidorov@mail.ru');
insert into email(id, email) values (5, 'orlov@mail.ru');
insert into email(id, email) values (6, 'sidorov@mail.ru');
