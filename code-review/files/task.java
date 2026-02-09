// Задача из Х5 Group (Пятерочка): Middle-Senior. Сделать код-ревью
public class EntityLoader {

    public static volatile HashSet<String> CACHE = new HashSet<>();

    public Object syncronizer;
    public String path;

    public EntityLoader(String path) {
        syncronizer = new Object();
        this.path = path;
    }

    public void loadEntities() throws Exception {
        if (CACHE.contains(path)) throw new Exception("Path already processed");
        CACHE.add(path);

        File file = new File(path);
        File[] files = file.listFiles();
        process(files);
    }

    private void process(File[] files) {
        synchronized (syncronizer) {
            for (File file : files) {
                processFile(file);
            }
        }

    }

    private void processFile(File file) {

        int maxReadLines = 150;
        BufferedReader br = null;
        String line = null;
        int readed = 0;

        try {
            br = new BufferedReader(new FileReader(file));
            while ((line = br.readLine()) != null && readed < maxReadLines) {
                store(line);
                readed++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void store(String line) {
        try {
            Connection conn = DriverManager.getConnection("...");
            Statement st = conn.createStatement();
            st.executeUpdate(createStatementString(line));
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private String createStatementString(String line) {
        //Method create real INSERT statement.
        //Skipped for readability
        return "...";
    }

}

public static void main(String[] args) {
    // вызов
    EntityLoader el = new EntityLoader("/some/path    ");
    el.loadEntities();
}
