import java.util.List;

public class ProjectAnalysis {
    public static void main(String[] args) {
        List<Project> projects = List.of(
                new Project("WebApp", List.of(
                        new Task("Дизайн", true, 40),
                        new Task("Frontend", false, 60),
                        new Task("Бэкенд", false, 80)
                )),
                new Project("Мобильное приложение", List.of(
                        new Task("Прототип", true, 30),
                        new Task("UI", false, 50),
                        new Task("Бэкенд", false, 80),
                        new Task("Тестирование", false, 40)
                )),
                new Project("Аналитика", List.of(
                        new Task("Сбор данных", true, 20),
                        new Task("Frontend", false, 60)
                ))
        );

        // 1. Получаем все уникальные незавершенные задачи
        List<Task> incompleteTasks = projects.stream()
                .flatMap(project -> project.tasks().stream())
                .filter(task -> !task.completed())
                .distinct()
                .toList();

        System.out.println("Незавершенные задачи:");
        incompleteTasks.forEach(task ->
                System.out.println("  - " + task.name() + " (" + task.hours() + "ч)"));

        // 2. Сумма трудозатрат по незавершенным задачам
        int totalHours = incompleteTasks.stream()
                .mapToInt(Task::hours)
                .sum();
        System.out.println("\nОбщие трудозатраты: " + totalHours + "ч");

        // 3. Самая объемная незавершенная задача
        String largestTask = incompleteTasks.stream()
                .max((t1, t2) -> Integer.compare(t1.hours(), t2.hours()))
                .map(Task::name)
                .orElse("Нет задач");
        System.out.println("Самая объемная задача: " + largestTask);
    }
}

record Task(String name, boolean completed, int hours) {}
record Project(String name, List<Task> tasks) {}
