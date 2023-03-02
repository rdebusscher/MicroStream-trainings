package be.rubus.microstream.training.performance.hibernate.domain;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class NamedWithCodeEntity extends NamedEntity {
    @Column(name = "CODE")
    private String code;

    protected NamedWithCodeEntity() {
        super();
    }

    protected NamedWithCodeEntity(String name, String code) {
        super(name);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [" + this.getName() + " - " + this.code + "]";
    }

}
