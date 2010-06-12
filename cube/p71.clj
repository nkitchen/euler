(ns euler.p71
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(def ans
  (loop [d 2
         nbest 0
         dbest 1]
    (if (== d 1000001)
      nbest
      (let [n* (quot (* 3 d) 7)
            n (if (== (* 7 n*) (* 3 d))
                (dec n*)
                n*)]
        (if (> (* n dbest) (* d nbest))
          (recur (inc d) n d)
          (recur (inc d) nbest dbest))))))

(repl)
