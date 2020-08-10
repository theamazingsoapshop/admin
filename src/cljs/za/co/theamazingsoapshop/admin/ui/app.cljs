(ns za.co.theamazingsoapshop.admin.ui.app
  (:require #_["/com/tailwindui/Transition" :as tw-transition :refer [Transition]]
            #_["/demo/bar" :as bar :refer [myComponent]]
            [za.co.theamazingsoapshop.admin.ui.svg :as -svg]
            [reagent.core :as r]
            [clojure.string :as str]
            [integrant.core :as ig]
            [lambdaisland.glogi :as log]
            [cljs-gravatar.core :as gravatar]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(def color "green")

(defn mobile-menu-view-profile
  [{:person/keys [firstname lastname email]}]
  [:a.flex-shrink-0.group.block.focus:outline-none {:href "#"}
   [:div.flex.items-center
    [:div
     [:img.inline-block.h-10.w-10.rounded-full
      (cond-> {:alt (str firstname "'s image.")}
        email (assoc :src (gravatar/url email)))]]
    [:div.ml-3
     [:p.text-base.leading-6.font-medium.text-white (str firstname " " lastname)]
     [:p.text-sm.leading-5.font-medium.group-focus:underline.transition.ease-in-out.duration-150
      {:class (str "text-" color "-300 "
                   "group-hover:text-" color "-100.")} "View profile"]]]])

(defn wide-view-profile
  [{:person/keys [firstname lastname email]}]
  [:a {:href "#"
       :class "flex-shrink-0 w-full group block"}
   [:div.flex.items-center
    [:div
     [:img.inline-block.h-9.w-9.rounded-full
      (cond-> {:alt (str firstname " " lastname " image.")}
        email (assoc :src (gravatar/url email)))]]
    [:div.ml-3
     [:p.text-sm.leading-5.font-medium.text-white (str firstname " " lastname)]
     [:p.text-xs.leading-4.font-medium.transition.ease-in-out.duration-150
      {:class (str "text-" color "-300 "
                   "group-hover:text-" color "-100.")}
      "View profile"]]]])

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn menu-text [{:keys [text key]}]
  (if text text (-> key name str/capitalize)))

(defn mobile-menu-item
  [{:keys [icon selected? key on-click]
    :as item}]
  (let [text (menu-text item)
        base-classes " group flex items-center px-2 py-2 text-base leading-6 font-medium rounded-md transition ease-in-out duration-150"
        selected-classes (str " text-white focus:outline-none bg-" color "-900 focus:bg-" color "-700" )
        unselected-classes (str " hover:text-white focus:outline-none focus:text-white text-" color "-300 hover:bg-" color "-700 focus:bg-" color "-700")]
    [:a
     (cond-> {:class (str base-classes " " (if selected? selected-classes unselected-classes))
              :href "#"}
       on-click (assoc :on-click on-click))
     icon
     text]))

(defn wide-menu-item
  ;; just copied and pasted from mobile-menu-items, still have be filled out with the actual template
  [{:keys [icon selected? key on-click]
    :as item}]
  (let [text (menu-text item)
        unselected-classes (str " rounded-md hover:text-white focus:outline-none focus:text-white transition ease-in-out duration-150 text-" color "-300 hover:bg-" color "-700 focus:bg-" color "-700")
        selected-classes (str " text-white rounded-md focus:outline-none transition ease-in-out duration-150 bg-" color "-900 focus:bg-" color "-700")
        base-classes " group flex items-center px-2 py-2 text-sm leading-5 font-medium"]
    [:a (cond-> {:class (str base-classes " " (if selected? selected-classes unselected-classes))
                 :href "#"}
          on-click (assoc :on-click on-click))
     icon
     text]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(let [common [:h1.font-medium.text-base.text-white "Admin"]]

  (defn menu-heading []
    [:div.flex-shrink-0.flex.items-center.px-4 common])

  (defn wide-menu-heading []
    [:div.flex.items-center.flex-shrink-0.px-4 common]))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn make-menu-items
  [selected-key set-select-key-fn]
  (log/debug ::making-menu-items selected-key)
  (log/spy (->> [{:key ::payments}
                 {:key ::team}
                 {:key ::logout
                  :text "Instant logout"}]
                (map #(assoc % :selected? (= selected-key (:key %))))
                (map #(assoc % :on-click (fn [_]
                                           (log/debug ::hierso (:key %))
                                           (set-select-key-fn (:key %)))))
                (map #(assoc % :text (menu-text %))))))

(defn ui [{system ::app}]
  (let [state (::state system)
        user-profile {:person/firstname "Pieter"
                      :person/lastname "Breed"
                      :person/email "pieter@pb.co.za"}]

    (log/debug ::system system)
    (fn []
      (let [selected-key (-> @state :selected-menu-key)
            set-selected-key #(do
                                (log/debug ::setting-a-selected-key (pr-str %))
                                (swap! state assoc :selected-menu-key %))
            menu-items (make-menu-items (-> @state :selected-menu-key)
                                        set-selected-key)
            selected-menu-item (->> menu-items
                                    (filter #(= selected-key (:key %)))
                                    first)]
        (log/debug ::selected-key selected-key)
        [:div.h-screen.flex.overflow-hidden.bg-gray-100
         (comment "<!-- Off-canvas menu for mobile -->")
         [:div.md:hidden
          {:class (str (when (-> @state (:small-screen-menu-hidden false)) "hidden"))}
          [:div.fixed.inset-0.flex.z-40
           (comment "<!--\n        Off-canvas menu overlay, show/hide based on off-canvas menu state.\n\n        Entering: \"transition-opacity ease-linear duration-300\"\n          From: \"opacity-0\"\n          To: \"opacity-100\"\n        Leaving: \"transition-opacity ease-linear duration-300\"\n          From: \"opacity-100\"\n          To: \"opacity-0\"\n      -->")
           [:div.fixed.inset-0
            [:div.absolute.inset-0.bg-gray-600.opacity-75]]
           ;;"<!--\n        Off-canvas menu, show/hide based on off-canvas menu state.\n\n        Entering: \"transition ease-in-out duration-300 transform\"\n          From: \"-translate-x-full\"\n          To: \"translate-x-0\"\n        Leaving: \"transition ease-in-out duration-300 transform\"\n          From: \"translate-x-0\"\n          To: \"-translate-x-full\"\n      -->"
           [:div.relative.flex-1.flex.flex-col.max-w-xs.w-full
            {:class (str "bg-" color "-800")}
            [:div.absolute.top-0.right-0.-mr-14.p-1
             [:button.flex.items-center.justify-center.h-12.w-12.rounded-full.focus:outline-none.focus:bg-gray-600
              {:aria-label "Close sidebar"
               :on-click #(swap! state assoc :small-screen-menu-hidden true)}
              [-svg/close-sidebar "h-6 w-6 text-white"]]]
            [:div.flex-1.h-0.pt-5.pb-4.overflow-y-auto
             [menu-heading]
             [:nav.mt-5.px-2.space-y-1

              ;; build mobile menu
               (for [menu-item (->> menu-items
                                    (map #(assoc % :icon (-svg/icon-for (:key %)
                                                                        (str "mr-4 h-6 w-6 transition ease-in-out duration-150 "
                                                                             " text-" color "-400"
                                                                             " group-hover:text-" color "-300"
                                                                             " group-focus:text-" color "-300"))))
                                    (map #(assoc % :on-click (fn []
                                                               ((:on-click %))
                                                               (swap! state assoc :small-screen-menu-hidden true)))))]
                 ^{:key (str "mobile-menu-item-" (:key menu-item))}
                [mobile-menu-item menu-item])]]

            [:div.flex-shrink-0.flex.border-t.p-4
             {:class (str "border-" color "-700")}
             [mobile-menu-view-profile user-profile]]]
           [:div.flex-shrink-0.w-14
            ;;"<!-- Force sidebar to shrink to fit close icon -->"
            ]]]
         ;;"<!-- Static sidebar for desktop -->"
         [:div.hidden.md:flex.md:flex-shrink-0
          [:div.flex.flex-col.w-64
           ;;"<!-- Sidebar component, swap this element with another sidebar if you like -->"
           [:div.flex.flex-col.h-0.flex-1
            {:class (str "bg-" color "-800")}
            [:div.flex-1.flex.flex-col.pt-5.pb-4.overflow-y-auto
             [wide-menu-heading]
             [:nav.mt-5.flex-1.px-2.space-y-1
              {:class (str "bg-" color "-800.")}

              ;; build wide menu
              (for [menu-item (->> menu-items
                                   (map #(assoc % :icon (-svg/icon-for (:key %) "mr-3 h-6 w-6 transition ease-in-out duration-150 "
                                                                       " text-" color "-400"
                                                                       " group-hover:text-" color "-300"
                                                                       " group-focus:text-" color "-300"
                                                                       ))))]
                ^{:key (str "wide-menu-item-" (:key menu-item))} 
                [wide-menu-item menu-item])


              ]]
            [:div.flex-shrink-0.flex.border-t.p-4
             {:class (str "border-" color "-700")}
             [wide-view-profile user-profile]]]]]
         [:div.flex.flex-col.w-0.flex-1.overflow-hidden
          [:div.md:hidden.pl-1.pt-1.sm:pl-3.sm:pt-3
           [:button.-ml-0.5.-mt-0.5.h-12.w-12.inline-flex.items-center.justify-center.rounded-md.text-gray-500.hover:text-gray-900.focus:outline-none.focus:bg-gray-200.transition.ease-in-out.duration-150
            {:aria-label "Open sidebar"
             :on-click #(swap! state assoc :small-screen-menu-hidden false)}
            [-svg/open-sidebar "h-6 w-6"]]]
          [:main.flex-1.relative.z-0.overflow-y-auto.focus:outline-none
           {:tabIndex "0"}
           (when (or selected-menu-item)
             (log/debug ::selected-menu-item selected-menu-item)
             [:div.pt-2.pb-6.md:py-6
              [:div.max-w-7xl.mx-auto.px-4.sm:px-6.md:px-8
               [:h1.text-2xl.font-semibold.text-gray-900 (:text selected-menu-item)]]
              [:div.max-w-7xl.mx-auto.px-4.sm:px-6.md:px-8
               ;;"<!-- Replace with your content -->"
               [:div.py-4
                [:div.border-4.border-dashed.border-gray-200.rounded-lg.h-96]]
               ;;"<!-- /End replace -->"
               ]])]]]))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defmethod ig/init-key ::app
  [_ cfg]
  (assoc cfg ::state (r/atom {})))
