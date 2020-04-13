(ns sustainability-project-places-api.places-api
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

;; Function for getting data for a place from Google places API.
;; Example of calling the function:
;; (auth/get-place-detail "<key-to-api>" "<place-id>" "https://maps.googleapis.com/maps/api/place")
(defn get-place-detail [key place-id base-url]
  (let [resp (http/get (str base-url "/details/json")
                       {:query-params {"key" key
                                       "place_id" place-id}})]
    (json/parse-string (:body resp) true)))
