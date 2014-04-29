(ns mudder.world.ces)

(def current-entity-id (atom 0))
(def entities (atom (sorted-map)))

(defn- component-map [data]
  (hash-map (keyword (::name data)) data))

(defn entity-id [ent]
  (first (keys ent)))

(defn entity*
  "Creates a new entity containing the given components.
  Returns the entity ID."
  [& components]
  (let [id (swap! current-entity-id inc)
        ent {id {:id id :components (into {} (map component-map components))}}]
    (swap! entities conj ent)
    id))

(defmacro component*
  "Creates a new component by defining a method named `name`
  that takes params `params`. `r` is a set of keys and values
  that define the default data for the component; params can
  be used.

  Example:

  (component* position [x y z]
              :x x
              :y y
              :z z)"
  [name params & r]
  `(defn ~name ~params
     (merge {::name ~(keyword name)} ~(apply hash-map r))))

(defn with-component
  "Returns the entity ID of all entities that contain the given component
  and optionally matches the given guard clause. The clause is passed the
  entity ID."
  ([world component]
   (with-component world component (constantly true)))
  ([world component guard]
    (let [matching (filter #(contains? (set (keys (:components (%1 1)))) component) @world)
          guarded (filter guard matching)]
      (vec (keys guarded)))))

(defn build-world []
  entities)

(component* room [entities]
            :entities (set entities))

(component* describable [description]
            :description description)

(component* container [entities]
            :entities (set entities))

(component* player-character []
            :output nil)

(def player (entity* (describable "It's you!")
                     (container [])
                     (player-character)))

(def city-center (entity* (room [player])
                          (describable "The center of town. It's quite nice.")))

(def next-room (entity* (room [])
                        (describable "It's that other room.")))

;; (defn remove-from-room [world ent room]
;;   (let [id (first (keys room))]
;;     (update-in room [id :components :room :entities] disj ent)))

;; (defn place-in-room [world ent room]
;;   (let [id (first (keys room))]
;;     (update-in room [id :components :room :entities] conj ent)))

;; (defn move-to-room [world ent destination]
;;   (let [source-room (first (withc world :room #(contains? (:entities %1) ent)))]
;;     (if source-room
;;       (-> world
;;           (remove-from-room ent source-room)
;;           (place-in-room ent destination))
;;       (place-in-room world ent destination))))

;; (defn update-component [world entity path f & args]
;;   (update-in world (concat [entity :components] path) f args))

;; (defn move-entity [world ent from to]
;;   (-> world
;;       (update-component from [:room :entities] disj ent)
;;       (update-component to [:room :entities] conj ent)))

;; (move-entity world 3 1 2)
