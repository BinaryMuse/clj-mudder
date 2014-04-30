(ns mudder.systems.environment
  (:use [clojure.core.match :only [match]]
        [mudder.world.ces :as ces]
        [mudder.systems.communication :as sys.comm]))

; gross but works. clean it up!
(defn- look-here [character]
  (dosync
   (let [world (:world character)
         rooms (filter (fn [ent]
                         (some #{:room} (keys (:components ent)))) (vals @world))
         char-room (first (filter (fn [ent]
                             (let [r (get-in ent [:components :room])]
                               (contains? (:entities @r) (:id character)))) rooms))
         room-ent (ces/->entity world (:id char-room))
         comps (ces/get-components room-ent)
         desc (:describable comps)]
     (sys.comm/send-text character (:description @desc)))))

; will need to resolve `target` into an entity in the current room
(defn- look-at [character target]
  (sys.comm/send-text character (str "Sorry, you can't look at " target " yet.")))

(defn look
  "The given character looks at the target. Target can be \"here\",
  representing the character's current room, or a target, which will
  be resolved based on the character's posessions and environment."
  [character target]
  (match [target]
    ["here"] (look-here character)
    [at] (look-at character at)))
