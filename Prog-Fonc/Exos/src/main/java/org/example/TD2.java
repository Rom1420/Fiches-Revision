package org.example;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;

public class TD2 {
    public static<T,R> Lst<R> map(Function<T,R> f, Lst<T> l) {
        if(l == null){
            return null;
        }
        return new Lst<>(f.apply(l.car()), map(f, l.cdr()));
    }

    public static Lst<Integer> squares(Lst<Integer> l) {
        return map(x -> x * x, l);
    }

    public static Lst<Integer> sizeOfStrings(Lst<String> l) {
        return map(String::length, l);
    }

    public static<T> Lst<T> filter(Predicate<T> f, Lst<T> l) {
        if(l == null){
            return null;
        } else if(f.test(l.car())) {
            return new Lst<>(l.car(), filter(f, l.cdr()));
        }
        return filter(f, l.cdr());
    }

    public static Lst<String> lowers(Lst<String> l) {
        return filter(s -> s.equals(s.toLowerCase()), l);
    }

    public static<T> int count(Predicate<T> f, Lst<T> l) {
        if(l == null){
            return 0;
        } else if(f.test(l.car())){
            return 1 + count(f, l.cdr());
        }
        return count(f, l.cdr());
    }

    public static<T> int countTerminale(Predicate<T> f, Lst<T> l) {
        return countTerminaleHelper(f, l, 0);
    }

    private static<T> int countTerminaleHelper(Predicate<T> f, Lst<T> l, int i) {
        if(l == null){
            return i;
        } else if(f.test(l.car())){
            return countTerminaleHelper(f, l.cdr(), i + 1);
        }
        return countTerminaleHelper(f, l.cdr(), i);
    }

    public static int nbPositives(Lst<Integer> l) {
        return countTerminale(x -> x >= 0, l);
    }

    public static<T,R> R reduce(BiFunction<T,R,R> f, Lst<T> l, R init) {
        if(l == null){
            return init;
        }
        return f.apply(l.car(), reduce(f, l.cdr(), init));
    }

    public static<T,R> R reduceTerminale(BiFunction<T,R,R> f, Lst<T> l, R init) {
        return reduceTerminaleHelper(f, l, init);
    }

    private static <R, T> R reduceTerminaleHelper(BiFunction<T,R,R> f, Lst<T> l, R acc) {
        if(l == null){
            return acc;
        }
        return reduceTerminaleHelper(f, l.cdr(), f.apply(l.car(), acc));
    }

    public static int sum(Lst<Integer> l) {
        return reduceTerminale(Integer::sum, l, 0);
    }

    public static<T extends Comparable<T>> T min(Lst<T> l) {
        return reduceTerminale((x, y) -> x.compareTo(y) < 0 ? x : y, l.cdr(), l.car());
    }

    public static int sumLengthLowers(Lst<String> l) {
        return sum(sizeOfStrings(lowers(l)));
    }

    public static int sumLength(Lst<String> l) {
        return reduce(Integer::sum, map(String::length, l), 0);
    }

    public static<T> String repr(Lst<T> l) {
        return "(" + reduce((s1, acc) -> s1 + " " + acc, map(Object::toString, l), ")");
    }

    public static<T> Lst<T> append(Lst<T> l1, Lst<T> l2) {
        return reduce(Lst::new, l1, l2);
    }

    public static<T> Lst<T> concat(Lst<Lst<T>> ll) {
        return reduce(TD2::append, ll, null);
    }

    public static<T> Lst<T> toSet(Lst<T> l) {
        return reduce((car, l2) -> TD1.member(car, l2) ? l2 : new Lst<>(car, l2), l, null);
    }

    public static<T> Lst<T> union(Lst<T> s1, Lst<T> s2) {
        return toSet(reduce((x, acc) -> new Lst<>(x, acc), s1, s2));
    }

    public static int product(Lst<Integer> l){
        return reduce((x, acc) -> x * acc, l, 1);
    }

    public static <T> boolean allMatch(Predicate<T> pred, Lst<T> l){
        return reduce((x, acc) -> pred.test(x) && acc, l, true);
    }

    public static <T> int length(Lst<T> l){
        return reduce((x ,acc) -> 1 + acc, l, 0);
    }

    public static <T> Lst<T> reverse(Lst<T> l){
        return reduce((x, acc) -> append(acc, new Lst<>(x, null)), l, null);
    }

    public static <T> boolean contains(Lst<T> l, T e) {
        return reduce((x, acc) -> x.equals(e) || acc, l, false);
    }



    public static<T> Predicate<T> equalsTo(T x) {
        return null;
    }

    public static<T extends Comparable<T>> Predicate<T> between(T a, T b) {
        return null;
    }

    public static<T> int countOccurence(Lst<T> l, T e) {
        return 0;
    }
}