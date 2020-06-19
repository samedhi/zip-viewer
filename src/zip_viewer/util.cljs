(ns zip-viewer.util
  (:require
   [clojure.string :as string]
   [zip-viewer.zip-data :as zip-data]))

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
