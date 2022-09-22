package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.*;

@Entity
@Table(name = "CITY")
public class CityEntity extends NamedEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STATE_ID")
    private StateEntity state;

    public CityEntity() {
        super();
    }

    public CityEntity(Long id, String name, StateEntity state) {
        super(name);
        this.state = state;
        setId(id);
    }

    public StateEntity getState() {
        return state;
    }

    public void setState(StateEntity state) {
        this.state = state;
    }

}
