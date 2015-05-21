
tgcase
======

Provides a restful service to access remote product information combined with a locally stored price, along with another service to update the price.




Initial Setup
-------------

Start the bundled cassandra cluster with:

        mvn cassandra:run

connect via cqlsh and run the following to create the keyspace and table:

        create keyspace tgcase WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };
        USE tgcase;
        CREATE TABLE localPrice (   id text PRIMARY KEY, price decimal );


Build
-----

This can be built and run via maven.

To build the project and run the integration tests.

        mvn verify

Running Locally
---------------

        mvn cassandra:start jetty:run 


Use
---

a http put to /tgcase/products/{id}?price=123.34

will set the price of the product id to 123.34

a http get to /tgcase/products{id}

will return a json message combing the remote name and local price if they are available in this format:

    {"id":"13860429","name":"SPONGEBOB SQUAREPANTS: SPONGEBOB FROZEN FACE-OFF Dvd Video","price":12.89}
