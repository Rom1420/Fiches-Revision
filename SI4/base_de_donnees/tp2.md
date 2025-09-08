**Résumé des commandes du TP2 ElasticSearch**

### 1. Requêtes Simples
- **search_data** : Recherche un film avec un titre spécifique (ex: "Star Wars").
  ```json
  {
    "query": {
      "match": {
        "title": "Star Wars"
      }
    }
  }
  ```
- **search_data avec bool must** : Recherche un film correspondant à plusieurs critères (titre + réalisateur).
  ```json
  {
    "query": {
      "bool": {
        "must": [
          { "match": { "title": "Star Wars" }},
          { "match": { "directors": "George Lucas" }}
        ]
      }
    }
  }
  ```
- **search_data avec must et must_not** : Recherche un film en incluant et excluant certains termes.
  ```json
  {
    "query": {
      "bool": {
        "must": [
          { "match": { "actors": "Harrison Ford" }},
          { "match": { "plot": "Jones" }}
        ],
        "must_not": [
          { "match": { "plot": "Nazis" }}
        ]
      }
    }
  }
  ```
- **search_data avec filtre** : Recherche un film avec un critère de réalisateur et un filtre sur un classement inférieur à 100.
  ```json
  {
    "query": {
      "bool": {
        "must": [
          { "match": { "directors": "Steven Spielberg" }}
        ],
        "filter": [
          { "range": { "rank": { "lt": 100 }}}
        ]
      }
    }
  }
  ```
- **search_data avec exclusion de genres** : Recherche un film d'un réalisateur donné avec une note minimale mais en excluant certains genres.
  ```json
  {
    "query": {
      "bool": {
        "must": [
          { "match": { "directors": "James Cameron" }},
          { "range": { "rating": { "gt": 6.5 }}}
        ],
        "must_not": [
          { "match": { "genres": "Action" }},
          { "match": { "genres": "Drama" }}
        ]
      }
    }
  }
  ```
- **fetch_data** : Récupère tous les films avec un affichage formaté.
  ```json
  GET http://localhost:9200/movies/_search?pretty=true
  ```

### 2. Agrégations
- **Agregate_search 5** : Calcule la moyenne, le min et le max des notes des films par année.
  ```json
  {
    "size": 0,
    "aggs": {
      "avg_rating_by_year": {
        "terms": { "field": "year", "size": 10000 },
        "aggs": {
          "min_rating": { "min": { "field": "rating" } },
          "max_rating": { "max": { "field": "rating" } },
          "average_rating": { "avg": { "field": "rating" } }
        }
      }
    }
  }
  ```
- **Agregate_search 6** : Trie les films par année en fonction du rang moyen.
  ```json
  {
    "size": 0,
    "aggs": {
      "avg_rating_by_year": {
        "terms": { "field": "year", "size": 10000, "order": {"rang_moyen": "desc"} },
        "aggs": {
          "rang_moyen": { "avg": { "field": "rank" }}
        }
      }
    }
  }
  ```

### 3. Commandes de base
- **index_doc** : Ajoute un document dans l'index "magasin".
  ```json
  {
    "titre": "TP ElasticSearch",
    "sous-titre": "Savoir utiliser et configurer ElasticSearch",
    "formateurs": [{ "prenom": "Pierre", "nom": "Monnin" }],
    "jours": 3
  }
  ```
- **delete_data** : Supprime un document spécifique de l'index "magasin".
  ```json
  DELETE http://localhost:9200/magasin/_doc/1
  ```
- **search_with_key_word** : Recherche un document où un champ a une valeur spécifique.
  ```json
  GET http://localhost:9200/magasin/_search?q=jours:3
  ```
- **import_data** : Importation de plusieurs documents dans l'index "movies".
  ```json
  { "index": { "_index": "movies", "_id": "1" } }
  { "title": "Inception", "directors": ["Christopher Nolan"], "year": 2010 }
  ```

### Explication
Ce TP utilise **ElasticSearch**, un moteur de recherche basé sur **Lucene**, permettant des requêtes avancées sur des bases de données NoSQL. Les requêtes sont principalement de deux types :
- **Recherches classiques** (match, bool, filter) pour retrouver des documents spécifiques.
- **Agrégations** pour regrouper et analyser des données (ex: moyenne des notes par année).

L'objectif du TP est de manipuler ces différents types de requêtes pour mieux comprendre comment indexer et interroger efficacement des données dans ElasticSearch.

