/*
Критические проблемы:
1. Аннотация @RestController указана некорректно - путь нужно задавать через @RequestMapping:

@RestController
@RequestMapping("/resize/v1")
2. Нет обработки исключений:
        photoRepository.findById(photoId) может вернуть null
        photoValidationService.validateSize() может выбросить исключение
Нет обработки PhotoNotFoundException или других бизнес-исключений

3. Проблема с кэшированием в iconifiedPhoto():
Метод iconifiedPhoto вызывает resizedPhoto(), но кэширование работает только при прямом вызове resizedPhoto() с параметрами
4. Для iconifiedPhoto (100x100) не будет отдельного кэша
5. Нет валидации входных параметров в контроллере
6. Нет ограничений на размеры width и height в resizedPhoto()
Можно запросить огромные размеры и перегрузить сервер
7. Нет логирования важных операций:
        - Не логируются запросы на ресайз
        - Не логируются ошибки

Архитектурные проблемы:
8. Нарушение Single Responsibility Principle:
CachedPhotosService отвечает и за кэширование, и за бизнес-логику
9. Жестко закодированные константы:
        return resizedPhoto(photoId, 100, 100);
Размеры иконки должны быть конфигурируемыми
10. Поиск по строке сомнительный, почему ID String photoId строковый
*/

//Предлагаемые исправления:
@RestController
@RequestMapping("/resize/v1")
@RequiredArgsConstructor
@Slf4j
public class ResizeController {

    private final CachedPhotosService cachedPhotosService;

    @GetMapping("/resized-photo/{photo-id}")
    public ResponseEntity<PhotoDTO> getResizedPhoto(
            @PathVariable("photo-id") @NotBlank String photoId,
            @RequestParam(defaultValue = "100") @Min(1) @Max(5000) int width,
            @RequestParam(defaultValue = "100") @Min(1) @Max(5000) int height) {

        log.info("Resize request for photoId: {}, width: {}, height: {}", photoId, width, height);

        PhotoDTO result = cachedPhotosService.resizedPhoto(photoId, width, height);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/iconified-photo/{photo-id}")
    public ResponseEntity<PhotoDTO> getIconifiedPhoto(
            @PathVariable("photo-id") @NotBlank String photoId) {

        log.info("Icon request for photoId: {}", photoId);

        PhotoDTO result = cachedPhotosService.iconifiedPhoto(photoId);
        return ResponseEntity.ok(result);
    }

    @ExceptionHandler({PhotoNotFoundException.class})
    public ResponseEntity<ErrorResponse> handlePhotoNotFound(PhotoNotFoundException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(new ErrorResponse("Photo not found"));
    }
}
// Второй вариант
@Component
@RequiredArgsConstructor
@Slf4j
public class CachedPhotosService {
    private static final String RESIZED_PHOTO_CACHE_NAME = "resizedPhotos";
    private static final String ICONIFIED_PHOTO_CACHE_NAME = "iconifiedPhotos";

    @Value("${photo.icon.width:100}")
    private int iconWidth;

    @Value("${photo.icon.height:100}")
    private int iconHeight;

    private final PhotoRepository photoRepository;
    private final PhotoValidationService photoValidationService;
    private final PhotoOperations photoOperations;

    @Cacheable(cacheNames = RESIZED_PHOTO_CACHE_NAME,
            key = "{#photoId, #width, #height}",
            unless = "#result == null")
    public PhotoDTO resizedPhoto(String photoId, int width, int height) {
        log.debug("Resizing photo {} to {}x{}", photoId, width, height);

        photoValidationService.validateSize(width, height);

        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new PhotoNotFoundException(photoId));

        PhotoDTO photoDto = ConversionUtils.convert(photo);
        return photoOperations.resize(photoDto, width, height);
    }

    @Cacheable(cacheNames = ICONIFIED_PHOTO_CACHE_NAME,
            key = "#photoId",
            unless = "#result == null")
    public PhotoDTO iconifiedPhoto(String photoId) {
        log.debug("Creating icon for photo {}", photoId);
        return resizedPhoto(photoId, iconWidth, iconHeight);
    }
}