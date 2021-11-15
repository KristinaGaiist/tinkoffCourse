package tinkoff.unit11.controller.contracts;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import tinkoff.unit11.entity.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CurrentUserDto {

    private String firstName;
    private String lastName;
    private String username;
    private Role role;
}
