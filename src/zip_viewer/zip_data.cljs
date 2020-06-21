(ns zip-viewer.zip-data
  (:require [clojure.zip :as zip]))

(def action->zip-fn
  {:left zip/left
   :leftmost zip/leftmost
   :right zip/right
   :rightmost zip/rightmost
   :up zip/up
   :down zip/down
   :replace zip/replace
   :seq-zip zip/seq-zip
   :vector-zip zip/vector-zip
   :xml-zip zip/xml-zip})

(def action->positional-arguments
  {:replace ["value"]
   :seq-zip ["value"]
   :vector-zip ["value"]
   :xml-zip ["value"]})

(def constructors
  #{:seq-zip
    :vector-zip
    :xml-zip})
