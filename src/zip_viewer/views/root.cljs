(ns zip-viewer.views.root
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]
   [zip-viewer.views.actions :as actions]))

(defn code-block [o]
  [:pre {:class "code-block"}
   [:code o]])

(re-frame/reg-sub
 ::pretty-print-db
 (fn [db]
   (with-out-str (cljs.pprint/pprint db))))

(defn app-db-viewer []
  (let [db @(re-frame/subscribe [::pretty-print-db])]
    [mui/card
     [mui/card-content
      [mui/typography {:color "textSecondary"} "Content of app-db is:"]
      [code-block db]]]))

(defn component []
  [mui/container
   {:max-width "xl"}
   [mui/grid
    {:container true
     :justify :space-evenly}
    [mui/grid
     {:item true
      :style {:background-color :red}
      :xs 2}
     "AAAAAAAAAAAAAAA"]
    [mui/grid
     {:item true
      :style {:background-color :green}
      :xs 5}
     "BBBBBBBBBBBBBBB"]
    [mui/grid
     {:item true
      :style {:background-color :blue}
      :xs 5}
     "CCCCCCCCCCCCCCC"]]
   [actions/component]
   [app-db-viewer]])
