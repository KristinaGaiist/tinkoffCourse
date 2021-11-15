package tinkoff.unit11.controller.contracts;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CarDto {

    private Long id;
    @NotBlank
    private String stateNumber;
    @NotNull
    private ModelDto model;
    private UserDto author;
}
