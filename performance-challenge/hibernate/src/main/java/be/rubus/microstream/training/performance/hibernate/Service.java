package be.rubus.microstream.training.performance.hibernate;

import be.rubus.microstream.training.performance.QueryInformation;
import be.rubus.microstream.training.performance.StopWatch;
import be.rubus.microstream.training.performance.hibernate.domain.CountryEntity;
import be.rubus.microstream.training.performance.hibernate.domain.PurchaseEntity;
import be.rubus.microstream.training.performance.utils.DurationUtil;
import org.hibernate.Session;
import org.hibernate.query.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;

public class Service {

    private final Session session;

    public Service(Session session) {
        this.session = session;
    }

    public QueryInformation<PurchaseEntity> purchasesOfForeigners(String countryCode, int year) {

        CountryEntity country = readCountry(countryCode);

        String sql = "select p from PurchaseEntity p " +
                "  JOIN FETCH p.shop s JOIN FETCH s.address ad JOIN FETCH ad.city c JOIN FETCH c.state ss JOIN FETCH ss.country csc " +
                "  JOIN FETCH p.customer pc JOIN FETCH pc.address cad JOIN FETCH cad.city cc JOIN FETCH cc.state cs JOIN FETCH cs.country ccc " +
                "  JOIN FETCH p.items pi JOIN FETCH pi.book b  " +
                "  where p.customer.address.city <> p.shop.address.city " +
                "  and p.shop.address.city.state.country.id = :countryId " +
                "  and EXTRACT(YEAR FROM p.timestamp) = :year";


        StopWatch stopWatch = StopWatch.start();

        Query<PurchaseEntity> query = session.createQuery(sql, PurchaseEntity.class);
        query.setParameter("countryId", country.getId());
        query.setParameter("year", year);

        List<PurchaseEntity> results = query.getResultList();

        long elapsed = stopWatch.stop();

        purchaseOfForeignersHotRun(country, year, sql);
        return new QueryInformation<>(results, elapsed);
    }

    private void purchaseOfForeignersHotRun(CountryEntity country, int year, String sql) {

        StopWatch stopWatch = StopWatch.start();

        Query<PurchaseEntity> query = session.createQuery(sql, PurchaseEntity.class);
        query.setParameter("countryId", country.getId());
        query.setParameter("year", year);

        List<PurchaseEntity> results = query.getResultList();

        DurationUtil.printDuration("Hot Run Query Execution", stopWatch.stop());

    }

    private CountryEntity readCountry(String countryCode) {
        Query<CountryEntity> query = session.createQuery("SELECT C FROM CountryEntity C WHERE C.code = :countryCode ", CountryEntity.class);
        query.setParameter("countryCode", countryCode);
        List<CountryEntity> countries = query.getResultList();

        if (countries.isEmpty()) {
            throw new NoSuchElementException("CountryCode " + countryCode);
        }

        // This method is just to get some countries, but we should not influence the Session cache.
        countries.forEach(session::evict);

        return countries.get(0);  // If we have something, it should only be 1.

    }

}
