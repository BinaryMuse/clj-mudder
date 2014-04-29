(ns mudder.systems.environment
  (:use [clojure.core.match :only [match]]
        [mudder.world.ces :as ces]))

(defn- room-contains-entity [world room ent]
  (contains? (get-in (last room) [:components :room :entities]) ent))

(defn- get-entity-room [world ent]
  (ces/with-component world :room #(room-contains-entity world %1 ent)))

(defn- look-here [world character]
  (let [room (first (get-entity-room world character))]
    (get-in @world [room :components :describable :description])))

; will need to resolve `target` into an entity in the current room
(defn- look-at [target]
  (println "Can't look at" target "yet."))

(defn look [world character target]
  (match [target]
    ["here"] (look-here world character)
    [at] (look-at at)))

(def world (ces/build-world))
(def character (first (ces/with-component world :player-character)))
(look world character "here")
; need to send it back to output somehow... function w/ character???
; (sys.comm/send-text character "some text") <-- use chan
