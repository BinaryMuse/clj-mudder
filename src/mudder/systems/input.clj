(ns mudder.systems.input
  (:require [mudder.commands :as cmd]
            [clojure.string :as string]))



(defn has-behavior [beh ent]
  (println "checking " ent " for beh " beh)
  (some #(= beh %) (ent 1)))

(defn find-player-entities [entities]
  (let [ents (filter (partial has-behavior :keyboard-input-controlled) @entities)]
    (doseq [e ents]
      (println "entity, " e))))

(defn parseline [line]
  (let [line (string/trim line)]))

(defn readlines [processor]
  (doseq [line (line-seq (java.io.BufferedReader. *in*))]
    (processor line)))

(defn keyboard-system [entities]
  (readlines (fn [line]
               (let [parsed (parseline line)]
                 (find-player-entities entities)))))
