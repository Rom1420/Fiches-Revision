# Révision QCM Deep Learning : CNN et Interprétabilité

## Partie 1 : Réseaux de Neurones Convolutifs (CNN)

### 1. Inspiration Biologique et Structure
- Les CNN sont inspirés par l'architecture visuelle du cerveau :
  - **Rétine** : photorécepteurs (cônes et bâtonnets) et cellules ganglionnaires.
  - **Cortex visuel** : détecteurs de formes basiques (V1), primitives (V2), abstractions avancées (V3 et plus).
- Motivation pour les CNN :
  - Utilisés pour la classification d'images.
  - Utilisation de convolutions pour la détection de motifs similaires (Sobel, Laplace, etc.).

### 2. Convolution et Corrélation
- **Convolution** : Opérateur de multiplication entre image et filtre pour extraire des caractéristiques.
- **Corrélation** : Identique à la convolution si le filtre est symétrique.

### 3. Couches Convolutives
- **Filtre convolutif** = neurone avec des poids partagés appliqué à tous les pixels.
- Paramètres clés dans Keras :
  - `padding` : "valid" (pas de remplissage), "same" (conserve la dimension).
  - `stride` : détermine le déplacement du filtre (par ex. `strides=(1,1)`).

### 4. Pooling
- **MaxPooling** et **AveragePooling** : réduisent la taille des données après convolution.
- **Global pooling** : réduit la dimension à (batch, channels), utile pour les modèles plus compacts.

### 5. Architectures Célèbres
- **LeNet-5 (1998)** : premier CNN pour la reconnaissance de chiffres manuscrits.
- **AlexNet (2012)** : vainqueur du ILSRVC, utilise des couches convolutives et ReLU.
- **VGG (2014)** : couches convolutives plus petites, profondeur augmentée.
- **GoogLeNet (2014)** : modules Inception avec convolutions 1x1, 3x3, et 5x5.
- **ResNet (2015)** : connexions résiduelles permettant des réseaux plus profonds.
- **EfficientNet (2019)** : mise à l'échelle composée, améliorant profondeur, largeur et résolution.

### 6. Régularisation, Optimisation et Réduction de Dimensions

- **Dropout** : Désactive aléatoirement un pourcentage de neurones à chaque itération d'entraînement pour éviter le sur-apprentissage. Cela rend le modèle plus robuste en réduisant la dépendance à certains neurones.

- **Batch Normalization** : Normalise les entrées de chaque couche pour accélérer l'entraînement, éviter les problèmes de gradients (vanishing/exploding), et améliorer la généralisation.

- **Pooling** : Réduit les dimensions spatiales des données après une couche convolutive, en conservant les informations essentielles.
  - **Max Pooling** : Sélectionne la valeur maximale dans une zone du filtre (par exemple, 2x2) pour chaque pas, ce qui capture les caractéristiques dominantes.
  - **Average Pooling** : Prend la moyenne des valeurs dans chaque zone du filtre, utile pour certaines tâches spécifiques.

- **Méthodes d'optimisation** : Algorithmes qui ajustent les poids du modèle pour minimiser l'erreur :
  - **Adam** : Combinaison d'Adagrad et RMSProp, adaptant le taux d'apprentissage en fonction des gradients.
  - **RMSProp** : Ajuste le taux d'apprentissage pour chaque paramètre, adapté aux réseaux profonds.


### 7. Augmentation de Données
- Techniques simples : rotation, translation, zoom.
- **MixUp** et **CutMix** : mélangent des échantillons pour améliorer la robustesse du modèle.
- Intégration dans Keras ou PyTorch pour augmenter les données en temps réel.

---

## Partie 2 : Interprétabilité des CNN

### 1. Contexte et Définition
- **Interprétabilité** : capacité à comprendre pourquoi un modèle prend une décision.
- Méthodes **agnostiques** (LIME, Shapley) ou spécifiques aux CNN (Grad-CAM).

### 2. Techniques d'Interprétation pour CNN
- **Maximally Activating Patches** : visualise les régions qui activent fortement certains canaux.
- **Occlusion Maps** : masque des parties de l'image pour voir l'impact sur les prédictions.
- **Saliency Maps** : calcul des gradients pour identifier les pixels les plus influents.
- **Class Activation Maps (CAM)** :
  - Met en évidence les régions discrimatives en ajoutant une couche Global Average Pooling.
- **Grad-CAM** :
  - Amélioration de CAM sans modification de l'architecture, obtient une carte de chaleur des pixels influents.

### 3. Méthodes Combinées : Guided Grad-CAM
- **Guided Backpropagation** : affiche les gradients positifs pour des visualisations plus nettes.
- **Guided Grad-CAM** : combine Grad-CAM et Guided Backpropagation pour localiser les pixels pertinents avec une haute résolution.