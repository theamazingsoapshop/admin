(ns za.co.theamazingsoapshop.admin.ui.common)

;; renders a widget that should fit into the main workspace
(defmulti render-workspace (fn [t & _] t))

(defmethod render-workspace :default
  [t system] [:div [:p "Default component."]])

