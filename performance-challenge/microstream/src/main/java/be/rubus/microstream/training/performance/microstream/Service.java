package be.rubus.microstream.training.performance.microstream;

import be.rubus.microstream.training.performance.QueryInformation;
import be.rubus.microstream.training.performance.StopWatch;
import be.rubus.microstream.training.performance.microstream.database.Data;
import be.rubus.microstream.training.performance.microstream.database.model.Purchase;
import be.rubus.microstream.training.performance.model.Country;
import be.rubus.microstream.training.performance.utils.DurationUtil;

import java.util.List;
import java.util.Optional;

public class Service {

    private final Data data;

    public Service(Data data) {
        this.data = data;
    }


    public QueryInformation<Purchase> purchasesOfForeigners(String countryCode, int year) {

        StopWatch stopWatch = StopWatch.start();

        Country country = countryOf(countryCode);
        List<Purchase> results = data.purchases().purchasesOfForeigners(year, country);

        long elapsed = stopWatch.stop();

        purchaseOfForeignersHotRun(country, year);

        return new QueryInformation<>(results, elapsed);
    }

    private void purchaseOfForeignersHotRun(Country country, int year) {
        StopWatch stopWatch = StopWatch.start();

        data.purchases().purchasesOfForeigners(year, country);

        DurationUtil.printDuration("Hot Run Query Execution", stopWatch.stop());

    }

    private Country countryOf(String code) {
        Optional<Country> country = data.shops().compute(shops ->
                shops.map(s -> s.address().city().state().country())
                        .filter(c -> c.code().equals(code))
                        .findAny()
        );
        return country.orElseThrow();
    }
}
