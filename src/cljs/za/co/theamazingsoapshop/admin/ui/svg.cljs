(ns za.co.theamazingsoapshop.admin.ui.svg)

(defn credit-card [classes]
  [:svg
   {:stroke "currentColor"
    :viewBox "0 0 24 24"
    :fill "none"
    :class classes}
   [:path
    {:d
     "M3 10h18M7 15h1m4 0h1m-7 4h12a3 3 0 003-3V8a3 3 0 00-3-3H6a3 3 0 00-3 3v8a3 3 0 003 3z",
     :stroke-width "2",
     :stroke-linejoin "round",
     :stroke-linecap "round"}]])

(defn team [classes]
  [:svg
   {:stroke "currentColor"
    :viewBox "0 0 24 24"
    :fill "none"
    :class classes}
   [:path
    {:d
     "M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z",
     :stroke-width "2",
     :stroke-linejoin "round",
     :stroke-linecap "round"}]])

(defn logout [classes]
  [:svg
   {:stroke "currentColor"
    :viewBox "0 0 24 24"
    :fill "none"
    :class classes}
   [:path
    {:d
     "M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
     :stroke-width "2",
     :stroke-linejoin "round",
     :stroke-linecap "round"}]])

(defn close-sidebar [classes]
  [:svg
   {:viewBox "0 0 24 24"
    :fill "none"
    :stroke "currentColor"
    :class classes}
   [:path
    {:d "M6 18L18 6M6 6l12 12",
     :stroke-width "2",
     :stroke-linejoin "round",
     :stroke-linecap "round"}]])

(defn open-sidebar [classes]
  [:svg {:class classes
         :viewBox "0 0 24 24"
         :fill "none"
         :stroke "currentColor"}
     [:path
      {:d "M4 6h16M4 12h16M4 18h16",
       :stroke-width "2",
       :stroke-linejoin "round",
       :stroke-linecap "round"}]]
)

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmulti icon-for (fn [key classes] key))
(defmethod icon-for :za.co.theamazingsoapshop.admin.ui.app/payments [_ classes] [credit-card classes])
(defmethod icon-for :za.co.theamazingsoapshop.admin.ui.app/team [_ classes] [team classes])
(defmethod icon-for :za.co.theamazingsoapshop.admin.ui.app/logout [_ classes] [logout classes])
