(ns euler.p85
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

; (set! *warn-on-reflection* true)

(defn rect-count [m n]
  (* m (+ m 1) n (+ n 1) 1/4))

(def ans
  (apply min-key
         first
         (for [m (range 1 2000001)
               n (take-while #(<= (rect-count m (dec %)) 2000000)
                             (iterate inc (-> (/ 500000 m (+ m 1))
                                            (Math/sqrt)
                                            (Math/floor)
                                            (int)
                                            (dec))))]
           [(Math/abs (- (rect-count m n) 2000000)) (* m n)])))

(repl)
