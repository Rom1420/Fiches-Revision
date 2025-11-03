library(dplyr)
library(tidyr)
library(ggplot2)
library(sf)
library(leaflet)

round1 <- read.csv("C:/Users/abbon/OneDrive/Documents/SI5/Data Visualisation/Data/results_pres_elections_dept_2017_round_1.csv")
round2 <- read.csv("C:/Users/abbon/OneDrive/Documents/SI5/Data Visualisation/Data/results_pres_elections_dept_2017_round_2.csv")

coords_region <- read.csv("C:/Users/abbon/OneDrive/Documents/SI5/Data Visualisation/Data/coordinates_regions_2016.csv")

round1 <- round1 %>%
  mutate(
    abstention_rate = absent_voters / registered_voters,
    voting_rate = present_voters / registered_voters,
    blank_rate = blank_ballot / votes_cast,
    null_rate = null_ballot / votes_cast,
    votes_cast_rate = votes_cast / present_voters,
    voter_rate = present_voters / registered_voters
  )

round2 <- round2 %>%
  mutate(
    abstention_rate = absent_voters / registered_voters,
    voting_rate = present_voters / registered_voters,
    blank_rate = blank_ballot / votes_cast,
    null_rate = null_ballot / votes_cast,
    votes_cast_rate = votes_cast / present_voters,
    voter_rate = present_voters / registered_voters
)

# Somme des votes par région
sum_votes_round1 <- round1 %>%
  group_by(region_name) %>%
  summarise(sum_votes_cast = sum(votes_cast, na.rm = TRUE))

# Moyenne des votants par région
avg_voters_round1 <- round1 %>%
  group_by(region_name) %>%
  summarise(avg_voters = mean(present_voters, na.rm = TRUE))

# Somme des votes par région
sum_votes_round2 <- round2 %>%
  group_by(region_name) %>%
  summarise(sum_votes_cast = sum(votes_cast, na.rm = TRUE))

# Moyenne des votants par région
avg_voters_round2 <- round2 %>%
  group_by(region_name) %>%
  summarise(avg_voters = mean(present_voters, na.rm = TRUE))


# Jointure avec les coordonnées des régions
round1 <- round1 %>% left_join(coords_region, by = c("region_code" = "insee_reg"))
round2 <- round2 %>% left_join(coords_region, by = c("region_code" = "insee_reg"))

# Ajouter la variable round
round1 <- round1 %>% mutate(round = 1)
round2 <- round2 %>% mutate(round = 2)

results <- bind_rows(round1, round2)

# Les colonnes des candidats
candidates <- c("ARTHAUD","ASSELINEAU","CHEMINADE","DUPONT.AIGNAN","FILLON",
                "HAMON","LASSALLE","LE.PEN","MACRON","MÉLENCHON","POUTOU")

results_tidy <- results %>%
  gather(key = "candidate", value = "votes", all_of(candidates))



# Filtrer uniquement le premier tour
round1_tidy <- results_tidy %>% filter(round == 1)

# Créer le bar chart
p1 <- ggplot(round1_tidy, aes(x = region_name, y = votes, fill = candidate)) +
  geom_bar(stat = "identity", position = "dodge") +
  labs(
    title = "Nombre de votes valides par candidat et par région - 1er tour",
    x = "Région",
    y = "Nombre de votes",
    fill = "Candidat"
  ) +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 45, hjust = 1))

p2 <- ggplot(results_tidy, aes(x = region_name, y = votes, fill = factor(round))) +
  geom_bar(stat = "identity", position = "dodge") +
  facet_wrap(~candidate, scales = "free_y") +
  labs(
    title = "Votes valides par candidat et par région - 1er et 2ème tour",
    x = "Région",
    y = "Nombre de votes",
    fill = "Tour"
  ) +
  theme_minimal() +
  theme(axis.text.x = element_text(angle = 45, hjust = 1))

# Filtrer uniquement le deuxième tour
round2_tidy <- results_tidy %>% filter(round == 2)

# Somme des votes par candidat
votes_per_candidate <- round2_tidy %>%
  group_by(candidate) %>%
  summarise(votes = sum(votes, na.rm = TRUE), .groups = "drop")

# print(votes_per_candidate)

# clnames <- colnames(coords_region)

# print(clnames)

# Somme des votes par candidat pour le 2ème tour
votes_round2 <- round2_tidy %>%
  filter(candidate %in% c("MACRON", "LE.PEN")) %>%
  group_by(candidate) %>%
  summarise(total_votes = sum(votes, na.rm = TRUE))


p4 <- ggplot(votes_round2, aes(x = candidate, y = total_votes, fill = candidate)) +
  geom_bar(stat = "identity") +
  labs(title = "Nombre total de votes - 2ème tour",
       x = "Candidat",
       y = "Votes") +
  theme_minimal()



# Calculer le taux de votes blancs par région
blank_rate_region <- round2 %>%
  group_by(region_code, region_name) %>%
  summarise(blank_rate = sum(blank_ballot, na.rm = TRUE) / sum(votes_cast, na.rm = TRUE),
            .groups = "drop")

# Transformer les coordonnées en objet sf
coords_region_sf <- coords_region %>%
  st_as_sf(coords = c("longitude", "latitude"), crs = 4326)

# Joindre avec les taux
map_data <- blank_rate_region %>%
  left_join(coords_region_sf, by = c("region_code" = "insee_reg"))

map_data <- map_data %>%
  mutate(longitude = st_coordinates(geometry)[,1],
         latitude  = st_coordinates(geometry)[,2])

# Carte interactive
p5 <- leaflet(map_data) %>%
  addTiles() %>%
  addCircleMarkers(
    ~longitude, ~latitude,
    radius = ~blank_rate * 50,
    color = "orange",
    fillOpacity = 0.6,
    label = ~paste(region_name, ": ", round(blank_rate*100, 2), "% votes blancs")
  ) %>%
  addLegend(
    "bottomright",
    colors = "orange",
    labels = "Taux de votes blancs",
    opacity = 0.7
  )

print(p5)