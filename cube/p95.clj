(ns euler.p95
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(defn succ [n]
  (sum (proper-divisors n)))

(def *max-elem* 1000000)

(let [memo (atom {})]
  (defn amicable-chain
    ([n] (amicable-chain n {}))
    ([n seen]
      (let [result (get @memo n [])]
        (cond
          (not= result []) result
          (> n *max-elem*) nil
          (= n 1) nil
          :else
            (let [result
                    (if (= (seen n) 2)
                      (apply sorted-set (for [[k m] seen :when (= m 2)] k))
                      (amicable-chain (succ n)
                                      (assoc seen
                                             n
                                             (inc (get seen n 0)))))]
              (swap! memo assoc n result)
              result))))))

(def ans
  (->> (range 1 (inc *max-elem*))
       (map (fn [n]
              (when (= 0 (mod (inc n) 10000)) (print ".") (flush))
              n))
       #_(map (fn [n]
              (let [c (amicable-chain n)]
                (prn n c)
                c)))
       (map amicable-chain)
       (remove nil?)
       (filter #(< (first (rseq %)) *max-elem*))
       (apply max-key count)
       (apply min)))
;(prn ans)

(repl)

