SELECT 
    c.name AS customer_name,
    a.description AS account_description,
    COALESCE(AVG(t.amount), 0) AS avg_transaction_amount
FROM 
    Customer c
LEFT JOIN 
    Account a ON c.id = a.customer_id
LEFT JOIN 
    Fin_transaction t ON a.id = t.account_id
GROUP BY 
    c.id, c.name, a.id, a.description
ORDER BY 
    c.name, a.description;
