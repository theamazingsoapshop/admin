(ns za.co.theamazingsoapshop.admin.ui.app
  (:require #_["/com/tailwindui/Transition" :as tw-transition :refer [Transition]]
            ["/demo/bar" :as bar :refer [myComponent]]
            [za.co.theamazingsoapshop.admin.ui.svg :as -svg]
            [reagent.core :as r]
            [clojure.string :as str]
            [integrant.core :as ig]
            [lambdaisland.glogi :as log]))

(defn mobile-menu-item
  [{:keys [text icon selected? key]}]
  (let [text (if text text (-> key name str/capitalize))
        base-classes "group flex items-center px-2 py-2 text-base leading-6 font-medium rounded-md transition ease-in-out duration-150"
        selected-classes "text-white bg-indigo-900 focus:outline-none focus:bg-indigo-700"
        unselected-classes "text-indigo-300 hover:text-white hover:bg-indigo-700 focus:outline-none focus:text-white focus:bg-indigo-700"]
    [:a
     {:class (str base-classes " " (if selected? selected-classes unselected-classes))
      :href "#"}
     icon
     text]))

(defn menu-items
  []
  (let [classes "mr-4 h-6 w-6 text-indigo-400 group-hover:text-indigo-300 group-focus:text-indigo-300 transition ease-in-out duration-150"]
    [{:text "Payments 2"
      :key ::payments
      :icon [-svg/credit-card classes]}
     {:key ::team
      :icon [-svg/team classes]}
     {:key ::logout
      :text "Instant logout"
      :icon [-svg/logout classes]}]))

