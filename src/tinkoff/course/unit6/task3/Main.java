package unit6.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

public class Main {

    public static void main(String... args) {
        List<Person> personList = new ArrayList<>();
        personList.add(new Person("Кирилл", 23));
        personList.add(new Person("Кирилл", 33));
        personList.add(new Person("Кирилл", 43));
        personList.add(new Person("Кирилл", 21));
        personList.add(new Person("Кирилл", 10));
        personList.add(new Person("Екатерина", 19));
        personList.add(new Person("Петр", 66));
        personList.add(new Person("Павел", 40));
        personList.add(new Person("Елизавета", 70));
        personList.add(new Person("Анастасия", 35));
        personList.add(new Person("Ангелина", 10));
        personList.add(new Person("Ульяна", 11));
        personList.add(new Person("Артем", 10));
        personList.add(new Person("Дмитрий", 10));

        int totalAge = personList.stream()
            .filter(person -> person.getName().equals("Кирилл"))
            .mapToInt(Person::getAge)
            .sum();
        System.out.println("1. Суммарный возраст для имени Кирилл: " + totalAge);

        Set<String> personNames = personList.stream()
            .map(Person::getName)
            .collect(Collectors.toSet());
        System.out.println("2. Set из имен: ");
        personNames.forEach(System.out::println);

        boolean isListHasPersonWithAgeMoreCurrent = personList.stream()
            .anyMatch(person -> person.getAge() > 50);
        System.out.println("3. Есть ли в списке человек, чей возраст больше 50: " + isListHasPersonWithAgeMoreCurrent);

        Map<UUID, String> personMap = personList.stream()
            .collect(Collectors.toMap(Person::getId, Person::getName));
        System.out.println("4. Map ключ - id, значение - имя: ");
        personMap.forEach((k, v) -> System.out.println(k + " " + v));

        Map<Integer, List<Person>> personMap2 = personList.stream()
            .collect(Collectors.groupingBy(Person::getAge));
        System.out.println("5. Map ключ - возраст, значение - объект у которого возраст равен ключу: ");
        personMap2.forEach((k, v) -> System.out.println(k + " " + v));
    }
}
