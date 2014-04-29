(defproject mudder "0.1.0-SNAPSHOT"
  :description "MUD system based on CES"
  :url "https://github.com/BinaryMuse"
  :license {:name "MIT"
            :url "http://opensource.org/licenses/MIT"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [instaparse "1.3.2"]
                 [org.clojure/core.match "0.2.1"]
                 [org.clojure/core.async "0.1.298.0-2a82a1-alpha"]]
  :main mudder.main)
