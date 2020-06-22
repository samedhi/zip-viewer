(ns zip-viewer.views.actions
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]
   [zip-viewer.util :as util]
   [zip-viewer.zip-data :as zip-data]))

(def constructor-section
  [{:name "Contructors"
    :grid [[:seq-zip]
           [:vector-zip]
           [:xml-zip]]}])

(def section
  [{:name "Movement"
    :grid [[:up]
           [:leftmost :left :right :rightmost]
           [:down]]}
   {:name "Traversal"
    :grid [[:prev :end? :next]]}
   {:name "Data"
    :grid [[:branch? :children :lefts :node :path :rights :root]]}
   {:name "Modify"
    :grid [[:insert-left :remove :replace  :insert-right]
           [:insert-child :append-child]]}])

(defn action-button [options]
  (let [{:keys [action constructor? can-be-clicked? variant]} options
        action-str @(re-frame/subscribe [:action-str action])]
    [mui/button
     {:full-width true
      :variant variant
      :style {:text-transform :none}
      :color (if can-be-clicked? :primary :default)
      :disabled (not can-be-clicked?)
      :on-click (when can-be-clicked? #(re-frame/dispatch [action]))
      :on-mouse-enter #(re-frame/dispatch [:enter-hover-action action])
      :on-mouse-leave #(re-frame/dispatch [:leave-hover-action action])}
     [mui/typography action-str]]))

(defn action-with-no-arguments [options]
  [action-button (assoc options :variant "outlined")])

(defn action-with-arguments [options]
  (let [{:keys [action arguments can-be-clicked?]} options]
    [mui/grid
     {:container true
      :direction :column}
     [mui/paper
      {:variant :outlined
       :style (when can-be-clicked? {:border "1px solid rgba(63, 81, 181, 0.5)"})}
      [action-button (assoc options :variant "text")]
      [mui/divider]
      [mui/grid
       (doall
        (for [[i argument] (map-indexed vector arguments)]
          ^{:key i}
          [mui/text-field {:style {:padding "0.5rem"}
                           :placeholder argument
                           :on-change #(let [v (util/evt->value %)]
                                         (re-frame/dispatch [:set-argument-value action i v]))
                           :value @(re-frame/subscribe [:argument-value action i])}]))]]]))

(defn terminal-action [options]
  (let [{:keys [action arguments can-be-clicked?]} options
        action-str @(re-frame/subscribe [:action-str action])
        loc @(re-frame/subscribe [:loc])
        fx (zip-data/action->zip-fn action)
        _ (println :terminal-action action action-str loc fx)
        result (try
                 (fx loc)
                 (catch :default e
                   (js/console.warn "I wasn't able to call" (pr-str action) "on" (pr-str loc))
                   (js/console.warn e)
                   "???"))]
    [mui/paper
     {:style {:padding "0.4rem"}}
     [mui/grid
      {:container true}
      [mui/typography (str action-str " => " (pr-str result))]]]))

(defn action-component [action]
  (let [arguments (zip-data/action->positional-arguments action)
        constructor? (contains? zip-data/constructors action)
        can-be-clicked? @(re-frame/subscribe [:can-be-clicked? action])
        options {:action action
                 :arguments arguments
                 :contructor? constructor?
                 :can-be-clicked? can-be-clicked?}]
    (cond
      (contains? zip-data/does-not-return-loc action) [terminal-action options]
      (seq arguments) [action-with-arguments options]
      :else [action-with-no-arguments options])))

(defn render-actions [section]
  [mui/grid
   {:container true
    :style {:padding-top "2rem"}}
   (doall
    (for [{:keys [grid name]} section]
      ^{:key name}
      [mui/grid
       {:container true}
       [mui/typography {:variant :h4} name]
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
               (action-component col)]))]))]))])

(defn component []
  (let [log-empty? @(re-frame/subscribe [:log-empty?])]
    [render-actions
     (if log-empty?
       constructor-section
       section)]))
