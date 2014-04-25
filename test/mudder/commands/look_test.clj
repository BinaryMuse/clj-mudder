(ns mudder.commands.look-test
  (:require [clojure.test :refer :all]
            [mudder.commands :as cmd]))

(deftest parses-basic-look
  (is (=
       (cmd/parse "look")
       [:command {:command :look, :target "here"}])))

(deftest parses-look-with-target
  (is (=
       (cmd/parse "look ring")
       [:command {:command :look, :target "ring"}])))
