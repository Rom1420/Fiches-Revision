# Tokenization (fin) - Apprentissage auto-supervisé

Résultat de tokénisation = vocabulaire initial 

#### Byte Pair encoding

#### Byte Level BPE :
- Appliquer sur des octes plutot que sur les carcatères directement
- Chaque caractère sur 1 à 4 octets 

#### WordPiece tokenizer :
- Au lieu de selectionner la 

#### Vraissemblance (likelihood) :

> La vraisemblance, c’est une façon de dire : "À quel point mon modèle est bon pour expliquer ce que j’ai observé ?"

- Idée de base : On calcule la probabilité que l’événement observé (les données) se produise, en fonction des paramètres du modèle.
- Si cette probabilité est grande, le modèle est bon.
- Si elle est petite, le modèle ne correspond pas bien aux données.

On utilise souvent le log de la vraisemblance (le logarithme de cette probabilité) parce que :

- Ça simplifie les calculs.
- Les produits de probabilités (souvent très petits) deviennent des sommes.

##### Exemple simple :

Imaginons qu’on lance un dé 5 fois et on observe {6, 6, 4, 5, 6}.

- On veut vérifier si le dé est biaisé et favorise le "6".
- La vraisemblance regarde : "Quelle est la probabilité d’obtenir cette suite de résultats avec mon modèle (par exemple, un dé truqué) ?".

#### UniGram :

- **But** : Réduire un grand vocabulaire à une taille plus petite tout en minimisant la perte d'information.
- **Principe** : Chaque token (symbole ou mot) est traité indépendamment des autres.

##### Étapes de l'algorithme :
- **Calcul de la perte** :

  - À chaque étape de l'entraînement, l'algorithme utilise la vraisemblance logarithmique négative pour mesurer combien le vocabulaire actuel "explique" les données.
  - Moins la perte est élevée, meilleur est le vocabulaire.

- **Évaluation des symboles** :

  - Pour chaque symbole du vocabulaire, l’algorithme calcule combien la perte augmenterait si ce symbole était supprimé.

- **Sélection des candidats à supprimer** :

  - Les symboles dont la suppression augmente le moins la perte globale sont considérés comme "moins nécessaires".
  - Ces symboles sont supprimés du vocabulaire.