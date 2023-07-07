(ns racing-game-cljs.parenoia.view
  (:require ["regenerator-runtime"]
            ["@react-three/cannon" :refer [Physics usePlane useBox useCylinder useRaycastVehicle]]
            ["@react-three/drei" :refer [useGLTF Sky Environment PerspectiveCamera Html RoundedBox Box, OrbitControls
                                         useBoundingBox]]
            ["@react-three/fiber" :refer [Canvas useFrame]]
            ["react" :refer [useRef Suspense useEffect]]
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

;; onst vFov = (camera.fov * Math.PI) / 180;
;; const height = 2 * Math.tan(vFov / 2) * camera.position.z;
;; const aspect = window.innerWidth / window.innerHeight;
;; const width = height * aspect;
;; const pixelSize = window.innerWidth * ((1 / width) * sizeOfObject) // (e.g. size

(defn get-size-in-px [size-of-object]
  (let [vFov (/ (* 50 (.-PI js/Math)) 180)
        height (* 2 2 (.tan js/Math (/ vFov 2)))
        aspect (/ (.-innerWidth js/window) (.-innerHeight js/window))
        width (* height aspect)
        pixelSize (* (.-innerWidth js/window)
                     (* (/ 1 width) size-of-object))]
    pixelSize))


(defn box []
  (let [box-ref (useRef)]
    (useEffect (fn []
                 (let []

                   (.log js/console "hello: " (get-size-in-px 1)))
                 (fn []))
               #js [])
    [:<>
     [:> Suspense {:fallback nil}

      [:group {:position [1 1 0]}
       [:mesh {:ref box-ref}
        [:> RoundedBox {:args [1 1 1]}]
        [:meshPhysicalMaterial {:color "yellow"}]]
      ;[:mesh {:geometry  [:boxGeometry {:args [1 1 1]}]}]

       [:> Html
        {:occlude true
         :distanceFactor  3.8
         :position  [0, 0, 0.51]
         :transform true
         :receiveShadow true
         :castShadow true
         :class "html-element"
         :style {:height "100px"
                 :width "100px"
                 :border-radius "10px"
                 :padding "10px"
                 :pointer-events :none
                 :border "1px solid rgba(255,0,0,0.3)"}}
        [:div
         {:style {:height "100%"
                  :pointer-events :none}}
         "hello"]]]]]))









(defn view []

  [canvas
   {:dpr [1 1.5]
    :shadows true
    :camera {:position [0 0 50] :near 0.1 :far 2000 :fov 50}}
  ;;  [:fog {:attach "fog" :args ["white" 0 350]}]
   [sky {:sun-position [100 10 100] :scale 1000}]
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