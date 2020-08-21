(ns za.co.theamazingsoapshop.admin.core
  (:require-macros [failjure.core :as f]
                   [cljs.core.async.macros :refer [go]]
                   [integrant.core :as ig])
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
            [integrant.core :as ig]
            [za.co.theamazingsoapshop.admin.ui.app :as -app]
            [za.co.theamazingsoapshop.admin.ui.sidebar :as -sidebar]))

(glogi-console/install!)

(log/set-levels
 {:glogi/root   :debug ;; Set a root logger level, this will be inherited by all loggers
  ;; 'my.app.thing :trace   ;; Some namespaces you might want detailed logging
  ;; 'my.app.other :error   ;; or for others you only want to see errors.
  })

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn make-default-config []
  {::-app/app         {::-sidebar/sidebar (ig/ref ::-sidebar/sidebar)}
   ::-sidebar/sidebar {}

   :za.co.theamazingsoapshop.admin.ui.payments/payments
   {::-sidebar/sidebar (ig/ref ::-sidebar/sidebar)}})

(defonce system (r/atom nil))

(defn start []
  (reset! system (ig/init (make-default-config))))

(defn stop []
  (swap! system ig/halt!))

(defn reset []
  (stop)
  (start))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn main-widget [widget]
  (render widget (js/document.getElementById "app")))

(defn ^:export run []
  (start)
  (main-widget [-app/ui @system]))

(defn ^:dev/after-load restart []
  (main-widget [-app/ui @system]))
