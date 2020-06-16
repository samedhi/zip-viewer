(ns zip-viewer.mui-icons
  (:require
   [goog.object :as gobj]
   [reagent.core :as reagent]
   ["@material-ui/icons" :as material-ui]))

(defn mui->reagent [icon-name]
  (if-let [mui-obj (gobj/get material-ui icon-name)]
    (reagent/adapt-react-class mui-obj)
    (js/console.error "Was unable to get MaterialUI icon '" icon-name "'")))

(def add (mui->reagent "Add"))
