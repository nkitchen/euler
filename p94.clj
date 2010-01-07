(ns euler.p94
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(def ans
;  (sum
;    (for [s (iterate #(+ % 2) 3)
;          off [-1 1]
;          :let [t (+ s off)
;                b (/ t 2)
;                d (- (* s s) (* b b))
;                h (long (Math/sqrt d))]
;          :when (= (* h h) d)]
;      (+ s s t))))
  (loop [s 3
         off -1
         total 0]
    (cond
      (> off 1) (recur (+ s 2) -1 total)
      (> (* 3 s) 1000000001) total
      :else
        (let [t (+ s off)
              b (/ t 2)
              d (- (* s s) (* b b))
              h (long (Math/sqrt d))
              p (+ s s t)]
          (recur s
                 (+ off 2)
                 (if (= (* h h) d)
                   (+ total p)
                   total))))))

(prn ans)
;(repl)

