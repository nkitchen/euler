(ns euler.p119
  (:use euler.core
        clojure.contrib.def
        clojure.contrib.pprint
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils
        clojure.contrib.seq-utils
        clojure.contrib.str-utils))

(set! *warn-on-reflection* true)

(defn s
  (sort (for [a (range 3 200)
              b (range 2 20)
              :let [n (pow a b)]
              :when (= a (sum (digits n)))]
          n)))

;; Too slow
(defn f
  "Returns a lazy sequence of numbers n such that n = a^b and the sum of
  n's digits is a."
  ([] (f (sorted-set [(pow 3 2) 3 2])))
  ([queue]
   (let [[n a b :as entry] (first queue)
         succ (-> queue
                (conj [(pow (inc a) b) (inc a) b])
                (conj [(pow a (inc b)) a (inc b)])
                (disj entry))]
     (if (and (= a (sum (digits n)))
              (> n 9))
       (cons entry (lazy-seq (f succ)))
       (lazy-seq (f succ))))))

(repl)
