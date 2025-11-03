# TP4 : HBase

## Objectifs du TP
Dans ce TP, nous allons  utiliser HBase, une base de données orientée colonnes. Nous allons effectuer des opérations de base telles que la création de tables, l'insertion de données, la récupération de données, et bien plus encore. Le TP inclut aussi une modélisation d'un serveur de chat utilisant HBase.

---

## I. Utilisation du Shell HBase

### 1. Créer une table HBase
Exécutez la commande suivante pour créer une table nommée <votre-nom>-shell avec une seule famille de colonnes nommée cf :

```bash
create '<votre-nom>-shell', 'cf'
```

Vérifiez que la table a été créée avec succès en exécutant la commande :

```bash
list
```

### 2. Insérer des données
Insérez les lignes suivantes dans la table <votre-nom>-shell en utilisant la commande put :

```bash
put '<votre-nom>-shell', 'jean-dupont', 'cf:nom', 'Dupont'
put '<votre-nom>-shell', 'jean-dupont', 'cf:prenom', 'Jean'
put '<votre-nom>-shell', 'jean-dupont', 'cf:ville', 'Nancy'

put '<votre-nom>-shell', 'marie-moine', 'cf:nom', 'Moine'
put '<votre-nom>-shell', 'marie-moine', 'cf:prenom', 'Marie'
put '<votre-nom>-shell', 'marie-moine', 'cf:ville', 'Paris'

put '<votre-nom>-shell', 'marc-pinault', 'cf:nom', 'Pinault'
put '<votre-nom>-shell', 'marc-pinault', 'cf:prenom', 'Marc'
put '<votre-nom>-shell', 'marc-pinault', 'cf:ville', 'Nancy'

put '<votre-nom>-shell', 'romain-guillet', 'cf:nom', 'Guillet'
put '<votre-nom>-shell', 'romain-guillet', 'cf:prenom', 'Romain'
put '<votre-nom>-shell', 'romain-guillet', 'cf:ville', 'Lyon'

put '<votre-nom>-shell', 'jeanne-lenglet', 'cf:nom', 'Lenglet'
put '<votre-nom>-shell', 'jeanne-lenglet', 'cf:prenom', 'Jeanne'
put '<votre-nom>-shell', 'jeanne-lenglet', 'cf:ville', 'Lyon'

put '<votre-nom>-shell', 'lisa-magnant', 'cf:nom', 'Magnant'
put '<votre-nom>-shell', 'lisa-magnant', 'cf:prenom', 'Lisa'
put '<votre-nom>-shell', 'lisa-magnant', 'cf:ville', 'Marseille'
```

### 3. Lister les lignes de la table
Exécutez la commande suivante pour lister toutes les lignes de la table <votre-nom>-shell :

```bash
scan '<votre-nom>-shell'
```

Exemple de sortie :
 ```rust
    ROW                              COLUMN+CELL
    jean-dupont                      cf:nom=Dupont, cf:prenom=Jean, cf:ville=Nancy
    marie-moine                      cf:nom=Moine, cf:prenom=Marie, cf:ville=Paris
    marc-pinault                     cf:nom=Pinault, cf:prenom=Marc, cf:ville=Nancy
    romain-guillet                   cf:nom=Guillet, cf:prenom=Romain, cf:ville=Lyon
    jeanne-lenglet                   cf:nom=Lenglet, cf:prenom=Jeanne, cf:ville=Lyon
    lisa-magnant                     cf:nom=Magnant, cf:prenom=Lisa, cf:ville=Marseille
```


### 4. Récupérer des informations sur un utilisateur
Récupérez les informations de l'utilisateur jean-dupont avec la commande get :

```bash
get '<votre-nom>-shell', 'jean-dupont'
```

Exemple de sortie :
 ```sql
    COLUMN                        CELL
    cf:nom                       timestamp=1683149127323, value=Dupont
    cf:prenom                    timestamp=1683149127323, value=Jean
    cf:ville                     timestamp=1683149127323, value=Nancy
```

### 5. Récupérer les utilisateurs dont le prénom commence par "ma"
Utilisez la commande suivante pour récupérer les utilisateurs dont le prénom commence par "ma" :

```bash
scan '<votre-nom>-shell', {FILTER => "PrefixFilter('ma')"}
```

