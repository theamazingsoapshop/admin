(ns za.co.theamazingsoapshop.admin.ui.common)

(defn first-arg [t & _] t)

;; renders a widget that should fit into the main workspace
(defmulti render-workspace first-arg)

(defmethod render-workspace :default
  [t system] [:div [:p "Default component."]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;; renders the component inside the right-hand-side sidebar
(defmulti render-sidebar first-arg)

(defmethod render-sidebar :default
  [t system] [:div [:p "Default sidebar"]])

