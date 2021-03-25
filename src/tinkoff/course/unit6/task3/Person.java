package unit6.task3;

import java.util.UUID;

public class Person {

    private final UUID id;
    private String name;
    private int age;

    public Person(String name, int age) {
        this.name = name;
        this.age = age;
        this.id = UUID.randomUUID();
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
