// решение 1
public class Main {
    public static void main(String[] args) {
        // Пример: n монет
        long n = 1000;

        int cell = 0;
        long remainingCoins = n;
        long coinsOnCell = 1;

        // Пока хватает монет на текущую клетку
        while (remainingCoins >= coinsOnCell) {
            remainingCoins -= coinsOnCell;
            cell++;
            coinsOnCell *= 2; // на следующей клетке в 2 раза больше
        }

        System.out.println("Монеты закончатся на " + cell + " клетке");
        System.out.println("Останется монет: " + remainingCoins);
    }
}

// решение 2
public class Main {
    public static void main(String[] args) {
        // Пример: n монет
        long n = 1000;

        // Вызываем рекурсивный метод
        distributeCoins(n, 1, 1);
    }

    // Рекурсивный метод для распределения монет
    public static void distributeCoins(long remainingCoins, int cell, long coinsOnCell) {
        // Базовый случай: если не хватает монет на текущую клетку
        if (remainingCoins < coinsOnCell) {
            System.out.println("Монеты закончатся на " + (cell - 1) + " клетке");
            System.out.println("Останется монет: " + remainingCoins);
            return;
        }

        // Рекурсивный случай: кладем монеты на текущую клетку и переходим к следующей
        distributeCoins(
                remainingCoins - coinsOnCell,  // остаток монет после текущей клетки
                cell + 1,                      // следующая клетка
                coinsOnCell * 2                 // монет на следующей клетке в 2 раза больше
        );
    }
}