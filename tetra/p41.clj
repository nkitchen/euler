(ns euler
  (:use [clojure.contrib.lazy-seqs :only [permutations]]))

(load-file "euler.clj")

(defn pandigitals [k]
  (map from-digits 
       (permutations (reverse (range 1 (inc k))))))

(def ans 
  (filter #(.isProbablePrime (bigint %) 20)
          (mapcat pandigitals (reverse (range 1 (inc 8))))))
;          (map #(from-digits (cons 8 %))
;               (permutations (reverse (range 1 (inc 7)))))))
;  
;  (def ans (apply max (mapcat (fn [k]
;                              (filter prime?
;                                      (map from-digits
;                                           (permutations (range 1 (inc k))))))
;                            (range 1 (inc 8)))))
