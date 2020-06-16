(ns ^:figwheel-hooks zip-viewer.core
  (:require
   [firemore.core :as firemore]
   [goog.dom :as goog.dom]
   [reagent.core :as reagent]
   [reagent.dom :as reagent.dom]
   [re-frame.core :as re-frame]
   [zip-viewer.config :as config]
   [zip-viewer.breakpoints :as breakpoints]
   [zip-viewer.mui :as mui]))

;; SUBSCRIPTIONS

(re-frame/reg-sub
 ::greeting
 (fn [db _]
   (:greeting db)))

;; EVENTS

(re-frame/reg-event-db
 ::initialize-db
 (fn
   [_ _]
   config/default-db))

;; FUNCTIONS

(defn main-panel []
  (let [greeting @(re-frame/subscribe [::greeting])]
    [mui/container greeting]))

(defn mount-root []
  (when-let [el (goog.dom/getElement "app")]
    (reagent.dom/render [main-panel] el)))

(defn ^:dev/after-load init []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch-sync [::initialize-db])
  (breakpoints/init)
  (mount-root)
  ::success)

(defonce run-at-app-startup
  (init))
