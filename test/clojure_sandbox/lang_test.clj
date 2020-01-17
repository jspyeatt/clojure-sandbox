(ns clojure-sandbox.lang-test
  (:require [clojure.test :refer :all])
  (:require [clojure.string :as str]))
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

(deftest test-math
  (is (= (/ 4 3) 4/3) "This really isn't division as it's a ratio of integers")
  (is (= (/ 4 2) 2))
  (is (= (quot 5 2) 2) "integer math")
  (is (= (/ 5.0 2.0) 2.5))

  (is (= 5 (max 1 3 5 2 -1))))

(deftest test-test-numbers
  (is (zero? 0))
  (is (pos? 1))
  (is (not (pos? 0)))
  (is (neg? -1))
  (is (not (neg? 0))))

(deftest test-strings
  (is (= "STRING string NUMBER 123 BOOLEAN true FLOAT 5.40" (format "STRING %s NUMBER %d BOOLEAN %b FLOAT %.2f" "string" 123 true 5.4)))
  (testing "subs"
    ;;         0123456789012345
    (let [src "abcdefghijklmnop"]
      (is (= src (subs src 0)) "just returns entire string")
      (is (= "ijklmnop" (subs src 8)) "starts at the 8th index or ninth character in the string")
      (is (= "ij" (subs src 8 10)) "the second argument means: up to the 10th index, non-inclusive.")))

  (testing "compare"
    (is (neg? (compare "abc" "def")) "first string less than second")
    (is (pos? (compare "def" "abc")) "second string less than first")
    (is (zero? (compare "abc" "abc")) "strings are equal"))

 (testing "join"
   (is (= "Thisisatest" (str/join ["This" "is" "a" "test"])) "no separator specified")
   (is (= "This|is|a|test" (str/join "|" ["This" "is" "a" "test"]))))

 (testing "split"
   (is (= ["This"
           "is"
           "a"
           "test."] (str/split "This is a test." #" "))))

 (testing "split-lines"
   (is (= ["This is a test. Of the emergency"
           "broadcast system."] (str/split-lines "This is a test. Of the emergency\nbroadcast system."))))

 (testing "replace"
   (let [src "This is a test of the emergency broadcast system."]
     (is (= "ThiSSS iSSS a teSSSt of the emergency broadcaSSSt SSSySSStem." (str/replace src "s" "SSS")))
     (is (= "This is a teSSTT of the emergency broadcaSSTT sySSTTem." (str/replace src "st" "SSTT")))
     (is (= "This is XY test of the emergenXYy XYroXYdXYst system." (str/replace src #"(a|b|c)+" "XY")) "if an a, b, or c is found replace it with XY")
     (is (= "This is XY test of the emergency broadcast system." (str/replace-first src #"(a|b|c)+" "XY")) "replace first occurrance of a, b, or c with XY"))))