(ns za.co.theamazingsoapshop.admin.ui.buttons
  (:require [za.co.theamazingsoapshop.admin.ui.svg :as -svg]
            [za.co.theamazingsoapshop.admin.ui.common :as -ui-common]
            [clojure.spec.alpha :as s]
            [reagent.core :as r]
            [clojure.string :as str]
            [integrant.core :as ig]
            [lambdaisland.glogi :as log]
            [cljs-gravatar.core :as gravatar]))


(def serious-button-classes (str " inline-flex items-center px-4 py-2 border border-transparent text-sm leading-5 font-medium rounded-md text-white "
                                 "bg-" -ui-common/color "-600 "
                                 "hover:bg-" -ui-common/color "-500 "
                                 "focus:outline-none "
                                 "focus:shadow-outline-" -ui-common/color " "
                                 "focus:border-" -ui-common/color "-700 "
                                 "active:bg-" -ui-common/color "-700"
                                 "transition duration-150 ease-in-out"))
(def not-so-serious-button-classes " inline-flex items-center px-4 py-2 border border-gray-300 text-sm leading-5 font-medium rounded-md text-gray-700 bg-white hover:text-gray-500 focus:outline-none focus:shadow-outline-blue focus:border-blue-300 active:text-gray-800 active:bg-gray-50 transition duration-150 ease-in-out")

(s/def ::text (s/and string?
                     #(< 0 (count %))))
(s/def ::serious? #{true})
(s/def ::extra-classes ::text)

(s/def ::button-spec (s/keys :req [::text]
                             :opt [::serious?
                                   ::extra-classes]))

;;;;;;;;;;;;;;;;;;;;

(defn span-button
  [bs]
  (let [button-spec (s/conform ::button-spec bs)]
    (if (= ::s/invalid button-spec)
      (do
        (log/warn ::invalid-spec-to-button (s/explain-data ::button-spec bs))
        [:pre.border.border-1.m-1 (s/explain-str ::button-spec bs)])
      (let [{::keys [text serious? extra-classes]} button-spec]
        [:span.shadow-sm.rounded-md
         [:button
          {:class (str (if serious? serious-button-classes
                           not-so-serious-button-classes)
                       " "
                       extra-classes)
           :type "button"}
          text]]))))
