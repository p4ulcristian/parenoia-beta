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


(defn html-component [{:keys [text width height]}]
  [:> Html
   {:occlude true
    :distanceFactor  3.8
    :position  [0, 0, 0.51]
    :transform true
    :class "html-element"
    :style {:height height
            :width width
            :border-radius "10px"
            :padding "10px"
            :pointer-events :none
            :border "1px solid rgba(255,0,0,0.3)"}}
   [:div
    {:style {:height "100%"
             :pointer-events :none}}
    text]])

(defn box [{:keys [text position size color]}]
  (let [box-ref (useRef)
        [box-width box-height] size
        html-width (* 100 box-width)
        html-height (* 100 box-height)]
    (useEffect (fn []
                 (let []

                   (.log js/console "hello: " (get-size-in-px 1)))
                 (fn []))
               #js [])
    [:<>
     [:> Suspense {:fallback nil}
      [:group {:position position}
       [:mesh {:ref box-ref :castShadow true :receiveShadow true}
        [:> RoundedBox {:args [box-width box-height 1] :castShadow true :receiveShadow true}
         [:meshPhongMaterial {:color color
                              :attach "material"
                              :roughness 1
                              :metalness 0.1}]]]
       [:f> html-component {:text  text
                            :width html-width
                            :height html-height}]]]]))






(defn lights []
  [:<>
   [:pointLight {:color "yellow"
                 :intensity 0.6
                 :position [3, 5, 3]
                 :castShadow true}]
   [:ambientLight {:args ["white" 0.2]
                   :castShadow true}]

   [:spotLight {:args ["white" 1]
                :position [-2 2 0]
                :castShadow true}]])




(defn view []

  [canvas
   {:dpr [1 1.5]
    :shadows true
    :camera {:position [0 0 7] :near 0.1 :far 2000 :fov 50}}
  ;;  [:fog {:attach "fog" :args ["white" 0 350]}]
  ; [sky {:sun-position [100 10 100] :scale 1000}]
   ;[:ambientLight {:intensity 0.1}]
   [:> OrbitControls {:makeDefault true}]
   [:f> lights]
   [:mesh {:rotation [(- 1.5) 0 0] :position [0 0 0]
           :receiveShadow true}
    [:planeGeometry {:args [7 7]}]

    [:meshPhongMaterial {:color "blue"}]]
   [:f> box {:text "Hello there"
             :size [1 2]
             :position [0 0 0]
             :color "yellow"}]
   [:f> box {:text "mizu"
             :size [1 1]
             :position [0.5 2 0]
             :color "deeppink"}]
   [:f> box {:text "mizu"
             :size [1 1]
             :position [-1.5 -1 0]
             :color "red"}]
   [:f> box {:text "mizu"
             :size [1 1]
             :position [-1.5 1 -1]
             :color "pink"}]])

    ;; [:f> plane {:rotation [(/ (- js/Math.PI) 2) 0 0]
    ;;             :userData {:id "floor"}}]
    ;; [:f> vehicle {:rotation [0 (/ js/Math.PI 2) 0]
    ;;               :position [0 2 0]
    ;;               :angularVelocity [0 0.5 0]
    ;;               :wheelRadius 0.3}]]])