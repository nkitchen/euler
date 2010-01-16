(ns euler.p94
  (:gen-class)
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn -main []
  (let [ans
;  (sum
;    (for [s (iterate #(+ % 2) 3)
;          off [-1 1]
;          :let [t (+ s off)
;                b (/ t 2)
;                d (- (* s s) (* b b))
;                h (long (Math/sqrt d))]
;          :when (= (* h h) d)]
;      (+ s s t))))
  (loop [s (long 3)
         off (int -1)
         total (long 0)]
    (cond
      (> off (int 1)) (recur (long (+ s (int 2))) (int -1) total)
      (> (* (long 3) s) (long 1000000001)) total
      :else
        (let [t (long (+ s off))
              b (long (/ t 2))
              d (long (- (* s s) (* b b)))
              h (long (Math/sqrt d))
              p (long (+ (+ s s) t))]
          (if (== (* h h) d)
            (recur s (int (+ off 2)) (long (+ total p)))
            (recur s (int (+ off 2)) total)))))]
    (prn ans)))
(-main)
;(repl)

