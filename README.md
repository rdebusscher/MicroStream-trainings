# MicroStream Basic training

Repository with material for the Basic training.

Why MicroStream? Performance issues (due to latency because database is remotely) and 'debugging' and tuning of Hibernate queries make it not the fastest option to develop and at runtime. See directory `performance-challange`.

DataModel: Object Graph modelling is a bit different than relational database for a few aspects. A playground for data modelling can be found in the directory `objectModelExample`.

Configuration of MicroStream is one of the most important aspects. Some examples can be found in the `config` project.

- simple : Most minimal example.
- builder : Using the Configuration builder.
- external : Using external files like properties and XML files
- Foundation : Full option.

Templates contain a few minimalistic getting started with some frameworks (Payara Micro, Spring Boot, Micronaut and Quarkus) for a REST application that make use MicroStream. It is not using integrations, but just a JVM singleton is used to define StorageManager and Root. As we want to keep focussing on MicroStream itself and not the integration aspects. See directory `templates`.

The directory `CRUD` contains example of basic CRUD operations with MicroStream.  The Class `BookController` contains the skeleton, solution can be found in `SolutionController`.

An exercise on the basics (creating datamodel, configuration, and CRUD) is described in the `exercise.md` file. The solution is in the `exercise` directory.

The directory `transient` has an example of the effect of the keyword `transient` within MicroStream.

The directory `Java8Streams` has a crash course Java 8 Streams, the Query language for MicroStream.

The usage of Lazy loading can be demonstrated with the example in the directory `lazy`. You can use a tool like _Azul Mission Control_  to take and analyse heap dumps.

The project _autimatic_ in the Legacy Type Mapping Git hub repo, see https://github.com/rdebusscher/microstream-legacy-type-mapping/tree/main/automatic, can be used to demonstrate the working of the automatic Legacy Type Mapping functionality of MicroStream.

The directory `backup` contains an example of incremental backup and how to issue a full backup.
