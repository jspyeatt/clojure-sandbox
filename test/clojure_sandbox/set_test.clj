(ns clojure-sandbox.set-test
  (:require [clojure.test :refer :all]))

(def simple-set #{"John" "Steve" "Fred"})
(deftest test-contains
  (is (contains? simple-set "Steve")))

(deftest test-disj
  (is (= #{"John" "Steve"} (disj simple-set "Fred"))))
