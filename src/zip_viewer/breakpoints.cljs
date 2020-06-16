(ns zip-viewer.breakpoints
  (:require
   [breaking-point.core :as bp]
   [re-frame.core :as re-frame]
   [zip-viewer.config :as config]))

(defn init []
  (re-frame/dispatch-sync [::bp/set-breakpoints config/breakpoints]))


