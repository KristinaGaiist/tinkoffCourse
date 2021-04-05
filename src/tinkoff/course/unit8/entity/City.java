package unit8.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cities")
@NoArgsConstructor
@Getter
@Setter
public class City {

    @Id
    @SequenceGenerator(name = "cities_seq", sequenceName = "cities_seq", allocationSize = 1)
    @GeneratedValue(generator = "cities_seq", strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
}
