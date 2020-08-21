(ns za.co.theamazingsoapshop.admin.ui.sidebar
  (:require [za.co.theamazingsoapshop.admin.ui.common :as -ui-common]
            [integrant.core :as ig]
            [reagent.core :as r]
            [lambdaisland.glogi :as log]
            [za.co.theamazingsoapshop.admin.ui.svg :as -svg]))



(defprotocol ISidebar
  (get-state [this] "Gets the reagent atom containing the state for the UI of this component")
  (show [this item] "Sets the component that must be displayde in the sidebar and opens it.")
  (sidebar-hidden? [this] "Returs true if the sidebar must be HIDDEN. False if it must be shown. Goes with .hidden CSS class")
  (close-sidebar [this] "Closes / Cancels the current sidebar"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn sidebar [{system ::sidebar}]
  (log/debug ::sidebar-component system)
  (let [state (get-state system)]
    (fn []
      (let [{sidebar-component ::sidebar-component
             :as               state} @state]
        (log/debug ::sidebar-rendering state)
        (let [is-hidden (get state :hidden false)]
          [:div.fixed.inset-0.overflow-hidden
           {:class (str (when (sidebar-hidden? system) "hidden"))}
           [:div.absolute.inset-0.overflow-hidden
            ;; "<!--\n      Background overlay, show/hide based on slide-over state.\n\n      Entering: \"ease-in-out duration-500\"\n        From: \"opacity-0\"\n        To: \"opacity-100\"\n      Leaving: \"ease-in-out duration-500\"\n        From: \"opacity-100\"\n        To: \"opacity-0\"\n    -->"
            [:div.absolute.inset-0.bg-gray-500.bg-opacity-75.transition-opacity]
            [:section.absolute.inset-y-0.right-0.pl-10.max-w-full.flex
             ;; "<!--\n        Slide-over panel, show/hide based on slide-over state.\n\n        Entering: \"transform transition ease-in-out duration-500 sm:duration-700\"\n          From: \"translate-x-full\"\n          To: \"translate-x-0\"\n        Leaving: \"transform transition ease-in-out duration-500 sm:duration-700\"\n          From: \"translate-x-0\"\n          To: \"translate-x-full\"\n      -->"
             [:div.w-screen.max-w-md
              [:div.h-full.flex.flex-col.space-y-6.py-6.bg-white.shadow-xl.overflow-y-scroll
               [:header.px-4.sm:px-6
                [:div.flex.items-start.justify-between.space-x-3
                 [:h2.text-lg.leading-7.font-medium.text-gray-900
                  (when sidebar-component
                    (-ui-common/sidebar-title sidebar-component))]
                 [:div.h-7.flex.items-center
                  [:button.text-gray-400.hover:text-gray-500.transition.ease-in-out.duration-150
                   {:aria-label "Close sidebar"
                    :on-click #(close-sidebar system)}
                   [-svg/close-sidebar "h-6 w-6"]]]]]
               [:div.relative.flex-1.px-4.sm:px-6
                (when sidebar-component
                  (-ui-common/sidebar-component sidebar-component))]]]]]])))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(deftype Sidebar
    [cfg]
  ISidebar

  (get-state
    [_]
    (::state cfg))

  (show
    [_ item]
    (if (satisfies? -ui-common/ISidebarComponent item)
      (swap! (::state cfg)
             (fn [old-state]
               (assoc old-state
                      ::sidebar-component item)))
      (log/warn ::unable-to-set-sidebar-to-ISidebarComponent item)))

  (sidebar-hidden? [_] (-> cfg ::state deref ::sidebar-component nil?))

  (close-sidebar
    [_]
    (log/debug ::close-sidebar ::called
               ::cfg cfg)
    (swap! (::state cfg)
           (fn [old-state]
             (dissoc old-state ::sidebar-component)))))

(defmethod ig/init-key ::sidebar
  [_ cfg]
  (let [state (r/atom {:hidden true})]
    (Sidebar. (assoc cfg ::state state))))

