(ns za.co.theamazingsoapshop.admin.ui.new-payment
  (:require [za.co.theamazingsoapshop.admin.ui.svg :as -svg]
            [za.co.theamazingsoapshop.admin.ui.common :as -ui-common]
            [za.co.theamazingsoapshop.admin.ui.buttons :as -buttons]
            [reagent.core :as r]
            [clojure.string :as str]
            [integrant.core :as ig]
            [lambdaisland.glogi :as log]
            [cljs-gravatar.core :as gravatar]
            [clojure.spec.alpha :as s]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn ui [cfg]
  [:form
   [:div
    [:div
     [:div
      [:h3.text-lg.leading-6.font-medium.text-gray-900 "New Invoice Introduction"]
      [:p.mt-1.text-sm.leading-5.text-gray-500
       "You are about to capture the information required to issue a new " [:em "Invoice."]]
      [:p.mt-1.text-sm.leading-5.text-gray-500
       "You will be required to provide information about the " [:em "Payee"] ", who will be the recipient of the invoice, " [:em "Description"] ", " [:em "Amount"] " and some " [:em "Invoice"] " meta data."]]

     [:div.pt-6.mt-6.grid.grid-cols-1.row-gap-6.col-gap-4.sm:grid-cols-6.border-t.border-gray-200
      [:div.sm:col-span-4
       [:h3.text-lg.leading-6.font-medium.text-gray-900 "Payee"]]

      [:div.sm:col-span-4
       [:label.block.text-sm.font-medium.leading-5.text-gray-700 {:for "person/email"}
        "Email"]
       [:div.mt-1.flex.rounded-md.shadow-sm
        [:input {:class "flex-1 form-input block w-full min-w-0 rounded rounded-md transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                 :id "email"}]]]

      [:div.sm:col-span-2
       [:label.block.text-sm.font-medium.leading-5.text-gray-700 {:for "person/phone-number"}
        "Phone"]
       [:div.mt-1.flex.rounded-md.shadow-sm
        [:input {:class "flex-1 form-input block w-full min-w-0 rounded rounded-md transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                 :id "person/phone-number"}]]]

      [:div.sm:col-span-3
       [:label.block.text-sm.font-medium.leading-5.text-gray-700 {:for "person/firstname"}
        "First name"]
       [:div.mt-1.flex.rounded-md.shadow-sm
        [:input {:class "flex-1 form-input block w-full min-w-0 rounded rounded-md transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                 :id "person/firstname"}]]]

      [:div.sm:col-span-3
       [:label.block.text-sm.font-medium.leading-5.text-gray-700 {:for "person/lastname"}
        "Last name"]
       [:div.mt-1.flex.rounded-md.shadow-sm
        [:input {:class "flex-1 form-input block w-full min-w-0 rounded rounded-md transition duration-150 ease-in-out sm:text-sm sm:leading-5"
                 :id "person/lastname"}]]]]

     [:div.pt-6.mt-6.grid.grid-cols-1.row-gap-6.col-gap-4.sm:grid-cols-6.border-t.border-gray-200

      [:div.sm:col-span-4
       [:h3.text-lg.leading-6.font-medium.text-gray-900 "Description"]]

      [:div.sm:col-span-3
       [:label.block.text-sm.font-medium.leading-5.text-gray-700 {:for "lineitem/id"}
        "Category "]
       [:div.mt-1.rounded-md.shadow-sm
        [:select.form-select.block.w-full.transition.duration-150.ease-in-out.sm:text-sm.sm:leading-5
         {:id "lineitem/id"}
         [:option "Counseling"]
         [:option "Hypnosis"]
         [:option "Lauging therapy"]]]]

      [:div.sm:col-span-6
       [:label.block.text-sm.font-medium.leading-5.text-gray-700
        {:for "lineitem/description"}
        "Extra notes"]
       [:div.mt-1.rounded-md.shadow-sm
        [:textarea.form-textarea.block.w-full.transition.duration-150.ease-in-out.sm:text-sm.sm:leading-5
         {:rows "3"
          :id "lineitem/description"}]]]]


     [:div.pt-6.mt-6.grid.grid-cols-1.row-gap-6.col-gap-4.sm:grid-cols-6.border-t.border-gray-200

      [:div.sm:col-span-4
       [:h3.text-lg.leading-6.font-medium.text-gray-900 "Amount"]]

      [:div.sm:col-span-3
       [:label.block.text-sm.font-medium.leading-5.text-gray-700
        {:for "lineitem/amount"}
        "Amount"]
       [:div.mt-1.relative.rounded-md.shadow-sm
        [:div.absolute.inset-y-0.left-0.pl-3.flex.items-center.pointer-events-none
         [:span.text-gray-500.sm:text-sm.sm:leading-5
          "R"]]
        [:input#price.form-input.block.w-full.pl-7.pr-12.sm:text-sm.sm:leading-5
         {:aria-describedby "price-currency", :placeholder "0.00"
          :id "lineitem/amount"}]
        [:div.absolute.inset-y-0.right-0.pr-3.flex.items-center.pointer-events-none
         [:span#price-currency.text-gray-500.sm:text-sm.sm:leading-5
          "ZAR"]]]]]

     [:div.pt-6.mt-6.grid.grid-cols-1.row-gap-6.col-gap-4.sm:grid-cols-6.border-t.border-gray-200

      [:div.sm:col-span-4
       [:h3.text-lg.leading-6.font-medium.text-gray-900 "Invoice Details"]]

      [:dl.sm:col-span-3
       [:div
        [:dt.block.text-sm.font-medium.leading-5.text-gray-700 "Invoice ID"]
        [:dd.sm:text-sm.sm:leading-5 "1234"]]
       [:div.mt-1
        [:dt.block.text-sm.font-medium.leading-5.text-gray-700 "Invoice Issue Date"]
        [:dd.sm:text-sm.sm:leading-5 "31 August 2020"]]]]

     [:div.pt-6.mt-6.grid.grid-cols-1.row-gap-6.col-gap-4.sm:grid-cols-6.border-t.border-gray-200

      [-buttons/span-button {::-buttons/text "Save"
                             ::-buttons/serious? true}]]

     ]]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(s/def ::cfg (s/keys :req []
                     :opt []))

;;;;;;;;;;;;;;;;;;;;

(deftype NewPayment
    [cfg]
    -ui-common/IWorkspaceItem
  (workspace-item-title [_] "Issue New Invoice")
  (workspace-item-actions [_] [{::-buttons/text "Cancel"}
                               {::-buttons/text "Show help"}
                               {::-buttons/text "Save"
                                ::-buttons/serious? true}])
  (workspace-item-ui [this]
    (ui cfg)))

(defn new-payment
  [cfg]
  (let [default-config {}]
    (NewPayment. (merge default-config cfg))))
