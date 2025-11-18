@Getter
class Person {
    private String name;
    private Integer age;
    boolean active;

    public Person(String name, Integer age, boolean active) {
        this.name = name;
        this.age = age;
        this.active = active;
    }

// TODO: Есть коллекция персон, необходимо сгруппировать активные персоны по имени, чей возраст больше 25 лет
var result = personList.stream()...
