(ns zip-viewer.views.logbook
  (:require
   [clojure.zip :as zip]
   [re-frame.core :as re-frame]
   [zip-viewer.mui :as mui]))

(defn component []
  [mui/grid
   (for [loc @(re-frame/subscribe [:locs])]
     ^{:key loc}
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
       (pr-str (zip/node loc))]
      [mui/grid
       {:item true
        :style {:background-color :blue}
        :xs 5}
       (pr-str loc)]])])

