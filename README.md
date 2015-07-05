# ragtime-example

An example app running ragtime migrations from an uberjar.

There are two examples, one for SQL (using postgres) and the other for
mongodb.

For SQL:

- We assume that PG is installed and running.
- The example migration is in `resources/migrations/sql/` where we
  create a new table named `FOO`.

For Mongo:

- We assume that Mongo is installed and running.
- The example migration is in `resources/migrations/mongo/` where we
  create a unique index on the `users` collection.
- Mongo migrations are just Clojure files that are executed and they
  are responsbile for connecting to the Mongo database.

## Usage

Build the uberjar, create the database, and then run the migrations
from the custom class.

For SQL:

    lein uberjar
    make init
    (cd target; java -cp rtexample-0.2.0-standalone.jar rtexample.sqlmigrate migrate)

For Mongo:

    lein uberjar
    (cd target; java -cp rtexample-0.2.0-standalone.jar rtexample.mongomigrate migrate)

## Author

Chris Dean <ctdean@sokitomi.com>
