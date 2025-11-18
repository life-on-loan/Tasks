var result = Optional.ofNullable(user)
        .map(user -> user.getType())
        .map(type -> type.getName())

// TODO: Что произойдет в этом коде, если user.getType() == null ?