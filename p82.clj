(ns euler.p82
  (:use euler.core
        clojure.contrib.duck-streams
        clojure.contrib.repl-ln))

(set! *warn-on-reflection* true)

(defn min-path-sum [mat]
  (let [m (count mat)
        n (count (mat 0))
        memo (atom {})
        opp {:up :down, :down :up}
        mps (fn mps [& [i j dir :as args]]
              (if-let [s (@memo args)]
                s
                (let [x (get-in mat [i j])
                      s (if
                          (== j (dec n)) x
                          (let [k ({:up (dec i)
                                    :down (inc i)
                                    :right i}
                                   dir)
                                l ({:up j
                                    :down j
                                    :right (inc j)}
                                   dir)]
                            (+ x
                               (reduce
                                 min
                                 (for [d [:up :down :right]
                                       :when (not= d (opp dir))
                                       :when (or (pos? k) (not= d :up))
                                       :when (or (< k (dec m)) (not= d :down))]
                                   (mps k l d))))))]
                  (swap! memo assoc args s)
                  s)))]
    (reduce min (for [i (range m)
                      dir [:up :down :right]
                      :when (or (pos? i) (not= dir :up))
                      :when (or (< i (dec m)) (not= dir :down))]
                  (mps i 0 dir)))))

;(def m [[131	673	234	103	18]
;        [201	96	342	965	150]
;        [630	803	746	422	111]
;        [537	699	497	121	956]
;        [805	732	524	37	331]])

(def ans
;  (min-path-sum m))
  (let [m (with-open [r (reader "matrix.txt")]
            (into [] (for [line (line-seq r)]
                       (into [] (for [word (.split line ",")]
                                  (Integer/parseInt word))))))]
    (min-path-sum m)))
(repl)
