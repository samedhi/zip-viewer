(ns zip-viewer.views.actions
  (:require
   [clojure.zip :as zip]
   [zip-viewer.mui :as mui]))

(def keyword->zip-fn
  {:left zip/left
   :right zip/right
   :up zip/up
   :down zip/down
   :replace zip/replace
   :init zip/vector-zip})

(def pos-arg-actions
  {:init ["inital data structure"]
   :replace ["value to replace"]})

(def start-grid
  [[:init]])

(def grid
  [[:up]
   [:left :replace :right]
   [:down]])

(defn component []
  [mui/grid
   {:container true
    :style {:background-color :red}}
   (for [row grid]
     ^{:key row}
     [mui/grid
      {:container true
       :justify :space-evenly
       :item true}
      (for [col row]
        ^{:key col}
        [mui/typography
         {:align :center
          :style {:background-color :green
                  :min-width "5rem"}}
         (name col)])])])
