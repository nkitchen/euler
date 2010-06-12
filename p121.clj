(ns euler.p121
  (:use euler.core
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn bit-count [x]
  (loop [x x
         c 0]
    (if (zero? x)
      c
      (recur (bit-shift-right x 1)
             (if (bit-test x 0) (inc c) c)))))
      
(defn win-probability [n]
  (sum (for [bits (range (bit-shift-left 1 n))
             :when (> (bit-count bits) (quot n 2))]
         (prod (for [i (range n)
                     :let [p (/ 1 (+ i 2))]]
                 (if (bit-test bits i)
                   p
                   (- 1 p)))))))

(defn max-prize [n]
  (let [p (win-probability n)]
    (+ 1 (quot (- 1 p) p))))

(repl)
