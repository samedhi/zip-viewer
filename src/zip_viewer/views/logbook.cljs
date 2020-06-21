(ns zip-viewer.views.logbook
  (:require
   [clojure.zip :as zip]
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]
   [zip-viewer.mui-icons :as mui-icons]))

(defn row-component [i relative-to-index loc]
  (let [opened? @(re-frame/subscribe [:opened i])]
    [mui/table-row
     {:hover true
      :on-click #(re-frame/dispatch [:set-index i])
      :style
      (condp = relative-to-index
        -1 {:color :gray}
        0  {:font-weight :bold}
        1  nil)}
     [mui/table-cell
      [mui/icon-button
       {:size :small
        :on-click #(re-frame/dispatch [:flip-opened i])}
       (if 
           [mui-icons/keyboard-arrow-up]
         [mui-icons/keyboard-arrow-down])]]
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
      (pr-str loc)]]))

(defn preview-row [action-str]
  [mui/table-row
   [mui/table-cell ""]
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
        [mui/table-cell ""]
        [mui/table-cell "Action"]
        [mui/table-cell "Focus"]
        [mui/table-cell "Loc"]]]
      [mui/table-body
       (for [[i {:keys [loc]}] (map-indexed vector @(re-frame/subscribe [:log]))]
         ^{:key i}
         [row-component i (compare index i) loc])
       [preview-row action-str]]]]))

