(ns euler.p83
  (:use euler.core
        clojure.contrib.duck-streams
        clojure.contrib.repl-ln)
  (:import java.util.PriorityQueue))

(set! *warn-on-reflection* true)

(defn min-path-sum [mat]
  ; A* search from [0 0] to [m n]
  (let [m (count mat)
        n (count (mat 0))
        u (reduce min (map #(reduce min %) mat))
        dist (fn [i j] (+ (Math/abs (double (- m 1 i)))
                          (Math/abs (double (- n 1 j)))))
        queue (PriorityQueue.)]
    (.add queue (with-meta [(get-in mat [0 0]) [0 0]]
                           {:path #{[0 0]}}))
    (loop []
      (let [entry (.poll queue)
            s (first entry)
            [i j :as ij] (second entry)
            {path :path} (meta entry)]
        (if (and (== i (dec m))
                 (== j (dec n)))
          s
          (do 
            (doseq [p [0 1]
                    f [inc dec]
                    :let [[k l :as kl] (update-in ij [p] f)]
                    :when (<= 0 k (dec m))
                    :when (<= 0 l (dec n))
                    :when (not (path kl))
                    :let [e (* (dist k l) u)
                          c (+ s e (get-in mat [k l]))]]
              (.add queue (with-meta [c kl]
                                     {:path (conj path kl)})))
            (recur)))))))

(def m1 [[131	673	234	103	18]
        [201	96	342	965	150]
        [630	803	746	422	111]
        [537	699	497	121	956]
        [805	732	524	37	331]])

(def m2
  (with-open [r (reader "matrix.txt")]
    (into [] (for [line (line-seq r)]
               (into [] (for [word (.split line ",")]
                          (Integer/parseInt word)))))))

(let [mps min-path-sum]
  (defn min-path-sum [& args]
    (try
      (apply mps args)
      (catch java.lang.OutOfMemoryError e (System/exit 1)))))
(repl)
