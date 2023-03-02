package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "STATE")
public class StateEntity extends NamedEntity {
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "COUNTRY_ID")
    private CountryEntity country;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "state")
    private List<CityEntity> cities = new ArrayList<>();

    public StateEntity() {
        super();
    }

    public StateEntity(Long id, String name, CountryEntity country) {
        super(name);
        this.country = country;
        setId(id);
    }

    public CountryEntity getCountry() {
        return country;
    }

    public void setCountry(CountryEntity country) {
        this.country = country;
    }

    public List<CityEntity> getCities() {
        return cities;
    }

    public void setCities(List<CityEntity> cities) {
        this.cities = cities;
    }


}
