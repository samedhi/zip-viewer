(ns zip-viewer.util
  (:require
   [clojure.string :as string]
   [cljs.pprint :as pprint]
   [zip-viewer.zip-data :as zip-data]))

(defn pprint [o]
  (with-out-str (pprint/pprint o)))

(defn evt->value [evt]
  (.-value (.-target evt)))

(defn build-action-str [action arguments]
  (str "("
       (name action)
       " "
       (when-not (contains? zip-data/constructors action) "<loc>")
       (when-not (empty? arguments) " ")
       (string/join " " (map #(if (nil? %) "nil" %) arguments))
       ")"))