(defn ui [{system ::app}]
  (log/debug ::system system)
  (let [state (::state system)]
    (log/debug ::system system
               ::state state)
    (fn []
      [:div.h-screen.flex.overflow-hidden.bg-gray-100
       (comment "<!-- Off-canvas menu for mobile -->")
       [:div.md:hidden
        {:class (str (when (-> @state (:small-screen-menu-hidden false)) "hidden"))}
        [:div.fixed.inset-0.flex.z-40
         (comment "<!--\n        Off-canvas menu overlay, show/hide based on off-canvas menu state.\n\n        Entering: \"transition-opacity ease-linear duration-300\"\n          From: \"opacity-0\"\n          To: \"opacity-100\"\n        Leaving: \"transition-opacity ease-linear duration-300\"\n          From: \"opacity-100\"\n          To: \"opacity-0\"\n      -->")
         [:div.fixed.inset-0
          [:div.absolute.inset-0.bg-gray-600.opacity-75]]
         ;;"<!--\n        Off-canvas menu, show/hide based on off-canvas menu state.\n\n        Entering: \"transition ease-in-out duration-300 transform\"\n          From: \"-translate-x-full\"\n          To: \"translate-x-0\"\n        Leaving: \"transition ease-in-out duration-300 transform\"\n          From: \"translate-x-0\"\n          To: \"-translate-x-full\"\n      -->"
         [:div.relative.flex-1.flex.flex-col.max-w-xs.w-full.bg-indigo-800
          [:div.absolute.top-0.right-0.-mr-14.p-1
           [:button.flex.items-center.justify-center.h-12.w-12.rounded-full.focus:outline-none.focus:bg-gray-600
            {:aria-label "Close sidebar"
             :on-click #(swap! state assoc :small-screen-menu-hidden true)}
            [-svg/close-sidebar "h-6 w-6 text-white"]]]
          [:div.flex-1.h-0.pt-5.pb-4.overflow-y-auto
           [:div.flex-shrink-0.flex.items-center.px-4
            [:h1.font-medium.text-base.text-white "Admin"]]
           [:nav.mt-5.px-2.space-y-1
            (for [menu-item (menu-items)]
              ^{:key (str "menu-item-" (-> menu-item :key name))}
              [mobile-menu-item (assoc menu-item :selected? (= (:key menu-item)
                                                               (-> @state :selected-menu-key)))])]]
          [:div.flex-shrink-0.flex.border-t.border-indigo-700.p-4
           [:a.flex-shrink-0.group.block.focus:outline-none {:href "#"}]
           [:div.flex.items-center
            [:div
             [:img.inline-block.h-10.w-10.rounded-full
              {:alt "",
               :src
               "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"}]]
            [:div.ml-3
             [:p.text-base.leading-6.font-medium.text-white
              "\n                  Tom Cook\n                "]
             [:p.text-sm.leading-5.font-medium.text-indigo-300.group-hover:text-indigo-100.group-focus:underline.transition.ease-in-out.duration-150
              "\n                  View profile\n                "]]]]]
         [:div.flex-shrink-0.w-14
          ;;"<!-- Force sidebar to shrink to fit close icon -->"
          ]]]
       ;;"<!-- Static sidebar for desktop -->"
       [:div.hidden.md:flex.md:flex-shrink-0
        [:div.flex.flex-col.w-64
         ;;"<!-- Sidebar component, swap this element with another sidebar if you like -->"
         [:div.flex.flex-col.h-0.flex-1.bg-indigo-800
          [:div.flex-1.flex.flex-col.pt-5.pb-4.overflow-y-auto
           [:div.flex.items-center.flex-shrink-0.px-4
            [:img.h-8.w-auto
             {:alt "Workflow",
              :src
              "https://tailwindui.com/img/logos/workflow-logo-on-brand.svg"}]]
           [:nav.mt-5.flex-1.px-2.bg-indigo-800.space-y-1
            [:a.group.flex.items-center.px-2.py-2.text-sm.leading-5.font-medium.text-white.rounded-md.bg-indigo-900.focus:outline-none.focus:bg-indigo-700.transition.ease-in-out.duration-150
             {:href "#"}
             [:svg.mr-3.h-6.w-6.text-indigo-400.group-focus:text-indigo-300.transition.ease-in-out.duration-150
              {:stroke "currentColor", :viewBox "0 0 24 24", :fill "none"}
              [:path
               {:d
                "M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6",
                :stroke-width "2",
                :stroke-linejoin "round",
                :stroke-linecap "round"}]]
             "\n              Dashboard\n            "]
            [:a.group.flex.items-center.px-2.py-2.text-sm.leading-5.font-medium.text-indigo-300.rounded-md.hover:text-white.hover:bg-indigo-700.focus:outline-none.focus:text-white.focus:bg-indigo-700.transition.ease-in-out.duration-150
             {:href "#"}
             [:svg.mr-3.h-6.w-6.text-indigo-400.group-hover:text-indigo-300.group-focus:text-indigo-300.transition.ease-in-out.duration-150
              {:stroke "currentColor", :viewBox "0 0 24 24", :fill "none"}
              [:path
               {:d
                "M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z",
                :stroke-width "2",
                :stroke-linejoin "round",
                :stroke-linecap "round"}]]
             "\n              Team\n            "]
            [:a.group.flex.items-center.px-2.py-2.text-sm.leading-5.font-medium.text-indigo-300.rounded-md.hover:text-white.hover:bg-indigo-700.focus:outline-none.focus:text-white.focus:bg-indigo-700.transition.ease-in-out.duration-150
             {:href "#"}
             [:svg.mr-3.h-6.w-6.text-indigo-400.group-hover:text-indigo-300.group-focus:text-indigo-300.transition.ease-in-out.duration-150
              {:stroke "currentColor", :viewBox "0 0 24 24", :fill "none"}
              [:path
               {:d
                "M3 7v10a2 2 0 002 2h14a2 2 0 002-2V9a2 2 0 00-2-2h-6l-2-2H5a2 2 0 00-2 2z",
                :stroke-width "2",
                :stroke-linejoin "round",
                :stroke-linecap "round"}]]
             "\n              Projects\n            "]
            [:a.group.flex.items-center.px-2.py-2.text-sm.leading-5.font-medium.text-indigo-300.rounded-md.hover:text-white.hover:bg-indigo-700.focus:outline-none.focus:text-white.focus:bg-indigo-700.transition.ease-in-out.duration-150
             {:href "#"}
             [:svg.mr-3.h-6.w-6.text-indigo-400.group-hover:text-indigo-300.group-focus:text-indigo-300.transition.ease-in-out.duration-150
              {:stroke "currentColor", :viewBox "0 0 24 24", :fill "none"}
              [:path
               {:d
                "M8 7V3m8 4V3m-9 8h10M5 21h14a2 2 0 002-2V7a2 2 0 00-2-2H5a2 2 0 00-2 2v12a2 2 0 002 2z",
                :stroke-width "2",
                :stroke-linejoin "round",
                :stroke-linecap "round"}]]
             "\n              Calendar\n            "]
            [:a.group.flex.items-center.px-2.py-2.text-sm.leading-5.font-medium.text-indigo-300.rounded-md.hover:text-white.hover:bg-indigo-700.focus:outline-none.focus:text-white.focus:bg-indigo-700.transition.ease-in-out.duration-150
             {:href "#"}
             [:svg.mr-3.h-6.w-6.text-indigo-400.group-hover:text-indigo-300.group-focus:text-indigo-300.transition.ease-in-out.duration-150
              {:stroke "currentColor", :viewBox "0 0 24 24", :fill "none"}
              [:path
               {:d
                "M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4",
                :stroke-width "2",
                :stroke-linejoin "round",
                :stroke-linecap "round"}]]
             "\n              Documents\n            "]
            [:a.group.flex.items-center.px-2.py-2.text-sm.leading-5.font-medium.text-indigo-300.rounded-md.hover:text-white.hover:bg-indigo-700.focus:outline-none.focus:text-white.focus:bg-indigo-700.transition.ease-in-out.duration-150
             {:href "#"}
             [:svg.mr-3.h-6.w-6.text-indigo-400.group-hover:text-indigo-300.group-focus:text-indigo-300.transition.ease-in-out.duration-150
              {:stroke "currentColor", :viewBox "0 0 24 24", :fill "none"}
              [:path
               {:d
                "M9 19v-6a2 2 0 00-2-2H5a2 2 0 00-2 2v6a2 2 0 002 2h2a2 2 0 002-2zm0 0V9a2 2 0 012-2h2a2 2 0 012 2v10m-6 0a2 2 0 002 2h2a2 2 0 002-2m0 0V5a2 2 0 012-2h2a2 2 0 012 2v14a2 2 0 01-2 2h-2a2 2 0 01-2-2z",
                :stroke-width "2",
                :stroke-linejoin "round",
                :stroke-linecap "round"}]]
             "\n              Reports\n            "]]]
          [:div.flex-shrink-0.flex.border-t.border-indigo-700.p-4
           [:a.flex-shrink-0.w-full.group.block {:href "#"}
            [:div.flex.items-center
             [:div
              [:img.inline-block.h-9.w-9.rounded-full
               {:alt "",
                :src
                "https://images.unsplash.com/photo-1472099645785-5658abf4ff4e?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=facearea&facepad=2&w=256&h=256&q=80"}]]
             [:div.ml-3
              [:p.text-sm.leading-5.font-medium.text-white
               "\n                  Tom Cook\n                "]
              [:p.text-xs.leading-4.font-medium.text-indigo-300.group-hover:text-indigo-100.transition.ease-in-out.duration-150
               "\n                  View profile\n                "]]]]]]]]
       [:div.flex.flex-col.w-0.flex-1.overflow-hidden
        [:div.md:hidden.pl-1.pt-1.sm:pl-3.sm:pt-3
         [:button.-ml-0.5.-mt-0.5.h-12.w-12.inline-flex.items-center.justify-center.rounded-md.text-gray-500.hover:text-gray-900.focus:outline-none.focus:bg-gray-200.transition.ease-in-out.duration-150
          {:aria-label "Open sidebar"
           :on-click #(swap! state assoc :small-screen-menu-hidden false)}
          [-svg/open-sidebar "h-6 w-6"]]]
        [:main.flex-1.relative.z-0.overflow-y-auto.focus:outline-none
         {:tabIndex "0"}
         [:div.pt-2.pb-6.md:py-6
          [:div.max-w-7xl.mx-auto.px-4.sm:px-6.md:px-8
           [:h1.text-2xl.font-semibold.text-gray-900 "Dashboard"]]
          [:div.max-w-7xl.mx-auto.px-4.sm:px-6.md:px-8
           ;;"<!-- Replace with your content -->"
           [:div.py-4
            [:div.border-4.border-dashed.border-gray-200.rounded-lg.h-96]]
           ;;"<!-- /End replace -->"
           ]]]]])))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmethod ig/init-key ::app
  [_ cfg]
  (assoc cfg ::state (r/atom {})))
