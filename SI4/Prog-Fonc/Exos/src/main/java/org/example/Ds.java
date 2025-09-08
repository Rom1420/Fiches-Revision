package org.example;

import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Function;
import java.util.stream.Stream;
import java.util.stream.IntStream;


public class Ds {
    public static <Te> Function<Te, Te> retainer(Te e) {
        // Stocke la valeur précédente dans une référence mutable
        AtomicReference<Te> previous = new AtomicReference<>(e);

        return (Te newValue) -> {
            Te oldValue = previous.get(); // Sauvegarde l'ancienne valeur
            previous.set(newValue);       // Met à jour avec la nouvelle valeur
            return oldValue;              // Retourne l'ancienne valeur
        };
    }

    public Stream<Character> toStream(String s){
        return IntStream.iterate(0, i -> i + 1)
                .limit(s.length())
                .mapToObj(s::charAt);
    }



    public static void main(String[] args) {
        Function<Integer, Integer> retain = retainer(0);

        System.out.println(retain.apply(10)); // Affiche 0 (valeur initiale)
        System.out.println(retain.apply(20)); // Affiche 10 (valeur précédente)
        System.out.println(retain.apply(30)); // Affiche 20 (valeur précédente)
    }
}
