(ns za.co.theamazingsoapshop.admin.core
  (:require-macros [failjure.core :as f]
                   [cljs.core.async.macros :refer [go]])
  (:require [clojure.string :as str]
            [failjure.core :as f]
            [reagent.dom :refer [render]]
            [reagent.core :as r]
            [cuerdas.core :as s]
            [lambdaisland.glogi :as log]
            [lambdaisland.glogi.console :as glogi-console]
            [cljs-http.client :as http]
            [cljs.core.async :as csp]
            md5.core
            [integrant.core :as ig]))

(glogi-console/install!)

(log/set-levels
 {:glogi/root   :debug ;; Set a root logger level, this will be inherited by all loggers
  ;; 'my.app.thing :trace   ;; Some namespaces you might want detailed logging
  ;; 'my.app.other :error   ;; or for others you only want to see errors.
  })

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn main-widget [widget]
  (render widget (js/document.getElementById "app")))

(defn ^:export run []
  (main-widget [:div [:p "Hello andrea"]]))
