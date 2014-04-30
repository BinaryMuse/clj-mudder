(ns mudder.systems.communication
  (:require [clojure.core.async :as async :refer [put!]]
            [mudder.world.ces :as ces]))

(defn install-output-channel
  "Specifies the output channel to use for the given character.
  Text sent to that character will be `put!`ed on that channel."
  [character outchan]
  (dosync
   (let [pc (:player-character (ces/get-components character))]
    (alter pc assoc :output outchan))))

(defn send-text
  "Sends text to the given character."
  [character text]
  (let [pc (:player-character (ces/get-components character))
        output (:output @pc)]
    (if (not (nil? output))
      (put! output text))))
