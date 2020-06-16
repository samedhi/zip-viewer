(ns ^:figwheel-hooks zip-viewer.core
  (:require
   [goog.dom :as goog.dom]
   [reagent.core :as reagent]
   [reagent.dom :as reagent.dom]
   [re-frame.core :as re-frame]
   [zip-viewer.breakpoints :as breakpoints]
   [zip-viewer.config :as config]
   [zip-viewer.events :as events]
   [zip-viewer.mui :as mui]
   [zip-viewer.subs :as subs]))

(defn main-panel []
  (let [greeting @(re-frame/subscribe [:greeting])]
    [mui/container greeting]))

(defn mount-root []
  (when-let [el (goog.dom/getElement "app")]
    (reagent.dom/render main-panel el)))

(defn ^:after-load init []
  (re-frame/clear-subscription-cache!)
  (re-frame/dispatch-sync [:initialize-db])
  (breakpoints/init)
  (mount-root)
  :success)

(defonce run-at-app-startup
  (init))
