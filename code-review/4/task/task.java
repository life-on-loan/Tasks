public class TestService{
    @Transactional
    public void first() {
        second();
    }

    @Transactional
    protected void second() {
        //todo
    }
}
// TODO: Сколько транзакций будет создано?