(ns euler.p84
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils)
  (:import [cern.colt.matrix DoubleFactory1D DoubleFactory2D DoubleMatrix2D]
           [cern.colt.matrix.linalg EigenvalueDecomposition]
           [cern.jet.math Functions]))

; (set! *warn-on-reflection* true)

(def squares [:go :a1 :cc1 :a2 :t1 :r1 :b1 :ch1 :b2 :b3
              :jail :c1 :u1 :c2 :c3 :r2 :d1 :cc2 :d2 :d3
              :fp :e1 :ch2 :e2 :e3 :r3 :f1 :f2 :u2 :f3
              :g2j :g1 :g2 :cc3 :g3 :r4 :ch3 :h1 :t2 :h2])

(def square-index (into {} (map vector squares
                                (iterate inc 0))))

(def redirects {:g2j {:jail 1}
                :cc1 {:jail 1/16, :go 1/16, :cc1 14/16}
                :cc2 {:jail 1/16, :go 1/16, :cc2 14/16}
                :cc3 {:jail 1/16, :go 1/16, :cc3 14/16}
                :ch1 {:go 1/16, :jail 1/16, :c1 1/16, :e3 1/16, :h2 1/16,
                      :r1 1/16, :r2 2/16, :u1 1/16, :t1 1/16, :ch1 6/16}
                :ch2 {:go 1/16, :jail 1/16, :c1 1/16, :e3 1/16, :h2 1/16,
                      :r1 1/16, :r3 2/16, :u2 1/16, :d3 1/16, :ch2 6/16}
                :ch3 {:go (+ 1/16 (* 1/16 1/16)),
                      :jail (+ 1/16 (* 1/16 1/16)),
                      :c1 1/16, :e3 1/16, :h2 1/16, :r1 3/16, :u1 1/16,
                      :cc3 (* 1/16 14/16),
                      :ch3 6/16}})

(defn sum-dist
  "Distribution of sums of values from distributions f and g."
  [f g]
  (apply merge-with + (for [[x p] f
                            [y q] g]
                        {(+ x y) (* p q)})))

(defn roll-dist
  "Distribution of roll values for n dice, k sides each."
  [n k]
  (let [r (for [x (range 1 (inc k))] [x (/ 1 k)])]
    (reduce sum-dist (repeat n r))))


(defn square-dist
  "Stationary distribution of squares, using n dice with k sides each."
  [n k]
  (let [m (count squares)
        F1 DoubleFactory1D/dense
        F2 DoubleFactory2D/sparse
        #^DoubleMatrix2D M (. F2 make m m)
        rolls (roll-dist n k)]
    (doseq [i (range m)
            [steps p] rolls
            :let [s (squares i)
                  j (mod (+ i steps) m)
                  t (squares j)]
            [u q] (get redirects t {t 1})
            :let [k (square-index u)]]
      (.set M i k (+ (double (* p q))
                     (.get M i k))))
    (let [eigen (EigenvalueDecomposition. (.viewDice M))
          _ (prn (-> eigen
                   (.getRealEigenvalues)
                   (.get 0)))
          ;_ (assert (>= 1.0e-6 (Math/abs (- 1
          ;                                  (-> eigen
          ;                                    (.getRealEigenvalues)
          ;                                    (.get 0))))))
          v (-> eigen
              (.getV)
              (.viewColumn 0))
          p (.assign v (Functions/div (.zSum v)))]
      (reverse (sort (for [i (range m)]
                       [(.get p i) (squares i)]))))))
    
(def ans (square-dist 2 4))
(repl)
