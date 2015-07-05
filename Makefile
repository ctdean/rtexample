#
# Makefile - utility commands
# 
# Chris Dean

# Create the db
init:
	createuser -s postgres -h localhost || exit 0
	createdb -Upostgres -h localhost rtexample

# Drop the db
drop:
	dropdb -Upostgres -h localhost rtexample      || exit 0

migrate:
	lein run -m rtexample.sqlmigrate migrate

# Nuke the existing databases and recreate
rebuild: drop init migrate

