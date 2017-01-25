(ns clojure-rest.handler
  (:import com.mchange.v2.c3p0.ComboPooledDataSource)
  (:use compojure.core)
  (:use cheshire.core)
  (:use ring.util.response)
  (:require [compojure.handler :as handler]
            [ring.middleware.json :as middleware]
            [clojure.java.jdbc :as jdbc]
            [compojure.route :as route]))

(def db-config
  {:classname "org.h2.Driver"
   :subprotocol "h2"
   :subname "mem:documents"
   :user ""
   :password ""})

(defn pool
  [config]
  (let [cpds (doto (ComboPooledDataSource.)
               (.setDriverClass (:classname config ))
               (.setJdbcUrl (str "jdbc:" (:subprotocol config) ":" (:subname config)))
               (.setUser (:user config))
               (.setPassword (:password config))
               (.setMaxPoolSize 6)
               (.setMinPoolSize 1)
               (.setInitialPoolSize 1))]
    {:datasource cpds}))

(def pooled-db (delay (pool db-config)))

(defn db-connection [] @pooled-db)

(jdbc/db-do-commands 
 (db-connection)
  (jdbc/create-table-ddl :documents 
                    [[:id "varchar(256)" "primary key"]
                     [:title "varchar(1024)"]
                     [:text :varchar]]))


(defn get-document [id]
  (jdbc/db-query-with-resultset
   (db-connection)
    ["select * from documents where id = ?" id]
    #(cond
      (empty? %) {:status 404}
      :else (response (first %)))))

(defn uuid [] (str (java.util.UUID/randomUUID)))

(defn get-all-documents []
  (response
   (jdbc/db-query-with-resultset
    (db-connection)
     ["select * from documents"]
     #(into [] %))))

(defn update-document [id doc]
  (jdbc/update!
   (db-connection)
   :documents
   (assoc doc "id " id)
   ["id = ?" id])
  (get-document id))


(defn delete-document [id]
  (jdbc/delete!
   (db-connection)
   :documents
   ["id=?" id])
  {:status 204})



(defn create-new-document [doc]
 (let [id (uuid)]
   (jdbc/insert!
    (db-connection)
     :documents
     (assoc doc "id" id))
   (get-document id)) )


(defroutes app-routes
  (context "/documents" [] (defroutes documents-routes
                             (GET "/" [] (get-all-documents))
                             (POST "/" {body :body} (create-new-document body))
                             (context "/:id" [id] (defroutes document-routes
                                                    (GET "/" [] (get-document id))
                                                    (PUT "/" {body :body}  (update-document id body))
                                                    (DELETE "/" [] (delete-document id))))))
  (route/not-found "Not Found"))

(def app
  (-> (handler/api app-routes)
      (middleware/wrap-json-body)
      (middleware/wrap-json-response)))
