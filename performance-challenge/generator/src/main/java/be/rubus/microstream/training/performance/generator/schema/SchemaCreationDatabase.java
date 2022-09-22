package be.rubus.microstream.training.performance.generator.schema;


import be.rubus.microstream.training.performance.hibernate.domain.*;
import be.rubus.microstream.training.performance.hibernate.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SchemaCreationDatabase {

    public static void main(String[] args) throws IOException {

        Map<String, String> settings = new HashMap<>();
        settings.put("connection.driver_class", "org.postgresql.Driver");

        settings.put("dialect", "org.hibernate.dialect.PostgreSQL9Dialect");
        settings.put("hibernate.connection.url", "jdbc:postgresql://localhost:5432/postgres");

        settings.put("hibernate.connection.username", "postgres");
        settings.put("hibernate.connection.password", "mysecretpassword");
        settings.put("hibernate.show_sql", "true");
        settings.put("hibernate.format_sql", "true");


        ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder().applySettings(settings).build();

        MetadataSources metadata =
                new MetadataSources(serviceRegistry);

        metadata.addAnnotatedClass(CustomerEntity.class);
        metadata.addAnnotatedClass(NamedWithAddressEntity.class);
        metadata.addAnnotatedClass(NamedEntity.class);
        metadata.addAnnotatedClass(BaseEntity.class);
        metadata.addAnnotatedClass(AddressEntity.class);
        metadata.addAnnotatedClass(CityEntity.class);
        metadata.addAnnotatedClass(StateEntity.class);
        metadata.addAnnotatedClass(CountryEntity.class);
        metadata.addAnnotatedClass(PurchaseEntity.class);
        metadata.addAnnotatedClass(PurchaseItemEntity.class);
        metadata.addAnnotatedClass(EmployeeEntity.class);
        metadata.addAnnotatedClass(ShopEntity.class);
        metadata.addAnnotatedClass(BookEntity.class);
        metadata.addAnnotatedClass(AuthorEntity.class);
        metadata.addAnnotatedClass(GenreEntity.class);
        metadata.addAnnotatedClass(LanguageEntity.class);
        metadata.addAnnotatedClass(PublisherEntity.class);
        metadata.addAnnotatedClass(InventoryItemEntity.class);

        SqlFile sqlFile = new SqlFile("./create_database.sql");

        Session session = HibernateUtil.getSessionFactory().openSession();

        Transaction transaction = session.beginTransaction();
        for (String statement : sqlFile.getStatements()) {
            session.createNativeQuery(statement).executeUpdate();
        }

        transaction.commit();


    }
}
