(ns clj-tour.coll
  (:require [clojure.pprint]
            [clojure.string :as str])
  (:use [clojure.repl]))

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

(defn magnitude
  [x]
  (-> x
      Math/log10
      Math/floor))

(defn compare-magnitude
  [a b]
  (- (magnitude a) (magnitude b)))


;; Clojure Programming page 118-123, READ IT!
(def playlist
  [{:title "Elephant", :artist "The White Stripes", :year 2003}
   {:title "Helioself", :artist "Papas Fritas", :year 1997}
   {:title "Stories from the City, Stories from the Sea",
    :artist "PJ Harvey", :year 2000}
   {:title "Buildings and Grounds", :artist "Papas Fritas", :year 2000}
   {:title "Zen Rodeo", :artist "Mardi Gras BB", :year 2002}])

(defn summarize
  [{:keys [title artist year]}]
  (str title " // " artist " // " year))

;; (map summarize playlist)
;; (group-by :artist playlist)
;; (group-by (juxt :artist :year) playlist)
;; (group-by #(odd? %) (range 10))
;; (group-by #(rem % 3) (range 10))
(into {} (for [[k v] (group-by :artist playlist)]
           [k (count v)]))

(def orders
  [{:product "Clock", :customer "Wile Coyote", :qty 6, :total 300}
   {:product "Dynamite", :customer "Wile Coyote", :qty 20, :total 5000}
   {:product "Shotgun", :customer "Elmer Fudd", :qty 2, :total 800}
   {:product "Shells", :customer "Elmer Fudd", :qty 4, :total 100}
   {:product "Hole", :customer "Wile Coyote", :qty 1, :total 1000}
   {:product "Hole", :customer "Wile Coyote", :qty 12, :total 11000}
   {:product "Anvil", :customer "Elmer Fudd", :qty 2, :total 300}
   {:product "Anvil", :customer "Wile Coyote", :qty 6, :total 900}])

(into {} (for [[k v] (group-by :product orders)]
           [k (apply + (map :total v))]))

(defn reduce-by
  [key-fn f init coll]
  (reduce (fn
            [summary x]
            (let [k (key-fn x)]
              (assoc summary k (f (summary k init) x))))
          {}
          coll))

(reduce-by :customer #(+ %1 (:total %2)) 0 orders)
(reduce-by :product #(conj %1 (:customer %2)) #{} orders)
(reduce-by (juxt :customer :product) #(+ %1 (:total %2)) 0 orders)
