(ns zip-viewer.subs
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.zip-data :as zip-data]))

(re-frame/reg-sub
 :locs
 (fn [db _]
   (:locs db)))

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

(re-frame/reg-sub
 :can-be-clicked?
 (fn [db [_ action]]
   (if (contains? zip-data/action->positional-arguments action)
     (and
      (-> db :inputs action empty? not)
      (->> db
           :inputs
           action
           (every? :parsed)))
     true)))
