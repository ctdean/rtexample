# ragtime-example

An example app running the ragtime migrations from an uberjar.

The example works with postgres and assumes the PG is installed and
running.

## Usage

Build the uberjar, create the database, and then run the migrations
from the custom class.

    lein uberjar
    make init
    (cd target; java -cp rtexample-0.1.0-standalone.jar rtexample.sqlmigrate migrate)

## Author

Chris Dean <ctdean@sokitomi.com>
