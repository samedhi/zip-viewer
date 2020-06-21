(ns zip-viewer.subs
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.zip-data :as zip-data]
   [zip-viewer.util :as util]))

(defn build-action-string [action inputs]
  (when action
    (util/build-action-str
     action
     (->> inputs action (mapv :parsed)))))

(re-frame/reg-sub
 :index
 (fn [db _]
   (:index db)))

(re-frame/reg-sub
 :opened
 (fn [db _]
   false))

(re-frame/reg-sub
 :preview-action-str
 (fn [db _]
   (let [{:keys [action-hover inputs]} db]
     (build-action-string action-hover inputs))))

(re-frame/reg-sub
 :action-str
 (fn [db [_ action]]
   (let [{:keys [inputs]} db]
     (build-action-string action inputs))))

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
