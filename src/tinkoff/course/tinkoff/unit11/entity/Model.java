package tinkoff.unit11.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "models")
@NoArgsConstructor
@Data
public class Model {

    @Id
    @SequenceGenerator(name = "models_seq", sequenceName = "models_seq", allocationSize = 1)
    @GeneratedValue(generator = "models_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Valid
    @NotBlank(message = "Model is mandatory")
    private String model;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Override
    public String toString() {
        return model;
    }
}
