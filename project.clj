(defproject bus-stop "0.1.0-SNAPSHOT"
  ; clojure 1.10.2-alpha fixes graalvm build..
  ; https://github.com/clojure/clojure/commit/f5403e9c666f3281fdb880cb2c21303c273eed2d
  :dependencies [[org.clojure/clojure "1.10.2-alpha2"]
                 [clj-http "3.10.3"]
                 [org.clojure/data.json "1.0.0"]
                 [ring/ring-core "1.6.3"]
                 [ring/ring-jetty-adapter "1.6.3"]]
  :main ^:skip-aot bus-stop.server
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
