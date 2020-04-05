(ns sustainability-project-places-api.auth
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
