# TP1 : Visualisation des Résultats des Élections Présidentielles 2017

1. [Importation des données](#importation-des-données)
2. [Manipulation des données avec dplyr](#manipulation-des-données-avec-dplyr)
3. [Transformation des données avec tidyr](#transformation-des-données-avec-tidyr)
4. [Visualisation avec ggplot2](#visualisation-avec-ggplot2)
5. [Création de cartes statiques et interactives](#création-de-cartes-statiques-et-interactives)

---

## 1. Importation des données

```r
round1 <- read.csv("path_to_round1.csv")
round2 <- read.csv("path_to_round2.csv")
coords_region <- read.csv("path_to_coords_region.csv")
```

* `read.csv()` : lit un fichier CSV et le transforme en data frame.
* Paramètres :

  * `file` : chemin du fichier CSV.
  * `header` (optionnel) : indique si le fichier contient une ligne d'en-tête.

---

## 2. Manipulation des données avec dplyr

### a. Création de nouvelles colonnes

```r
round1 <- round1 %>%
  mutate(
    abstention_rate = absent_voters / registered_voters,
    voting_rate = present_voters / registered_voters,
    blank_rate = blank_ballot / votes_cast,
    null_rate = null_ballot / votes_cast
  )
```

* `mutate()` : ajoute ou modifie des colonnes.
* Paramètres :

  * Noms des nouvelles colonnes = expressions à calculer.

### b. Agrégation de données

```r
sum_votes_round1 <- round1 %>%
  group_by(region_name) %>%
  summarise(sum_votes_cast = sum(votes_cast, na.rm = TRUE))
```

* `group_by()` : regroupe les données par une ou plusieurs colonnes.
* `summarise()` : calcule des statistiques sur chaque groupe.
* `na.rm = TRUE` : ignore les valeurs manquantes.

### c. Fusion de tables

```r
round1 <- round1 %>% left_join(coords_region, by = c("region_code" = "insee_reg"))
```

* `left_join()` : fait une jointure gauche entre deux data frames.
* Paramètres :

  * `by` : colonnes pour faire la correspondance entre les deux tables.

### d. Transformation en format long

```r
results_tidy <- results %>% gather(key = "candidate", value = "votes", all_of(candidates))
```

* `gather()` (tidyr) : convertit des colonnes en lignes pour passer d'un format large à long.
* Paramètres :

  * `key` : nom de la nouvelle colonne qui contiendra les noms des variables.
  * `value` : nom de la colonne qui contiendra les valeurs.
  * `cols` : colonnes à transformer.

---

## 3. Visualisation avec ggplot2

### a. Histogrammes / Bar Charts

```r
p1 <- ggplot(results_tidy %>% filter(round == 1), aes(x = region_name, y = votes, fill = candidate)) +
  geom_bar(stat = "identity") +
  facet_wrap(~candidate, scales = "free_y")
```

* `ggplot()` : initialise un graphique.
* `aes()` : définit l'esthétique (axes, couleurs, etc.).
* `geom_bar(stat = "identity")` : crée un diagramme à barres avec des valeurs explicites.
* `facet_wrap()` : crée un petit graphique par catégorie.

### b. Comparaison entre tours

```r
p2 <- ggplot(results_tidy, aes(x = region_name, y = votes, fill = factor(round))) +
  geom_bar(stat = "identity", position = "dodge") +
  facet_wrap(~candidate, scales = "free_y")
```

* `position = "dodge"` : place les barres côte à côte.
* `fill = factor(round)` : couleur différente selon le tour.

### c. Couleurs manuelles

```r
scale_fill_manual(values = c("MACRON" = "blue", "LE.PEN" = "red"))
```

* Permet de définir explicitement les couleurs pour certaines catégories.

---

## 4. Calculs de votes totaux par candidat

```r
votes_round2 <- round2_tidy %>%
  filter(candidate %in% c("MACRON", "LE.PEN")) %>%
  group_by(candidate) %>%
  summarise(total_votes = sum(votes, na.rm = TRUE))
```

* Permet d'obtenir le total de votes par candidat, indépendamment des régions.

---

## 5. Cartes statiques (sf et geom\_sf)

### a. Conversion en objet spatial

```r
coords_region_sf <- coords_region %>% st_as_sf(coords = c("longitude", "latitude"), crs = 4326)
```

* `st_as_sf()` : transforme un data frame avec coordonnées en objet spatial.
* `coords` : colonnes des coordonnées.
* `crs` : système de projection.

### b. Carte simple par votes

```r
p4 <- ggplot(votes_round2, aes(x = candidate, y = total_votes, fill = candidate)) +
  geom_bar(stat = "identity")
```

* Pour une carte plus avancée avec géométrie et couleurs par région, on peut utiliser `geom_sf()`.

---

## 6. Cartes interactives (leaflet)

```r
library(leaflet)
leaflet(round2) %>%
  addTiles() %>%
  addCircleMarkers(~longitude, ~latitude, radius = ~blank_rate*50, color = "blue")
```

* `leaflet()` : initialise une carte interactive.
* `addTiles()` : ajoute le fond de carte.
* `addCircleMarkers()` : ajoute des cercles proportionnels à une variable.
* `radius` : taille du cercle selon la variable.
* `color` : couleur des marqueurs.

