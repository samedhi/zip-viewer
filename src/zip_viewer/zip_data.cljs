(ns zip-viewer.zip-data
  (:require [clojure.zip :as zip]))

(def action->zip-fn
  {:append-child zip/append-child
   :branch? zip/branch?
   :children zip/children
   :down zip/down
   ;; :edit zip/edit
   :end? zip/end?
   :insert-child zip/insert-child
   :insert-left zip/insert-left
   :insert-right zip/insert-right
   :left zip/left
   :leftmost zip/leftmost
   :lefts zip/lefts
   :make-node zip/make-node
   :next zip/next
   :node zip/node
   :path zip/path
   :prev zip/prev
   :remove zip/remove
   :replace zip/replace
   :right zip/right
   :rightmost zip/rightmost
   :rights zip/rights
   :root zip/root
   :seq-zip zip/seq-zip
   :up zip/up
   :vector-zip zip/vector-zip
   :xml-zip zip/xml-zip
   ;; :zipper zip/zipper
   })

(def action->positional-arguments
  {:append-child ["item"]
   :insert-child ["item"]
   :insert-left ["item"]
   :insert-right ["item"]
   :make-node ["node" "children"]
   :replace ["node"]
   :seq-zip ["root"]
   :vector-zip ["root"]
   :xml-zip ["root"]
   :zipper ["branch?" "children" "make-node" "root"]})

(def constructors
  #{:seq-zip
    :vector-zip
    :xml-zip
    :zipper})
