// 1. Добавление недостающих аннотаций

@RestController
@RequestMapping("/api/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @PostMapping
    public void create(@RequestBody List<Organization> organizations) {
        organizationService.create(organizations);
    }
}

public interface OrganizationService {
    void create(List<Organization> organizations);
}

@Service
@Transactional
public class OrganizationServiceImpl implements OrganizationService {

    @Autowired
    private OrganizationRepository organizationRepository;

    @Override
    public void create(List<Organization> organizations) {
        // Валидация атрибутов организаций
        validate(organizations);

        // Маппинг организаций на сущности
        Set<OrganizationEntity> entities = map(organizations);

        // Сохранение организаций в БД
        save(entities);
    }

    private void validate(List<Organization> organizations) {
        // 2. Проверить что список организаций не пустой
        if (organizations == null || organizations.isEmpty()) {
            throw new IllegalArgumentException("Organizations list cannot be empty");
        }

        // 3. Проверить что нет организаций без счетов
        boolean hasOrganizationWithoutAccounts = organizations.stream()
                .anyMatch(org -> org.getAccounts() == null || org.getAccounts().isEmpty());

        if (hasOrganizationWithoutAccounts) {
            throw new IllegalArgumentException("Organizations must have at least one account");
        }

        // 4. Посчитать общее количество счетов у всех организаций
        //    и проверить что количество счетов не больше 10
        long totalAccounts = organizations.stream()
                .mapToLong(org -> org.getAccounts().size())
                .sum();

        if (totalAccounts > 10) {
            throw new IllegalArgumentException("Total number of accounts cannot exceed 10, but got: " + totalAccounts);
        }

        // 5. Для каждого счета проверить что
        //    - счет не пустой
        //    - счет содержит только цифры
        organizations.stream()
                .flatMap(org -> org.getAccounts().stream())
                .forEach(account -> {
                    if (account == null || account.trim().isEmpty()) {
                        throw new IllegalArgumentException("Account cannot be empty");
                    }
                    if (!account.matches("\\d+")) {
                        throw new IllegalArgumentException("Account must contain only digits: " + account);
                    }
                });
    }

    private Set<OrganizationEntity> map(List<Organization> organizations) {
        // 6. Удалить дубликаты счетов
        //    Organization(accounts=[1, 1]) -> Organization(accounts=[1])
        List<Organization> distinctOrganizations = organizations.stream()
                .map(org -> {
                    Organization distinctOrg = new Organization();
                    List<String> distinctAccounts = org.getAccounts().stream()
                            .distinct()
                            .collect(Collectors.toList());
                    distinctOrg.setAccounts(distinctAccounts);
                    return distinctOrg;
                })
                .collect(Collectors.toList());

        // 7. Из списка организаций составить множество сущностей
        //    Organization(accounts=[1, 2]) -> [OrganizationEntity(account=1), OrganizationEntity(account=2)]
        return distinctOrganizations.stream()
                .flatMap(org -> org.getAccounts().stream())
                .map(account -> {
                    OrganizationEntity entity = new OrganizationEntity();
                    entity.setAccount(Integer.parseInt(account));
                    return entity;
                })
                .collect(Collectors.toSet());
    }

    private void save(Set<OrganizationEntity> entities) {
        organizationRepository.saveAll(entities);
    }
}

// Дополнительный репозиторий
@Repository
public interface OrganizationRepository extends JpaRepository<OrganizationEntity, UUID> {
}