(ns euler)

(load-file "euler.clj")

(def *coins* [200 100 50 20 10 5 2 1])

(defn coin-partitions
  ([n] (coin-partitions n *coins*))
  ([n coins]
   (cond
     (zero? n) (list (replicate (count coins) 0))
     (seq coins)
       (let [c (first coins)]
         ;(prn :n n :coins coins :c c :x (range (inc (quot n c))))
         (for [x (range (inc (quot n c)))
               ps (coin-partitions (- n (* x c)) (rest coins))]
           (cons x ps)))
     :else nil)))

(prn (count (coin-partitions 200)))
