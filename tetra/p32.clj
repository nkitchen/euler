(ns euler)

(load-file "euler.clj")

;;; Note: We need to consider which combinations of numbers of digits can work.
;;; E.g., 1x4=4, 2x3=4, 
(def *digits* (set (range 1 10)))

(defn pandigital? [a b]
  (let [d (mapcat digits [a b (* a b)])]
    (and (= (count d) 9)
         (= *digits* (set d)))))

(defn pan-prod [[a b]]
  (when (pandigital? a b)
    (* a b)))

(defn with-digits [n]
  (range (pow 10 (dec n)) (pow 10 n)))

(defn pan-prods [m n]
  (remove nil? (map pan-prod (for [a (with-digits m)
                                   b (with-digits n)]
                               [a b])))

(def pans (concat (pan-prods 1 4)
                  (pan-prods 2 3)))
