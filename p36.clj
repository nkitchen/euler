(ns euler)

(load-file "euler.clj")

(defn palindrome?
  ([n] (palindrome? n 10))
  ([n radix]
   (let [d (digits n radix)]
     (= d (reverse d)))))

(defn multi-palindrome? [n bases]
  (every? #(palindrome? n %) bases))
