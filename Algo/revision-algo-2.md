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

