(ns euler.p103
  (:use euler.core
        euler.special-sum-sets
        clojure.contrib.combinatorics
        clojure.contrib.duck-streams
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn grow-set [s]
  (let [n (count s)
        m (quot n 2)
        b (get s m)]
    (vec (cons b (map #(+ b %) s)))))

(defn grown-set [n]
  (first (filter #(= n (count %)) (iterate grow-set [1]))))

(def #^cvc3.ValidityChecker *checker*)

(defmulti #^cvc3.Expr cvc-expr type)
(defmethod cvc-expr Integer [#^Integer x]
  (.ratExpr *checker* x))
(defmethod cvc-expr cvc3.Expr [e]
  e)
(defmethod cvc-expr java.util.List [a]
  (map cvc-expr a))
(defmethod cvc-expr clojure.lang.Symbol [e]
  (prn "Cannot convert symbol" e)
  (assert false))
(defmethod cvc-expr clojure.lang.ISeq [[op & args]]
  (let [[a b & r] (map cvc-expr args)]
    (condp = op
      '+ (if (instance? java.util.List a)
           (.plusExpr *checker* a)
           (.plusExpr *checker* a b))
      '* (.multExpr *checker* a b))))
(prefer-method cvc-expr clojure.lang.ISeq java.util.List)

(def relation-ctor
  {'= #(.eqExpr *checker* %1 %2) 
   '< #(.ltExpr *checker* %1 %2) 
   '> #(.gtExpr *checker* %1 %2) 
   '<= #(.leExpr *checker* %1 %2) 
   '>= #(.geExpr *checker* %1 %2)
   'not= #(.notExpr *checker* (.eqExpr *checker* %1 %2))})

(defn #^cvc3.Expr cvc-formula [f]
  (let [args (map cvc-expr (rest f))]
    (apply (relation-ctor (first f)) args)))

(defn distinct-subset-constraints [vars]
  (let [n (count vars)]
    (for [k (range 2 (inc (quot n 2)))
          idxs (combinations (range n) (* 2 k))
          signs (unbalanced-matched-seqs k 1 -1)]
      `(~'not= ~0 (~'+ [~@(map #(list '* (vars %1) %2)
                                   idxs
                                   signs)])))))

(defn monotonic-subset-constraints [vars]
  (let [n (count vars)]
    (for [k (range 1 n)
          :while (<= (+ k k 1) n)]
      `(~'> (~'+ ~(subvec vars 0 (inc k)))
            (~'+ ~(subvec vars (- n k)))))))

(defn optimum-special-sum-set [n]
  (binding [*checker* (cvc3.ValidityChecker/create)]
    (let [int-type (.intType *checker*)
          vars (vec (for [i (range n)]
                      (.varExpr *checker* (str "x" i) int-type)))]
      (doseq [f (concat (for [pair (partition 2 1 (cons 0 vars))]
                          (cons '< pair))
                        (distinct-subset-constraints vars)
                        (monotonic-subset-constraints vars))]
        (.assertFormula *checker* (cvc-formula f)))
      (loop [best (grown-set n)]
        (let [max-sum (sum best)]
          (.push *checker*)
          (let [obj `(~'< (~'+ ~vars) ~(dec max-sum))
                result (.checkUnsat *checker* (cvc-formula obj))]
            (if (= result cvc3.SatResult/SATISFIABLE)
              (let [model (.getConcreteModel *checker*)]
                (.pop *checker*)
                (->> (vals model)
                     (map #(.getInteger (.getRational #^cvc3.Expr %)))
                     (sort)))
                ;(recur (.getConcreteModel *checker*))
              best)))))))
                     
(repl)
