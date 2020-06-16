(ns zip-viewer.views.root
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]))

(defn component []
  (let [greeting @(re-frame/subscribe [:greeting])]
    [mui/grid
     {:container true
      :justify "flex-end"}
     greeting]))
