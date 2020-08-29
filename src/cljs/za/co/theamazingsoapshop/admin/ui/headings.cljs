(ns za.co.theamazingsoapshop.admin.ui.headings
  (:require [za.co.theamazingsoapshop.admin.ui.svg :as -svg]
            [za.co.theamazingsoapshop.admin.ui.common :as -ui-common]
            [za.co.theamazingsoapshop.admin.ui.sidebar :as -sidebar]
            [za.co.theamazingsoapshop.admin.ui.tables :as -tables]
            [za.co.theamazingsoapshop.admin.ui.buttons :as -buttons]
            [clojure.spec.alpha :as s]
            [reagent.core :as r]
            [clojure.string :as str]
            [integrant.core :as ig]
            [lambdaisland.glogi :as log]
            [cljs-gravatar.core :as gravatar]))

(s/def ::text (s/and string?
                     #(< 0 (count %))))

(s/def ::breadcrumb (s/coll-of (s/keys :req [::text])
                               into []
                               :min-count 1
                               :max-count 7))

(s/def ::menu (s/coll-of ::-buttons/button-spec
                         into []
                         :min-count 0
                         :max-count 3))

(s/def ::title ::text)

(s/def ::heading-spec (s/keys :req [::title]
                              :opt [::breadcrumb
                                    ::menu]))

(comment

  (s/conform ::heading-spec {})
  (s/conform ::heading-spec {::breadcrumb [{::text "one"}
                                           {::text "two"}
                                           {::text "three"}]})

  (s/conform ::heading-spec {::breadcrumb [{::text "one"}
                                           {::text "two"}
                                           {::text "three"}]
                             ::menu [{::text "Open"}]})


  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn heading-with-breadcrumb-and-buttons
  [ts]
  (let [table-spec (s/conform ::heading-spec ts)]
    (if (= ::s/invalid table-spec)
      (do
        (log/warn ::invalid-spec-to-heading (s/explain-data ::heading-spec ts))
        [:pre.border.border-1.m-1 (s/explain-str ::heading-spec ts)])
      (let [{::keys [breadcrumb menu title]} table-spec

            breadcrumb-render-fn (fn [{crumb ::text}]
                                   [:a.text-gray-500.hover:text-gray-700.transition.duration-150.ease-in-out
                                    {:href "#"}
                                    crumb])]
        [:div
         [:div
          [:nav.sm:hidden
           [:a.flex.items-center.text-sm.leading-5.font-medium.text-gray-500.hover:text-gray-700.transition.duration-150.ease-in-out
            {:href "#"}
            [:svg.flex-shrink-0.-ml-1.mr-1.h-5.w-5.text-gray-400
             {:fill "currentColor", :viewBox "0 0 20 20"}
             [:path
              {:clip-rule "evenodd",
               :d
               "M12.707 5.293a1 1 0 010 1.414L9.414 10l3.293 3.293a1 1 0 01-1.414 1.414l-4-4a1 1 0 010-1.414l4-4a1 1 0 011.414 0z",
               :fill-rule "evenodd"}]]
            "Back"]]
          [:nav.hidden.sm:flex.items-center.text-sm.leading-5.font-medium
           (for [ui-item (interpose [:svg.flex-shrink-0.mx-2.h-5.w-5.text-gray-400
                                     {:fill "currentColor", :viewBox "0 0 20 20"}
                                     [:path
                                      {:clip-rule "evenodd",
                                       :d
                                       "M7.293 14.707a1 1 0 010-1.414L10.586 10 7.293 6.707a1 1 0 011.414-1.414l4 4a1 1 0 010 1.414l-4 4a1 1 0 01-1.414 0z",
                                       :fill-rule "evenodd"}]]
                                    (->> breadcrumb
                                         (map breadcrumb-render-fn)))]
             ui-item)]]
         [:div.mt-2.md:flex.md:items-center.md:justify-between
          [:div.flex-1.min-w-0
           [:h2.text-2xl.font-bold.leading-7.text-gray-900.sm:text-3xl.sm:leading-9.sm:truncate
            title]]
          [:div.mt-4.flex-shrink-0.flex.md:mt-0.md:ml-4
           (for [ui-item (interpose [:span.ml-3 ""]
                                    (->> menu
                                         (map (fn [mb] [-buttons/span-button mb]))))]
             ui-item)]]]))))
