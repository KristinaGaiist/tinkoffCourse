package unit11.entity;

import com.sun.istack.NotNull;
import javax.persistence.Access;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import javax.validation.Valid;

@Data
@Entity
@NoArgsConstructor
@Table(name = "brands")
public class Brand {

    @Id
    @SequenceGenerator(name = "brands_seq", sequenceName = "brands_seq", allocationSize = 1)
    @GeneratedValue(generator = "brands_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Valid
    @NotBlank(message = "Name is mandatory")
    private String name;
}
