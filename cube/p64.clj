(ns euler.p64
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn sqrt-cf-period
  "Period of continued fraction of sqrt(x)."
  [x]
  (let [r (Math/sqrt x)
        a0 (+ 0 (int (Math/floor r)))]
    (if (= x (* a0 a0))
      0
      (loop [i 1
             history {}
             b a0
             c 1]
        (if-let [last (history [b c])]
          (- i last)
          (let [d (/ (- x (* b b)) c)
                ai (quot (+ a0 b) d)
                bi (- (* ai d) b)
                ci d]
            (assert (zero? (rem (- x (* b b)) c)))
            (recur (inc i)
                   (assoc history [b c] i)
                   bi
                   ci)))))))

(def ans (count (for [x (range 2 (inc 10000))
                      :when (= 1 (mod (sqrt-cf-period x) 2))]
                  x)))
(repl)
