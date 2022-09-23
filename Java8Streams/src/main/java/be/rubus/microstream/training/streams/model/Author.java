package be.rubus.microstream.training.streams.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class Author {
    private Long id;
    @JsonProperty("first_name")
    private String firstname;

    @JsonProperty("last_name")
    private String lastname;
    private String email;
    private String gender;

    private List<Address> addresses = new ArrayList<>();

    public Author() {
        super();
    }

    public Author(Long id, String firstname, String lastname, String email, String gender) {
        super();
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.gender = gender;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<Address> getAddresses() {
        return addresses;
    }

    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Author.class.getSimpleName() + "   [", "]\n")
                .add("id=" + id)
                .add("firstname='" + firstname + "'")
                .add("lastname='" + lastname + "'")
                .add("email='" + email + "'")
                .add("gender='" + gender + "'")
                .add("\naddresses=" + addresses)
                .toString();
    }
}
