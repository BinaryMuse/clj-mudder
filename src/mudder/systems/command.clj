(ns mudder.systems.command
  (:require [mudder.systems.environment :as sys.env]
            [mudder.systems.communication :as sys.comm])
  (:use [clojure.core.match :only [match]]))

(defn- execute-command [character command arguments]
  (match [command]
    [:look] (sys.env/look character (:target arguments))))

(defn process-command [character cmd]
  (match [cmd]
    [[:command cmd-details]] (execute-command character (:command cmd-details) cmd-details)
    [[:error msg]] (sys.comm/send-text character "Don't know how to do that, sorry.")))
