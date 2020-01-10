(ns clojure-sandbox.destructuring-test
  (:require [clojure.test :refer :all]))

(def simple-map-1-layer {:firstname "John" :lastname "Pyeatt" :age 59 :hometown "Somewhere, USA"})
(def simple-vec-1-layer ["John" "Pyeatt" 59])
(def simple-vec-2-layer ["74" ["fred" "steve"] 42])

(deftest test-simple-in-let
  (testing "destructure to 3 args from a vector based on position.
            Essentially you are taking the source vector and binding values
            in another vector of variables.
            Also note we are ignoring the final element of simple-vec-1-layer :hometown"
    (let [[firstname lastname age] simple-vec-1-layer]
      (is (= firstname (nth simple-vec-1-layer 0)))
      (is (= lastname (nth simple-vec-1-layer 1)))
      (is (= age (nth simple-vec-1-layer 2)))))

  (testing "destructure a vector which has another vector in it into individual values."
    (let [[height [fred steve] forty-two] simple-vec-2-layer]
      (is (= height (first simple-vec-2-layer)))
      (is (= fred "fred"))
      (is (= steve "steve"))
      (is (= forty-two 42))))

  (testing "destructure a vector which has another vector in it into first level response."
    (let [[height fred-steve-vec forty-two] simple-vec-2-layer]
      (is (= height (first simple-vec-2-layer)))
      (is (= fred-steve-vec ["fred" "steve"]))
      (is (= forty-two 42)))))
