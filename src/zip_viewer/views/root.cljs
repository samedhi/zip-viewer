(ns zip-viewer.views.root
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]
   [zip-viewer.views.actions :as actions]
   [zip-viewer.views.logbook :as logbook]
   [zip-viewer.util :as util]))

(defn code-block [o]
  [:pre {:class "code-block"}
   [:code o]])

(re-frame/reg-sub
 ::pretty-print-db
 (fn [db]
   (util/pprint db)))

(defn title []
  [mui/typography
   {:variant :h2
    :align :center}
   "clojure.zip/viewer"])

(defn app-db-viewer []
  (let [db @(re-frame/subscribe [::pretty-print-db])]
    [mui/card
     {:style {:margin "1rem 0"}}
     [mui/card-content
      [mui/typography {:color "textSecondary"} "Content of app-db is:"]
      [code-block db]]]))

(defn component []
  [mui/container
   {:max-width "xl"}
   [title]
   [actions/component]
   [logbook/component]
   [app-db-viewer]])
