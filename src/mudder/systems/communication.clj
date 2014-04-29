(ns mudder.systems.communication
  (:require [clojure.core.async :as async :refer [put!]]
            [mudder.world.ces :as ces]))

(defn install-output-channel
  "Specifies the output channel to use for the given character.
  Text sent to that character will be `put!`ed on that channel."
  [world character outchan]
  (swap! world assoc-in [character :components :player-character :output] outchan))

(defn send-text
  "Sends text to the given character."
  [world character text]
  (let [charent (get @world character)
        output (get-in charent [:components :player-character :output])]
    (if (not (nil? output))
      (put! output text))))
