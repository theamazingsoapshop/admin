(ns za.co.theamazingsoapshop.admin.ui.common)

(def color "green")

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

(defprotocol ISidebarComponent
  (title [this] "Provides the title text for the sidebar component")
  (component [this] "Provides the main UI for the sidebar component"))

