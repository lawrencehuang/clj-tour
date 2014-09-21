(ns clj-tour.prime
  (:use [clojure.repl]
        [clojure.pprint :only [pprint]])
  (:require [clojure.core.memoize :as memo]))

(defn prime?
  [n]
  (cond
   (== 1 n) false
   (== 2 n) true
   (even? n) false
   :else (->> (range 3 (inc (Math/sqrt n)) 2)
              (filter #(zero? (rem n %)))
              (empty?))))

(defn first-n-prime
  [n]
  (take n (lazy-seq (for [x (range 2 Long/MAX_VALUE) :when (prime? x)] x))))

(defn nth-prime
  [n]
  (nth (for [x (range 2 Long/MAX_VALUE) :when (prime? x)] x) n))

(println "prime? time test .....")
;;(time (prime? 1125899906842679))
;; (require '[clojure.core.memoize :as memo])
(def m-prime? (memo/memo prime?))
;; (time (m-prime? 1125899906842679))
;; (time (m-prime? 1125899906842683))
;; (time (m-prime? 1125899906842697))

(defn slow-prime?
  [n]
  (empty? (for [x (range 2 n) :when (zero? (rem n x))] x)))

;; (println "slow-prime? time test ......")
;; (time (slow-prime? 1125899906842679))

(dotimes [_ 5]
  (time (prime? 1299709)))
(println "****************")
(dotimes [_ 5]
  (time (slow-prime? 1299709)))
