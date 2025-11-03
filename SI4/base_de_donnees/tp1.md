# **Résumé du TP MongoDB**

## 1. Introduction à MongoDB
MongoDB est une base de données NoSQL orientée documents, où les données sont stockées sous forme de documents JSON. Elle est conçue pour offrir une **persistance distribuée** et repose sur des mécanismes de **réplication et de sharding**. Il supporte également des transactions ACID multi-documents, garantissant la cohérence des opérations complexes. 

- **Réplication** : MongoDB permet de répliquer les données sur plusieurs serveurs via des replica sets, garantissant ainsi la **tolérance aux pannes**.
- **Sharding** : La distribution des données sur **plusieurs nœuds** (shards) permet de mieux gérer de grands volumes de données et d’améliorer les performances.
- **Transactions** : Une transaction en base de données est un ensemble d'opérations traitées comme une unité. Les propriétés ACID garantissent la fiabilité des transactions :

    - **Atomicité** : L'ensemble des opérations **réussit ou échoue** ensemble, sans état intermédiaire.
    - **Cohérence** : La transaction amène la base de données d'un **état valide à un autre état valide**.
    - **Isolation** : Les transactions simultanées **n'interfèrent pas entre elles**.
    - **Durabilité** : Une fois la transaction validée, ses **effets sont permanents**, même en cas de panne.

### **Installation avec Docker**    
1. Télécharger l’image MongoDB :
   ```sh
   docker pull mongodb/mongodb-community-server:latest
   ```
2. Lancer une instance MongoDB :
   ```sh
   docker run --name mongodb -p 27017:27017 -d mongodb/mongodb-community-server:latest
   ```
3. Vérifier que le conteneur est bien lancé :
   ```sh
   docker container ls
   ```
4. Se connecter au serveur MongoDB via `mongosh` :
   ```sh
   mongosh
   ```

---

## 2. Création et manipulation des bases et collections

### **Créer et sélectionner une base de données**
```js
use("myDatabase");
```
Cette commande sélectionne la base `myDatabase` (et la crée si elle n'existe pas).

### **Créer une collection et insérer un document**
Insertion d’un document dans la collection `movies` :
```js
db.movies.insertOne({
    "title": "Star Wars, épisode I : La Menace fantôme",
    "realisateur": "George Lucas"
});
```

---

## 3. Requêtes MongoDB

### **Afficher tous les documents d'une collection**
```js
printjson(db.books.find().toArray());
```

### **Rechercher un livre par auteur**
```js
printjson(db.books.find({ "author": "J. R. R. Tolkien" }).pretty());
```

### **Rechercher les titres des livres vendus en paperback à la Fnac pour moins de 11€**
```js
printjson(
    db.books.find(
        { "format.paperback.shop": "Fnac", "format.paperback.price": { $lt: 11 } },
        { "title": 1, "_id": 0 }
    ).sort({ "title": 1 }).pretty()
);
```

### **Rechercher les livres qui n'existent pas en Kindle ou dont le prix Kindle est inférieur à 6€**
```js
printjson(
    db.books.find({
        $or: [
            { "format.kindle": { $exists: false } },
            { "format.kindle.price": { $lt: 6 } }
        ]
    }).pretty()
);
```

### **Compter les livres contenant le mot "dwar" dans leur description (insensible à la casse)**
```js
printjson(
    db.books.countDocuments({
        "description": { $regex: "dwar", $options: "i" }
    })
);
```

---

## 4. Modifications et suppressions de documents

### **Modifier le prix Kindle et ajouter une liste de personnages à un livre spécifique**
```js
db.books.updateOne(
    { "title": "The Hobbit; or, There and Back Again" },
    { $set: { "format.kindle.price": 7.00, "personnages": ["Gandalf", "Bilbon", "Elrond"] } }
);
```

### **Ajouter un magasin à la liste des vendeurs d’un livre spécifique**
```js
db.books.updateOne(
    { "title": "Tolkien’s World from A to Z: The Complete Guide to Middle-Earth" },
    { $addToSet: { "format.paperback.shop": "Cultura" } }
);
```

### **Supprimer les livres ayant un éditeur ou une liste de personnages renseignée**
```js
db.books.deleteMany(
    { $or: [ { "editeur": { $exists: true } }, { "personnages": { $exists: true } } ] }
);
```

---

## 5. Indexation et opérations avancées

### **Créer un index sur le champ "titre" et afficher sa taille**
```js
db.books.createIndex({ "title": 1 });
db.books.stats().indexSizes;
```

### **Reimporter les données et calculer la moyenne des prix Kindle par auteur**
```sh
mongoimport --db myDatabase --collection books --file books.json --jsonArray
```
```js
db.books.aggregate([
    {
        $group: {
            _id: "$author",
            avgKindlePrice: { $avg: "$format.kindle.price" }
        }
    },
    {
        $match: { "avgKindlePrice": { $ne: null } }
    }
]);
```

### **MapReduce : Compter les livres paperback vendus par magasin**
```js
db.books.mapReduce(
    function () {
        if (this.format && this.format.paperback && this.format.paperback.shop) {
            this.format.paperback.shop.forEach(shop => emit(shop, 1));
        }
    },
    function (key, values) {
        return Array.sum(values);
    },
    {
        out: "books_count_per_shop"
    }
);
```

### **MapReduce : Sélectionner les livres vendus uniquement chez Amazon**
```js
db.books.mapReduce(
    function () {
        if (this.format && this.format.paperback && this.format.paperback.shop.length === 1 && this.format.paperback.shop[0] === "Amazon") {
            emit(this._id, this);
        }
    },
    function (key, values) {
        return values[0];
    },
    {
        out: "books_amazon"
    }
);
```

---

## 6. Cas d'utilisation et avantages de NoSQL
### **Pourquoi utiliser MongoDB et les bases NoSQL ?**
- **Scalabilité horizontale** grâce au sharding
- **Grande flexibilité** dans le schéma des données
- **Performance optimisée** pour les lectures et écritures rapides
- **Idéal pour le Big Data**, les applications web et mobiles, et l’IoT
- **Stockage et traitement efficace** des documents JSON semi-structurés

MongoDB est souvent utilisé pour :
- **Les applications en temps réel** (chat, monitoring)
- **Les systèmes de gestion de contenu** (CMS, blogs)
- **Les bases de données pour microservices**
- **Le stockage de données hétérogènes et évolutives**

