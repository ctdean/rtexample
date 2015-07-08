(ns rtexample.mongomigrate
  "Running a ragtime migration with Mongo

   @ctdean"
  (:require
   monger.ragtime                       ; Force load of Mongo ragtime/Migratable code
   [ragtime.jdbc :as jdbc]
   [ragtime.repl :as repl]
   [resauce.core :as resauce])
  (:import
   [java.io File])
  (:gen-class))

;;;
;;; A replication of the Ragtime jdbc code to support our mongo
;;; migrations.  Mongo migrations are just Clojure files that are
;;; executed by Ragtime.
;;;

(let [pattern (re-pattern (str "([^" File/separator "]*)" File/separator "?$"))]
  (defn- basename [file]
    (second (re-find pattern (str file)))))

(defn- remove-extension [file]
  (second (re-matches #"(.*)\.[^.]*" (str file))))

;; We are using the .mongoclj extension
(defn- mongoclj-file-parts [file]
  (rest (re-matches #"(.*?)\.(up|down)(?:\.(\d+))?\.mongoclj" (str file))))

(defn- gen-load-fn
  "Generate a function the loads the mongo migrations.  The migration
   is responsbile for connecting to the mongo instance"
  [urls]
  (fn [_]
    (doseq [u urls]
      (load-string (slurp u)))))

(defmethod jdbc/load-files ".mongoclj" [files]
  (for [[id files] (group-by (comp first mongoclj-file-parts) files)]
    (let [{:strs [up down]} (group-by (comp second mongoclj-file-parts) files)]
      {:id (basename id)
       :up (gen-load-fn up)
       :down (gen-load-fn down)})))

;; The hardcoded config
(def mongo (mg/connect-via-uri "mongodb://127.0.0.1/rtexample"))
(def config
  {:database (:db mongo)
   :migrations (jdbc/load-resources "migrations/mongo")})

(defn -main
  "Run the migrations or rollbacks"
  [& args]
  (println "Running mongo migrations")
  (case (first args)
    "migrate" (repl/migrate config)
    "rollback" (repl/rollback config (if-let [n (second args)]
                                       (str->int n)
                                       1))
    (println "usage: rtexample.mongomigrate {migrate,rollback}")))
