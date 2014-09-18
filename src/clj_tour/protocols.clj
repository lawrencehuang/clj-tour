(ns clj-tour.protocols
  (:use [clojure.repl]))

(defprotocol Matrix
  "Protocol for working with 2d dimension data structure"
  (lookup [matrix i j])
  (update [this i j value])
  (rows [this])
  (cols [this])
  (dims [this]))

;; Extending to existing types
(extend-protocol Matrix
  clojure.lang.IPersistentVector
  (lookup [vov i j]
    (get-in vov [i j]))
  (update [vov i j val]
    (assoc-in vov [i j] val))
  (rows [vov]
    (seq vov))
  (cols [vov]
    (apply map vector vov))
  (dims [vov]
    [(count vov) (count (first vov))]))

;; define record
(defrecord NamedPoint [^String name ^Long x ^Long y])
(NamedPoint/getBasis)
(map meta (NamedPoint/getBasis))
