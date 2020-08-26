(ns za.co.theamazingsoapshop.admin.ui.tables
  (:require [lambdaisland.glogi :as log]
            [clojure.spec.alpha :as s]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(s/def ::id keyword?)

(s/def ::react-id
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
   (s/map-of keyword? identity)))

(s/def ::column-header string?)
(s/def ::header-classes string?)
(s/def ::cell-classes string?)

(s/def ::cell-fn (s/or :fn fn?
                       :kw keyword?))

(s/def ::columns
  (s/coll-of
   (s/keys :req [::column-header
                 ::cell-fn]
           :opt [::header-classes
                 ::cell-classes])
   :into []))

(s/def ::table-spec
  (s/keys :req [::columns
                ::row-data
                ::table-id
                ::row-id-fn]
          :opt []))

;;;;;;;;;;;;;;;;;;;;

(comment

  (def test-rows [{:a 1 :b 2 :c 3}
                  {:a 4 :b 5 :c 6}
                  {:a 7 :b 8 :c 9}])
  #_(maps->rows {::id-fn #(->> % :a (str "row-"))
               ::col-fns {:a :a
                          :c (partial str "bb-")
                          :b str}}
              test-rows)

  )

;;;;;;;;;;;;;;;;;;;;

(comment

  (def test-rows [{:a 1 :b 2 :c 3}
                  {:a 4 :b 5 :c 6}
                  {:a 7 :b 8 :c 9}])

  (make-table {::table-id "second-testing-table"
               ::columns [{::cell-fn :b
                           ::column-header "Bee"}
                          {::cell-fn (comp str :a)
                           ::column-header "Ah"}
                          {::cell-fn #(str (:a %)
                                           " "
                                           (:b %))
                           ::column-header "See"}]
               ::row-data test-rows
               ::row-id-fn #(pr-str %)})
  )

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn make-table
  [table-spec]
  (let [table-spec* (s/conform ::table-spec table-spec)]
    (if (= ::s/invalid table-spec*)
      (do
        (log/error ::invalid-tabla-spec (s/explain-data ::table-spec table-spec))
        [:div
         [:h1 "Invalid table"]
         [:p (with-out-str (cljs.pprint/pprint (s/explain-data ::table-spec table-spec)))]])
      (let [{::keys [row-data columns table-id row-id-fn]
             :as table-spec} table-spec
            ordered-columns (map-indexed vector columns)]
        [:div.align-middle.inline-block.min-w-full.shadow.overflow-hidden.sm:rounded-lg.border-b.border-gray-200
         [:table.min-w-full.divide-y.divide-gray-200
          [:thead
           [:tr
            (for [[i {::keys [column-header header-classes]
                      :as column}] ordered-columns]
              (do
                (log/debug ::column column)
                ^{:key (str table-id "-column-" i)}
                [:th.px-6.py-3.bg-gray-50.text-left.text-xs.leading-4.font-medium.text-gray-500.uppercase.tracking-wider
                 {:class (str (when header-classes header-classes))}
                 column-header]))]]
          [:tbody.bg-white.divide-y.divide-gray-200
           (for [row row-data]
             ^{:key (row-id-fn row)}
             [:tr
              (for [[i {::keys [cell-classes cell-fn]}] ordered-columns]

                ^{:key (str table-id "-row-" (row-id-fn row) "-coll-" i)}
                [:td.px-6.py-4.whitespace-no-wrap.text-sm.leading-5.text-gray-500
                 {:class (str cell-classes)}
                 (cell-fn row)])])]]]))))

