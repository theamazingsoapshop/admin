(ns za.co.theamazingsoapshop.admin.ui.breadcrumbs
  (:require [za.co.theamazingsoapshop.admin.ui.common :as -ui-common]
            [integrant.core :as ig]
            [reagent.core :as r]
            [clojure.spec.alpha :as s]
            [lambdaisland.glogi :as log]
            [za.co.theamazingsoapshop.admin.ui.headings :as -headings]))

(defprotocol IBreadcrumb
  (push-item! [bc new-top-mot-item]
    "Pushes a new item to the workspace, growing the breadcrumb stack.")
  (reset-items! [bc]
    "Removes all of the items from the crumbs list.")
  (workspace-ui-item [bc]
    "The user-interface supplied by the items in the crumbs"))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defn breadcrumb-ui
  [{:as cfg}
   ratom-state]
  (fn []
    (when-let [top (first @ratom-state)]
      (-ui-common/workspace-item-title top))))

(defn workspace-ui
  [cfg ratom-state]
  (fn []
    (log/debug ::ratom-state @ratom-state)
    (let [[top & rst] @ratom-state]
      (when top
        [:div.pt-2.pb-6.md:py-6
         [:div.max-w-7xl.mx-auto.px-4.sm:px-6.md:px-8
          (let [non-top-crumbs (->> rst
                                    (map #(hash-map ::-headings/text
                                                    (-ui-common/workspace-item-title %)))
                                    (map-indexed #(assoc %2
                                                         ::-headings/on-click
                                                         (fn []
                                                           (swap! ratom-state nthrest (inc %1))))))

                actions (-ui-common/workspace-item-actions top)]
            (-headings/heading-with-breadcrumb-and-buttons
             (cond-> {}
               true
               (assoc ::-headings/title (-ui-common/workspace-item-title top))

               (pos? (count actions))
               (assoc ::-headings/menu actions)

               (pos? (count non-top-crumbs))
               (assoc ::-headings/breadcrumb non-top-crumbs))))]
         [:div.max-w-7xl.mx-auto.px-4.sm:px-6.md:px-8
          [:div.py-4
           (-ui-common/workspace-item-ui top)]]]))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(s/def ::cfg (s/keys :req []
                     :opt []))

(defmethod ig/init-key ::breadcrumbs
  [_ cfg]
  (let [default-state nil
        state (r/atom default-state)]
    (reify
      IBreadcrumb
      (push-item! [_ x]
        (if (satisfies? -ui-common/IWorkspaceItem x)
          (swap! state conj x)
          (do
            (log/error ::does-not-satisfy-IWorkspaceItem x))))
      (reset-items! [_] (reset! state default-state))
      (workspace-ui-item [_] (workspace-ui cfg state)))))

