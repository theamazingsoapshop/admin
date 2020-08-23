(ns za.co.theamazingsoapshop.admin.ui.tables
  (:require [lambdaisland.glogi :as log]
            [clojure.spec.alpha :as s]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(s/def ::react-ad
  (s/and string?
         #(not (or (nil? %)
                   (= 0 (count %))))))
(s/def ::row-id ::react-id)
(s/def ::table-id ::react-id)

(s/def ::cells
  (s/coll-of string?
             :into []))

(s/def ::row-data
  (s/coll-of 
   (s/keys :req [::row-id
                 ::cells]
           :opt [])))

(s/def ::column-header string?)
(s/def ::header-classes string?)
(s/def ::cell-classes string?)

(s/def ::columns
  (s/coll-of
   (s/keys :req [::column-header]
           :opt [::header-classes
                 ::cell-classes])
   :into []))

(s/def ::table-spec
  (s/keys :req [::columns
                ::row-data
                ::table-id]
          :opt []))

(comment

  (def test-table {::table-id "testing-table"
                   ::row-data [{::row-id "1"
                                ::cells ["1" "2" "3"]}
                               {::row-id "2"
                                ::cells ["4" "5" "6"]}
                               {::row-id "3"
                                ::cells ["7" "8" "9"]}]
                   ::columns [{::column-header "Column 1"}
                              {::column-header "Column 2"
                               ::header-classes "text-red-400"}
                              {::column-header "Column 3"
                               ::cell-classes " text-blue-500"}]})

  (make-table test-table)
  (s/conform ::table-spec
             )

  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn make-table
  [{:as table-spec
    :keys [columns rows table-id]}]
  (let [{::keys [row-data columns]
         :as table-spec}
        (s/conform ::table-spec table-spec)]
    (if-not (= ::s/invalid table-spec)
      [:div.align-middle.inline-block.min-w-full.shadow.overflow-hidden.sm:rounded-lg.border-b.border-gray-200
       [:table.min-w-full.divide-y.divide-gray-200
        [:thead
         [:tr
          (for [{::keys [column-header header-classes]}
                columns]
            ^{:key (str table-id "-column-" column-header)}
            [:th.px-6.py-3.bg-gray-50.text-left.text-xs.leading-4.font-medium.text-gray-500.uppercase.tracking-wider
             {:class (str (when header-classes header-classes))}
             column-header])]]
        [:tbody.bg-white.divide-y.divide-gray-200
         (for [{:as row
                ::keys [row-id cells]}
               row-data]
           ^{:key (str table-id "-row-" row-id)}
           [:tr
            (for [[cell {::keys [column-header cell-classes]}] (map list cells columns)]
              ^{:key (str table-id "-row-" row-id "-coll-" column-header)}
              [:td.px-6.py-4.whitespace-no-wrap.text-sm.leading-5.text-gray-500
               {:class (str cell-classes)}
               cell])])]]]
      (let [warning (s/explain-data ::table-spec table-spec)]
        (log/warn ::unable-to-can-a-table warning)
        [:p warning]))))

