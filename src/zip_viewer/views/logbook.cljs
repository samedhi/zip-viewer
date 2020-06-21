(ns zip-viewer.views.logbook
  (:require
   [clojure.zip :as zip]
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]))

(defn row-component [i relative-to-index loc]
  [mui/table-row
   {:hover true
    :on-click #(re-frame/dispatch [:set-index i])
    :style
    (condp = relative-to-index
      -1 {:color :gray}
      0  {:font-weight :bold}
      1  nil)}
   [mui/table-cell
    {:style {:font-weight :inherit
             :color :inherit}}
    (-> loc meta :action-str)]
   [mui/table-cell
    {:style {:font-weight :inherit
             :color :inherit}}
    (pr-str (zip/node loc))]
   [mui/table-cell
    {:style {:font-weight :inherit
             :color :inherit}}
    (pr-str loc)]])

(defn preview-row [action-str]
  [mui/table-row
   [mui/table-cell
    (or action-str "--")]
   [mui/table-cell "--"]
   [mui/table-cell "--"]])

(defn component []
  (let [action-str @(re-frame/subscribe [:preview-action-str])
        index @(re-frame/subscribe [:index])]
    [mui/table-container
     [mui/table
      {:size :small}
      [mui/table-head
       [mui/table-row
        [mui/table-cell "Action"]
        [mui/table-cell "Focus"]
        [mui/table-cell "Loc"]]]
      [mui/table-body
       (for [[i loc] (map-indexed (fn [i v] [(inc i) v]) @(re-frame/subscribe [:locs]))]
         ^{:key i}
         [row-component i (compare index i) loc])
       [preview-row action-str]]]]))

