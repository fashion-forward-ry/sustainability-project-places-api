(ns sustainability-project-places-api.parser
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

;; Function for parsing city name under the address_components
;; City name is recognized by type
(defn get-city-name [place]
  (->> (:address_components place)
       (filter #(.contains (:types %) "locality"))
       first
       :long_name))


;; Function for parsing place gotten from the google places API
;; Selects the keys that we want and flattens location and address components info
(defn parse-place [place-map]
  (-> place-map
      (select-keys [:name :place_id :types :formatted_address])
      (merge (get-in place-map [:geometry :location]))
      (merge {:city_name (get-city-name place-map)})))
