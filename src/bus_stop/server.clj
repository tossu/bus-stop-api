(ns bus-stop.server
  (:require [ring.adapter.jetty :refer [run-jetty]]
            [bus-stop.data :refer [get-times]]
            [bus-stop.display :refer [bus-info]])
  (:gen-class))

(defn handler [request]
    ; TODO: catch exception?
    {:status 200
     :headers {"Content-Type" "text/plain" "Refresh-In" "500"}
     :body (bus-info (get-times))})

(defn -main
  [& args]
  (run-jetty handler {:port  3000 :join? false}))
