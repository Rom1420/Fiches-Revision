package org.example;

public class TD1 {

    public static double average(Lst<Lst<Integer>> l) {
        return averageHelper(l.car(), l.cdr(), 0, 0);
    }

    private static double averageHelper(Lst<Integer> cur, Lst<Lst<Integer>> rest, int sum, int count) {
        if(cur == null){
            if(rest == null){
                if(count == 0){
                    return (double) 0;
                }
                return (double) sum / count;
            } else{
                return averageHelper(rest.car(), rest.cdr(), sum, count);
            }
        }
        return averageHelper(cur.cdr(), rest, sum + cur.car(), count + 1);
    }


    public static <T> int length(Lst<T> l) {
        if (l == null) {
            return 0;
        } else {
            return 1 + length(l.cdr());
        }
    }

    public static <T> int lengthTerminale(Lst<T> l) {
        return lengthTerminaleHelper(l, 0);
    }

    private static <T> int lengthTerminaleHelper(Lst<T> l, int i) {
        if(l == null){
            return i;
        }
        return lengthTerminaleHelper(l.cdr(), i + 1)
    }

    public static int max(Lst<Integer> l) {
        if(l == null){
            throw new IllegalArgumentException("List cant be empty !");
        }
        return maxHelper(l, Integer.MIN_VALUE);
    }

    private static int maxHelper(Lst<Integer> l, int currentMax) {
        if (l == null) {
            return currentMax;
        } else {
            int newMax = Math.max(currentMax, l.car());
            return maxHelper(l.cdr(), newMax);
        }
    }

    public static <T> boolean member(T val, Lst<T> l) {
        if(l == null){
            return false;
        } else if (l.car().equals(val)){
           return true;
        }else{
            return member(val, l.cdr());
        }
    }

    public static <T> Lst<T> append(Lst<T> l1, Lst<T> l2) {
        if(l1 == null){
            return l2;
        }
        return new Lst<>(l1.car(), append(l1.cdr(), l2));
    }

    public static <T> Lst<T> remove(T val, Lst<T> l) {
        if(l == null){
            return null;
        } else if(l.car().equals(val)){
            return l.cdr();
        }
        return new Lst<>(l.car(), remove(val, l.cdr()));
    }

    public static <T> Lst<T> removeTerminal(T val, Lst<T> l) {
        return removeTerminalHelper(val, l, null);
    }

    private static <T> Lst<T> removeTerminalHelper(T val, Lst<T> l, Lst<T> acc) {
        if(l == null){
            return acc;
        } else if(l.car().equals(val)) return removeTerminalHelper(val, null, l.cdr());
        return removeTerminalHelper(val, l.cdr(), new Lst<>(l.car(), acc));
    }

    public static <T> Lst<T> removeAll(T val, Lst<T> l) {
        if (l == null) {
            return null;
        } else if (l.car().equals(val)) {
            return removeAll(val, l.cdr());
        } else {
            return new Lst<>(l.car(), removeAll(val, l.cdr()));
        }
    }
    public static <T> Lst<T> removeAllTerminal(T val, Lst<T> l) {
        return removeAllTerminalHelper(val, l, null);
    }

    private static <T> Lst<T> removeAllTerminalHelper(T val, Lst<T> l, Lst<T> acc) {
        if(l == null){
            return acc;
        } else if(l.car().equals(val)) return removeAllTerminalHelper(val, l.cdr(), acc);
        return removeAllTerminalHelper(val, l.cdr(), new Lst<>(l.car(), acc));
    }


    public static Lst<String> fizzbuzz(int a, int b) {
        if (a == b) {
            return null;
        } else {
            Lst<String> next = fizzbuzz(a + 1, b);
            String elt = "" + a;
            if (a % 15 == 0) {
                elt = "FizzBuzz";
            } else if (a % 3 == 0) {
                elt = "Fizz";
            } else if (a % 5 == 0) {
                elt = "Buzz";
            }
            return new Lst<String>(elt, next);
        }
    }

    public static <T> Lst<T> fromArray(T[] arr) {
        return fromArray(arr, 0);
    }

    private static <T> Lst<T> fromArray(T[] arr, int i) {
        if(i >= arr.length){
            return null;
        } else {
            return new Lst<>(arr[i], fromArray(arr, i + 1));
        }
    }

    public static <T> Lst<T> reverse(Lst<T> l) {
        return reverseHelper(l, null);
    }

    private static <T> Lst<T> reverseHelper(Lst<T> l, Lst<T> reversed) {
        if(l == null){
            return reversed;
        }
        return reverseHelper(l.cdr(), new Lst<>(l.car(), reversed));
    }

    public static <T extends Comparable<T>> Lst<T> insert(T val, Lst<T> l) {
        if(l == null || val.compareTo(l.car()) <= 0){
            return new Lst<>(val, l);
        }
        return new Lst<>(l.car(), insert(val, l.cdr()));
    }

    public static <T extends Comparable<T>> Lst<T> sort(Lst<T> l) {
        if(l == null){
            return null;
        }
        return insert(l.car(), sort(l.cdr()));
    }

    public static <T> Lst<T> take(int n, Lst<T> l) {
        if (n <= 0 || l == null)
            return null;
        return new Lst<>(l.car(), take(n - 1, l.cdr()));
    }

    public static <T> int indexOf(T val, Lst<T> l) {
        return 0;
    }

    public static <T> Lst<T> unique(Lst<T> l) {
        return null;
    }


//
//    public static <T, U> boolean has(Lst<Pair<T, U>> l, T k) {
//        return false;
//    }
//
//    public static <T, U> U get(Lst<Pair<T, U>> l, T k) {
//        return null;
//    }
//
//    public static <T, U> Lst<Pair<T, U>> set(Lst<Pair<T, U>> l, T k, U v) {
//        return null;
//    }
}