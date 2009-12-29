(ns euler.p92
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils)
  (:import java.util.LinkedHashMap))

(set! *warn-on-reflection* true)

(def square #(* % %))

(defn step [n]
  (sum (map square (digits n))))

(def *memo-size* 100000)
(def #^java.util.Map dest-memo
  (proxy [LinkedHashMap] []
    (removeEldestEntry [#^java.util.Map$Entry eldest]
      (>= (.size #^java.util.Map this) *memo-size*))))

(defn dest [n]
  (condp = n
    1 1
    89 89
    (if-let [result (.get dest-memo n)]
      result
      (let [result (dest (step n))]
        (.put dest-memo n result)
        result))))
    
(prn (count (filter #(= 89 %) (map dest (range 1 10000000)))))
;(repl)

