(ns mudder.commands.say-test
  (:require [clojure.test :refer :all]
            [mudder.commands :as cmd]))

(deftest parses-basic-say
  (is (=
       (cmd/parse "say Hello")
       [:command {:command :say, :message "Hello"}])))

(deftest parses-say-with-multiple-words
  (is (=
       (cmd/parse "say Hello there, you!")
       [:command {:command :say, :message "Hello there, you!"}])))

(deftest parses-say-with-alternate-syntax
  (are [x y] (= x y)

       (cmd/parse "'Hello there, you!")
       [:command {:command :say, :message "Hello there, you!"}]

       (cmd/parse "\"Hello there, you!")
       [:command {:command :say, :message "Hello there, you!"}]))

(deftest parses-say-with-whitespace
  (are [x y] (= x y)
       (cmd/parse "say    Hello there, you! ")
       [:command {:command :say, :message "Hello there, you!"}]

       (cmd/parse "' Hello there, you! ")
       [:command {:command :say, :message "Hello there, you!"}]

       (cmd/parse "\" Hello there, you! ")
       [:command {:command :say, :message "Hello there, you!"}]))
