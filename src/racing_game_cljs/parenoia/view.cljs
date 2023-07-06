(ns racing-game-cljs.parenoia.view
  (:require ["regenerator-runtime"]
            ["@react-three/cannon" :refer [Physics usePlane useBox useCylinder useRaycastVehicle]]
            ["@react-three/drei" :refer [useGLTF Sky Environment PerspectiveCamera Html RoundedBox Box, OrbitControls
                                         useBoundingBox]]
            ["@react-three/fiber" :refer [Canvas useFrame]]
            ["react" :refer [useRef Suspense]]
            ["three" :as THREE]
            [applied-science.js-interop :as j]
            [clojure.string :as str]
            [goog.object :as ob]
            [racing-game-cljs.events :as events]
            [racing-game-cljs.subs :as subs]
            [re-frame.core :refer [dispatch subscribe]]

            [reagent.core :as r]))

(def canvas (r/adapt-react-class Canvas))
(def sky (r/adapt-react-class Sky))
(def environment (r/adapt-react-class Environment))
(def physics (r/adapt-react-class Physics))
(def suspense (r/adapt-react-class Suspense))
(def perspective-camera (r/adapt-react-class PerspectiveCamera))



(defn box []
  [:<>
   [:> Suspense {:fallback nil}
    [:group
     [:mesh {:position [0 0 0]}
      [:boxGeometry {:args [1 1 1]}]
      [:meshStandardMaterial {:color "hotpink"}]]]
    [:group
     [:mesh {:position [1 1 0]}
      [:boxGeometry {:args [1 1 1]}]
      [:meshStandardMaterial {:color "yellow"}]
      [:> Html
       {:occlude true
        ;:distanceFactor  1.5
        :position  [0, 0, 0.51]
        :geometry [:boxGeometry {:args [1 1 1]}]
        :transform true
        :receiveShadow true
        :castShadow true
        :style {:height "100%"
                :width "100%"
                :background :red}}
       [:div
        {:style {:height "100%"}}
        "hello"]]]]]])




(defn view []
  [canvas
   {:dpr [1 1.5]
    :shadows true
    :camera {:position [0 0 2] :near 1 :far 200 :fov 50}}
  ;;  [:fog {:attach "fog" :args ["white" 0 350]}]
   ;[sky {:sun-position [100 10 100] :scale 1000}]
   [:ambientLight {:intensity 0.1}]
   [:> OrbitControls {:makeDefault true}]
   [:pointLight {:color "white"
                 :intensity 0.7
                 :position [0, 0, 5]}]
   [:f> box]])

    ;; [:f> plane {:rotation [(/ (- js/Math.PI) 2) 0 0]
    ;;             :userData {:id "floor"}}]
    ;; [:f> vehicle {:rotation [0 (/ js/Math.PI 2) 0]
    ;;               :position [0 2 0]
    ;;               :angularVelocity [0 0.5 0]
    ;;               :wheelRadius 0.3}]]])