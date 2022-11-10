# Performance demo

Based on the Performance White Paper code where query execution (read) is compared between MicroStream and database based frameworks.  See  https://github.com/rdebusscher/performance-whitepaper.


# generation of the test data

The data is generated in 2 steps

- Create test data with the help of JavaFaker and store it using MicroStream framework
- Read the MicroStream data and upload them into the database tables.

Have a look at the following programs within the _generator_ module.

- `GenerateData` which generates the data in the MicroStream format.
- Make sure a PostgreSQL database is running so that the required tables can be created.  The connection details can be changed within the _hibernate.cfg.xml_ file within _Hibernate_ module if needed.
You can run it when you have a Docker Client available with the following command
- the Docker container
```
docker run --name performance -e POSTGRES_PASSWORD=mysecretpassword -d -p 5432:5432 postgres
```
- `SchemaCreationDatabase` which generates the tables based on the SQL script.
- `UploadIntoData` which uploads the data into the database.

# Testing

The following query is performed 

For the Shops in Country X for year Y, get the list of all purchases that are done by customers that doesn't live in the same city as the shop.

Make sure you have all info of Customers (address, city, state, and country) and for each purchase the book data including the author.

The Java class `PurchaseOfForeigners` within the _microstream_ and _hibernate_ modules perform the query.

# Comparison

First execution (cold status). Values are in milliseconds

|              | MicroStream | Hibernate |
|--------------|-------------|-----------|
| Load/connect | 1636        | 1546      |
| Query        | 77,8        | 727,9     |

Second execution (hot status). Values are in milliseconds

|              | MicroStream | Hibernate |
|--------------|-------------|-----------|
| Query        | 0,5         | 87,8      |