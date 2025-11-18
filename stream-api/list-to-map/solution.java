Map<String, List<Employee>> newMap = employees.stream()
        .filter(employee -> employee != null && employee.getDepartment() != null && !employee.getDepartment().trim().isEmpty())
        .collect(Collectors.groupingBy(Employee::getDepartment));