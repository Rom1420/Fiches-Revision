# Fiche de Révision : Algorithmique et Complexité

## 1. Classes de Complexité

### P, NP, EXP, NEXP
- **P** : Problèmes résolus en temps polynomial par une machine déterministe.
- **NP** : Problèmes vérifiables en temps polynomial par une machine déterministe.
- **EXP** : Problèmes résolus en temps exponentiel.
- **NEXP** : Problèmes résolus en temps exponentiel par une machine non déterministe.

### Relations et Inclusions
- **P ⊂ NP ⊆ EXP ⊆ NEXP**
- **P ⊂ EXP** et **NP ⊂ EXP** sont des inclusions strictes.

## 2. Complexité du Temps de Calcul
- **DTIME(t)** : Ensemble des langages reconnus en temps déterministe t(n).
- **NTIME(t)** : Ensemble des langages reconnus en temps non déterministe t(n).

## 3. Théorèmes Utiles

### Théorème de la Hiérarchie en Temps Déterministe
- Si \( f(n) \) et \( g(n) \) sont des fonctions telles que \( f(n) \log(f(n)) = o(g(n)) \), alors **DTIME(f) ⊂ DTIME(g)**.
- **Traduction** : En augmentant le temps de calcul disponible, il devient possible de décider des langages qui n'étaient pas accessibles auparavant.

### Théorème d'Accélération Linéaire
- Pour toute constante \( ε > 0 \), si un langage L est reconnu par une machine M en temps \( ≤ t(n) \), alors il existe une machine M' reconnaissant L et fonctionnant en temps \( ≤ (1 + ε)n + εt(n) \).
- **Astuce** : Agrandir l'alphabet permet de réaliser plusieurs étapes de calcul en une seule fois.

### Problème P vs NP
- La question **P = NP ?** est l'un des problèmes ouverts les plus célèbres en informatique. Elle demande si tout problème dont la solution est vérifiable en temps polynomial peut être également résolu en temps polynomial.

## 4. Réductions et NP-complétude
- **Réduction polynomiale (A ≤P_m B)** : Transformation d'un problème A en un problème B en temps polynomial.
- **Problèmes NP-complets** :
  - Un problème est **NP-complet** s'il est dans **NP** et que tout problème dans **NP** peut se réduire à lui.
  - **Exemples** : **3-SAT**, **ENSEMBLE_DOMINANT**.

## 5. Problèmes Types
- **SAT** : Problème de satisfiabilité, qui consiste à déterminer si une formule logique est satisfiable.
- **3-SAT** : Version du problème SAT où chaque clause a au plus 3 littéraux.
- **ENSEMBLE_DOMINANT** : Trouver un sous-ensemble de sommets dans un graphe qui domine tous les autres sommets.
- **ACCESSIBILITÉ** : Déterminer s'il existe un chemin entre deux sommets dans un graphe orienté.
- **MULTIPLICATION D'ENTIERS** : Déterminer le k-ème bit du produit de deux entiers. Ces deux derniers sont dans **P**.

## 6. Méthodes de Résolution
- **Boucles Imbriquées** : Pour vérifier toutes les combinaisons possibles (ex. : triangles dans un graphe).
- **Algorithmes de Parcours de Graphe** : **BFS** (parcours en largeur) ou **DFS** (parcours en profondeur) pour vérifier des propriétés comme la connectivité ou la présence de cycles.
- **Réduction** : Utiliser des réductions pour démontrer la complexité ou résoudre des problèmes difficiles via d'autres problèmes connus.

---

### Notes Importantes
- **P = NP ?** : Problème non résolu, question de savoir si trouver une solution est aussi facile que la vérifier.
- **Exemples** :
  - **MULTIPLICATION D'ENTIERS** est dans **P**.
  - **ACCESSIBILITÉ** dans un graphe est aussi dans **P**.

### Classes de Complexité des Problèmes

