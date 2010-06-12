(ns euler.p55
  (:use euler.core))

(defn radd [x]
  (let [y (from-digits (reverse (digits x)))]
    (+ x y)))

(defn lychrel? [x]
  (not (first (filter palindrome? (take 49 (rest (iterate radd x)))))))

(def ans (count (filter lychrel? (range 1 10000))))
(prn ans)

; (def ans (count (filter #(> % 1000000)
;                         (for [n (range 1 (inc 100))
;                               r (range 1 n)]
;                           (choose n r)))))
; (prn ans) 
