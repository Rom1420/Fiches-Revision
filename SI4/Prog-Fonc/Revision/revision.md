# Explications et exemples des concepts Java

## 1. **Filter**
### Explication
`filter` est une méthode qui permet de sélectionner les éléments d'un flux (stream) qui satisfont une condition donnée. Elle prend un prédicat comme argument, c'est-à-dire une fonction qui retourne un booléen.

### Exemple
```java
public class StreamFilterExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6);
        
        // Filtrage des nombres pairs
        List<Integer> evenNumbers = numbers.stream()
                                           .filter(n -> n % 2 == 0)
                                           .collect(Collectors.toList());

        System.out.println("Nombres pairs: " + evenNumbers);
    }
}
```

---

## 2. **Map**
### Explication
`map` permet de transformer les éléments d'un flux selon une fonction donnée. Cela prend un transformateur (fonction) comme argument et renvoie un nouveau flux avec les éléments modifiés.

### Exemple
```java
public class StreamMapExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Transformation des nombres en leur carré
        List<Integer> squaredNumbers = numbers.stream()
                                              .map(n -> n * n)
                                              .collect(Collectors.toList());

        System.out.println("Nombres carrés: " + squaredNumbers);
    }
}
```

---

## 3. **Reduce**
### Explication
`reduce` permet de combiner les éléments d'un flux en un seul résultat. Cela prend une opération binaire (fonction de deux éléments) et une valeur initiale.

### Exemple
```java
public class StreamReduceExample {
    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);
        
        // Somme des éléments
        int sum = numbers.stream()
                         .reduce(0, (a, b) -> a + b);

        System.out.println("Somme: " + sum);
    }
}
```

---

## 4. **Stream**
### Explication
Un `Stream` en Java est une séquence d'éléments pouvant être traitée de manière fonctionnelle. Il peut provenir de différentes sources comme des collections, des tableaux ou des entrées/sorties.

### Exemple
```java
public class StreamExample {
    public static void main(String[] args) {
        List<String> words = Arrays.asList("apple", "banana", "cherry");
        
        // Filtrage des mots dont la longueur est supérieure à 5
        List<String> longWords = words.stream()
                                      .filter(w -> w.length() > 5)
                                      .collect(Collectors.toList());

        System.out.println("Mots longs: " + longWords);
    }
}
```

---

## 5. **SupplierStream**
### Explication
Un `Supplier` est une interface fonctionnelle qui fournit un élément sans prendre d'argument. Un `SupplierStream` pourrait désigner un flux alimenté par un `Supplier`, où chaque élément est généré par la fonction `get()` du `Supplier`.

### Exemple
```java
public class SupplierStreamExample {
    public static void main(String[] args) {
        Supplier<Integer> randomNumberSupplier = () -> (int) (Math.random() * 100);
        
        // Génération d'un flux de nombres aléatoires
        List<Integer> randomNumbers = Stream.generate(randomNumberSupplier)
                                            .limit(5)
                                            .collect(Collectors.toList());

        System.out.println("Nombres aléatoires: " + randomNumbers);
    }
}
```

---

## 6. **AtomicInteger**
### Explication
`AtomicInteger` est une classe qui permet de manipuler des entiers de manière atomique, utile pour les applications multi-threading où la synchronisation est nécessaire pour garantir que l'accès à la variable est sécurisé.

### Exemple
```java
public class AtomicIntegerExample {
    public static void main(String[] args) {
        AtomicInteger atomicCounter = new AtomicInteger(0);
        
        // Incrémentation atomique
        atomicCounter.incrementAndGet();
        atomicCounter.incrementAndGet();

        System.out.println("Valeur de l'AtomicInteger: " + atomicCounter.get());
    }
}
```

---

## 7. **Records**
### Explication
Introduits en Java 14, les `records` sont des classes immuables simplifiées pour contenir des données. Un `record` réduit le boilerplate pour la déclaration de classes, en générant automatiquement des méthodes comme `toString()`, `equals()`, `hashCode()`.

### Exemple
```java
public class RecordExample {
    // Déclaration d'un record
    public record Person(String name, int age) {}

    public static void main(String[] args) {
        // Création d'un objet de type Person
        Person person = new Person("John", 25);

        // Accès aux champs du record
        System.out.println("Nom: " + person.name());
        System.out.println("Âge: " + person.age());
    }
}
```
