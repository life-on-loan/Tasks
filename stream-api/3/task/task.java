List<String> list = new ArrayList<>();
list.add("milk");
list.add("bread");
list.add("sausage");
Stream<String> stream = list.stream();
list.add("eggs");
stream.forEach(System.out::println);

// TODO: Что будет выведено?