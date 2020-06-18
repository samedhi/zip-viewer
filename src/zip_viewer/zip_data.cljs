(ns zip-viewer.zip-data
  (:require [clojure.zip :as zip]))

(def action->zip-fn
  {:left zip/left
   :right zip/right
   :up zip/up
   :down zip/down
   :replace zip/replace
   :vector-zip zip/vector-zip})

(def action->positional-arguments
  {:vector-zip ["value"]
   :replace ["value"]})

(def constructors
  #{:vector-zip})
