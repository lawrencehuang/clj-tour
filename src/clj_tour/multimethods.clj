(ns clj-tour.multimethods
  (:use [clojure.repl])
  (:require [clojure.string :as str]))

;; Life without multimethods

;; (defn my-print
;;   [ob]
;;   (cond
;;    (nil? ob) (.write *out* "nil")
;;    (string? ob) (.write *out* ob)))

;; (defn my-println
;;   [ob]
;;   (my-print ob)
;;   (.write *out* "\n"))

(defmulti my-print class :default :everythingelse)

(defmethod my-print String [ob]
  (.write *out* ob))

(defmethod my-print nil
  [ob]
  (.write *out* "nil"))

(defmethod my-print java.util.Collection
  [c]
  (.write *out* "#<")
  (.write *out* (str/join " " c))
  (.write *out* ">"))

(defmethod my-print :everythingelse
  [_]
  (.write *out* "Not implemented yet!"))
