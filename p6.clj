(ns euler)

;;; Find the difference between the sum of the squares of the first one hundred
;;; natural numbers and the square of the sum.

(defn square [x] (* x x))

(defn sum [xs] (apply + xs))

(let [nums (range 1 101)]
  (prn (- (square (sum nums)) (sum (map square nums)))))
