package unit5.task3.entity;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Actor {

    private final String firstName;
    private final String lastName;
    private final Date birthDay;

    public Actor(String firstName, String lastName, Date birthDay) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDay = birthDay;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public Date getBirthDay() {
        return birthDay;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + new SimpleDateFormat("yyyy-MM-dd").format(birthDay);
    }
}
