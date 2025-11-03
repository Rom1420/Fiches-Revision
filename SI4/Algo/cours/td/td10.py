def meilleure_valeur(p,v,c):
    if not p or not v:
        return 0
    if p[0] > c:
        return meilleure_valeur(p[1:], v[1:], c)
    
    avec_objet = v[0] + meilleure_valeur(p[1:], v[1:], c - p[0])
    sans_objet = meilleure_valeur(p[1:], v[1:], c) 
    return max(avec_objet, sans_objet)


def meilleure_valeur2(p,v,c):
    n = len(p)
    M = [[0 for _ in range(c + 1)] for _ in range(n + 1)]

    for i in range(1, n+1):
        for j in range(c+1):
            if p[i-1] > j:
                M[i][j] = M[i-1][j]
            else:
                M[i][j] = max(M[i - 1][j], v[i - 1] + M[i - 1][j - p[i - 1]])
    return M[n][c]

poids = [2, 3, 4, 5]  
valeurs = [3, 4, 5, 6] 
capacite = 5 

valeur_maximale = meilleure_valeur2(poids, valeurs, capacite)
print(f"La valeur maximale que le sac peut contenir est : {valeur_maximale}")

import random
for n in range (1000) :
    p = [ ]
    v = [ ]
    for _ in range ( n ) :
        p.append ( random.randint(1,100))
        v.append ( random.randint(1,100))
    c = n * 25
    print ( n ,meilleure_valeur2 ( p , v , c )) 
