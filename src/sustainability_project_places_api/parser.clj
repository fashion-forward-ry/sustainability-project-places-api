(ns sustainability-project-places-api.parser
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

(defn get-city-name [place]
  (->> (:address_components place)
       (filter #(.contains (:types %) "locality"))
       first
       :long_name))

(defn parse-place [place-map]
  (-> place-map
      (select-keys [:name :place_id :types :formatted_address])
      (merge (get-in place-map [:geometry :location]))
      (merge {:long_name (get-city-name place-map)})))
