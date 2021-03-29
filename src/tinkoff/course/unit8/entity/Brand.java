package unit8.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "brands")
@NoArgsConstructor
@Getter
@Setter
public class Brand implements Serializable {

    @Id
    @SequenceGenerator(name = "brands_seq", sequenceName = "brands_seq")
    @GeneratedValue(generator = "brands_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "brand", fetch = FetchType.EAGER)
    @JsonManagedReference
    private Collection<Model> models;

    @Override
    public String toString() {
        return name;
    }
}
