(ns clj-tour.coll
  (:require [clojure.pprint]
            [clojure.string :as str])
  (:use clojure.repl))

(defn swap-pair
  [coll]
  (into (empty coll)
        (interleave
         (take-nth 2 (drop 1 coll))
         (take-nth 2 coll))))

(swap-pair (apply list (range 10)))

(defn swap-pair1
  [coll]
  (->> (interleave
        (take-nth 2 (drop 1 coll))
        (take-nth 2 coll))
       (into (empty coll))))

(swap-pair1 (apply vector (range 10)))

(defn map-map
  [f m]
  (into (empty m)
        (for [[k v] m]
          [k (f v)])))

;; Unsorted in, Unsorted out. Sorted in, Sorted out
(map-map inc (hash-map :a 1 :b 2 :c 33))
(map-map inc (sorted-map :z 3 :x 2 :a 1))

;; count in list is much quicker than in sequence
(let [s (range 1e6)]
  (time (count s)))

(let [s (apply list (range 1e6))]
  (time (count s)))

(repeatedly 10 (partial rand-int 50))
(repeatedly 10 (fn [] (rand-int 50)))
(repeatedly 10 #(rand-int 50))

(defn random-ints
  "Return a lazy seq of random interger in [0 limit]"
  [limit]
  (lazy-seq
   (println "Realizing lazy seq.....")
   (cons (rand-int limit) (random-ints limit))))