1. **P (Polynomial Time)**

   - Problèmes résolubles en temps polynomial par une machine déterministe.
   - **Preuves et Exemples** :
     - **2-Coloration** : Le problème de déterminer si un graphe est 2-coloriable (biparti) est en P. Cela peut être fait en temps polynomial en utilisant un algorithme de parcours de graphe (BFS ou DFS) qui attribue des couleurs alternées aux sommets, assurant que deux sommets adjacents n'ont pas la même couleur. 
     - **Horn-SAT** : Déterminer si une formule de Horn est satisfiable peut être résolu en temps polynomial. En utilisant un algorithme basé sur la propagation unitaire, chaque clause contenant un seul littéral positif impose une valuation, et ce processus se termine en temps polynomial.
     - **1-SAT** : Le problème de la satisfiabilité d'une formule 1-CNF est en P. L'algorithme vérifie simplement que chaque variable n'apparaît pas positivement et négativement à la fois, ce qui peut être fait en temps linéaire par rapport au nombre de variables et de clauses.

2. **NP (Nondeterministic Polynomial Time)**

   - Problèmes pour lesquels une solution peut être vérifiée en temps polynomial par une machine déterministe.
   - **Preuves et Exemples** :
     - **3-SAT** : Le problème de la satisfiabilité des formules en 3-CNF est NP-complet. Une solution peut être devinée en temps polynomial, puis vérifiée en temps polynomial en substituant les valeurs dans la formule.
     - **Ensemble Indépendant** : Trouver un ensemble de sommets non adjacents de taille k dans un graphe est en NP. Pour vérifier une solution, il suffit de vérifier que chaque sommet de l'ensemble n'est pas adjacent à un autre, ce qui peut être fait en temps polynomial.
     - **SAT Monotone** : Le problème de la satisfiabilité de formules CNF monotones est NP-complet. Une réduction de SAT montre que chaque instance de SAT peut être transformée en une instance de SAT Monotone en temps polynomial, démontrant ainsi la NP-difficulté.

3. **co-NP**

   - Classe des compléments des problèmes en NP.
   - **Preuves et Exemples** :
     - **Forte Cyclicité** : Le problème consistant à vérifier si tout sous-ensemble d'un graphe contient un cycle est dans co-NP. Un certificat pour une instance négative est un sous-ensemble sans cycle, qui peut être vérifié en temps polynomial.

4. **EXP (Exponential Time)**

   - Problèmes résolubles en temps exponentiel.
   - **Preuves et Exemples** :
     - **CLIQUE** : Déterminer si un graphe contient une clique de taille k est en EXP. Un algorithme récursif parcourt toutes les combinaisons possibles de sommets, ce qui prend un temps exponentiel.
     - **Équivalence AFI** : Déterminer si deux automates finis indéterministes reconnaissent le même langage est en EXP. Cela nécessite la déterminisation des automates, qui peut prendre un temps exponentiel par rapport au nombre d'états.

### NP-Complétude et Réduction

- Un problème est **NP-complet** s'il est dans NP et s'il est au moins aussi difficile que tous les autres problèmes de NP (NP-difficile).
- **Exemple de Réduction** :
  - **3-SAT → Ensemble Indépendant** : Pour prouver que le problème Ensemble Indépendant est NP-complet, une réduction est faite à partir de 3-SAT. Chaque clause de 3-SAT est représentée par un sous-ensemble de sommets, et la réduction est calculable en temps polynomial, montrant que Ensemble Indépendant est NP-difficile et en NP, donc NP-complet.

### Problèmes Spécifiques

1. **DIVISEUR** : Le problème de savoir si un entier n est divisible par un entier p tel que  est dans NP. Un algorithme déterministe a une complexité de , ce qui place le problème dans EXP, mais une solution non-déterministe devinant p permet une résolution en temps polynomial.
2. **CARRE MAGIQUE** : Compléter une grille partiellement remplie pour obtenir un carré magique est en EXP avec un algorithme de backtracking, mais peut être résolu en NP en utilisant une approche non-déterministe.
3. **HALF-CLIQUE** : Le problème de vérifier si un graphe contient une clique de taille  est NP-complet. Une réduction polynomiale à partir de CLIQUE permet de démontrer la NP-difficulté.

Ces exemples montrent la variété des classes de complexité, des preuves basées sur des réductions polynomiales ou sur la vérifiabilité en temps polynomial. Cela met en évidence la différence entre les classes P, NP, co-NP, et EXP, ainsi que la difficulté de certains problèmes bien connus.



