import java.util.Objects;

class Person {
    String firstName;
    String lastName;
    Integer age;

    @Override
    public boolean equals(Object o) {
        // 1. Проверка ссылки
        if (this == o) return true;

        // 2. Проверка типа и null
        if (o == null || getClass() != o.getClass()) return false;

        // 3. Приведение типа
        Person person = (Person) o;

        // 4. Сравнение полей с учетом null
        return Objects.equals(age, person.age) &&
                Objects.equals(firstName, person.firstName) &&
                Objects.equals(lastName, person.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, age);
    }
}