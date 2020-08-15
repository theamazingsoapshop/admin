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
       :stroke-linecap "round"}]])

(defn cog [classes]
  [:svg
   {:stroke "currentColor"
    :viewBox "0 0 24 24"
    :fill "none"
    :class classes}
   [:path
    {:d
     "M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z",
     :stroke-width "2",
     :stroke-linejoin "round",
     :stroke-linecap "round"}]
   [:path
    {:d "M15 12a3 3 0 11-6 0 3 3 0 016 0z",
     :stroke-width "2",
     :stroke-linejoin "round",
     :stroke-linecap "round"}]])

(defn ticket [classes]
  [:svg {:stroke "currentColor"
         :viewbox "0 0 24 24"
         :fill "none"
         :class classes}
   [:path
    {:d
     "M15 5v2m0 4v2m0 4v2M5 5a2 2 0 00-2 2v3a2 2 0 110 4v3a2 2 0 002 2h14a2 2 0 002-2v-3a2 2 0 110-4V7a2 2 0 00-2-2H5z",
     :stroke-width "2",
     :stroke-linejoin "round",
     :stroke-linecap "round"}]])

(defn document-text [classes]
  [:svg
   {:stroke "currentColor", :viewBox "0 0 24 24", :fill "none"
    :class classes}
   [:path
    {:d
     "M9 12h6m-6 4h6m2 5H7a2 2 0 01-2-2V5a2 2 0 012-2h5.586a1 1 0 01.707.293l5.414 5.414a1 1 0 01.293.707V19a2 2 0 01-2 2z",
     :stroke-width "2",
     :stroke-linejoin "round",
     :stroke-linecap "round"}]])


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmulti icon-for (fn [key classes] key))
(defmethod icon-for :default [_ classes] [cog classes])
(defmethod icon-for :za.co.theamazingsoapshop.admin.ui.payments/payments [_ classes] [credit-card classes])
(defmethod icon-for :za.co.theamazingsoapshop.admin.ui.app/team [_ classes] [team classes])
(defmethod icon-for :za.co.theamazingsoapshop.admin.ui.app/logout [_ classes] [logout classes])
(defmethod icon-for :za.co.theamazingsoapshop.admin.ui.payments/completed [_ classes] [credit-card classes])
(defmethod icon-for :za.co.theamazingsoapshop.admin.ui.payments/pending [_ classes] [document-text classes])

