(ns zip-viewer.zip-data
  (:require [clojure.zip :as zip]))

(def action->zip-fn
  {:left zip/left
   :right zip/right
   :up zip/up
   :down zip/down
   :replace zip/replace
   :init zip/vector-zip})

(def action->positional-arguments
  {:init ["value"]
   :replace [:loc "value"]})
