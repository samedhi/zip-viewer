(ns zip-viewer.views.root
  (:require
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]))

(defn component []
  (let [greeting @(re-frame/subscribe [:greeting])]
    [mui/container
     {:max-width "xl"}
     [mui/grid
      {:container true
       :justify :space-evenly}
      [mui/grid
       {:item true
        :style {:background-color :red}
        :xs 2}
       "AAAAAAAAAAAAAAA"]
      [mui/grid
       {:item true
        :style {:background-color :green}
        :xs 5}
       "BBBBBBBBBBBBBBB"]
      [mui/grid
       {:item true
        :style {:background-color :blue}
        :xs 5}
       "CCCCCCCCCCCCCCC"]]]))
