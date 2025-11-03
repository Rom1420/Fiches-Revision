def tsp_recursive(graph, current, visited, n, start):
    """
    Résout le problème du voyageur de commerce de manière récursive.
    graph : Matrice d'adjacence des distances
    current : Ville actuelle
    visited : Ensemble des villes visitées
    n : Nombre total de villes
    start : Ville de départ
    Retourne : Le coût minimal pour visiter toutes les villes et revenir au départ
    """
    # Si toutes les villes sont visitées, retour à la ville de départ
    if len(visited) == n:
        return graph[current][start]

    min_cost = float("inf")

    # Explorer les autres villes non visitées
    for next_city in range(n):
        if next_city not in visited:
            # Ajouter la ville suivante dans l'ensemble des villes visitées
            visited.add(next_city)
            # Coût pour visiter cette ville + récursion sur le reste
            cost = graph[current][next_city] + tsp_recursive(
                graph, next_city, visited, n, start
            )
            # Mettre à jour le coût minimum
            min_cost = min(min_cost, cost)
            # Retirer la ville suivante pour l'exploration d'autres chemins
            visited.remove(next_city)

    return min_cost


# Fonction principale pour appeler la récursion
def tsp(graph):
    n = len(graph)
    visited = set()
    visited.add(0)  # On commence à partir de la ville 0
    return tsp_recursive(graph, 0, visited, n, 0)


# Exemple de test
graph = [[0, 10, 15, 20], [10, 0, 35, 25], [15, 35, 0, 30], [20, 25, 30, 0]]

resultat = tsp(graph)
print(f"Le coût minimal pour le problème TSP est : {resultat}")
