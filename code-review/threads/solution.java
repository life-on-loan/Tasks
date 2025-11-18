public String update(String text) {
    text = "abc" + text;
    System.out.println(text);
    return text;  // Вернуть измененное значение
}

public synchronized void test() {
    String a = "123";
    a = update(a);  // Присвоить возвращаемое значение
    System.out.println(a);  // Теперь выведет "abc123"
}

abc123
123
abc123
123