(ns mudder.systems.environment
  (:use [clojure.core.match :only [match]]
        [mudder.world.ces :as ces]
        [mudder.systems.communication :as sys.comm]))

(defn- room-contains-entity [world room ent]
  (contains? (get-in (last room) [:components :room :entities]) ent))

(defn- get-entity-room [world ent]
  (ces/with-component world :room #(room-contains-entity world %1 ent)))

(defn- look-here [world character]
  (let [room (first (get-entity-room world character))]
    (sys.comm/send-text world character (get-in @world [room :components :describable :description]))))

; will need to resolve `target` into an entity in the current room
(defn- look-at [world character target]
  (sys.comm/send-text world character (str "Sorry, you can't look at " target " yet.")))

(defn look [world character target]
  (match [target]
    ["here"] (look-here world character)
    [at] (look-at world character at)))
