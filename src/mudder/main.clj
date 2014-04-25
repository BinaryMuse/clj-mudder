(ns mudder.main
  (:require [mudder.systems.input]))

(def entities (atom (sorted-map)))

(defn entity [name & r]
  (let [ent (merge {:behaviors #{}}
                   (apply hash-map r))]
    (swap! entities conj ent)))

; (behavior* :keyboard-input-controlled)

(entity :player
        :behaviors #{:keyboard-input-controlled})

(defn -oldmain []
  (mudder.systems.input/readlines mudder.systems.input/parseline))

(defn -main []
  (let [keysys (mudder.systems.input/keyboard-system entities)]))
