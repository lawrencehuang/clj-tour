(ns clj-tour.reuse-imp)

(defprotocol Measurable
  "A protocol for retrieving the dimensions of a widget"
  (width [measurable] "Returning the width of px")
  (height [measurable] "Returning the height of px"))

(defrecord Button [text])

(extend-type Button
  Measurable
  (width [btn] (* 8 (count (:text btn))))
  (height [btn] 8))
