public class MyImmutableObject {
    private Map<MyObject, Integer> objToInt;
    private MyObject myObject;
    private String value;

    public MyImmutableObject(Map<MyObject, Integer> map, MyObject myObject, String value) {
        this.objToInt = ?;
        this.myObject = ?;
        this.value = ?;
    }

    public Map<MyObject, Integer> getMap() {
        return ?;
    }

    public MyObject getMyObject() {
        return ?;
    }

    public String getValue() {
        return ?;
    }

}


// TODO: Сделать класс неизменяемым и добавить сеттеры (допускается псевдокод)