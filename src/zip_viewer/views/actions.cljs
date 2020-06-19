(ns zip-viewer.views.actions
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]
   [zip-viewer.util :as util]
   [zip-viewer.zip-data :as zip-data]))

(def constructor-grid
  [[:vector-zip]])

(def grid
  [[:up]
   [:left :replace :right]
   [:down]])

(defn action-component [action]
  (let [arguments (zip-data/action->positional-arguments action)
        constructor? (contains? zip-data/constructors action)
        can-be-clicked? @(re-frame/subscribe [:can-be-clicked? action])]
    [mui/button
     {:variant "outlined"
      :style {:text-transform :none}
      :color (if can-be-clicked? :primary :default)
      :disable-ripple (not can-be-clicked?)
      :on-click (when can-be-clicked? #(re-frame/dispatch [action]))}
     [mui/grid
      {:container true
       :align-items :center}
      [mui/typography "(" (name action) (when-not constructor? " <loc> ")]
      (doall
       (for [[i argument] (map-indexed vector arguments)]
         ^{:key i}
         [mui/text-field {:style {:padding-left "0.5rem"}
                          :placeholder argument
                          :on-change #(let [v (util/evt->value %)]
                                        (re-frame/dispatch [:set-argument-value action i v]))
                          :value @(re-frame/subscribe [:argument-value action i])}]))
      [mui/typography
       ")"]]]))

(defn render-actions [grid]
  [mui/grid
   {:container true}
   (doall
    (for [row grid]
      ^{:key (pr-str row)}
      [mui/grid
       {:container true
        :justify :space-evenly
        :item true
        :style {:padding "0.25rem 0"}}
       (doall
        (for [col row]
          ^{:key col}
          [mui/grid
           {:item true
            :align :center
            :style {:min-width "5rem"}}
           (action-component col)]))]))])

(defn contructor-view-component []
  [:div "CONTRUCTOR VIEW COMPONENT"])

(defn component []
  (let [we-have-a-loc? (not @(re-frame/subscribe [:loc-empty?]))]
    [render-actions
     (if we-have-a-loc?
       grid
       constructor-grid)]))
