--- 1. Континенты и суммарное число жителей на каждом

SELECT
    continent,
    SUM(population) as total_population
FROM country
GROUP BY continent
ORDER BY total_population DESC;

--- 2. Континенты с населением больше миллиарда

SELECT
    continent,
    SUM(population) as total_population
FROM country
GROUP BY continent
HAVING SUM(population) > 1000000000
ORDER BY total_population DESC;

--- 3. Название страны + ВВП на душу населения

SELECT
    c.name as country_name,
    g.value as gdp,
    c.population,
    ROUND(g.value::DECIMAL / c.population, 2) as gdp_per_capita
FROM country c
JOIN gdp g ON c.id = g.country_id
WHERE g.year = 2023  -- указать нужный год
ORDER BY gdp_per_capita DESC;

--- 4. Самые большие по населению страны на каждом континенте

WITH ranked_countries AS (
    SELECT
        continent,
        name as country_name,
        population,
        ROW_NUMBER() OVER (
            PARTITION BY continent
            ORDER BY population DESC
        ) as rank
    FROM country
)
SELECT
    continent,
    country_name,
    population
FROM ranked_countries
WHERE rank = 1
ORDER BY continent;

--- 5. Топ-3 страны по населению на каждом континенте

WITH ranked_countries AS (
    SELECT
        continent,
        name as country_name,
        population,
        ROW_NUMBER() OVER (
            PARTITION BY continent
            ORDER BY population DESC
        ) as rank
    FROM country
)
SELECT
    continent,
    country_name,
    population
FROM ranked_countries
WHERE rank <= 3
ORDER BY continent, rank;