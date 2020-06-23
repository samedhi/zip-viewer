(ns zip-viewer.views.logbook
  (:require
   [clojure.zip :as zip]
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]
   [zip-viewer.mui-icons :as mui-icons]))

(defn row-drawer [opened? loc]
  [mui/table-row
   [mui/table-cell {:col-span 4 :style {:padding-bottom 0 :padding-top 0}}
    [mui/collapse {:in opened? :timeout :auto :unmount-on-exit true}
     [:pre {:class "code-block"
            :style {:padding "1rem"}}
      (with-out-str (cljs.pprint/pprint loc))]]]])

(defn row-component [i relative-to-index action-string loc]
  (let [opened? @(re-frame/subscribe [:opened i])]
    [:<>
     [mui/table-row
      {:hover true
       :on-click #(re-frame/dispatch [:set-index i])
       :style
       (merge
        (condp = relative-to-index
          -1 {:color :gray}
          0  {:font-weight :bold}
          1  nil))}
      [mui/table-cell
       {:style {:border-bottom "0"}}
       [mui/icon-button
        {:size :small
         :on-click #(re-frame/dispatch [:flip-opened i])}
        (if opened?
          [mui-icons/keyboard-arrow-up]
          [mui-icons/keyboard-arrow-down])]]
      [mui/table-cell
       {:style {:font-weight :inherit
                :color :inherit
                :border-bottom "0"}}
       action-string]
      [mui/table-cell
       {:style {:font-weight :inherit
                :color :inherit
                :border-bottom "0"}}
       (if loc (pr-str (zip/node loc)) "???")]
      [mui/table-cell
       {:style {:font-weight :inherit
                :color :inherit
                :border-bottom "0"}}
       (if loc (pr-str (zip/root loc)) "???")]]
     [row-drawer opened? loc]]))

(defn preview-row []
  (let [action-string @(re-frame/subscribe [:preview-action-str])]
    [mui/table-row
     [mui/table-cell ""]
     [mui/table-cell
      (or action-string "--")]
     [mui/table-cell "--"]
     [mui/table-cell "--"]]))

(defn component []
  (let [index @(re-frame/subscribe [:index])
        log @(re-frame/subscribe [:log])]
    [mui/table-container
     [mui/table
      {:size :small}
      [mui/table-head
       [mui/table-row
        [mui/table-cell ""]
        [mui/table-cell "Action"]
        [mui/table-cell "zip/node"]
        [mui/table-cell "zip/root"]]]
      (when (empty? log)
        [preview-row])
      [mui/table-body
       (for [[i {:keys [loc action-string]}] (reverse (map-indexed vector log))]
         ^{:key i}
         [row-component i (compare index i) action-string loc])]]]))

