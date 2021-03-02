package unit2.task1.application.contracts;

import java.util.Objects;

public class EmployeeDto {

    private final String firstName;
    private final String lastName;

    public EmployeeDto(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        EmployeeDto that = (EmployeeDto) o;
        return Objects.equals(firstName, that.firstName) &&
            Objects.equals(lastName, that.lastName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName);
    }
}
