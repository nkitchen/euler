(ns euler.p91
  (:use euler.core
        clojure.contrib.repl-ln
        clojure.contrib.repl-utils))

(set! *warn-on-reflection* true)

(def *max-xy* 50)

;; I count the right triangles in two groups: those whose shorter sides are
;; aligned with the axes and those that are not aligned.
;;
;; For the first group, there are three right triangles including the origin
;; for each rectangle with non-zero coordinates of the upper-right corner.
;; I just need to count these rectangles.
;;
;; For the second group, I take the vector from (x1,y1) to (0,0) as u and
;; count the orthogonal vectors (x1,y1) to (x2,y2) that have integral
;; coordinates.
;; Orthogonal => vx = -a*uy; vy = a*ux
;;               (u dot v = -ux*a*uy + uy*a*ux = 0)
;; Solving for a and substituting gives the formula below for y2.
(def ans
  (+ (* 3 *max-xy* *max-xy*)
     (count (for [x1 (range 1 (inc *max-xy*))
                  y1 (range 1 (inc *max-xy*))
                  x2 (range 0 (inc *max-xy*))
                  :when (not= x1 x2)
                  :let [y2 (+ y1 (/ (* x1 (- x1 x2)) y1))]
                  :when (= y2 (int y2))
                  :when (<= 0 y2 *max-xy*)]
              [x1 y1 x2 y2]))))

(repl)

