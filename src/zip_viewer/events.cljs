(ns zip-viewer.events
  (:require
   [cljs.reader :as reader]
   [re-frame.core :as re-frame]
   [zip-viewer.config :as config]
   [zip-viewer.zip-data :as zip-data]))

(re-frame/reg-event-db
 :initialize-db
 (fn [_ _]
   config/default-db))

(doseq [[action fx] (reduce dissoc zip-data/action->zip-fn zip-data/constructors)
        :let [positional-arguments (zip-data/action->positional-arguments action)]]
  (re-frame/reg-event-db
   action
   (fn [db _]
     (let [{:keys [locs inputs]} db
           loc (peek locs)
           arguments (get inputs action)
           new-loc (apply fx loc arguments)]
       (update db :locs conj new-loc)))))

(re-frame/reg-event-db
 :vector-zip
 (fn [db _]
   (let [{:keys [locs inputs]} db
         arguments (->> inputs :vector-zip (mapv :parsed))
         new-loc (apply (:vector-zip zip-data/action->zip-fn) arguments)]
     (if (-> arguments first nil?)
       (do (println "Invalid Arguments: " arguments) db)
       (assoc db :locs [new-loc])))))

(defn try-to-read-string [v]
  (try
    (reader/read-string v)
    (catch js/Object o)))

(defn ensure-argument-vector [db action]
  (if (-> db :inputs action)
    db
    (assoc-in db [:inputs action]
              (vec
               (take (count (zip-data/action->positional-arguments action))
                     (repeat {}))))))

(defn attempt-to-parse-value [db action i value]
  (if-let [v (try-to-read-string value)]
    (assoc-in db [:inputs action i :parsed] v)
    (update-in db [:inputs action i] dissoc :parsed)))

(re-frame/reg-event-db
 :set-argument-value
 (fn [db [_ action i value]]
   (println :set-argument-value)
   (-> db
       (ensure-argument-vector action)
       (assoc-in [:inputs action i :raw] value)
       (attempt-to-parse-value action i value))))
