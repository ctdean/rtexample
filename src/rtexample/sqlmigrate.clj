(ns rtexample.sqlmigrate
  "Running a ragtime migration with SQL

   @ctdean"
  (:require
   [ragtime.jdbc :as jdbc]
   [ragtime.repl :as repl])
  (:gen-class))

;; The hardcoded config
(def config
  {:database (jdbc/sql-database
              {:connection-uri
               "jdbc:postgresql://localhost:5432/rtexample?user=postgres"})
   :migrations (jdbc/load-resources "migrations/sql")})

(defn -main
  "Run the migrations or rollbacks"
  [& args]
  (println "Running sql migrations")
  (case (first args)
    "migrate" (repl/migrate config)
    "rollback" (repl/rollback config)
    (println "usage: rtexample.sqlmigrate {migrate,rollback}")))
