;;; There exists exactly one Pythagorean triplet for which a + b + c = 1000.
;;; Find the product abc.

(ns euler)

(def n 1000)

(defn pythagorean? [a b c]
  (= (+ (* a a) (* b b))
     (* c c)))

(def nats (range 1 (inc n)))

(def triplets (filter #(apply pythagorean? %)
                      (for [a nats
                            b nats
                            :when (< a b)]
                        [a b (- n a b)])))
(assert (= (count triplets) 1))
(prn (apply * (first triplets)))
