List<String> list = new ArrayList<>();
list.add("milk");
list.add("bread");
list.add("sausage");

// Создается Stream, но он еще не обрабатывает данные
Stream<String> stream = list.stream();

// Добавляем еще один элемент В создание Stream'а
list.add("eggs");

// Только сейчас Stream начинает обрабатывать данные
stream.forEach(System.out::println); // Видит все 4 элемента

//milk
//bread
//sausage
//eggs