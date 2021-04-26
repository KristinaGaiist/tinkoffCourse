package unit6.task1;

import java.util.Map;

public class Main {

    public static void main(String... args) {
        PropertyRepository propertyRepository = new PropertyRepository("property");
        Map<String, String> properties = propertyRepository.getProperties();
        properties.forEach((k, v) -> System.out.println(k + " " + v));

        System.out.println("Если в Map добавить элемент с ключом, который уже присутствует, то значение будет "
                               + "перезаписано. Пример: ");
        properties.put("userName", "Kristina");
        properties.forEach((k, v) -> System.out.println(k + " " + v));
    }
}
