(ns euler.p92
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(def square #(* % %))

(defn step [n]
  (sum (map square (digits n))))

(def dest-memo (atom {}))
(defn dest [n]
  (condp = n
    1 1
    89 89
    (if-let [result (@dest-memo n)]
      result
      (let [result (dest (step n))]
        (swap! dest-memo assoc n result)
        result))))
    
(prn (count (filter #(= 89 %) (map dest (range 1 10000000)))))
;(repl)

