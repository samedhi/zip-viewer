(ns zip-viewer.breakpoints
  (:require
   [breaking-point.core :as bp]
   [re-frame.core :as re-frame]
   [zip-viewer.config :as config]))

(def previous-breakpoints (atom nil))

(defn init []
  (when (not= @previous-breakpoints config/breakpoints)
    (reset! previous-breakpoints config/breakpoints)
    (re-frame/dispatch-sync [::bp/set-breakpoints config/breakpoints])))
