var result = personList.stream()
        .filter(person -> person.isActive() && person.getAge() != null && person.getAge() > 25)
        .collect(Collectors.groupingBy(
                Person::getName,
                Collectors.toList()
        ));