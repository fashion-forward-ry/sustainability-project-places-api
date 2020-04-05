(ns sustainability-project-places-api.core)

(def place {:adr_address "<span class=\"street-address\">Itämerenkatu 21</span>, <span class=\"postal-code\">00180</span> <span class=\"locality\">Helsinki</span>, <span class=\"country-name\">Finland</span>",
            :international_phone_number "+358 10 4197567",
            :address_components [{:long_name "21",
                                  :short_name "21",
                                  :types ["street_number"]}
                                 {:long_name "Itämerenkatu",
                                  :short_name "Itämerenkatu",
                                  :types ["route"]}
                                 {:long_name "Helsinki",
                                  :short_name "HKI",
                                  :types ["locality" "political"]}
                                 {:long_name "Finland",
                                  :short_name "FI",
                                  :types ["country" "political"]}
                                 {:long_name "00180",
                                  :short_name "00180",
                                  :types ["postal_code"]}],
            :name "UFF Second Hand",
            :icon "https://maps.gstatic.com/mapfiles/place_api/icons/shopping-71.png",
            :plus_code {:global_code "9GG65W75+CW",
                        :compound_code "5W75+CW Helsinki, Finland"},
            :geometry {:viewport {:southwest {:lat 60.16205566970849,
                                              :lng 24.9085181697085},
                                  :northeast {:lat 60.1647536302915,
                                              :lng 24.9112161302915}},
                       :location {:lat 60.1635564, :lng 24.9098461}},
            :scope "GOOGLE",
            :formatted_phone_number "010 4197567",
            :types ["clothing_store" "point_of_interest" "store" "establishment"],
            :utc_offset 180,
            :reference "ChIJq4dHqmwLkkYROWXxpLd91Oc",
            :place_id "ChIJq4dHqmwLkkYROWXxpLd91Oc",
            :id "c89d04a5da3956f5063af6ae2e97248812442402",
            :url "https://maps.google.com/?cid=16705115145523782969",
            :opening_hours {:periods [{:open {:day 0, :time "1200"},
                                       :close {:day 0, :time "1600"}}
                                      {:open {:day 1, :time "1000"},
                                       :close {:day 1, :time "2000"}}
                                      {:open {:day 2, :time "1000"},
                                       :close {:day 2, :time "2000"}}
                                      {:open {:day 3, :time "1000"},
                                       :close {:day 3, :time "2000"}}
                                      {:open {:day 4, :time "1000"},
                                       :close {:day 4, :time "2000"}}
                                      {:open {:day 5, :time "1000"},
                                       :close {:day 5, :time "2000"}}
                                      {:open {:day 6, :time "1000"},
                                       :close {:day 6, :time "1800"}}],
                            :weekday_text ["Monday: 10:00 AM – 8:00 PM"
                                           "Tuesday: 10:00 AM – 8:00 PM"
                                           "Wednesday: 10:00 AM – 8:00 PM"
                                           "Thursday: 10:00 AM – 8:00 PM"
                                           "Friday: 10:00 AM – 8:00 PM"
                                           "Saturday: 10:00 AM – 6:00 PM"
                                           "Sunday: 12:00 – 4:00 PM"],
                            :open_now false},
            :vicinity "Itämerenkatu 21, Helsinki",
            :formatted_address "Itämerenkatu 21, 00180 Helsinki, Finland"})
