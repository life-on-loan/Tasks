private volatile int counter;

// Метод может выполняться конкурентно несколькими потоками одновременно
public void doGet() {
    counter++;
}

public int getCounter() {
    return counter;
}

// TODO: Всё ли хорошо? Если есть проблемы, то как их решить?