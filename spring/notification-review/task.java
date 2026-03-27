// Иннотех, ВТБ
@Component
public class StartService {

    private static final String CEO_USER_ID = "some_id";

    @Autowired
    private NotificationService notificationService;

    public StartService( NotificationService notificationService) {
        this.notificationService.notify(CEO_USER_ID, "Service successfully started");
    }
}

// TODO: объясни, что происходит?