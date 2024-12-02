# Table des Matières

- [Table des Matières](#table-des-matières)
    - [Cours 1: Représentation des mots et Réseaux Récurrents](#cours-1-représentation-des-mots-et-réseaux-récurrents)
    - [Cours 2: Architecture Seq2Seq et Attention](#cours-2-architecture-seq2seq-et-attention)
    - [Travaux Dirigés (TDs)](#travaux-dirigés-tds)
    - [Résumé des Étapes Réalisées lors du TD](#résumé-des-étapes-réalisées-lors-du-td)
    - [Création d'un RNN LSTM et d'un Modèle Seq2Seq](#création-dun-rnn-lstm-et-dun-modèle-seq2seq)

### Cours 1: Représentation des mots et Réseaux Récurrents

**1. Représentation des mots :**
- **One Hot Encoding (OHE)** : Représenter les mots comme des vecteurs ayant une valeur à 1 pour le mot correspondant et 0 ailleurs. Cette approche n'intègre aucune notion de similarité entre les mots (par exemple, "chat" et "félin").
- **Bag of Words (BoW)** : Représenter une phrase par un vecteur basé sur la fréquence des mots. Ne prend pas en compte l'ordre des mots.
- **Word Embeddings** : Les méthodes comme Word2Vec (2013) ou Glove (2014) permettent d'associer un vecteur dense à chaque mot, ce vecteur encapsulant le sens du mot. Le contexte d'utilisation est crucial pour construire ce vecteur.

**2. Approche par Embedding :**
- Utilisation de couches d'embedding en Keras ou TensorFlow : Les couches embedding transforment les indices des mots en vecteurs continus, facilitant l'apprentissage de représentations plus riches.
- **Word2Vec et Glove** : Ces modèles prédissent les mots manquants en fonction du contexte (Continuous Bag of Words) ou utilisent des matrices de co-occurrence.

**3. Les Réseaux Récurrents (RNN) :**
- Contrairement aux réseaux classiques qui considèrent chaque entrée indépendamment, les RNN utilisent les informations séquentielles en mémorisant l'état précédent.
- **Problèmes des RNN :** Problèmes de gradient évanescent ou explosif lors de l'entraînement, surtout sur de longues séquences.

![alt text](https://github.com/Rom1420/Fiches-Revision/blob/master/Deep%20Learning/images/image-1.png)


**4. Les cellules LSTM :**
- LSTM (“Long Short-Term Memory”) permet de contrôler les informations à mémoriser, oublier ou passer à la sortie via trois portes (“input gate”, “forget gate”, “output gate”).
- Les LSTM résolvent le problème du gradient évanescent, permettant un meilleur apprentissage des dépendances à long terme.

![alt text](https://github.com/Rom1420/Fiches-Revision/blob/master/Deep%20Learning/images/image.png)

### Cours 2: Architecture Seq2Seq et Attention

**1. Architecture Seq2Seq :**
- **Encodeur-Décodeur :** L'encodeur transforme une séquence d'entrée en un vecteur de contexte, que le décodeur utilise pour produire la séquence de sortie.
- **Sans Teacher Forcing :** L'apprentissage est plus lent, car le réseau doit réinjecter sa prédiction précédente pour prédire la suivante.
- **Avec Teacher Forcing :** Utilisation des vraies valeurs comme entrées lors de l'entraînement pour accélérer la convergence.

**2. Limites du Seq2Seq :**
- Difficulté à retenir toutes les informations des phrases longues avec un simple vecteur de contexte. Le décodeur risque de perdre des informations importantes des premières parties de la phrase.

**3. Le mécanisme d'attention :**
- L'attention permet de se concentrer sur certaines parties de l'entrée, plutôt que de donner la même importance à toutes les informations. Cela est particulièrement utile dans la traduction ou la génération de texte.
- **Attention dans Keras :** Utilisation de couches d'attention pour ajuster dynamiquement l'importance des états cachés de l'encodeur à chaque étape du décodage.
- 
### Travaux Dirigés (TDs)

**1. TD sur l'Embedding :**
- **Objectif :** Utiliser un embedding pré-entrainé pour représenter des mots et construire un réseau récurrent de type Many-to-Many pour des tâches de NER (Nommer les entités).
- **IOB Encoding pour NER :** Utilisation de labels de type IOB pour encoder les entités dans le texte (ex. “B-PER” pour début d'une personne).

**2. TD sur Seq2Seq avec Attention :**
- **Objectif :** Construire un modèle Seq2Seq pour traduire des dates dans différents formats. Intégrer le mécanisme d'attention pour améliorer la précision des prédictions.
- **Lab pratique :** Création des datasets, entraînement du réseau avec Early Stopping, et prédiction avec visualisation de la matrice d'attention pour comprendre le processus d'attention.

**3. Evaluation des erreurs en NER :**
- **Types d'erreurs :**
  - **Spurious (SPU)** : Fausse prédiction d'une entité non présente dans le texte.
  - **Missing (MIS)** : Entité présente dans le texte mais non prédite.
  - **Incorrect (INC)** : Bonne entité mais mauvaise étiquette.
  - **Partial (PAR)** : Chevauchement partiel des étiquettes.


### Résumé des Étapes Réalisées lors du TD

**1. Préparation des Données :**
- Définition du vocabulaire et des tags à partir des données d'entraînement (inclusion de `<PAD>` et `<UNK>`).
- Conversion des phrases (`X_train`, `X_val`, `X_test`) et des labels (`y_train`, `y_val`, `y_test`) en séquences d'indices avec `pad_sequences` pour garantir des longueurs uniformes.
  - **Correspondance avec le Cours :** Cette étape correspond à la **tokenisation** et à la **représentation des mots**. On transforme les mots en indices qui peuvent être utilisés dans le modèle.

**2. Construction du Modèle :**
- **Couche Embedding :** Utilisation d'une couche `Embedding` pour transformer chaque mot en un vecteur de taille fixe (ici, 50).
  - **Correspondance avec le Cours :** Cela correspond à l'utilisation des **embeddings** afin de transformer les indices en vecteurs continus pour une meilleure représentation des mots.
- **Couche Récurrente :** Utilisation d'une couche `LSTM` avec `_HIDDEN_SIZE` de 32 pour traiter la séquence, tout en conservant l'information à chaque étape (`return_sequences=True`).
  - **Correspondance avec le Cours :** Cela correspond à l'utilisation des **RNN (LSTM)** pour gérer les séquences et mémoriser les informations à long terme.
- **Couche de Sortie :** Utilisation d'une couche `Dense` avec une fonction d'activation `softmax` pour prédire les tags pour chaque mot.
  - **Correspondance avec le Cours :** C'est la **sortie Many-to-Many** qui prédit une étiquette pour chaque mot de la séquence d'entrée.

**3. Compilation et Entraînement du Modèle :**
- **Fonction de Perte :** Utilisation de `sparse_categorical_crossentropy` pour comparer les prédictions de classes à des labels sous forme d'indices.
  - **Correspondance avec le Cours :** La **fonction de coût** permet de mesurer la différence entre les prédictions et les vraies étiquettes.
- **Optimiseur :** Utilisation de `Adam` pour optimiser le modèle.
- **Callback EarlyStopping :** Mise en place d'un arrêt précoce pour éviter le surapprentissage en surveillant la perte sur les données de validation (`val_loss`).
  - **Correspondance avec le Cours :** Cela permet d'**éviter le surapprentissage** et de garantir une bonne généralisation du modèle.

**4. Accélération de l'Entraînement :**
- Réduction de la taille des séquences (`_MAX_LEN`), de la taille du modèle (`_HIDDEN_SIZE`), et de la taille des batches pour réduire le temps de calcul.
- Utilisation d'un sous-échantillon des données pour tester rapidement le modèle.
  - **Correspondance avec le Cours :** Ces ajustements correspondent à des **optimisations pratiques** pour rendre l'entraînement du modèle plus efficace.


![alt text](https://github.com/Rom1420/Fiches-Revision/blob/master/Deep%20Learning/images/image3.png)

### Création d'un RNN LSTM et d'un Modèle Seq2Seq

**1. Création d'un RNN LSTM :**

```python
_HIDDEN_SIZE = 32
_DROPOUT = 0.4

inputs = layers.Input(shape=(_MAX_LEN,), dtype=int)
emb = layers.Embedding(input_dim=_NUM_WORDS, output_dim=50)(inputs)  # Embedding step
hidden = layers.LSTM(_HIDDEN_SIZE, return_sequences=True, dropout=_DROPOUT)(emb)  # Recurrent step
outputs = layers.Dense(_NUM_TAGS, activation='softmax')(hidden)  # Output step

model = models.Model(inputs, outputs)
model.summary()
```

- **Étapes :**
  - **Couche Embedding :** Convertit les indices des mots en vecteurs d'embedding.
  - **Couche LSTM :** Traite la séquence et apprend les dépendances à long terme.
  - **Couche de Sortie :** Prédit les tags pour chaque mot de la séquence.

**2. Création d'un Modèle Seq2Seq :**

```python
def build_encoder():
    inputs = layers.Input(shape=(_INPUT_LENGTH,), name="encInput")
    h = layers.Embedding(input_dim=_VOCAB_SIZE, output_dim=_EMBEDDING_SIZE)(inputs)
    encoder_outputs, memory_state, carry_state = layers.LSTM(_RNN_SIZE, return_sequences=True, return_state=True, dropout=_DROPOUT_RATE, recurrent_dropout=_DROPOUT_RATE)(h)
    encoder_context = [memory_state, carry_state]
    return models.Model(inputs, [encoder_outputs, encoder_context], name="encoder")

def build_decoder():
    teacher_inputs = layers.Input(shape=(_OUTPUT_LENGTH,), name="decInput")
    ctx_input = [layers.Input(shape=(_RNN_SIZE,), name="ctxInputH"), layers.Input(shape=(_RNN_SIZE,), name="ctxInputC")]
    h = layers.Embedding(input_dim=_VOCAB_SIZE, output_dim=_EMBEDDING_SIZE)(teacher_inputs)
    decoder_outputs, memory_state, carry_state = layers.LSTM(_RNN_SIZE, return_sequences=True, return_state=True, dropout=_DROPOUT_RATE, recurrent_dropout=_DROPOUT_RATE)(h, initial_state=ctx_input)
    context = [memory_state, carry_state]
    outputs = layers.Dense(_VOCAB_SIZE, activation='softmax')(decoder_outputs)
    return models.Model([teacher_inputs, ctx_input], [outputs, context], name="decoder")

def build_seq2seq(encoder, decoder):
    enc_inputs = layers.Input(shape=(_INPUT_LENGTH,), name="encInput")
    _, enc_context = encoder(enc_inputs)
    teacher_inputs = layers.Input(shape=(_OUTPUT_LENGTH,), name="teacherInput")
    dec_sequence_outputs, _ = decoder([teacher_inputs, enc_context])
    return models.Model([enc_inputs, teacher_inputs], [dec_sequence_outputs])
```

- **Étapes :**
  - **Encodeur :** Prend une séquence d'entrée et la transforme en un vecteur de contexte.
  - **Décodeur :** Utilise le vecteur de contexte pour produire la séquence de sortie, mot par mot.
  - **Seq2Seq :** Combine l'encodeur et le décodeur pour traduire une séquence d'entrée en séquence de sortie.