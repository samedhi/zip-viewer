(ns zip-viewer.subs
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.zip-data :as zip-data]
   [zip-viewer.util :as util]))

(re-frame/reg-sub
 :preview-action-str
 (fn [db _]
   (let [{:keys [action-hover inputs]} db]
     (when action-hover
       (util/build-action-str
        action-hover
        (->> inputs action-hover (mapv :parsed)))))))

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
   (get-in db [:inputs action i :raw])))

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
