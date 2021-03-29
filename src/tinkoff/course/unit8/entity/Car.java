package unit8.entity;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cars")
@NoArgsConstructor
@Getter
@Setter
public class Car implements Serializable {

    @Id
    @SequenceGenerator(name = "cars_seq", sequenceName = "cars_seq")
    @GeneratedValue(generator = "cars_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "state_number")
    private String stateNumber;

    @ManyToOne(optional = false, cascade = CascadeType.ALL)
    @JoinColumn(name = "model_id")
    private Model model;

    @ManyToMany(fetch = FetchType.EAGER, mappedBy = "cars")
    private Set<Customer> customers = new HashSet<>();

    @Override
    public String toString() {
        return stateNumber;
    }
}
