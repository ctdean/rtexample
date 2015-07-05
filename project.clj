(defproject rtexample "0.1.0"
  :description "A simple Ragtime example"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.postgresql/postgresql "9.3-1102-jdbc41"]
                 [ragtime "0.4.1"]
                 ]
  :aot [rtexample.sqlmigrate])
