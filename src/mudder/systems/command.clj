(ns mudder.systems.command
  (:require [mudder.systems.environment :as sys.env]
            [mudder.systems.communication :as sys.comm])
  (:use [clojure.core.match :only [match]]))

(defn- execute-command [world character command arguments]
  (match [command]
    [:look] (sys.env/look world character (:target arguments))))

(defn process-command [world character cmd]
  (match [cmd]
    [[:command cmd-details]] (execute-command world character (:command cmd-details) cmd-details)
    [[:error msg]] (sys.comm/send-text world character "Don't know how to do that, sorry.")))
