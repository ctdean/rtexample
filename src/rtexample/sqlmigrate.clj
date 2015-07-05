(ns rtexample.sqlmigrate
  "@ctdean"
  (:require
   [ragtime.jdbc :as jdbc]
   [ragtime.repl :as repl])
  (:gen-class))

(def config
  {:database (jdbc/sql-database
              {:connection-uri
               "jdbc:postgresql://localhost:5432/rtexample?user=postgres"})
   :migrations (jdbc/load-resources "migrations")})

(defn -main [& args]
  (println "Running migrations")
  (case (first args)
    "migrate" (repl/migrate config)
    "rollback" (repl/rollback config)
    (println "usage: rtexample.sqlmigrate {migrate,rollback}")))
