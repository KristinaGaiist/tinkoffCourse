package tinkoff.unit11.controller.contracts;

import tinkoff.unit11.entity.Role;

public class AuthDto {

    private String message;
    private Role role;

    public AuthDto(String message, Role role) {
        this.message = message;
        this.role = role;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return String.format("HelloWorldBean [message=%s]", message);
    }
}
