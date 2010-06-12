(ns euler)

(load-file "euler.clj")

;;; No more than 6 digits

(defn p [n]
  (= n (sum (map factorial (digits n)))))

(prn (filter p (range 10 1000000)))
