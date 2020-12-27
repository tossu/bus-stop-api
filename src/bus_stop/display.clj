(ns bus-stop.display)

(defn date-to-time [date]
  (let [formatter (java.text.SimpleDateFormat. "HH:mm")]
    (.format formatter date)))

(defn row-to-str [row]
  (let [bus1 (first row)
        bus1-number (:bus-number bus1)
        bus1-time (date-to-time (:date bus1))

        bus2 (last row)
        bus2-number (:bus-number bus2)
        bus2-time (date-to-time (:date bus2))

        space (if (> (count (str bus1-number)) 2) " " "  ")]
  (str "   " bus1-time "|" bus1-number space bus2-time "|" bus2-number "\n")))

(defn split-stops-to-rows [stops]
  (map vector (take 4 stops) (take-last 4 stops)))

(defn bus-info [stops]
  (let [rows (split-stops-to-rows stops)
        time-info (apply str (map row-to-str rows))]
    (str "\n     BUSSIAIKATAULUT\n\n" time-info)))
