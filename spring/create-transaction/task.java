import java.util.*;
import java.lang.*;
import java.io.*;

@Service
class SomeService {
    @Transactional
    public void execute () {
        // ...
        save();
        // ...
    }

    private void save() {
        // ...
    }
}

// Будет ли создана транзакция для метода save?