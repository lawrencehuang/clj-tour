(ns clj-tour.state
  (:require [clojure.pprint :as pp]
            [clojure.string :as str])
  (:use [clojure.repl]))

;; STM - with ref
;; update with : alter, ref-set. alter is preffered
(defrecord Message [sender message])

(def messages (ref ()))

(defn naive-add-message
  [msg]
  (dosync (ref-set messages (cons msg @messages))))

(defn add-message-commute [msg]
(dosync (commute messages conj msg)))

; use 'conj' instead of 'cons' because alter return old value as the first arg
(defn add-message
  [msg]
  (dosync (alter messages conj msg)))

;; (naive-add-message (Message. "sender1" "message1"))
;; (add-message (Message. "Sender2" "message2"))


;; atom for lightweight data
;; update with : reset!, swap!
(def atom-p (atom {:name "Person" :gender "M"}))
(reset! atom-p {:who "Tom" :gender "m"})
(swap! atom-p assoc :who "TOM")

;; agent for async updates
(def counter (agent 0 :validator number?))
(send counter inc)

;; including Agents in Transactions
;; !!! Stil bugged
(def output-file "/home/lawrence/output/message-backup.clj" )
(def backup-agent (agent output-file))
(def backup-agent1 (agent nil))
(defn add-message-with-backup1
  [msg]
  (dosync
   (let [snapshot (commute messages conj msg)]
     (pp/pprint snapshot)
          ;; (send-off backup-agent
          ;;           (fn [file content] (spit file content)) snapshot))
     (send-off backup-agent1
               (fn [_ snapshot] (spit output-file snapshot)) snapshot))))

(defn add-message-with-backup [msg]
  (dosync
    (let [snapshot (commute messages conj msg)]
      (send-off backup-agent (fn [filename]
                               (spit filename snapshot)
                               filename))
      snapshot)))

(defn ptest
  [msg]
  (dosync
   (let [snapshot (commute messages conj msg)]
     (pp/pprint snapshot)
     (send-off backup-agent
               (fn [p1 p2] (spit output-file (str p1 " - " p2))) snapshot))))
