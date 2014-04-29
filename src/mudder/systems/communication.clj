(ns mudder.systems.communication
  (:require [clojure.core.async :as async :refer [put!]]
            [mudder.world.ces :as ces]))

(defn install-output-channel [world character outchan]
  (swap! world assoc-in [character :components :player-character :output] outchan))

(defn send-text [world character text]
  (let [charent (get @world character)
        output (get-in charent [:components :player-character :output])]
    (if (not (nil? output))
      (put! output text))))
