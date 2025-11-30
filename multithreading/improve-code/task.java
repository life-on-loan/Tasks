private volatile int counter;

// Метод может выполняться конкурентно несколькими потоками одновременно
public void doGet(HttpServletRequest req) {
    counter++;
}

public int getCounter() {
    return counter;
}

// TODO: Всё ли хорошо? Если нет, то какие проблемы могут быть? Если есть проблемы, то как их решить?