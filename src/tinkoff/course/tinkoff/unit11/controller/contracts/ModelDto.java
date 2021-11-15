package tinkoff.unit11.controller.contracts;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ModelDto {

    private long id;
    @NotBlank
    private String model;
    @NotNull
    private BrandDto brand;
    private UserDto author;
}
