import java.util.Collections;
import java.util.Map;

public final class MyImmutableObject {
    private final Map<MyObject, Integer> objToInt;
    private final MyObject myObject;
    private final String value;

    // Конструктор с защитным копированием
    public MyImmutableObject(Map<MyObject, Integer> map, MyObject myObject, String value) {
        // Глубокое копирование Map
        this.objToInt = map != null ?
                Collections.unmodifiableMap(new HashMap<>(map)) :
                Collections.emptyMap();

        // Создание копии MyObject (предполагая, что у него есть конструктор копирования)
        this.myObject = myObject != null ? new MyObject(myObject) : null;

        // String уже неизменяем, можно просто присвоить
        this.value = value;
    }

    // Геттеры возвращают неизменяемые представления или копии
    public Map<MyObject, Integer> getMap() {
        // Возвращаем неизменяемую копию
        return Collections.unmodifiableMap(new HashMap<>(objToInt));
    }

    public MyObject getMyObject() {
        // Возвращаем копию объекта
        return myObject != null ? new MyObject(myObject) : null;
    }

    public String getValue() {
        return value; // String неизменяем, можно возвращать как есть
    }

    // "Сеттеры" возвращают новые объекты с измененными значениями
    public MyImmutableObject withMap(Map<MyObject, Integer> newMap) {
        return new MyImmutableObject(newMap, this.myObject, this.value);
    }

    public MyImmutableObject withMyObject(MyObject newMyObject) {
        return new MyImmutableObject(this.objToInt, newMyObject, this.value);
    }

    public MyImmutableObject withValue(String newValue) {
        return new MyImmutableObject(this.objToInt, this.myObject, newValue);
    }

    // Дополнительные полезные "сеттеры"
    public MyImmutableObject withMapEntry(MyObject key, Integer value) {
        Map<MyObject, Integer> newMap = new HashMap<>(this.objToInt);
        newMap.put(key, value);
        return new MyImmutableObject(newMap, this.myObject, this.value);
    }

    public MyImmutableObject withoutMapEntry(MyObject key) {
        Map<MyObject, Integer> newMap = new HashMap<>(this.objToInt);
        newMap.remove(key);
        return new MyImmutableObject(newMap, this.myObject, this.value);
    }
}