(ns za.co.theamazingsoapshop.admin.ui.payments
  (:require [za.co.theamazingsoapshop.admin.ui.svg :as -svg]
            [za.co.theamazingsoapshop.admin.ui.common :as -ui-common]
            [reagent.core :as r]
            [clojure.string :as str]
            [integrant.core :as ig]
            [lambdaisland.glogi :as log]))

(defn overview-card
  [{:keys [icon title value]}]
  [:div.bg-white.overflow-hidden.shadow.rounded-lg
   {:key (str "overview-card-" title)}
   [:div.p-5
    [:div.flex.items-center
     [:div.flex-shrink-0 icon]
     [:div.ml-5.w-0.flex-1
      [:dl
       [:dt.text-sm.leading-5.font-medium.text-cool-gray-500.truncate title]
       [:dd
        [:div.text-lg.leading-7.font-medium.text-cool-gray-900 value]]]]]]])

(defn section-header [content]
  [:h2.text-lg.leading-6.font-medium.text-cool-gray-900 content])

(defn section-content [& content]
  [:div.mt-2.grid.grid-cols-1.gap-5.sm:grid-cols-2.lg:grid-cols-3 content])


(defmethod -ui-common/render-workspace ::payments
  [_ {:as system}]
  [:div
   [section-header "Overview"]
   (let [icon-classes "h-6 w-6 text-cool-gray-400"]
     [section-content
      (overview-card {:title "Completed (last 30 days)"
                      :value "R 4 345.00"
                      :icon [-svg/icon-for ::completed icon-classes]})
      (overview-card {:title "Pending"
                      :value "R 10 223.00"
                      :icon [-svg/icon-for ::pending icon-classes]})])])



