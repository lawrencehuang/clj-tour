(ns clj-tour.prime)

(defn prime?
  [n]
  (cond
   (== 1 n) false
   (== 2 n) true
   (even? n) false
   :else (->> (range 3 (inc (Math/sqrt n)) 2)
              (filter #(zero? (rem n %)))
              (empty?))))

(time (prime? 1125899906842679))
(require '[clojure.core.memoize :as memo])
(def m-prime? (memo/memo prime?))
(time (m-prime? 1125899906842679))
(time (m-prime? 1125899906842679))
