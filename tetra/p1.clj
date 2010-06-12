(def nats (iterate inc 1))
(defn mul3? [x] (= (rem x 3) 0))
(defn mul5? [x] (= (rem x 5) 0))

(def nums
  (let [low-nats (take-while #(< % 1000) nats)
        mults (filter #(or (mul3? %) (mul5? %)) low-nats)]
    (apply + mults)))

(println nums)
