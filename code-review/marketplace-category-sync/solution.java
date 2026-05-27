"""
Проблемы производительности:
1. N+1 проблема и вложенные циклы — для каждого product выполняется полный перебор categories.
Если продуктов 1000 и категорий 1000, получаем 1 000 000 итераций.
Решение: преобразовать categories в Map<Long, CategoryDto>.
2. Вызов save() в цикле — каждый продукт сохраняется отдельным SQL-запросом.
Для массового обновления эффективнее использовать saveAll().

Логические ошибки
1. Двойной цикл вместо маппинга — поиск категории по productId через вложенный цикл нечитаем
и неэффективен. Следует использовать мапу.
2. Изменение сущности Product — CategoryDto и Product имеют поле popularity,
но в Product оно не обновляется, хотя данные приходят от клиента.

Архитектурные замечания
1. Смешивание DTO и Entity в одном классе — Product одновременно аннотирован @Data и @Entity,
что может привести к проблемам с equals()/hashCode() и lazy-loading.
Для Entity лучше использовать @Getter/@Setter.
2. Отсутствие транзакционности — метод loadData() не транзакционный, что при сбое в середине
выполнения приведёт к частично сохранённым данным.
3. Связанность данных — интеграция напрямую сохраняет данные в репозиторий,
вместо возврата подготовленных данных.
"""

//Исправленная версия:
@Service
@RequiredArgsConstructor
@Transactional
public class IntegrationService {

    private final ProductRepository productRepository;
    private final IntegrationClient integrationClient;

    public void loadData() {
        var products = productRepository.findAll();
        var categories = integrationClient.getCategories();

        Map<Long, String> categoryMap = categories.stream()
                .collect(Collectors.toMap(CategoryDto::getProductId, CategoryDto::getName));

        List<Product> toUpdate = products.stream()
                .filter(Product::isMarketplace)
                .filter(product -> categoryMap.containsKey(product.getId()))
                .peek(product -> product.setCategoryName(categoryMap.get(product.getId())))
                .collect(Collectors.toList());

        if (!toUpdate.isEmpty()) {
            productRepository.saveAll(toUpdate);
        }
    }
}