(ns euler)

;;; A palindromic number reads the same both ways. The largest palindrome made
;;; from the product of two 2-digit numbers is 9009 = 91 × 99.

;;; Find the largest palindrome made from the product of two 3-digit numbers.

(defn reverse-str [s]
  (str (. (new StringBuffer s) (reverse))))

(defn palindrome? [n]
  (let [s (str n)]
    (= s (reverse-str s))))

(def *3-digit-nums* (range 100 1000))

(def prod-pals (filter palindrome?
                       (map (fn [[x y]] (* x y))
                            (for [x *3-digit-nums*
                                  y *3-digit-nums*
                                  (:when (<= x y))]
                              [x y]))))
(prn (apply max prod-pals))
