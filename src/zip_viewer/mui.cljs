(ns zip-viewer.mui
  (:refer-clojure :exclude [list stepper])
  (:require
   [goog.object :as gobj]
   [reagent.core :as reagent]
   [reagent.impl.template :as reagent-template]
   [camel-snake-kebab.core :as camel-snake-kebab]
   ["@material-ui/core" :as material-ui]))

(defn mui->reagent [mui-name]
  (if-let [mui-obj (gobj/get material-ui mui-name)]
    (reagent/adapt-react-class mui-obj)
    (js/console.error "Was unable to get MaterialUI component '" mui-name "'")))

;; Need to add another component? Easy as...
;; (def xxx-yyyy (mui->reagent "XxxYyyy")) ;; Where XxxYyyy is a component in MaterialUI

(def app-bar (mui->reagent "AppBar"))
(def avatar  (mui->reagent "Avatar"))
(def badge  (mui->reagent "Badge"))
(def bottom-navigation  (mui->reagent "BottomNavigation"))
(def button (mui->reagent "Button"))
(def button-group (mui->reagent "ButtonGroup"))
(def card  (mui->reagent "Card"))
(def card-actions  (mui->reagent "CardActions"))
(def card-content (mui->reagent "CardContent"))
(def card-header  (mui->reagent "CardHeader"))
(def card-media  (mui->reagent "CardMedia"))
(def checkbox  (mui->reagent "Checkbox"))
(def chip  (mui->reagent "Chip"))
(def circular-progress  (mui->reagent "CircularProgress"))
(def container (mui->reagent "Container"))
(def dialog  (mui->reagent "Dialog"))
(def divider  (mui->reagent "Divider"))
(def drawer  (mui->reagent "Drawer"))
(def fab (mui->reagent "Fab"))
(def grid (mui->reagent "Grid"))
(def grid-list  (mui->reagent "GridList"))
(def icon-button  (mui->reagent "IconButton"))
(def linear-progress  (mui->reagent "LinearProgress"))
(def list  (mui->reagent "List"))
(def list-item  (mui->reagent "ListItem"))
(def menu  (mui->reagent "Menu"))
(def menu-item  (mui->reagent "MenuItem"))
(def mui-theme-provider  (mui->reagent "MuiThemeProvider"))
(def paper  (mui->reagent "Paper"))
(def popover  (mui->reagent "Popover"))
(def slider  (mui->reagent "Slider"))
(def svg-icon  (mui->reagent "SvgIcon"))
(def step  (mui->reagent "Step"))
(def step-button  (mui->reagent "StepButton"))
(def step-content  (mui->reagent "StepContent"))
(def step-label  (mui->reagent "StepLabel"))
(def stepper  (mui->reagent "Stepper"))
(def snackbar  (mui->reagent "Snackbar"))
(def tabs  (mui->reagent "Tabs"))
(def tab  (mui->reagent "Tab"))
(def table  (mui->reagent "Table"))
(def table-body  (mui->reagent "TableBody"))
(def table-footer  (mui->reagent "TableFooter"))
(def table-row (mui->reagent "TableRow"))
(def toolbar  (mui->reagent "Toolbar"))
(def typography (mui->reagent "Typography"))

;; text-fields are weird...
;; https://github.com/reagent-project/reagent/blob/master/doc/examples/material-ui.md
(def input-component
  (reagent/reactify-component
   (fn [props]
     [:input (-> props
                 (assoc :ref (:inputRef props))
                 (dissoc :inputRef))])))

(def textarea-component
  (reagent/reactify-component
   (fn [props]
     [:textarea (-> props
                    (assoc :ref (:inputRef props))
                    (dissoc :inputRef))])))

(defn text-field [props & children]
  (let [internal-component (cond
                             ;; FIXME: Autosize multiline field is broken.
                             ;; Select doesn't require cursor fix so default can be used.
                             (or (:maxRows props)
                                 (:select props))
                             nil

                             (and (:multiline props)
                                  (:rows props))
                             textarea-component

                             :else
                             input-component)
        props (-> props
                  (assoc-in [:InputProps :inputComponent] internal-component)
                  reagent-template/convert-prop-value)]
    (apply reagent/create-element material-ui/TextField props (map reagent/as-element children))))
