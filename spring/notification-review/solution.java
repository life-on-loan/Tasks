// Происходит NPE

@Autowired
private NotificationService notificationService;

public StartService(NotificationService notificationService) {
    this.notificationService.notify(CEO_USER_ID, "Service successfully started");
    // На момент вызова конструктора field injection ещё не произошёл
}

//Почему это не работает
//Порядок инициализации Spring-бина:
// 1. Вызывается конструктор
// 2. Затем выполняются @Autowired поля
// 3. Затем @PostConstruct методы
//
//В конструкторе notificationService ещё null, но код пытается вызвать notify() на null → NullPointerException

// Вариант исправления:
@Component
public class StartService {

    private static final String CEO_USER_ID = "some_id";
    private final NotificationService notificationService;

    public StartService(NotificationService notificationService) {
        this.notificationService = notificationService;
        this.notificationService.notify(CEO_USER_ID, "Service successfully started");
    }
}