# Révision Algo

## Définitions

**Réduction Turing**  
Une **réduction Turing** (ou **réduction many-one**) d'un langage \( A \) à un langage \( B \) est une fonction calculable \( f : \Sigma^*_A \to \Sigma^*_B \) telle que :
\[ 
w \in A \iff f(w) \in B 
\]
On note cela \( A \leq_{m}^{T} B \).

### Intuition  
Un problème \( A \) se réduit à un problème \( B \) si, connaissant un algorithme pour décider ou calculer \( B \), on peut obtenir un algorithme pour décider ou calculer \( A \).  
Autrement dit, \( A \) est **au moins aussi facile** à résoudre que \( B \).

## Théorèmes

### Réduction

Si un langage \( A \) se réduit au langage \( B \) (i.e. \( A \leq_{m}^{T} B \)), alors :

- Si \( A \) n’est pas décidable, alors \( B \) non plus.
- Si \( A \) n’est pas semi-décidable, alors \( B \) non plus.
- Si \( A \) n’est pas co-semi-décidable, alors \( B \) non plus.

### Théorème de Rice

#### Définition

Soit \( P \) une famille de langages. On appelle \( P \) une **propriété non triviale** s'il existe deux machines de Turing \( M_1 \) et \( M_2 \) telles que :

- \( L(M_1) \in P \)
- \( L(M_2) \notin P \)

La propriété \( P \) et son complément \( \overline{P} \) peuvent être composés d'un nombre fini ou infini de langages. Mais clairement, si \( P \) est fini, alors \( \overline{P} \) est infini, et inversement.

#### Théorème

Pour toute propriété non triviale \( P \), il n'existe aucun algorithme pour décider si une machine de Turing \( M \) de code \( \langle M \rangle \) vérifie \( L(M) \in P \). Autrement dit, 

\[ 
L_P = \{\langle M \rangle \ | \ L(M) \in P\} 
\]

n'est pas décidable.

#### Remarque

Cependant, \( L_P \) est potentiellement semi-décidable ou co-semi-décidable.

## Langages Décidables, Semi-Décidables et Co-Semi-Décidables

- **Langages Décidables** : Un langage est dit décidable s'il existe une machine de Turing qui s'arrête toujours (dans un état d'acceptation ou de rejet) pour toute entrée. Autrement dit, il existe un algorithme qui peut déterminer si un mot appartient au langage en un nombre fini d'étapes.

- **Langages Semi-Décidables** : Un langage est semi-décidable (ou reconnaissable) s'il existe une machine de Turing qui s'arrête et accepte lorsque l'entrée appartient au langage, mais qui peut ne pas s'arrêter (boucle indéfiniment) si l'entrée n'appartient pas au langage. En d'autres termes, il est possible de reconnaître les mots du langage, mais il n'est pas nécessairement possible de rejeter tous les mots qui n'appartiennent pas au langage.

- **Langages Co-Semi-Décidables** : Un langage est co-semi-décidable si son complément est semi-décidable. Cela signifie qu'il existe une machine de Turing qui s'arrête et accepte lorsque l'entrée n'appartient pas au langage, mais peut ne pas s'arrêter si l'entrée appartient au langage. En résumé, on peut reconnaître les mots qui ne font pas partie du langage, mais pas nécessairement ceux qui y appartiennent.

## Langages et leur décidabilité

### 1. Langage \( L_u \)

- **Définition** : 
  \( L_u = \{\langle M \rangle \# w \ | \ M \ \text{accepte le mot} \ w\} \)

- **Propriétés** :
  - \( L_u \) n'est pas décidable.
  - Son complément \( \overline{L_u} = \{\langle M \rangle \# w \ | \ M \ \text{n'accepte pas } w\} \) n'est pas semi-décidable.

### 2. Langage \( L_{\text{halt}\varepsilon} \)

- **Définition** : 
  \( L_{\text{halt}\varepsilon} = \{\langle M \rangle \ | \ M \ \text{s’arrête quand on la lance sur l’entrée vide}\} \)

- **Propriétés** :
  - \( L_{\text{halt}\varepsilon} \) n'est pas décidable.
  - Son complément \( \overline{L_{\text{halt}\varepsilon}} = \{\langle M \rangle \ | \ M \ \text{s'arrête pas quand on la lance sur l’entrée vide}\} \) n'est pas semi-décidable.

### Exemples de Langages

#### Langages Décidables

- **Exemple** : Le langage des mots bien formés en parenthèses.
  - **Définition** : \( L_{\text{par}} = \{ w \ | \ w \text{ est une chaîne de parenthèses bien formées} \} \)
  - **Citation** : "Ce langage est décidable car on peut utiliser une machine de Turing qui simule un compteur pour vérifier la validité des parenthèses."

#### Langages Semi-Décidables

- **Exemple** : Le langage \( L_u \) (décrit ci-dessus).
  - **Citation** : "Bien que \( L_u \) ne soit pas décidable, il est semi-décidable car on peut construire une machine de Turing qui acceptera les chaînes où la machine de Turing accepte le mot."

#### Langages Co-Semi-Décidables

- **Exemple** : Le complément de \( L_{\text{halt}\varepsilon} \).
  - **Citation** : "Le langage des machines qui ne s'arrêtent pas sur l'entrée vide est co-semi-décidable, car nous pouvons reconnaître les machines qui s'arrêtent sur toutes les autres entrées."

### Récapitulatif des propriétés de décidabilité

- **Semi-décidable** : Un langage est semi-décidable s'il existe une machine de Turing qui accepte les mots dans le langage, mais qui peut ne pas s'arrêter pour les mots qui ne sont pas dans le langage.

- **Co-semi-décidable** : Un langage est co-semi-décidable si son complément est semi-décidable, c'est-à-dire qu'il existe une machine de Turing qui accepte les mots qui ne sont pas dans le langage, mais qui peut ne pas s'arrêter pour les mots dans le langage.

### Conclusion

- Les langages \( L_u \) et \( L_{\text{halt}\varepsilon} \) sont non décidable.
- \( \overline{L_u} \) et \( \overline{L_{\text{halt}\varepsilon}} \) ne sont pas semi-décidables.
