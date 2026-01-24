record Task(String name, boolean completed, int hours) {}
record Project(String name, List<Task> tasks) {}

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
                new Task("Frontend", false, 60)  // Задача с таким же названием
        ))
);

// 1. Получить список всех уникальных незавершенных задач из всех проектов
// 2. Для этих задач вычислить общую сумму трудозатрат
// 3. Найти название самой объемной незавершенной задачи
