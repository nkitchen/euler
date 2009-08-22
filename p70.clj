(ns euler.p70
  (:use euler.core
        clojure.contrib.lazy-seqs
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn totient [n]
  (if (prime? n)
    (dec n)
    (reduce * n (for [[p m] (factor n)] (/ (dec p) p)))))

(defn p [n]
  (= (sort (digits n))
     (sort (digits (totient n)))))

(def ans
  (pipe
    (take-while #(< % 10000000) primes)
    (map #(vector % (totient %)))
    (filter (fn [[n t]]
              (= (sort (digits n))
                 (sort (digits t)))))
    last))
;  (pipe (for [n (range 1 10000000)] 
;          [n (totient n)])
;        (map (fn [[n t :as v]]
;               (when (= (mod n 10000) 9999)
;                 (print \.)
;                 (flush))
;               v))
;        (filter (fn [[n t r]]
;                  (= (sort (digits n))
;                     (sort (digits t)))))
;        (map (fn [[n t]] [n t (/ n t)]))
;        (reduce (fn [a b] (min-key #(% 2) a b)))))

(repl)
