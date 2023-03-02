# MicroStream CRUD exercise

## Getting started

Compile and create application.

```
mvn clean package
```

Run the application

```
java -jar target/cars-microbundle.jar
```

See if test data is loaded (including company name)

```
curl localhost:8080/cars/hello
```

List the brands

```
curl localhost:8080/cars/brand
```

## Commands to test

Add a Brand

```
curl -X POST -v -H "Content-Type: application/json" --data '{"name":"vw"}' localhost:8080/cars/brand
```

List brands again, the new brand is added ?

```
curl localhost:8080/cars/brand
```

Update the Brand name

```
curl -X PUT -v -H "Content-Type: application/json" --data '{"name":"Volkswagen"}' localhost:8080/cars/brand/vw
```

List all cars

```
curl localhost:8080/cars/car
```

List cars by model name

```
curl localhost:8080/cars/car/byModel/Za
```

List cars by brand

```
curl localhost:8080/cars/car/byBrand/opel
```

Add car

```
curl -X POST -v -H "Content-Type: application/json" --data '{"model":"BMW 2 Active Tourer", "brand":{"name":"bmw"}, "price":149.9}' localhost:8080/cars/car
```
Get car by id

```
curl localhost:8080/cars/car/0
```

delete car

```
curl -X DELETE -v localhost:8080/cars/car/0
```
