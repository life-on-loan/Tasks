// В Spring Boot приложении есть сервисный класс с двумя методами:

@Service
public class EmployeeService {

    @Transactional
    public void updateSalary() {
        // 1: UPDATE employee SET salary = 50000 WHERE department = 'IT'

        try {
            validateDepartment();
        } catch (Exception e) {
            // логируем, не пробрасываем дальше
        }
    }

    propagation = Propagation.REQUIRES_NEW
    public void validateDepartment() {
        // 2: UPDATE employee SET salary = 30000 WHERE department = 'SALES'
        throw new IllegalArgumentException("Invalid department");
    }
}
// Метод updateSalary() вызывается из контроллера. После выполнения обоих методов в базе данных окажутся изменения:
//
// Варианты ответов:
// 1) Только первое обновление (IT → 50000)
// 2) Только второе обновление (SALES → 30000)
// 3) Оба обновления
// 4) Ни одного обновления