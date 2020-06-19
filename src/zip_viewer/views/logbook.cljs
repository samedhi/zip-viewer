(ns zip-viewer.views.logbook
  (:require
   [clojure.zip :as zip]
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]))

(defn row-component [loc]
  [mui/table-row
   [mui/table-cell
    (-> loc meta :action-str)]
   [mui/table-cell
    (pr-str (zip/node loc))]
   [mui/table-cell
    (pr-str loc)]])

(defn component []
  [mui/table-container
   [mui/table
    {:size :small}
    [mui/table-head
     [mui/table-row
      [mui/table-cell "Action"]
      [mui/table-cell "Focus"]
      [mui/table-cell "Loc"]]]
    [mui/table-body
     (for [[i loc] (map-indexed vector @(re-frame/subscribe [:locs]))]
       ^{:key i}
       [row-component loc])]]])

