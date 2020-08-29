(ns za.co.theamazingsoapshop.admin.ui.payments
  (:require [za.co.theamazingsoapshop.admin.ui.svg :as -svg]
            [za.co.theamazingsoapshop.admin.ui.common :as -ui-common]
            [za.co.theamazingsoapshop.admin.ui.sidebar :as -sidebar]
            [za.co.theamazingsoapshop.admin.ui.tables :as -tables]
            [za.co.theamazingsoapshop.admin.ui.headings :as -headings]
            [za.co.theamazingsoapshop.admin.ui.sidebar :as -sidebar]
            [reagent.core :as r]
            [clojure.string :as str]
            [integrant.core :as ig]
            [lambdaisland.glogi :as log]
            [cljs-gravatar.core :as gravatar]))

(defn open-item-btn [{:keys [text on-click]
                      :as item}]
  [:span.inline-flex.rounded-md.shadow-sm
   [:button
    {:class (str " inline-flex items-center px-4 py-2 border border-transparent text-sm leading-6 font-medium rounded-md text-white"
                 " bg-" -ui-common/color "-600"
                 " hover:bg-" -ui-common/color "-500"
                 " focus:outline-none"
                 " focus:border-" -ui-common/color "-700"
                 " focus:shadow-outline- " -ui-common/color
                 " active:bg-" -ui-common/color "-700"
                 " transition ease-in-out duration-150")
     :on-click #(do (log/debug ::clicked (str %))
                    (on-click item))
     :type "button"} text]])

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

(defn overview-cards [& cards]
  [:div.grid.grid-cols-1.gap-5.sm:grid-cols-2.lg:grid-cols-3 cards])

(defn section-header [content]
  [:h2.text-lg.leading-6.font-medium.text-cool-gray-900 content])

(defn section-content [& content]
  [:div.mt-2 content])

(defn narrow-invoice-list-item [{:person/keys [firstname lastname email]
                                 :invoice/keys [amount currency-code currency-symbol date]
                                 :as item
                                 on-click :on-click}]
  [:a.block.px-4.py-4.bg-white.hover:bg-cool-gray-50
   {:on-click #(do (log/debug ::clicked (pr-str item))
                   (when on-click (on-click item)))}
   [:div.flex.items-center.space-x-4
    [:div.flex-1.flex.space-x-2.truncate
     [-svg/icon-for ::narrow-invoice-list-item " flex-shrink-0 h-5 w-5 text-cool-gray-400"]
     [:div.text-cool-gray-500.text-sm.truncate
      [:p.truncate (str "Payment to " firstname " " lastname)]
      [:p
       [:span.text-cool-gray-900.font-bold (str currency-symbol " " amount)]
       (str " " currency-code)]
      [:p (str "Issued on " date)]]]
    [-svg/chevron-right " flex-shrink-0 h-5 w-5 text-cool-gray-400"]]])

(defn invoice-item-ui
  [{:person/keys  [firstname lastname email]
    :invoice/keys [amount currency-code currency-symbol date]
    :as           item}]
  (log/debug ::invoice-item-component item)
  [:div
   [:div.mt-5.border-t.border-gray-200.pt-5
    [:dl
     [:div.sm:grid.sm:grid-cols-3.sm:gap-4
      [:dt.text-sm.leading-5.font-medium.text-gray-500 "Full name"]
      [:dd.mt-1.text-sm.leading-5.text-gray-900.sm:mt-0.sm:col-span-2 (str firstname " " lastname)]]
     [:div.mt-8.sm:grid.sm:mt-5.sm:grid-cols-3.sm:gap-4.sm:border-t.sm:border-gray-200.sm:pt-5
      [:dt.text-sm.leading-5.font-medium.text-gray-500 "Email address"]
      [:dd.mt-1.text-sm.leading-5.text-gray-900.sm:mt-0.sm:col-span-2 email]]
     [:div.mt-8.sm:grid.sm:mt-5.sm:grid-cols-3.sm:gap-4.sm:border-t.sm:border-gray-200.sm:pt-5
      [:dt.text-sm.leading-5.font-medium.text-gray-500 "Gravatar"]
      [:dd.mt-1.text-sm.leading-5.text-gray-900.sm:mt-0.sm:col-span-2
       [:img {:src (gravatar/url email)}]]]
     [:div.mt-8.sm:grid.sm:mt-5.sm:grid-cols-3.sm:gap-4.sm:border-t.sm:border-gray-200.sm:pt-5
      [:dt.text-sm.leading-5.font-medium.text-gray-500 "Amount"]
      [:dd.mt-1.text-sm.leading-5.text-gray-900.sm:mt-0.sm:col-span-2 (str currency-symbol " "
                                                                           amount " "
                                                                           currency-code)]]
     [:div.mt-8.sm:grid.sm:mt-5.sm:grid-cols-3.sm:gap-4.sm:border-t.sm:border-gray-200.sm:pt-5
      [:dt.text-sm.leading-5.font-medium.text-gray-500 "Issue date"]
      [:dd.mt-1.text-sm.leading-5.text-gray-900.sm:mt-0.sm:col-span-2 date]]]]])

