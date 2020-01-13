(ns clojure-sandbox.vector-test
  (:require [clojure.test :refer :all]))

(def blended-type-vector ["one" "two" 3 true "five" 6])

(deftest test-take-rest
  (is (= ["one" "two" 3] (take 3 blended-type-vector)) "use the first 3 elements")
  (is (= ["two" 3 true "five" 6] (rest blended-type-vector)) "all elements except the first")
  (is (= [3 true "five" 6] (rest (rest blended-type-vector))) "all but the first 2 elements with rest rest"))

(deftest test-conj-cons
  (testing "conj: conjunction adds elements to the end of a vector"
    (is (= ["one" "two" 3 true "five" 6 "seven" "eight"] (conj blended-type-vector "seven" "eight"))))

  (testing "conj: add a vector to a vector"
    (is (= ["one" "two" 3 true "five" 6 [7 8]] (conj blended-type-vector [7 8])) "note 7 8 is a nested vector."))

  (testing "cons: constructs, adds elements to the front of a vector"
    (is (= ["zero" "one" "two" 3 true "five" 6] (cons "zero" blended-type-vector)))))
