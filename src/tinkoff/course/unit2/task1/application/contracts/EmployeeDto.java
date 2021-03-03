package unit2.task1.application.contracts;

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
}
