(ns zip-viewer.util)

(defn evt->value [evt]
  (.-value (.-target evt)))
