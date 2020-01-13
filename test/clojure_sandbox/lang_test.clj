(ns clojure-sandbox.lang-test
  (:require [clojure.test :refer :all]))
(deftest test-lang-functions

  (is (number? 1234))
  (is (number? 1234.234))
  (is (not (number? "Abc")))
  (is (not (number? "Abc123")))

  (is (string? "john"))
  (is (string? ""))
  (is (not (string? nil)))

  (is (keyword? :status))
  (is (not (keyword? "status")))

  (is (map? {:firstname "John"}))
  (is (not (map? :firstname)))

  (is (vector? []))
  (is (vector? ["John" "Pyeatt"]))
  (is (not (vector? '("John" "Pyeatt")))))
