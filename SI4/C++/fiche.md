# Résumé des Concepts C++

## Amitié et Compilation Séparée

```c++
    class A { 
    public: 
        void finc(int n); 
        friend void fonc(int n);
    };
    void A::finc(int n) { cout << "finc"; }
    void fonc(int n) { cout << "fonc"; }
```

- **Fonction amie** : Accède aux membres privés de la classe. 
- **Définition** : Ne doit pas utiliser le préfixe de portée (pas de `A::`).
- **Erreurs** : Utiliser `A::` avec une fonction amie entraîne des erreurs de compilation.
- **Question** : Une fonction amie déclarée dans les membres publics ou privés a-t-elle une différence ?
- **Réponse** : Non, il n'y a pas de différence. Une fonction amie a accès à tous les membres de la classe, quel que soit son emplacement.

---

## Taille
```c++
    class MVector { 
    private:
        vector<double> v;
    public:
        MVector(double a, double b, double c) : v{a, b, c} {}
    };
```
- **Solution** : Préférer l'utilisation d'une liste d'initialisation ``{a, b, c}`` pour initialiser un vecteur au lieu d'assigner les valeurs individuellement.

---

## Conversion
```c++
    class Celsius {
private:
    double temperature;
public:
    Celsius(double t) : temperature(t) {}
    // Opérateur de conversion vers Fahrenheit
    operator double() const {
        return (temperature * 9.0 / 5.0) + 32.0;
    }
};

int main() {
    Celsius temp(25.0); // 25°C
    double fahrenheit = temp; // Conversion implicite
    cout << "Temperature en Fahrenheit: " << fahrenheit << endl; // Affiche 77
    return 0;
}
```
- **Résultat** : Les opérateurs de conversion permettent une conversion implicite entre types, illustrée par des exemples de classes interagissant entre elles.
- ``static_cast`` est un opérateur de conversion en C++ qui permet de convertir un type en un autre de manière explicite.
---

## Constructeurs et Appels
```c++
    class A {
    public:
        A() { cout << "0 "; }
        A(int k) { cout << "1 "; }
    };

    int main() {
        A a; 
        A b(); // Ne crée pas un objet
        A c(1); 
        A d = 2; // Utilise le constructeur avec un int
    }
```
- **Observation** : La déclaration ``A b();`` ne crée pas un objet. Les appels à des constructeurs doivent être effectués correctement pour éviter des comportements inattendus.

## Const et Méthodes
```c++
    class A {
        int n;
    public:
        int getn() { return n; }
        int compute() const { return getn() + 2; }
    };
```
- **Explication** : Une méthode marquée ``const`` garantit qu'elle ne modifie pas l'état interne de l'objet. Les méthodes non ``const`` ne peuvent pas être appelées dans une méthode ``const`` si elles ne sont pas elles-mêmes ``const``.

---

## Héritage

- **Concept** : L'héritage permet à une classe (dite **classe dérivée**) d'hériter des caractéristiques d'une autre classe (dite **classe de base**). Cela favorise la réutilisation du code et la création de hiérarchies de classes.

- **Constructeurs** : Lorsqu'un objet de la classe dérivée est créé, le constructeur de la classe de base est appelé en premier. Cela garantit que les membres de la classe de base sont correctement initialisés avant d'initialiser les membres spécifiques de la classe dérivée.

- **Exemple** :
  ```cpp
  class A {
      public:
          A() { cout << "Constructeur de A"; }
  };

  class B : public A {
      public:
          B() { cout << "Constructeur de B"; }
  };

  int main() {
      B obj; // Affiche "Constructeur de A" puis "Constructeur de B"
  }
  ```

> - **Sortie :** dans l'exo du ds23-24 : A(int)B()B(int) 

## Notes sur `MVector`
- **`MVector`** utilise un vecteur pour stocker des éléments, permettant une gestion dynamique de la taille et une initialisation efficace par liste.

## Manipulation de `std::vector` en C++

- **Déclaration d'un vecteur** :
  ```cpp
  vector<int> vec; // Vecteur vide d'entiers
  vector<double> vec2{1.1, 2.2, 3.3}; // Initialise un vecteur avec trois éléments : 1.1, 2.2, 3.3
  vector<double> vec3(5, 1.5); // Crée un vecteur de taille 5, tous les éléments initialisés à 1.5
    ```
- **Remplissage d'un vecteur :** :
    ```cpp
    vec.push_back(4); // Ajoute l'élément 4 à la fin du vecteur
    vec.push_back(5); // Ajoute l'élément 5
    ```
- **Accéder à un élément :** :
  ```cpp
  int value = vec[0]; // Accède au premier élément
    ```
- **Taille et Capacité** :
  ```cpp
  size_t size = vec.size(); // Retourne le nombre d'éléments dans le vecteur
  bool isEmpty = vec.empty(); // Vérifie si le vecteur est vide
  vec.pop_back(); // Supprime le dernier élément
    ```

- **Boucles** :
```cpp
    for (size_t i = 0; i < vec.size(); i++) {
        std::cout << vec[i] << " "; // Affiche chaque élément
    }
    for (const auto& elem : vec) {
        std::cout << elem << " "; // Affiche chaque élément
    }

```

## En plus 

- ``virtual`` en mode classe abstraite pour dire qu'on pourra l'override 
- ``explicit`` : Lorsque vous déclarez un constructeur avec le mot-clé explicit, cela empêche la création implicite d'objets.
    > 
    ```cpp
    class MyClass {
    public:
        explicit MyClass(int value) { /* Initialisation */ }
    };

    int main() {
        MyClass obj1(5);       // OK : construction explicite
        // MyClass obj2 = 5;   // Erreur : construction implicite interdite
        MyClass obj3{5};       // OK : construction explicite avec liste d'initialisation
        return 0;
    }
    ```
---
