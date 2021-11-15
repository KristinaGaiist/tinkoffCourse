package tinkoff.unit11.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import java.util.Collection;
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
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany(mappedBy = "brand", fetch = FetchType.LAZY)
    @JsonManagedReference
    private Collection<Model> models;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "author_id")
    private User author;

    @Override
    public String toString() {
        return name;
    }
}
