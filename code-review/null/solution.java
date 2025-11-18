var result = Optional.ofNullable(user)
        .map(user -> user.getType())  // Если user.getType() == null
        .map(type -> type.getName())  // Этот map не выполнится