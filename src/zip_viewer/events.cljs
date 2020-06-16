(ns zip-viewer.events
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.config :as config]))

(re-frame/reg-event-db
 :initialize-db
 (fn
   [_ _]
   config/default-db))
