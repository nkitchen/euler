(ns euler.core
  (:use [clojure.contrib.lazy-seqs :only [primes]])
  (:use [clojure.contrib.seq-utils :only [reductions]]))

(defn tails [s]
  (take-while identity (iterate rest s)))

(defmacro pipe
  "Threads the expr through the forms. Inserts x as the
  last item in the first form, making a list of it if it is not a
  list already. If there are more forms, inserts the first form as the
  last item in second form, etc."
  ([x form] (if (seq? form)
              `(~(first form) ~@(rest form) ~x)
              (list form x)))
  ([x form & more] `(pipe (pipe ~x ~form) ~@more)))

(defn sum [xs] (apply + xs))
(defn prod [xs] (apply * xs))

(defn pow [x y]
  (if (instance? Integer y)
    (prod (replicate y x))
    (Math/pow (double x) (double y))))

(defn factorial [n] (prod (range 1 (inc n))))

(defn choose [n k]
  (/ (factorial n)
     (factorial k) (factorial (- n k))))

(defn digits
  ([n] (digits n 10))
  ([n radix]
    (cond
      (zero? n) (list 0)
      (pos? n)
        (loop [m n
               d nil]
          (if (zero? m)
            d
            (recur (quot m radix) (cons (rem m radix) d)))))))

(defn from-digits [digits]
  (loop [n 0
         ds digits]
    (if (seq ds)
      (recur (+ (* 10 n) (first ds))
             (rest ds))
      n)))

(defn digit-sum [n]
  (sum (digits n)))

(defmulti pandigital? type)
(defmethod pandigital? java.lang.Number [n]
  (pandigital? (digits n)))
(defmethod pandigital? :default [digits]
  (let [k (count digits)
        s (set digits)]
    (and (= k (count s))
         (= s (set (range 1 (inc k)))))))

(defn with-digits [n]
  (range (pow 10 (dec n)) (pow 10 n)))

(defn num-cat
  ([x & ys]
   (from-digits (mapcat digits (cons x ys)))))

(defn gcd [a b]
  (cond
    (< a b) (gcd b a)
    (= b 0) a
    :else (gcd b (rem a b))))

(defn divides?
  "Returns true if n is an integer multiple of k."
  [k n]
  (= (rem n k) 0))

(def *prime-certainty* 20)

(defn prime? [x]
  (.isProbablePrime (bigint x) *prime-certainty*))

(defn factor
  "Returns the prime factors of m as a map to multiplicities."
  [n]
  (assert (pos? n))
  (loop [n n
         candidates primes
         factors (sorted-map)]
    (let [f (first candidates)]
      (cond
        (= 1 n) factors
        (divides? f n)
          (recur (/ n f)
                 candidates
                 (merge-with + factors {f 1}))
        (< f n)
          (recur n (rest candidates) factors)
        :else [:error]))))

(defn factor-products [factors]
  (if (seq factors)
    (let [[f m] (first factors)]
      (for [x (map #(pow f %) (range 0 (inc m)))
            y (factor-products (rest factors))]
        (* x y)))
    [1]))
(def factor-products (memoize factor-products))

(defn divisors [n]
    (sort (factor-products (factor n))))

(defn proper-divisors [n]
  (filter #(< % n) (divisors n)))

(defn remove-first [pred coll]
  (when (seq coll)
    (if (pred (first coll))
      (rest coll)
      (lazy-seq (cons (first coll) (remove-first pred (rest coll)))))))

;(let [prime-set (ref #{})
;      prime-rest (ref primes)]
;  (defn prime? [n]
;    (when (pos? n)
;      (while (>= n (first @prime-rest))
;        (dosync
;          (alter prime-set conj (first @prime-rest))
;          (alter prime-rest rest)))
;      (@prime-set n))))

(defn lexico-next-permutation [p]
  ; If there exists a pair [x y] at consecutive indices such that x <= y,
  ; then the permutation has a lexicographic successor.
  (let [v (vec p)]
    (loop [i (- (count v) 2)]
      (if (neg? i)
        nil
        (let [x (v i)
              y (v (inc i))]
          (if (< x y)
            (let [z (apply min (filter #(> % x) (subvec v (inc i))))]
              (concat (subvec v 0 i)
                      [z]
                      (remove-first #{z} (sort (subvec v i)))))
            (recur (dec i))))))))

(defn lex-successors [p]
  (take-while identity (iterate lexico-next-permutation p)))

;(defn combinations [xs k]
;  (if (zero? k)
;    (list nil)
;    (apply concat (for [[y & zs] (tails xs)]
;                    (map #(cons y %) (combinations zs (dec k)))))))

(defn triangle?
  "= 1/2 k (k + 1) for some integer k."
  [x]
  (zero? (rem (/ (+ -1 (Math/sqrt (+ 1 (* 8 x)))) 2)
              1)))

(defn pentagonal? [x]
  (zero? (rem (/ (+ 1 (Math/sqrt (+ 1 (* 24 x)))) 6)
              1)))

(defn palindrome?
  ([n] (palindrome? n 10))
  ([n radix]
   (let [d (digits n radix)]
     (= d (reverse d)))))
