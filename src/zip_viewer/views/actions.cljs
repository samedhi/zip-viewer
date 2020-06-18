(ns zip-viewer.views.actions
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]
   [zip-viewer.zip-data :as zip-data]))

(def init-grid
  [[:init]])

(def grid
  [[:up]
   [:left :replace :right]
   [:down]])

(defn action-component [action]
  (let [arguments (get zip-data/action->positional-arguments action ["loc"])]
    [mui/button
     {:variant "outlined"
      :style {:text-transform :none}}
     [mui/grid
      {:container true
       :align-items :center}
      [mui/typography "(" (name action) " <loc>"]
      (for [[i argument] (map-indexed vector (rest arguments))]
        ^{:key i}
        [mui/text-field {:style {:padding-left "0.5rem"}
                         :placeholder argument}])
      [mui/typography
       ")"]]]))

(defn component []
  [mui/grid
   {:container true}
   (for [row grid]
     ^{:key (pr-str row)}
     [mui/grid
      {:container true
       :justify :space-evenly
       :item true
       :style {:padding "0.25rem 0"}}
      (for [col row]
        ^{:key col}
        [mui/grid
         {:item true
          :align :center
          :style {:min-width "5rem"}}
         (action-component col)])])])
