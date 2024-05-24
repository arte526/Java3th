import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class task1 {
    public static void main(String[] args) {
        // Створюємо список цілих чисел
        List<Integer> numbers = Arrays.asList(10, 24, 3, 56, 17, 89, 25);

        // Використовуємо лямбда-вираз для знаходження максимального значення
        Optional<Integer> max = numbers.stream().max(Integer::compareTo);

        // Виводимо максимальне значення, якщо воно існує
        if (max.isPresent()) {
            System.out.println("Максимальне значення: " + max.get());
        } else {
            System.out.println("Список порожній");
        }
    }
}