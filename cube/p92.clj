(ns euler.p92
  (:use euler.core
        clojure.contrib.cond
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(def square #(* % %))

(defn step [n]
  (sum (map square (digits n))))

(def dest-memo (atom {}))
(defn dest [n]
  (cond-let [result]
    (#{1 89} n) n
    (@dest-memo n) result
    :else
      (let [result (dest (step n))]
        (when (< n 600)
          (swap! dest-memo assoc n result))
        result)))
    
(def ans
  (->> (range 1 10000000)
       (map #(do
               (when (= (mod (inc %) 100000) 0)
                 (print \.)
                 (flush))
               %))
       (map #(dest (step %)))
       (filter #(= 89 %))
       (count)))

(prn ans)
;(repl)

