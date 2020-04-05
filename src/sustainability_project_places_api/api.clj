(ns sustainability-project-places-api.api
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

;; Function for getting spreadsheet content.
;; Example of calling the function:
;; (auth/get-spreadsheet-content "api-url" "my-access-token" "spreadsheet-id" "foo!A1:Z4")
;; Returns a map with majorDimension, values and range as keys
(defn get-spreadsheet-content [base-url access-token spreadsheet-id range]
  (let [resp (http/get (str base-url "/v4/spreadsheets/" spreadsheet-id "/values/" range)
                       {:oauth-token access-token})]
    (json/parse-string (:body resp) true)))

;; Function for getting spreadsheet content from firebase.
;; Example of calling the function:
;; (auth/get-spreadsheet-content-firebase "<project-id>/firebaseio.com/" "<spreadsheet-id")
;; Returns a map of all brands
(defn get-brands-firebase [base-url spreadsheet-id]
  (let [resp (http/get (str base-url "/" spreadsheet-id "/brands.json"))]
    (json/parse-string (:body resp) true)))

;; Function for getting data for a place from Google places API.
;; Example of calling the function:
;; (auth/get-place-detail "<key-to-api>" "<place-id>" "https://maps.googleapis.com/maps/api/place")
(defn get-place-detail [key place-id base-url]
  (let [resp (http/get (str base-url "/details/json?key=" key "&place_id=" place-id))]
    (json/parse-string (:body resp) true)))
