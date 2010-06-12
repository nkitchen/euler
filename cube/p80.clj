(ns euler.p80
  (:use euler.core
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn sqrt-digits
  "Returns the first k digits of the decimal expansion of sqrt(n)."
  [n k]
  (with-precision (int (+ k 2))
    (let [step (fn [#^BigDecimal r]
                 (/ (+ r (/ n r)) 2))
          [#^BigDecimal r _] (pipe (iterate step 1M)
                      (partition 2 1)
                      (drop-while (fn [[a b]] (not= a b)))
                      first)
          ds (-> r
               (.scaleByPowerOfTen (dec k))
               (.toBigInteger)
               digits)]
      (take k ds))))
            
(def ans
  (let [squares (set (map #(* % %) (range 1 11)))]
    (reduce + (for [n (remove squares (range 1 101))]
                (sum (sqrt-digits n 100))))))
;; Need 100 digits of decimal expansions of irrational square roots for 
;; 1,...,100.
;;
;; Find convergent pk/qk such that abs(pk/qk - pk-1/qk-1) < 10 ** -100.
;; First digit is a0.  Let r = pk/qk - a0.
;; For k=1,...:
;;   d*10**-k <= r < (d+1) * 10**-k
;;   d <= 10**k * r < d+1
;;   d = floor(10**k * r)
;;   r-= d/10**k

(repl)
