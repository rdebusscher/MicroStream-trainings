package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "COUNTRY")
public class CountryEntity extends NamedWithCodeEntity {
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "country")
    private List<StateEntity> states = new ArrayList<>();

    public CountryEntity() {
        super();
    }

    public CountryEntity(String name, String code) {
        super(name, code);
    }

    public List<StateEntity> getStates() {
        return this.states;
    }

    public void setStates(
            List<StateEntity> states
    ) {
        this.states = states;
    }

}
