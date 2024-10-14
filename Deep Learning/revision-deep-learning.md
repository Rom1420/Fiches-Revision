# Révision Deep Learning pour QCM

## Table des Matières
- [Révision Deep Learning pour QCM](#révision-deep-learning-pour-qcm)
  - [Table des Matières](#table-des-matières)
  - [1. Concepts de base du Deep Learning](#1-concepts-de-base-du-deep-learning)
    - [Neurone et Poids](#neurone-et-poids)
    - [Fonction d'Activation](#fonction-dactivation)
  - [2. Structures de données et opérations de base avec PyTorch](#2-structures-de-données-et-opérations-de-base-avec-pytorch)
    - [Tensors](#tensors)
    - [linspace](#linspace)
    - [arange](#arange)
    - [Autograd (Backpropagation)](#autograd-backpropagation)
  - [3. Modèles de Régression et Classification](#3-modèles-de-régression-et-classification)
    - [Régression](#régression)
    - [Classification binaire](#classification-binaire)
    - [Dense Layer (Fully Connected Layer)](#dense-layer-fully-connected-layer)
  - [4. Entraînement des Modèles et Données](#4-entraînement-des-modèles-et-données)
    - [Split des datasets (train/test)](#split-des-datasets-traintest)
    - [Epoch](#epoch)
    - [Batch](#batch)
  - [5. Optimisation du modèle](#5-optimisation-du-modèle)
    - [Fonction de perte (loss function)](#fonction-de-perte-loss-function)
    - [Descente de gradient](#descente-de-gradient)
    - [Momentum](#momentum)
    - [Callbacks](#callbacks)
  - [6. Frameworks et implémentation (Keras, PyTorch)](#6-frameworks-et-implémentation-keras-pytorch)
    - [fit()](#fit)
    - [history](#history)
    - [MLP (Multi-Layer Perceptron)](#mlp-multi-layer-perceptron)
  - [7. Visualisation](#7-visualisation)
    - [Scatter Plot](#scatter-plot)
  - [8. Exemple global : Régression linéaire avec PyTorch](#8-exemple-global--régression-linéaire-avec-pytorch)
  - [9. Création d'un Convolutional Neural Network (CNN)](#9-création-dun-convolutional-neural-network-cnn)
    - [Qu'est-ce que la Convolution ?](#quest-ce-que-la-convolution-)
      - [Concept de Convolution](#concept-de-convolution)
      - [Exemple de Convolution](#exemple-de-convolution)
      - [Importance de la Convolution dans les CNN](#importance-de-la-convolution-dans-les-cnn)
    - [Conclusion](#conclusion)


## 1. Concepts de base du Deep Learning

### Neurone et Poids
Un **neurone** reçoit plusieurs entrées pondérées par des **poids**, les additionne, ajoute un **biais**, et passe le tout dans une **fonction d'activation** pour produire une sortie.
**Pourquoi ?**
Les poids sont les paramètres que le modèle apprend pour s'adapter aux données. Leur ajustement permet au modèle de faire des prédictions plus précises.

### Fonction d'Activation
Les fonctions d'activation introduisent la non-linéarité dans les réseaux neuronaux.
>**Pourquoi ?**
    >Sans fonction d'activation, un réseau ne pourrait modéliser que des relations linéaires simples.
- **ReLU (Rectified Linear Unit)** , est rapide à calculer et aide à éviter les gradients très petits.
  
   ```python
  import torch
  torch.relu(torch.tensor([-1.0, 0.0, 1.0]))  # Retourne tensor([0., 0., 1.])
  ```

- **Sigmoïde** : Utilisée pour la **classification binaire**,  est utile en classification binaire pour obtenir une sortie entre 0 et 1.
  
  ```python
  import torch
  torch.sigmoid(torch.tensor([0.0]))  # Retourne tensor([0.5])    
  ```

- **Softmax** : Utilisée pour **classification multiclasses**, Normalise les scores pour les tâches de classification multiclasses.
    
  
  ```python
  import torch
  torch.softmax(torch.tensor([1.0, 2.0, 3.0]), dim=0)  # Normalise les sorties
  ```

## 2. Structures de données et opérations de base avec PyTorch

### Tensors
Les **tensors** sont des structures de données multidimensionnelles pour manipuler des données numériques.

```python
import torch
tensor = torch.tensor([[1.0, 2.0], [3.0, 4.0]]) # Tenseur 2D
tensor_zeros = torch.zeros(3, 3)  # Tenseur 3x3 rempli de zéros
tensor_normal = torch.randn(3, 3) # Tenseur 3x3 normalisé
  ```

### linspace
Crée un tenseur avec des valeurs équidistantes.

```python
torch.linspace(0, 10, 5)  # Retourne tensor([0., 2.5, 5., 7.5, 10.])
  ```

### arange
Génère une séquence de nombres régulièrement espacés.

```python
torch.arange(0, 10, 2)  # Retourne tensor([0, 2, 4, 6, 8])  
  ```

### Autograd (Backpropagation)
PyTorch calcule automatiquement les gradients pour l'optimisation.

>**Pourquoi ?**
>La **backpropagation** permet d'optimiser les poids afin de minimiser l'erreur du modèle.

```python
x = torch.tensor(1.0, requires_grad=True)
y = x ** 2
y.backward()  # Calcule le gradient de y par rapport à x
print(x.grad)  # Retourne tensor(2.)
  ```

## 3. Modèles de Régression et Classification

### Régression
En **régression**, l'objectif est de prédire une **valeur continue**.

Exemple de régression linéaire :
```python
import torch.nn as nn
model = nn.Linear(in_features=1, out_features=1)  
  ```

### Classification binaire
Utilise une **fonction sigmoïde** pour prédire une classe entre 0 et 1.

```python
import torch.nn as nn
model = nn.Sequential(
    nn.Linear(in_features=2, out_features=1),
    nn.Sigmoid()
)  
  ```

### Dense Layer (Fully Connected Layer)
Chaque neurone est connecté à tous les neurones de la couche précédente.

```python
  import torch.nn as nn
  layer = nn.Linear(in_features=3, out_features=2)  # Couche dense avec 3 entrées et 2 sorties
  ```

## 4. Entraînement des Modèles et Données

### Split des datasets (train/test)
Division des données en deux parties : **train** pour l'entraînement, **test** pour l'évaluation.

```python
from sklearn.model_selection import train_test_split
X_train, X_test, y_train, y_test = train_test_split(X, y, test_size=0.2)
  ```

### Epoch
Une **epoch** correspond à un passage complet sur toutes les données d'entraînement.
>**Pourquoi ?**
>Plusieurs epochs sont nécessaires pour que le modèle apprenne correctement les patterns dans les données.
### Batch
Un **batch** correspond à un sous-ensemble de données utilisé pour calculer les gradients.
> **Pourquoi ?**
> L'entraînement par batch rend le calcul plus efficace et plus stable qu'avec toutes les données d'un coup.

```python
dataloader = torch.utils.data.DataLoader(dataset, batch_size=32, shuffle=True)
  ```

## 5. Optimisation du modèle

### Fonction de perte (loss function)
- **MSE (Mean Squared Error)** pour la régression.
  
  ```python
  loss_fn = nn.MSELoss()
  ```

- **Binary Cross-Entropy** pour la classification binaire.

  ```python
  loss_fn = nn.BCELoss()
  ```

### Descente de gradient
La **descente de gradient** ajuste les poids pour minimiser la perte.
>  **Pourquoi ?**
> Elle permet de trouver les bons paramètres pour rendre le modèle plus précis.


```python
# Initialisation de theta, du taux d'apprentissage et des conditions d'arrêt
theta = torch.tensor(0.2, requires_grad=True)
alpha = 0.01  # Taux d'apprentissage
limite = 100  # Nombre maximal d'itérations
epsilon = 0.01  # Condition d'arrêt pour le gradient
cpt = 1  # Compteur d'itérations

# Fonction à minimiser (fictivement définie ici)
def f(theta):
    return (theta - 2)**2  # Exemple d'une simple fonction quadratique

# Calcul initial
yi = f(theta)
yi.backward()  # Calcul du gradient
dyi = theta.grad

# Mise à jour initiale de theta
with torch.no_grad():
    theta -= alpha * dyi

# Boucle de descente de gradient
while abs(dyi) > epsilon and cpt < limite:
    theta.grad.zero_()  # Remettre le gradient à zéro
    yi = f(theta)
    yi.backward()  # Recalculer le gradient
    dyi = theta.grad  # Mettre à jour le gradient
    with torch.no_grad():
        theta -= alpha * dyi  # Mise à jour de theta
    cpt += 1  
```

### Momentum
Ajoute un **momentum** pour accélérer la descente de gradient.

```python
optimizer = torch.optim.SGD(model.parameters(), lr=0.01, momentum=0.9)
  ```

### Callbacks
Les **callbacks** permettent d'arrêter ou de sauvegarder l'entraînement selon certains critères (disponible dans Keras).

```python
from keras.callbacks import EarlyStopping
early_stopping = EarlyStopping(monitor='val_loss', patience=5)
  ```

## 6. Frameworks et implémentation (Keras, PyTorch)

### fit()
La méthode **fit()** entraîne un modèle.

```python
history = model.fit(X_train, y_train, epochs=10, batch_size=32, validation_data=(X_test, y_test))
  ```

### history
Stocke les performances de l'entraînement.

```python
import matplotlib.pyplot as plt
plt.plot(history.history['loss'])
  ```

### MLP (Multi-Layer Perceptron)
Un réseau avec plusieurs couches cachées, utilisé pour la régression ou classification.

```python
model = nn.Sequential(
    nn.Linear(10, 20),
    nn.ReLU(),
    nn.Linear(20, 1)
)
  ```

## 7. Visualisation

### Scatter Plot
Affiche un graphe de dispersion pour visualiser les données.

```python
import matplotlib.pyplot as plt
plt.scatter(X, y, color='blue')
plt.plot(X, model(X).detach().numpy(), color='red')  # Prédictions du modèle
plt.show()
  ```

## 8. Exemple global : Régression linéaire avec PyTorch
Voici un exemple complet qui combine plusieurs fonctions et concepts pour entraîner un modèle de régression simple en PyTorch.

```python
import torch
import torch.nn as nn
import torch.optim as optim
import matplotlib.pyplot as plt

# 1. Génération de données
X = torch.linspace(0, 10, 100).reshape(-1, 1)
y = 3 * X + torch.randn(X.size()) * 2  # Relation linéaire avec du bruit

# 2. Modèle linéaire
model = nn.Linear(in_features=1, out_features=1)

# 3. Fonction de perte et optimiseur
loss_fn = nn.MSELoss()
optimizer = optim.SGD(model.parameters(), lr=0.01)

# 4. Entraînement du modèle
epochs = 100
for epoch in range(epochs):
    model.train()
    optimizer.zero_grad()
    y_pred = model(X)
    loss = loss_fn(y_pred, y)
    loss.backward()
    optimizer.step()
    if epoch % 10 == 0:
        print(f"Epoch {epoch}, Loss: {loss.item()}")

# 5. Visualisation
plt.scatter(X, y, color='blue')
plt.plot(X, model(X).detach().numpy(), color='red')  # Prédictions du modèle
plt.show()

  ```

## 9. Création d'un Convolutional Neural Network (CNN)

Nous allons construire un CNN pour classifier des images en utilisant Keras. Voici les étapes clés du processus :

1. **Préparation des données**
   - Convertir les labels de classes en format catégorique.

2. **Définition du modèle**
   - Utiliser une architecture CNN avec des couches convolutionnelles, des couches de normalisation par lot, des couches de pooling, et une couche dense finale.

3. **Compilation du modèle**
   - Choisir l'optimiseur, la fonction de perte, et les métriques pour l'évaluation.

4. **Entraînement du modèle**
   - Ajuster les poids du modèle en utilisant les données d'entraînement.

5. **Évaluation du modèle**
   - Tester le modèle sur des données de test pour évaluer sa performance.

Voici le code pour mettre cela en œuvre :

```python
# Importation des bibliothèques nécessaires
from keras.models import Sequential
from keras.layers import Conv2D, MaxPooling2D, Flatten, Dense, BatchNormalization
from keras.utils import to_categorical

# Nombre de classes
nbClasses = 10

# Conversion des labels en format catégorique
y_train = to_categorical(y_train, nbClasses)
y_test = to_categorical(y_test, nbClasses)

# Définition du modèle CNN
model = Sequential()

# Couche de convolution 1
model.add(Conv2D(8, (3, 3), padding='same', strides=3, activation='relu', input_shape=(28, 28, 1)))
model.add(BatchNormalization())  # Normalisation par lot pour stabiliser l'entraînement
model.add(MaxPooling2D(pool_size=(2, 2), strides=2, padding='same'))  # Réduction de la taille des caractéristiques

# Couche de convolution 2
model.add(Conv2D(16, (3, 3), padding='same', strides=3, activation='relu'))
model.add(BatchNormalization())  # Normalisation par lot
model.add(MaxPooling2D(pool_size=(2, 2), strides=2, padding='same'))  # Réduction de la taille des caractéristiques

# Aplatissement des caractéristiques pour la couche dense
model.add(Flatten())

# Couche dense finale
model.add(Dense(nbClasses, activation='softmax'))  # Classification multi-classes

# Compilation du modèle
model.compile(optimizer='rmsprop', loss='categorical_crossentropy', metrics=['accuracy'])

# Entraînement du modèle
history = model.fit(x_train, y_train, epochs=15, batch_size=64, validation_split=0.2)

# Évaluation du modèle
test_loss, test_accuracy = model.evaluate(x_test, y_test)
print(f"Test accuracy: {test_accuracy * 100:.2f} %")
```

### Qu'est-ce que la Convolution ?

La **convolution** est une opération mathématique essentielle utilisée dans le traitement d'images et de signaux, notamment dans les réseaux de neurones convolutifs (CNN). Elle permet d'extraire des caractéristiques significatives d'une image en appliquant des filtres, appelés **kernels** ou **noyaux**.

#### Concept de Convolution

1. **Définition** :
   - La convolution combine deux fonctions pour en créer une nouvelle. Dans le cas des images, cela signifie appliquer un filtre à l'image pour produire une sortie.

2. **Kernels (Filtres)** :
   - Un kernel est une petite matrice (par exemple, 3x3 ou 5x5) qui contient des poids. Ce filtre est glissé sur l'image d'entrée pour détecter des motifs spécifiques tels que des contours ou des textures.

3. **Opération** :
   - Pendant la convolution, le kernel se déplace sur l'image, réalisant un produit scalaire entre les éléments du kernel et les valeurs de l'image sous-jacente.
   - Chaque position du filtre produit une nouvelle valeur, formant ainsi une **carte de caractéristiques**.

#### Exemple de Convolution

Pour illustrer la convolution, considérons un exemple :

- **Image d'entrée** (3x3) : [1, 2, 3] [4, 5, 6] [7, 8, 9]
 
- **Kernel** (2x2) : [1, 0] [0, -1]

- **Processus de convolution** :
  1. Positionner le kernel sur l'image.
  2. Calculer le produit scalaire entre les éléments du kernel et les valeurs sous le kernel.
  3. Déplacer le kernel et répéter ce processus.

#### Importance de la Convolution dans les CNN

- **Extraction de Caractéristiques** : La convolution permet aux CNN d'extraire automatiquement des caractéristiques pertinentes d'images à différentes échelles et niveaux de complexité.
- **Invariance à la Position** : Les CNN peuvent reconnaître des motifs indépendamment de leur position dans l'image, ce qui améliore leur robustesse.
- **Réduction de la Dimensionnalité** : En utilisant des couches de pooling après la convolution, les CNN réduisent la taille des cartes de caractéristiques, ce qui aide à minimiser le surapprentissage et à rendre le modèle plus efficace.

### Conclusion

La convolution est un outil fondamental pour le traitement d'images dans les réseaux de neurones, car elle permet d'extraire des informations significatives tout en réduisant la complexité des données d'entrée. Elle est à la base de la puissance des CNN pour des tâches telles que la reconnaissance d'images, la détection d'objets, et bien d'autres applications en vision par ordinateur.
