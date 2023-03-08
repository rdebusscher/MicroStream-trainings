package be.rubus.microstream.training.quickstart.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringJoiner;

public class DataRoot {

    private final List<Person> persons = new ArrayList<>();

    private Date timeStamp;

    public List<Person> getPersons() {
        return persons;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", DataRoot.class.getSimpleName() + "[", "]")
                .add("persons=" + persons)
                .add("timeStamp=" + timeStamp)
                .toString();
    }
}
