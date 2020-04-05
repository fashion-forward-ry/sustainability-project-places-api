(defproject sustainability-project-places-api "0.1.0-SNAPSHOT"
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-http "3.10.0"]
                 [proto-repl "0.3.1"]
                 [cheshire "5.10.0"]]
  :source-paths ["src" "src/sustainability_api_places_api"]
  :target-path "target/%s"
  :repl-options {:port 8081})
