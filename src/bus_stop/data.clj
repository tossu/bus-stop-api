(ns bus-stop.data
  (:require [clj-http.client :as client]
            [clojure.data.json :as json]))

;; TODO: Use proper logger

(def URL "https://api.digitransit.fi/routing/v1/routers/waltti/index/graphql/batch")
(def QUERY "query StopRoute($id_0:String!,$startTime_1:Long!,$timeRange_2:Int!,$numberOfDepartures_3:Int!) {stop(id:$id_0) {id,...F2}} fragment F0 on Stoptime{realtimeArrival,realtime,serviceDay,trip {pattern {route {shortName}}}} fragment F1 on Stop {stoptimes:stoptimesWithoutPatterns(startTime:$startTime_1,timeRange:$timeRange_2,numberOfDepartures:$numberOfDepartures_3,omitCanceled:false) {...F0}} fragment F2 on Stop {...F1}")

(def TIETONIEKANTIE1 "LINKKI:207525")
(def YLIOPPILASKYLA1 "LINKKI:207532")

(def STOPS [TIETONIEKANTIE1 YLIOPPILASKYLA1])

(defn now [] (quot (System/currentTimeMillis) 1000))

(defn query [id] [{
  :query QUERY
  :variables {
    :id_0 id
    :startTime_1 (now)
    :timeRange_2 43200,
    :numberOfDepartures_3 4 }}])

(defn fetch [stop-id]
  (try
    (client/post URL {:body (json/write-str (query stop-id))
                      :headers {"Content-Type" "application/json"}})
  (catch Exception e
    (println (.getMessage e))
    {})))

(defn parse-json [data]
  (try
    (json/read-str data :key-fn keyword)
    (catch Exception e
      (println (.getMessage e))
      [])))

(defn get-time [stop-id]
  (let [response (:body (fetch stop-id))
        json (parse-json response)
        stops (-> json first :payload :data :stop :stoptimes)]
    (if (nil? stops)
      (do
        (println "SOMETHING IS WRONG")
        [])
      stops)))

(defn format-stop [stop]
  ; reittiopas gives time in seconds and date in seconds from epoch...
  ; nobody told them you could use just single value?
  (let [seconds (-> stop :realtimeArrival)
        epoch (-> stop :serviceDay (+ seconds) (* 1000))
        date (-> epoch java.util.Date.)
        bus-number (-> stop :trip :pattern :route :shortName)]
    {:bus-number bus-number :date date }))

(defn get-times []
  (let [times (flatten (map get-time STOPS))
        formatted (map format-stop times)
        sorted (sort-by :date formatted)]
    sorted))
