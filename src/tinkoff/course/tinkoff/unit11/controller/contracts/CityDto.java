package tinkoff.unit11.controller.contracts;

import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CityDto {

    private long id;
    @NotBlank
    private String name;
    private UserDto author;
}
