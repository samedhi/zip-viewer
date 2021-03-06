(ns zip-viewer.events
  (:require
   [cljs.reader :as reader]
   [re-frame.core :as re-frame]
   [zip-viewer.config :as config]
   [zip-viewer.util :as util]
   [zip-viewer.zip-data :as zip-data]
   [clojure.zip :as zip]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   config/default-db))

(defn save-the-action [db action arguments]
  (let [{:keys [index]} db]
    (assoc-in
     db
     [:log index :action-string]
     (util/build-action-str action arguments))))

(defn try-to-read-string [v]
  (try
    (reader/read-string v)
    (catch js/Object o)))

(defn ensure-argument-vector [inputs action]
  (assoc inputs
         action
         (vec
          (take (count (zip-data/action->positional-arguments action))
                (repeat {})))))

(defn build-initial-inputs []
  (reduce ensure-argument-vector
          {}
          (keys zip-data/action->positional-arguments)))

(defn clear-inputs [db]
  (assoc db :inputs (build-initial-inputs)))

(re-frame/reg-event-db
 :set-up-inputs
 (fn [db _]
   (assoc db :inputs (build-initial-inputs))))

(defn clear-action-hover [db]
  (dissoc db :action-hover))

(defn conj-new-loc [db new-loc]
  (let [{:keys [index]} db
        new-index (inc index)]
    (-> db
        (update :log subvec 0 new-index)
        (update :log conj {:loc new-loc})
        (assoc :index new-index)
        clear-inputs
        clear-action-hover)))

(doseq [[action fx] (reduce dissoc zip-data/action->zip-fn zip-data/constructors)
        :let [positional-arguments (zip-data/action->positional-arguments action)]]
  (re-frame/reg-event-db
   action
   (fn [db _]
     (let [{:keys [inputs index]} db
           loc (get-in db [:log index :loc])
           arguments (->> inputs action (mapv :parsed))
           new-loc (apply fx loc arguments)]
       (-> db
           (conj-new-loc new-loc)
           (save-the-action action arguments))))))

(doseq [action zip-data/constructors
        :let [positional-arguments (zip-data/action->positional-arguments action)]]
  (re-frame/reg-event-db
   action
   (fn [db _]
     (let [{:keys [inputs]} db
           arguments (->> inputs action (mapv :parsed))
           new-loc (apply (get zip-data/action->zip-fn action) arguments)]
       (-> db
           (conj-new-loc new-loc)
           (save-the-action action arguments))))))

(re-frame/reg-event-db
 :set-index
 (fn [db [_ i]]
   (assoc db :index i)))

(re-frame/reg-event-db
 :flip-opened
 (fn [db [_ i]]
   (update-in db [:log i :opened?] (fnil not false))))

(defn attempt-to-parse-value [db action i value]
  (if-let [v (try-to-read-string value)]
    (assoc-in db [:inputs action i :parsed] v)
    (update-in db [:inputs action i] dissoc :parsed)))

(re-frame/reg-event-db
 :set-argument-value
 (fn [db [_ action i value]]
   (-> db
       (assoc-in [:inputs action i :raw] value)
       (attempt-to-parse-value action i value))))

(re-frame/reg-event-db
 :enter-hover-action
 (fn [db [_ action]]
   (assoc db :action-hover action)))

(re-frame/reg-event-db
 :leave-hover-action
 (fn [db [_ action]]
   (if (= action (:action-hover db))
     (dissoc db :action-hover)
     db)))
