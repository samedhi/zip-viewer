(ns zip-viewer.subs
  (:require
   [re-frame.core :as re-frame]))

(re-frame/reg-sub
 :loc-empty?
 (fn [db _]
   (-> db :locs empty?)))

(re-frame/reg-sub
 :argument-value
 (fn [db [_ action i]]
   (if-let [v (get-in db [:inputs action i :raw])]
     (do (println "v is " v) v)
     (println "v was nil"))))
