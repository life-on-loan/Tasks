// TODO: сделать ревью, найти проблемы

@RequiredArgsConstructor
@RestController("/resize/v1")
public class Controller {

    private final CachedPhotosService cachedPhotosService;

    @GetMapping("/resized-photo/{photo-id}")
    public PhotoDTO getResizedPhoto(@PathVariable("photo-id") String photoId) {
        return cachedPhotosService.iconifiedPhoto(photoId);
    }
}

@Component
@RequiredArgsConstructor
public class CachedPhotosService {
    private static final String RESIZED_PHOTO_CACHE_NAME = "RESIZED_PHOTO_CACHE_NAME";

    private final PhotoRepository photoRepository;
    private final PhotoValidationService photoValidationService;
    private final PhotoOperations photoOperations;

    @Cacheable(cacheNames = RESIZED_PHOTO_CACHE_NAME)
    public PhotoDTO resizedPhoto(String photoId, int width, int height) {
        photoValidationService.validateSize(width, height);

        Photo photo = photoRepository.findById(photoId);

        PhotoDTO photoDto = ConversionUtils.convert(photo);
        var resizedPhoto = photoOperations.resize(photoDto, width, height);

        return resizedPhoto;
    }

    public PhotoDTO iconifiedPhoto(String photoId) {
        return resizedPhoto(photoId, 100, 100);
    }
}