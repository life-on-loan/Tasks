// Провести код-ревью
@Service
@RequiredArgsConstructor
public class IntegrationService {

    private final ProductRepository productRepository;
    private final IntegrationClient integrationClient;

    public void loadData() {
        var products = productRepository.findAll();
        var categories = integrationClient.getCategories();

        for (var product : products) {
            if (product.isMarketplace()) {
                for (var category : categories) {
                    if (product.getId().equals(category.getProductId())) {
                        product.setCategoryName(category.getName());
                        productRepository.save(product);
                    }
                }
            }
        }
    }

    @Data
    public class CategoryDto {

        private Long productId;
        private String popularity;
        private String name;
    }

    @Data
    @Entity
    public class Product {

        @Id
        private Long id;
        private String name;
        private String categoryName;
        private boolean marketplace;
    }
}