package org.example;

import com.sun.source.doctree.EscapeTree;

import java.util.*;
import java.util.stream.Collectors;

public class StreamExercises {

    // 1️⃣ Retourner la somme des nombres pairs dans la liste
    public static int sumOfEvenNumbers(List<Integer> numbers) {
        return numbers.stream()
                .filter(x -> x % 2 == 0)
                .reduce(0, Integer::sum);
    }

    // 2️⃣ Retourner la liste des carrés des nombres impairs
    public static List<Integer> squareOfOddNumbers(List<Integer> numbers) {
        // TODO : Utiliser filter + map + collect
        return numbers.stream().filter(x -> x % 2 == 1)
                .map(x -> x *x)
                .collect(Collectors.toList());
    }

    // 3️⃣ Compter le nombre de chaînes de plus de 5 caractères
    public static long countLongStrings(List<String> strings) {
        // TODO : Utiliser filter + count
        return strings.stream().filter(s -> s.length() > 5).count();
    }

    // 4️⃣ Trouver la chaîne la plus longue (ou retourner "" si la liste est vide)
    public static String findLongestString(List<String> strings) {
        // TODO : Utiliser max avec Comparator
        return strings.stream().max(Comparator.comparingInt(String::length)).orElse("");
    }

    // 5️⃣ Concaténer les chaînes en majuscules séparées par des virgules
    public static String concatenateUpperCase(List<String> strings) {
        // TODO : Utiliser map + Collectors.joining
        return strings.stream().map(String::toUpperCase).collect(Collectors.joining(","));
    }

    // 6️⃣ Vérifier si la liste contient au moins un nombre négatif
    public static boolean containsNegative(List<Integer> numbers) {
        // TODO : Utiliser anyMatch
        return numbers.stream().anyMatch(x -> x < 0);
    }

    // 7️⃣ Calculer le produit de tous les éléments de la liste
    public static int productOfList(List<Integer> numbers) {
        // TODO : Utiliser reduce
        return numbers.stream().reduce(1, (a, b) -> a * b);
    }

    // 8️⃣ Retourner la moyenne des nombres (OptionalDouble si la liste est vide)
    public static OptionalDouble averageOfNumbers(List<Integer> numbers) {
        // TODO : Utiliser mapToInt + average
        return numbers.stream().mapToInt(Integer::intValue)
                .average();
    }

    // 9️⃣ Extraire les doublons d'une liste
    public static List<Integer> findDuplicates(List<Integer> numbers) {
        // TODO : Identifier les doublons et les retourner sous forme de liste
        return numbers.stream()
                .filter(x -> Collections.frequency(numbers, x) > 1)
                .distinct().collect(Collectors.toList());
    }

    // 🔟 Retourner les 3 premiers éléments triés par ordre décroissant
    public static List<Integer> top3Descending(List<Integer> numbers) {
        // TODO : Utiliser sorted + limit
        return numbers.stream().sorted(Comparator.reverseOrder()).limit(3).collect(Collectors.toList());
    }

    // Classe principale pour tester les méthodes
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1, 2, 3, 4, 5, 6, 4, 3);
        List<String> words = Arrays.asList("apple", "banana", "kiwi", "strawberry", "pear");

        System.out.println(sumOfEvenNumbers(nums));           // Attendu : 12 (2 + 4 + 6)
        System.out.println(squareOfOddNumbers(nums));         // Attendu : [1, 9, 25, 9]
        System.out.println(countLongStrings(words));          // Attendu : 2 ("banana", "strawberry")
        System.out.println(findLongestString(words));         // Attendu : "strawberry"
        System.out.println(concatenateUpperCase(words));      // Attendu : "APPLE,BANANA,KIWI,STRAWBERRY,PEAR"
        System.out.println(containsNegative(nums));           // Attendu : false
        System.out.println(productOfList(nums));              // Attendu : 8640 (produit de tous les éléments)
        System.out.println(averageOfNumbers(nums));           // Attendu : OptionalDouble[3.5]
        System.out.println(findDuplicates(nums));             // Attendu : [4, 3] (doublons)
        System.out.println(top3Descending(nums));             // Attendu : [6, 5, 4]
    }
}

