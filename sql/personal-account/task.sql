/*
Даны таблицы
Customer
-id pk
-name
-address

Account
-id pk
-acc_number
-description
-customer_id fk

Fin_transaction
-id pk
-transactDate
-amount
-account_id fk
-description

У клиента может не быть лицевых счетов. По лицевому счету может не быть транзакций.

Необходимо написать SQL-запрос, возвращающий имя клиента, описание его лицевого счета и среднюю сумму транзакции по этому счету.
*/