(defn invoice-item-sidebar-title
  [_] "Invoice Detail")

(deftype InvoiceItem
    [item]
  -ui-common/ISidebarComponent
  (sidebar-title [this] (invoice-item-sidebar-title item))
  (sidebar-component [this] (invoice-item-ui item)))

(defn invoices [{:as system
                 :keys [::-sidebar/sidebar]}]
  (log/debug ::system system)
  (try
    (let [view-invoice-fn (fn [invoice-item]
                            (log/info ::viewing-invoice invoice-item)
                            (-sidebar/show sidebar
                                           (InvoiceItem. invoice-item)))
          invoices [{:person/firstname        "Pieter"
                     :person/lastname         "Breed"
                     :person/email            "pieter@pb.co.za"
                     :invoice/amount          "1234"
                     :invoice/currency-code   "ZAR"
                     :invoice/currency-symbol "R"
                     :invoice/date            "20 November 1980"}
                    {:person/firstname        "Andrew"
                     :person/lastname         "Jones"
                     :person/email            "andrew@jones.co.za"
                     :invoice/amount          "7655"
                     :invoice/currency-code   "ZAR"
                     :invoice/currency-symbol "R"
                     :invoice/date            "4 July 1999"}
                    {:person/firstname        "Priscilla"
                     :person/lastname         "of the Desert, Queen"
                     :person/email            "cillar@queen.com"
                     :invoice/amount          "675"
                     :invoice/currency-code   "ZAR"
                     :invoice/currency-symbol "R"
                     :invoice/date            "20 April 1981"}]]
      [:div

       ;; invoices for narrow screens, presented as unordered list items
       [:div.shadow.sm:hidden
        [:ul.mt-2.divide-y.divide-cool-gray-200.overflow-hidden.shadow.sm:hidden
         (for [{:as invoice
                :person/keys [firstname lastname]
                :invoice/keys [date]} invoices]
           ^{:key (str date "narrow_invoice_item_" firstname "-" lastname)}
           [:li (narrow-invoice-list-item (assoc invoice :on-click view-invoice-fn))])]

        [:nav.bg-white.px-4.py-3.flex.items-center.justify-between.border-t.border-cool-gray-200
         [:div.flex-1.flex.justify-between
          [:a.relative.inline-flex.items-center.px-4.py-2.border.border-cool-gray-300.text-sm.leading-5.font-medium.rounded-md.text-cool-gray-700.bg-white.hover:text-cool-gray-500.focus:outline-none.focus:shadow-outline-blue.focus:border-blue-300.active:bg-cool-gray-100.active:text-cool-gray-700.transition.ease-in-out.duration-150
           {:href "#"}
           "\n                Previous\n              "]
          [:a.ml-3.relative.inline-flex.items-center.px-4.py-2.border.border-cool-gray-300.text-sm.leading-5.font-medium.rounded-md.text-cool-gray-700.bg-white.hover:text-cool-gray-500.focus:outline-none.focus:shadow-outline-blue.focus:border-blue-300.active:bg-cool-gray-100.active:text-cool-gray-700.transition.ease-in-out.duration-150
           {:href "#"}
           "\n                Next\n              "]]]]

       ;; invoices for wide screens, as a table
       [:div.hidden.sm:block
        [:div.flex.flex-col
         [:div.-my-2.py-2.overflow-x-auto.sm:-mx-6.sm:px-6.lg:-mx-8.lg:px-8
          [-tables/make-table {::-tables/table-id  "payments-invoices"
                               ::-tables/columns   [{::-tables/cell-fn       #(str (:person/firstname %) " " (:person/lastname %))
                                                     ::-tables/column-header "name"}
                                                    {::-tables/cell-fn        (comp str :person/email)
                                                     ::-tables/column-header  "email"
                                                     ::-tables/header-classes "hidden lg:table-cell"
                                                     ::-tables/cell-classes   "hidden lg:table-cell"}
                                                    {::-tables/cell-fn        (fn [x]
                                                                                [:span
                                                                                 [:span (:invoice/currency-symbol x)] " "
                                                                                 [:span.font-bold (:invoice/amount x)] " "
                                                                                 [:span (:invoice/currency-code x)]])
                                                     ::-tables/column-header "amount"}
                                                    {::-tables/cell-fn       (comp str :invoice/date)
                                                     ::-tables/column-header "date"}
                                                    {::-tables/cell-fn       (fn [x] [open-item-btn {:text     "Open"
                                                                                                     :on-click #(view-invoice-fn x)}])
                                                     ::-tables/column-header "action"}]
                               ::-tables/row-data  invoices
                               ::-tables/row-id-fn #(str (:person/firstname %) " " (:person/lastname %))}]]]]])
    (catch :default e
      (let [s (with-out-str (cljs.pprint/pprint e))]
        (log/error ::error-handler s)
        (:div [:p s])))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn narrow-payment-list-item [{:person/keys [firstname lastname email]
                                 :payment/keys [amount currency-code currency-symbol date]}]
  [:a.block.px-4.py-4.bg-white.hover:bg-cool-gray-50
   {:href "#"}
   [:div.flex.items-center.space-x-4
    [:div.flex-1.flex.space-x-2.truncate
     [-svg/icon-for ::narrow-invoice-list-item " flex-shrink-0 h-5 w-5 text-cool-gray-400"]
     [:div.text-cool-gray-500.text-sm.truncate
      [:p.truncate (str  firstname " " lastname)]
      [:p
       [:span.text-cool-gray-900.font-bold (str currency-symbol " " amount)]
       (str " " currency-code)]
      [:p (str "Paid on " date)]]]
    [-svg/chevron-right " flex-shrink-0 h-5 w-5 text-cool-gray-400"]]])

(defn payments []
  (let [payments [{:person/firstname "Pieter"
                   :person/lastname "Breed"
                   :person/email "pieter@pb.co.za"
                   :payment/amount "1500"
                   :payment/currency-code "ZAR"
                   :payment/currency-symbol "R"
                   :payment/date "19 November 1980"}
                  {:person/firstname "Kosie"
                   :person/lastname "van der Merwe"
                   :person/email "kosie@dieplaas.co.za"
                   :payment/amount "1050"
                   :payment/currency-code "ZAR"
                   :payment/currency-symbol "R"
                   :payment/date "29 February 2929"}]]
    [:div

     ;; payment items for narrow screens, presented as unordered list items
     [:div.shadow.sm:hidden
      [:ul.mt-2.divide-y.divide-cool-gray-200.overflow-hidden.shadow.sm:hidden
       (for [{:as payment
              :person/keys [firstname lastname]
              :invoice/keys [date]} payments]
         ^{:key (str date "narrow_invoice_item_" firstname "-" lastname)}
         [:li (narrow-payment-list-item payment)])]

      [:nav.bg-white.px-4.py-3.flex.items-center.justify-between.border-t.border-cool-gray-200
       [:div.flex-1.flex.justify-between
        [:a.relative.inline-flex.items-center.px-4.py-2.border.border-cool-gray-300.text-sm.leading-5.font-medium.rounded-md.text-cool-gray-700.bg-white.hover:text-cool-gray-500.focus:outline-none.focus:shadow-outline-blue.focus:border-blue-300.active:bg-cool-gray-100.active:text-cool-gray-700.transition.ease-in-out.duration-150
         {:href "#"}
         "\n                Previous\n              "]
        [:a.ml-3.relative.inline-flex.items-center.px-4.py-2.border.border-cool-gray-300.text-sm.leading-5.font-medium.rounded-md.text-cool-gray-700.bg-white.hover:text-cool-gray-500.focus:outline-none.focus:shadow-outline-blue.focus:border-blue-300.active:bg-cool-gray-100.active:text-cool-gray-700.transition.ease-in-out.duration-150
         {:href "#"}
         "\n                Next\n              "]]]]

     ;; payments for wide screens, as a table
     [:div.hidden.sm:block
      [:div.flex.flex-col
       [:div.-my-2.py-2.overflow-x-auto.sm:-mx-6.sm:px-6.lg:-mx-8.lg:px-8
        [-tables/make-table {::-tables/table-id  "payments"
                               ::-tables/columns   [{::-tables/cell-fn       #(str (:person/firstname %) " " (:person/lastname %))
                                                     ::-tables/column-header "name"}

                                                    {::-tables/cell-fn        (comp str :person/email)
                                                     ::-tables/column-header  "email"
                                                     ::-tables/header-classes "hidden lg:table-cell"
                                                     ::-tables/cell-classes   "hidden lg:table-cell"}

                                                    {::-tables/cell-fn        (fn [x]
                                                                                [:span
                                                                                 [:span (:payment/currency-symbol x)] " "
                                                                                 [:span.font-bold (:payment/amount x)] " "
                                                                                 [:span (:payment/currency-code x)]])
                                                     ::-tables/column-header "amount"}

                                                    {::-tables/cell-fn       (comp str :payment/date)
                                                     ::-tables/column-header "date"}

                                                    {::-tables/cell-fn       (fn [x] [open-item-btn {:text     "Open"
                                                                                                     :on-click identity}])
                                                     ::-tables/column-header "action"}]
                               ::-tables/row-data  payments
                             ::-tables/row-id-fn #(str (:person/firstname %) " " (:person/lastname %))}]]]]]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmethod -ui-common/render-workspace ::payments
  [_ {system ::payments
      :keys [::-sidebar/sidebar]}]
  [:div
   [:div
    [section-header "Overview"]
    (let [icon-classes "h-6 w-6 text-cool-gray-400"]
      [section-content
       [overview-cards
        (overview-card {:title "Completed (last 30 days)"
                        :value "R 4 345.00"
                        :icon [-svg/icon-for ::completed icon-classes]})
        (overview-card {:title "Pending"
                        :value "R 10 223.00"
                        :icon [-svg/icon-for ::pending icon-classes]})]])]
   [:div.my-4
    [section-header "Pending Transactions"]
    [section-content [invoices system]]]

   [:div.my-4
    [section-header "Completed Payments"]
    [section-content [payments]]]])



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmethod ig/init-key ::payments
  [_ cfg] cfg)
