(defproject clojure-rest "0.1.0-SNAPSHOT"
  :description "Rest service for documents"
  :url "http://blog.interlinked.org"
  :min-lein-version "2.0.0"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring/ring-json "0.4.0"]
                 [ring/ring-defaults "0.2.1"]
                 [c3p0/c3p0 "0.9.1.2"]
                 [org.clojure/java.jdbc "0.7.0-alpha1"]
                 [com.h2database/h2 "1.4.193"]
                 [cheshire "5.7.0"]]
  :plugins [[lein-ring "0.9.7"]]
  :ring {:handler clojure-rest.handler/app}
  :profiles
  {:dev {:dependencies [[javax.servlet/servlet-api "2.5"]
                        [ring/ring-mock "0.3.0"]]}})
