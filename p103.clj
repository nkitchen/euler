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

(defn random-neighbor [s]
  (let [i (rand-int (count s))
        j (rand-int (count s))
        d (- (* 2 (rand-int 5) 2))
        e (- (* 2 (rand-int 5) 2))
        t (-> s
            (update-in [i] #(+ % d))
            (update-in [j] #(+ % e))
            (sort)
            (vec))]
    (if (and (apply < t)
             (special-sum-set? t))
      t
      s)))

(def *temperature* 1.0)

(defn anneal [state obj-fn neighbor-fn iter-count]
  (loop [state state
         best state
         n iter-count]
    (if (zero? n)
      best
      (let [next-state (neighbor-fn state)
            f-cur (obj-fn state)
            f-next (obj-fn next-state)
            delta (- f-cur f-next)
            new-best (if (< (obj-fn next-state)
                            (obj-fn best))
                       next-state
                       best)]
        (if (and (pos? delta)
                 (>= (rand) (Math/exp (/ (- delta) *temperature*))))
          (recur state new-best (dec n))
          (recur next-state new-best (dec n)))))))

(defn find-optimum-special-sum-set [n iter-count]
  (let [init-set (nth (iterate grow-set [1]) (dec n))
        obj-fn :obj
        neighbor-fn (fn [state]
                      (let [n (random-neighbor (:set state))]
                        {:set n :obj (sum n)}))]
    (anneal {:set init-set :obj (sum init-set)}
            obj-fn
            neighbor-fn
            iter-count)))

(repl)
