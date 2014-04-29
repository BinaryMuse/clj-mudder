(ns mudder.main
  (:require [clojure.core.async :as async :refer [<! chan put! go]]
            [mudder.world.ces :as ces]
            [mudder.systems.command :as sys.cmd]
            [mudder.parser :as parser]))

(defn- read-from-stdin [input]
  (doseq [line (line-seq (java.io.BufferedReader. *in*))]
    (put! input line)))

(defn- start-game-loop [world character input]
  (go (while true
        (let [string (<! input)
              command (parser/parse string)]
          (sys.cmd/process-command world character command)))))

(defn -main []
  (let [input (chan)
        world (ces/build-world)
        character (first (ces/with-component world :player-character))]
    (start-game-loop world character input)
    (println "Ready...")
    (read-from-stdin input)))



; how to prevent race conditions when multiple commands are
; entered quickly, e.g.
;
;  > go north
;  > look
;
; and the system is still processing "go north" when "look"
; begins to be processed... some sort of command queue per player?
