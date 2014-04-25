(ns mudder.world.ces)

(def current-entity-id (atom 0))
(def entities (atom (sorted-map)))

(defn- component-map [data]
  (hash-map (keyword (::name data)) data))

(defn entity-id [ent]
  (first (keys ent)))

(defn entity* [& components]
  (let [id (swap! current-entity-id inc)
        ent {id {:id id :components (into {} (map component-map components))}}]
    (swap! entities conj ent)
    ent))

(defmacro component* [name params & r]
  `(defn ~name ~params
     (merge {::name ~(keyword name)} ~(apply hash-map r))))

(defn withc
  ([world component]
   (withc world component identity))
  ([world component guard]
    (let [matching (filter #(contains? (set (keys (:components (%1 1)))) component) world)
          guarded (filter guard matching)]
      (into {} guarded)))) ; fix this into thing -- moving to only ids?

(def world {1 {:components {:room {:entities #{3}}}}
            2 {:components {:room {:entities #{}}}}})

(defn room-contains-entity [room ent]
  (contains? (get-in (last room) [:components :room :entities]) ent))

;; too much switching between maps and vectors with filter???
(withc world :room #(room-contains-entity %1 3))

(withc world :room)

(component* room [entities]
           :entities (set entities))

(component* describable [description]
           :description description)

(component* container [entities]
           :entities (set entities))

(component* keyboard-controlled [])

(def city-center (entity* (room [])
                          (describable "The center of town. It's quite nice.")))

(def player (entity* (describable "It's you!")
                     (container [])
                     (keyboard-controlled)))

; (swap! entities update-in [(entity-id city-center) :components :room :entities] conj (entity-id player))

(defn remove-from-room [world ent room]
  (let [id (first (keys room))]
    (update-in room [id :components :room :entities] disj ent)))

(defn place-in-room [world ent room]
  (let [id (first (keys room))]
    (update-in room [id :components :room :entities] conj ent)))

(defn move-to-room [world ent destination]
  (let [source-room (first (withc world :room #(contains? (:entities %1) ent)))]
    (if source-room
      (-> world
          (remove-from-room ent source-room)
          (place-in-room ent destination))
      (place-in-room world ent destination))))

; need IDs only?? or should we just shove refs around...
(swap! entities move-to-room (entity-id player) city-center)

; turn entities into refs and pass only ids around?

(def world {1 {:components {:room {:entities #{3}}}}
            2 {:components {:room {:entities #{}}}}})

(defn update-component [world entity path f & args]
  (update-in world (concat [entity :components] path) f args))

(defn move-entity [world ent from to]
  (-> world
      (update-component from [:room :entities] disj ent)
      (update-component to [:room :entities] conj ent)))

(move-entity world 3 1 2)
