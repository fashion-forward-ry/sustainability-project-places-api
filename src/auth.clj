(ns auth
  (:require [clj-http.client :as http]
            [cheshire.core :as json]))

;; Function for getting new refresh token from google API.
;; An example of calling function:
;; (auth/get-token  "my-client-secret" "my-refresh-token" "this-is-client-id" "url-to-get-token")
;; Returns map with token_type, access_token, scope and expires_in as keys

(defn get-token [client-secret refresh-token client-id url]
  (let [response (http/post url {:form-params {:client_id client-id
                                               :client_secret client-secret
                                               :refresh_token refresh-token
                                               :grant_type "refresh_token"}})]
    (json/parse-string (:body response) true)))

;; Function for getting spreadsheet content.
;; Example of calling the function:
;; (auth/get-spreadsheet-content "api-url" "my-access-token" "spreadsheet-id" "foo!A1:Z4")
;; Returns a map with majorDimension, values and range as keys
(defn get-spreadsheet-content [base-url access-token spreadsheet-id range]
  (let [resp (http/get (str base-url "/v4/spreadsheets/" spreadsheet-id "/values/" range)
                       {:oauth-token access-token})]
    (json/parse-string (:body resp) true)))
