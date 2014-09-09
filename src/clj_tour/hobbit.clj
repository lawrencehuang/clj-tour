(ns clj-tour.hobbit)

(def asym-hobbit-body-parts [{:name "head" :size 3}
                             {:name "left-eye" :size 1}
                             {:name "left-ear" :size 1}
                             {:name "mouth" :size 1}
                             {:name "nose" :size 1}
                             {:name "neck" :size 2}
                             {:name "left-shoulder" :size 3}
                             {:name "left-upper-arm" :size 3}
                             {:name "chest" :size 10}
                             {:name "back" :size 10}
                             {:name "left-forearm" :size 3}
                             {:name "abdomen" :size 6}
                             {:name "left-kidney" :size 1}
                             {:name "left-hand" :size 2}
                             {:name "left-knee" :size 2}
                             {:name "left-thigh" :size 4}
                             {:name "left-lower-leg" :size 3}
                             {:name "left-achilles" :size 1}
                             {:name "left-foot" :size 2}])

(defn has-matching-part?
  [part]
  (re-find #"^left-" (:name part)))

(defn matching-part
  [part]
  {:name (clojure.string/replace (:name part) #"^left-" "right-")
   :size (:size part)})

(defn symmetrize-loop
  [asym-body-parts]
  (loop [to-inspect asym-body-parts
         final-parts []]
    (if (empty? to-inspect)
      final-parts
      (let [[part & remaining] to-inspect
            final-parts (conj final-parts part)]
        ;; (clojure.pprint/pprint final-parts)
        ;; (clojure.pprint/pprint remain)
        (if (has-matching-part? part)
          (recur remaining (conj final-parts (matching-part part)))
          (recur remaining final-parts))))))

(defn better-symmetrize
  [asym-body-parts]
  (reduce (fn [final-parts part]
            (let [final-parts (conj final-parts part)]
                 (if (has-matching-part? part)
                   (conj final-parts (matching-part part))
                   final-parts)))
          []
          asym-body-parts))

;; (defn symmetrize-body-parts
;;   "Expects a seq of maps which have a :name and :size"
;;   [asym-body-parts]
;;   (loop [remaining-asym-parts asym-body-parts
;;          final-body-parts []]
;;     (if (empty? remaining-asym-parts)
;;       final-body-parts
;;       (let [[part & remaining] remaining-asym-parts
;;             final-body-parts (conj final-body-parts part)]
;;         (if (has-matching-part? part)
;;           (recur remaining (conj final-body-parts (matching-part part)))
;;           (recur remaining final-body-parts))))))

;; (defn better-symmetrize-body-parts
;;   "Expects a seq of maps which have a :name and :size"
;;   [asym-body-parts]
;;   (reduce (fn [final-body-parts part]
;;             (let [final-body-parts (conj final-body-parts part)]
;;               (if (has-matching-part? part)
;;                 (conj final-body-parts (matching-part part))
;;                 final-body-parts)))
;;           []
;;           asym-body-parts))
