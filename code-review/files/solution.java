import java.io.*;
import java.nio.file.*;
import java.sql.*;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicBoolean;
import javax.sql.DataSource;

public class EntityLoader {
    // 1. Заменяем небезопасный HashSet на потокобезопасный ConcurrentHashMap
    //    с AtomicBoolean для отслеживания состояния обработки пути
    private static final ConcurrentHashMap<String, AtomicBoolean> PROCESSING_PATHS = new ConcurrentHashMap<>();

    // 2. Выносим константы для конфигурируемости (вместо магических чисел)
    private static final int BATCH_SIZE = 100;           // Размер пакета для batch-обработки SQL
    private static final int MAX_LINES_PER_FILE = 150;   // Максимальное количество строк на файл

    private final String path;
    private final DataSource dataSource;  // 3. Используем DataSource вместо прямого DriverManager

    public EntityLoader(String path, DataSource dataSource) {
        // 4. Нормализуем путь для избежания дубликатов типа "/some/path" и "/some/path    "
        this.path = Paths.get(path).normalize().toString();
        this.dataSource = dataSource;
    }

    public void loadEntities() throws IOException, SQLException {
        // 5. Используем атомарную операцию putIfAbsent для избежания race condition
        AtomicBoolean processingFlag = PROCESSING_PATHS.putIfAbsent(path, new AtomicBoolean(true));

        // 6. Проверяем, не обрабатывается ли уже этот путь
        if (processingFlag != null && processingFlag.get()) {
            throw new IllegalStateException("Path " + path + " is already being processed");
        }

        try {
            // 7. Проверяем, что путь является директорией (ранняя валидация)
            Path directory = Paths.get(path);
            if (!Files.isDirectory(directory)) {
                throw new IllegalArgumentException("Path must be a directory: " + path);
            }

            // 8. Используем Stream API для обработки файлов (можно легко распараллелить)
            try (Stream<Path> filesStream = Files.list(directory)) {
                filesStream
                        .filter(Files::isRegularFile)  // 9. Фильтруем только обычные файлы
                        .forEach(this::processFile);    // 10. Обрабатываем каждый файл
            }
        } finally {
            // 11. Гарантируем удаление пути из кэша даже при исключении
            PROCESSING_PATHS.remove(path);
        }
    }

    private void processFile(Path file) {
        // 12. Используем try-with-resources для автоматического закрытия всех ресурсов
        try (BufferedReader reader = Files.newBufferedReader(file);
             Connection conn = dataSource.getConnection();      // 13. Берем соединение из пула
             PreparedStatement stmt = conn.prepareStatement(createInsertStatement())) {

            // 14. Отключаем auto-commit для пакетной обработки
            conn.setAutoCommit(false);

            String line;
            int lineCount = 0;
            int batchCount = 0;

            // 15. Читаем файл с ограничением по максимальному количеству строк
            while ((line = reader.readLine()) != null && lineCount < MAX_LINES_PER_FILE) {
                // 16. Используем PreparedStatement для защиты от SQL-инъекций
                prepareStatement(stmt, line);
                stmt.addBatch();  // 17. Добавляем в пакет
                lineCount++;
                batchCount++;

                // 18. Выполняем пакет при достижении размера BATCH_SIZE
                if (batchCount >= BATCH_SIZE) {
                    stmt.executeBatch();
                    conn.commit();  // 19. Фиксируем транзакцию
                    batchCount = 0;
                }
            }

            // 20. Выполняем оставшиеся команды в пакете
            if (batchCount > 0) {
                stmt.executeBatch();
                conn.commit();
            }

        } catch (IOException | SQLException e) {
            // 21. Пробрасываем исключение с информацией о файле для удобства отладки
            throw new RuntimeException("Failed to process file: " + file, e);
        }
    }

    private String createInsertStatement() {
        // 22. Используем параметризованный запрос
        return "INSERT INTO entities (data) VALUES (?)";
    }

    private void prepareStatement(PreparedStatement stmt, String line) throws SQLException {
        // 23. Устанавливаем параметры безопасным способом
        stmt.setString(1, line);
    }

    public static void main(String[] args) {
        DataSource dataSource = createDataSource(); // Инициализация DataSource

        try {
            EntityLoader el = new EntityLoader("/some/path", dataSource);
            el.loadEntities();
        } catch (IOException | SQLException e) {
            System.err.println("Error loading entities: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static DataSource createDataSource() {
        // 24. Пример создания DataSource (реализация зависит от используемого пула соединений)
        // Например, для HikariCP:
        // HikariConfig config = new HikariConfig();
        // config.setJdbcUrl("jdbc:mysql://localhost:3306/db");
        // config.setUsername("user");
        // config.setPassword("password");
        // return new HikariDataSource(config);
        return null; // Заглушка для примера
    }
}