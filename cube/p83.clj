(ns euler.p83
  (:use euler.core
        clojure.contrib.duck-streams
        clojure.contrib.repl-ln)
  (:import clojure.lang.PersistentQueue))

(set! *warn-on-reflection* true)

(defn min-path-sum [mat]
  ; A* search from [0 0] to [m n]
  (let [m (count mat)
        n (count (mat 0))
        neighbors (fn [ij]
                    (for [p [0 1]
                          f [inc dec]
                          :let [[k l :as kl] (update-in ij [p] f)]
                          :when (<= 0 k (dec m))
                          :when (<= 0 l (dec n))]
                      kl))]
    (loop [queue (conj (PersistentQueue/EMPTY) [0 0])
           best (vec (repeat m (vec (repeat n nil))))]
      (if (empty? queue)
        (get-in best [(dec m) (dec n)])
        (let [[i j :as ij] (peek queue)
              c (get-in mat ij)
              sold (get-in best ij)
              adj-sums (for [kl (neighbors ij)
                             :let [s (get-in best kl)]
                             :when s]
                         s)
              snew (cond
                     (seq adj-sums) (+ c (reduce min adj-sums))
                     sold sold
                     :else c)]
          (if (or (nil? sold) (< snew sold))
            (recur (apply conj (pop queue) (neighbors ij))
                   (assoc-in best ij snew))
            (recur (pop queue)
                   best)))))))

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
