(ns sustainability-project-places-api.firebase-api
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

;; Function for getting spreadsheet content from firebase.
;; Example of calling the function:
;; (auth/get-spreadsheet-content-firebase "<project-id>/firebaseio.com/" "<spreadsheet-id")
;; Returns a map of all brands
(defn get-data-from-firebase [base-url spreadsheet-id tab-name]
  (let [resp (http/get (str base-url "/" spreadsheet-id "/" tab-name ".json"))]
    (json/parse-string (:body resp) true)))

;; Function for getting all brands listed in firebase real-time database
(defn get-brands [project-id]
  (get-data-from-firebase (str "https://" project-id ".firebaseio.com/") "1UFqUg8WKS111fSrUgc9ghhrABEWG2SrTOxKvdDd8ab4" "brands"))

;; Function for getting all online stores listed in firebase real-time database
(defn get-online-stores [project-id]
  (get-data-from-firebase (str "https://" project-id ".firebaseio.com/") "1UFqUg8WKS111fSrUgc9ghhrABEWG2SrTOxKvdDd8ab4" "onlineSustainableStores"))

;; Function for filtering sequence of elements by id
(defn filter-by-id [id elems]
  (filter #(= (:id %) id) elems))

;; Function for getting a single brand by its id
(defn get-brand-by-id [id project-id]
  (let [brands (get-brands project-id)]
    (filter-by-id id brands)))

;; Function for getting a single online store by its id
(defn get-online-store-by-id [id project-id]
  (let [online-stores (get-online-stores project-id)]
    (filter-by-id id online-stores)))
