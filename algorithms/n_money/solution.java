public class Main {
    public static void main(String[] args) {
        // Пример: n монет
        long n = 1000;

        // Находим результат
        Result result = calculateRemainingCoins(n);

        // Выводим результат
        System.out.println("Падишах сможет заполнить до клетки: " + result.cellNumber);
        System.out.println("Останется монет в казне: " + result.remainingCoins);
    }

    // Вспомогательный класс для возврата результата
    static class Result {
        long cellNumber;
        long remainingCoins;

        Result(long cellNumber, long remainingCoins) {
            this.cellNumber = cellNumber;
            this.remainingCoins = remainingCoins;
        }
    }

    public static Result calculateRemainingCoins(long totalCoins) {
        // Если у падишаха 0 или меньше монет, он не сможет положить ни на одну клетку
        if (totalCoins <= 0) {
            return new Result(0, totalCoins);
        }

        long cell = 0;          // Номер клетки (начинаем с 0, так как еще не положили)
        long coinsOnCell = 1;   // Количество монет на текущей клетке (начинаем с 1)
        long remainingCoins = totalCoins;

        // Пока можем положить монеты на следующую клетку
        while (remainingCoins >= coinsOnCell) {
            // Кладем монеты на текущую клетку
            remainingCoins -= coinsOnCell;
            cell++;

            // Рассчитываем количество монет для следующей клетки
            // Проверяем на переполнение (2^63 может превысить Long.MAX_VALUE)
            if (coinsOnCell > Long.MAX_VALUE / 2) {
                // Если следующее значение вызовет переполнение,
                // и у нас еще есть монеты, то останавливаемся
                if (remainingCoins > 0) {
                    // Переходим на следующую клетку, но монеты уже не кладем
                    cell++;
                }
                break;
            }

            coinsOnCell *= 2; // Удваиваем для следующей клетки
        }

        return new Result(cell, remainingCoins);
    }
}