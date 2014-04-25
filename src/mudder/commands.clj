(ns mudder.commands
  (:require [instaparse.core :as insta]
            [clojure.string :as string]))

(def parser
  (insta/parser
   "command = look | say | emote
   look = <'look'> (<whitespace> identifier)?
   identifier = #'[A-Za-z]'+
   say = <(('say' whitespace) | '\\'' | '\"')> #'.'+
   emote = <(('emote' whitespace) | ';')> #'.'+
   whitespace = #'\\s'"))

(defn transform-look [cmd]
  {:target (nth cmd 1 "here")})

(defn transform-say [cmd]
  {:message (string/trim (apply str (rest cmd)))})

(defn transform-emote [cmd]
  {:emote (string/trim (apply str (rest cmd)))})

(def command-transformations
  {:look transform-look
   :say transform-say
   :emote transform-emote})

(defn transform-command [tree]
  (let [full-cmd (tree 1)
        cmd (full-cmd 0)
        transform (or (command-transformations cmd) identity)]
    [:command (assoc (transform full-cmd) :command cmd)]))

(defn parse [cmd]
  (try
    (->> (parser cmd)
         (insta/transform
          {:identifier str})
         (transform-command))
    (catch Exception e [:error (str "Could not parse command: '" cmd "'")])))

(parse "look key")
(parse "look")
(parse "say Well hello there!")
(parse "say")
(parse "'testing")
(parse "\"testing")
(parse "emote looks around")
(parse "; looks around")
