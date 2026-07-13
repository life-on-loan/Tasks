// TODO: Провести код ревью, определить проблемы, написать решение проблем

@Slf4j
public class SyncServiceImpl implements SyncService {

    @Autowired
    private SellerRestClient restClient;
    @Autowired
    private ShopRepository shopRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private KafkaTemplate<String, ProductDto> kafkaTemplate;

    @Schedule(cron = "* * * * * * ") // 1 sec
    @Override
    public void syncShops() {
        for (Shop shop : shopRepository.findAll()) {
            try {
                syncShop(shop);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Transactional
    @Override
    public void syncShop(Shop shop) {
        log.info("Syncing shop");
        shopRepository.lockShop(shop.getId()); // @Query(native = true, value = ""SELECT * FROM shop WHERE id = ? FOR UPDATE"")

        var products = shop.getNotSynchedProducts();
        products.parallelStream()
                .forEach(product -> {
                    log.info("Syncing product");
                    var seller = product.getSeller();
                    var sellerDetails = restClient.getLegalDetails(seller.getInn());

                    var dto = new ProductDto();
                    dto.setProduct(product);
                    dto.setSellerDetails(sellerDetails);

                    kafkaTemplate.send("product_details", "product", dto); // topic, key, value

                    product.setSynced(true);
                    productRepository.save(product);
                });
        shop.setSynced(true);
    }
}