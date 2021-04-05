package unit8.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Collection;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "models")
@NoArgsConstructor
@Getter
@Setter
public class Model {

    @Id
    @SequenceGenerator(name = "models_seq", sequenceName = "models_seq", allocationSize = 1)
    @GeneratedValue(generator = "models_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String model;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "brand_id")
    private Brand brand;

    @OneToMany(mappedBy = "model", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<Car> cars;

    @Override
    public String toString() {
        return model;
    }
}