### 6. Suppression des données conformément au RGPD
Suppression des données de Lisa Magnant :

```bash
deleteall '<votre-nom>-shell', 'lisa-magnant'
get '<votre-nom>-shell', 'lisa-magnant'
Row 'lisa-magnant' does not exist
```


Suppression de la ville de Jeanne Lenglet :

```bash
delete '<votre-nom>-shell', 'jeanne-lenglet', 'cf:ville'
get '<votre-nom>-shell', 'jeanne-lenglet'
```

```rust
COLUMN    CELL
 cf:nom    timestamp=1597830197551, value=Lenglet
 cf:prenom timestamp=1597830197551, value=Jeanne
```

## II. **Modélisation**

Pour le projet de serveur de chat, nous devons créer plusieurs tables pour stocker les informations suivantes : utilisateurs, channels, et messages. Voici les étapes de la modélisation.

### 1. **Nombre de tables nécessaires**

Nous aurons besoin de **3 tables principales** :

- **Table des utilisateurs** : pour stocker les informations des utilisateurs (pseudonyme, e-mail, channels rejoints).
- **Table des channels** : pour stocker les channels disponibles et leurs messages.
- **Table des messages** : pour stocker les messages envoyés dans chaque channel.

### 2. **Familles de colonnes nécessaires**

#### Table des utilisateurs :
- **info** : pour stocker le pseudonyme et l'email.
- **channels** : pour stocker les channels rejoints et la date de jonction.

#### Table des channels :
- **info** : pour stocker les informations sur le channel.
- **messages** : pour stocker les messages envoyés dans le channel.

#### Table des messages :
- **content** : pour stocker le contenu du message.
- **metadata** : pour stocker la date et l'utilisateur qui a envoyé le message.

### 3. **Clé utilisée pour les lignes**

#### Table des utilisateurs :
- La **clé de ligne** sera le nom de l'utilisateur (ex. : `jean-dupont`).

#### Table des channels :
- La **clé de ligne** sera le nom du channel (ex. : `general`).

#### Table des messages :
- La **clé de ligne** sera composée du **nom du channel** et du **timestamp** du message.

### 4. **Lister tous les channels disponibles**

Pour lister tous les channels disponibles, vous pouvez effectuer une commande `scan` sur la table des channels :


```bash
scan 'channels'
```

### 5. Lister les channels dont un utilisateur est membre
Pour lister les channels auxquels un utilisateur a rejoint, vous pouvez effectuer un get sur la table des utilisateurs :

```bash
get 'utilisateurs', 'jean-dupont'
```

### 6. Lister les informations d’un utilisateur
Pour lister les informations d’un utilisateur, vous pouvez exécuter la commande get sur la table des utilisateurs :

```bash
get 'utilisateurs', 'jean-dupont'
```

### 7. Lister les 20 derniers messages d’un channel
Pour lister les derniers messages d’un channel, vous pouvez utiliser un scan avec un filtre sur la famille de colonnes messages et trier les résultats par timestamp inverse.

```bash
scan 'messages', {FILTER => "TimestampFilter(...)"}
```

### 8. Modifier un message
Pour modifier un message, vous devez d'abord supprimer l'ancien message puis en insérer un nouveau avec les modifications. Utilisez la commande delete puis put :

```bash
delete 'messages', 'channel1', 'timestamp1'
put 'messages', 'channel1', 'timestamp1', 'content', 'nouveau message'
```

### 9. Lister l’historique de modification d’un message
Pour l'historique des modifications, il faudra stocker les différentes versions d'un message avec des timestamps différents. Utilisez une clé composite pour identifier chaque version.

---

## III. Utilisation de l'API Java (ou Python)

### 1. Exécuter les opérations en Java (ou Python)
Exécutez toutes les opérations mentionnées dans la section "Utilisation du Shell HBase" en utilisant l'API Java ou Python, en vous référant aux exemples donnés dans la documentation d'HBase.

### 2. Implémentation des fonctions de la partie modélisation
Implémentez les fonctions décrites dans la section "Modélisation" en Java ou Python pour interagir avec les tables HBase. Vous pouvez utiliser des bibliothèques comme HBaseClient en Java ou happybase en Python.