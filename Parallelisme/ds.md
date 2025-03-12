# **Révision DS**

## **Programmation Concurrente**

### **Threads et Mutex en pthread**
- `pthread_create` : Crée un thread.
- `pthread_join` : Attend la fin d'un thread.
- `pthread_mutex_lock(&lock);` : Verrouille un mutex pour empêcher d'autres threads d'y accéder.
- `pthread_mutex_unlock(&lock);` : Libère le mutex pour permettre l'accès à d'autres threads.
- `pthread_mutex_init(&lock, NULL);` : Initialise un mutex.
- `pthread_mutex_destroy(&lock);` : Détruit un mutex à la fin.

```c
void *fonction_thread(void *arg) {
    int num = *(int *)arg;
    printf("Thread %d : Hello World !\n", num);
    pthread_exit(NULL);
}

int main() {
    int N = 5;
    pthread_t threads[N];
    int args[N];

    for (int i = 0; i < N; i++) {
        args[i] = i + 1;
        pthread_create(&threads[i], NULL, fonction_thread, &args[i]);
    }

    for (int i = 0; i < N; i++) {
        pthread_join(threads[i], NULL);
    }

    printf("Tous les threads sont terminés.\n");
    return 0;
}
```

```c
#define NB_THREADS 4

pthread_mutex_t lock;
int compteur = 0;

void *incrementer(void *arg) {
    for (int i = 0; i < 100000; i++) {
        pthread_mutex_lock(&lock);  // 🔒 Verrouille le mutex
        compteur++;
        pthread_mutex_unlock(&lock);  // 🔓 Déverrouille le mutex
    }
    pthread_exit(NULL);
}

int main() {
    pthread_t threads[NB_THREADS];

    pthread_mutex_init(&lock, NULL);  // Initialisation du mutex

    for (int i = 0; i < NB_THREADS; i++) {
        pthread_create(&threads[i], NULL, incrementer, NULL);
    }

    for (int i = 0; i < NB_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }

    pthread_mutex_destroy(&lock);  // Libère le mutex
    printf("Valeur finale du compteur : %d\n", compteur);
    return 0;
}
```

#### **Deadlock en pthread (Interblocage)**
Un deadlock se produit lorsque deux threads (ou plus) attendent indéfiniment qu'une ressource se libère.

**Comment l'éviter ?**
✅ Toujours verrouiller les mutex dans le même ordre.

### **Sémaphore**
Un sémaphore est une structure de synchronisation permettant de limiter l'accès à une ressource partagée.

#### **Exemple en Java :**
```java
import java.util.concurrent.Semaphore;

class Parking {
    private Semaphore semaphore;

    public Parking(int places) {
        this.semaphore = new Semaphore(places);
    }

    public void entrer(String voiture) {
        try {
            System.out.println(voiture + " essaie d'entrer");
            semaphore.acquire();
            System.out.println(voiture + " est entrée 🚗 (places restantes : " + semaphore.availablePermits() + ")");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            semaphore.release();
            System.out.println(voiture + " sort du parking 🏁");
        }
    }
}

public class TestSemaphore {
    public static void main(String[] args) {
        Parking parking = new Parking(3);
        for (int i = 1; i <= 5; i++) {
            final String voiture = "Voiture " + i;
            new Thread(() -> parking.entrer(voiture)).start();
        }
    }
}
```

semaphore.acquire() : Une voiture prend une place. Si le parking est plein, elle attend.
semaphore.release() : Une voiture quitte le parking, libérant une place.
Les 5 voitures ne peuvent pas entrer simultanément : seulement 3 places sont disponibles.

📌 **Cas d'usage des sémaphores :**
✅ Contrôle d'accès à une ressource limitée.
✅ Limiter le nombre de threads en exécution simultanée.

AtomicInteger garantit que incrementAndGet() est exécuté sans interférence entre les threads.
Sans AtomicInteger, le résultat serait incorrect et imprévisible à cause des interruptions possibles entre valeur++.
📌 Cas d’usage des Variables Atomiques :
✅ Éviter les conditions de course sans utiliser synchronized ou des locks.
✅ Utilisé pour des compteurs, indicateurs d'état, etc.

### **Moniteur**

Un monitor est une structure de synchronisation utilisée pour éviter les conditions de course en permettant l'accès exclusif à une ressource partagée. Il fournit :

Un verrou implicite (synchronized en Java)
Des variables conditionnelles (wait(), notify(), notifyAll() en Java)

#### **Exemple en Java :**
```java
private final Object isBufferEmptyCondition = new Object();


@Override
public void put(E data) throws InterruptedException {
	synchronized (isBufferFullCondition) {
		while (count == SIZE) {
			isBufferFullCondition.wait();
		}
	}
	synchronized (isBufferEmptyCondition) {
		queue[tail] = data;
		tail = (tail + 1) % SIZE;
		count++;
		isBufferEmptyCondition.notify();
	}
}
```

#### 📌 Explication
Le mot-clé synchronized garantit qu'un seul thread peut modifier valeur à la fois.
Sans synchronized, plusieurs threads pourraient lire et écrire valeur en même temps, provoquant une condition de course.

- **wait()** : Un thread qui appelle wait() sur un objet libère le verrou et attend qu'un autre thread le réveille.
- **notify()**: Réveille un thread en attente sur le même objet.
- **notifyAll()** : Réveille tous les threads en attente sur cet objet.

📌 **Méthodes de synchronisation :**
| Méthode | Utilisation | Avantages |
|----------|------------|------------|
| Monitor (synchronized) | Exclusion mutuelle + coordination entre threads | Simple à utiliser, natif en Java |
| Mutex (ReentrantLock) | Verrou exclusif sur une ressource partagée | Plus flexible que synchronized |
| Sémaphore | Contrôle le nombre de threads accédant à une ressource | Peut autoriser plusieurs threads simultanés |

---

## **Programmation Parallèle**

### **OpenMP**
OpenMP est une API qui facilite la parallélisation en C/C++.

#### **Principales directives :**
- `#pragma omp parallel` : Crée des threads parallèles.
- `#pragma omp for` : Divise une boucle en plusieurs threads.
- `omp_set_num_threads(nbth)` : Définit le nombre de threads.
- `omp_get_num_procs()` : Obtient le nombre de processeurs disponibles.

- `#pragma omp parallel reduction(op : var)` : Effectue une réduction parallèle sur var en appliquant l'opérateur op (exemple : +, *, max, min). Chaque thread dispose d'une copie locale de var et une réduction est effectuée à la fin de la région parallèle.
- `#pragma omp atomic` : Assure une opération atomique sur une variable partagée, empêchant les conditions de course.
- `#pragma omp critical` : Définit une section critique où un seul thread à la fois peut exécuter le code.
- `#pragma omp barrier` : Synchronise tous les threads à un point donné du programme.
- `#pragma omp single` : Spécifie qu’un seul thread doit exécuter un bloc de code, les autres attendant sa fin.
- `#pragma omp master` : Seul le thread maître (thread 0) exécute la section.

---

### **Modèle PRAM (Parallel Random Access Machine)**
Le modèle PRAM (Parallel Random Access Machine) est un modèle théorique de calcul parallèle utilisé pour analyser et concevoir des algorithmes parallèles de manière abstraite. Il suppose un ensemble de processeurs (P) travaillant en parallèle et partageant une mémoire commune à accès aléatoire (RAM).

#### **Variantes du modèle PRAM :**
| Modèle PRAM | Lecture concurrente (CR) | Écriture concurrente (CW) |
|-------------|------------------|------------------|
| EREW PRAM  | ❌ Non | ❌ Non |
| CREW PRAM  | ✅ Oui | ❌ Non |
| ERCW PRAM  | ❌ Non | ✅ Oui |
| CRCW PRAM  | ✅ Oui | ✅ Oui |

---

Arbitrary CW : Une des valeurs est écrite aléatoirement.
Priority CW : Une règle de priorité définit quelle valeur est écrite.
Common CW : Tous les processeurs doivent écrire la même valeur.

| Mode        | Condition d'écriture                                           | Exemple d'application                                  |
|-------------|---------------------------------------------------------------|-------------------------------------------------------|
| Arbitrary   | Une seule écriture réussit, choisie arbitrairement            | Mise à jour non critique (ex. marquer une mémoire comme "occupée") |
| Consistent  | Écriture réussie seulement si toutes les valeurs sont identiques | Validation de consensus, protection contre écriture incohérente |
| Associative | Toutes les écritures sont combinées avec une opération associative | Somme parallèle, min/max d’un ensemble               |

## **Introduction aux Ordinateurs Quantiques**

### **Les bases de l’informatique quantique**
- **Qubit** : Un qubit peut être en superposition entre 0 et 1.
- **Intrication** : Deux qubits peuvent être liés, influençant instantanément l'un l'autre.
- **Décohérence** : Difficulté à maintenir les qubits stables.

### **Pourquoi est-ce important ?**
✅ Les ordinateurs quantiques pourraient résoudre certains problèmes exponentiellement plus vite.
✅ L'algorithme de Shor pourrait casser la cryptographie classique.

### **Historique et Défis**
- **1990s** : Premiers calculateurs quantiques.
- **2009** : Premier processeur quantique à Yale.
- **Défi principal** : Stabilisation des qubits pour éviter la décohérence.

### **Applications possibles**
- **Cryptographie post-quantique**
- **Simulation moléculaire pour la médecine et la chimie**
- **Optimisation et intelligence artificielle**

📌 **Conclusion** : Les ordinateurs quantiques sont prometteurs mais restent expérimentaux.


### Correction ds

#### 1. Problème à résoudre avec une opération d'écriture parallèle
Imaginons un problème où plusieurs processeurs veulent ajouter un même type de valeur à une adresse mémoire partagée. Par exemple, un problème de comptage parallèle où chaque processeur doit incrémenter un compteur global.

**Opération binaire choisie :**
L’opération binaire idéale dans ce cas serait l'addition. Si plusieurs processeurs veulent incrémenter la même valeur de compteur simultanément, l'addition permet de cumuler efficacement les valeurs. Chaque processeur additionne simplement sa valeur locale au compteur global.

**Pourquoi l'opération binaire doit-elle être associative ?**
L'associativité est cruciale pour garantir que la combinaison des valeurs se fasse indépendamment de l'ordre d'exécution des opérations. Par exemple, si plusieurs processeurs tentent d'ajouter une valeur au compteur global, l'ordre dans lequel les valeurs sont combinées ne doit pas affecter le résultat final. L'addition est associative car peu importe si on effectue (a + b) + c ou a + (b + c), le résultat final sera le même. Cela permet d'exécuter les écritures en parallèle de manière efficace sans se soucier de l'ordre dans lequel les processeurs effectuent leurs ajouts.

#### 2. Reproduire ce comportement avec une EW PRAM
Une EW PRAM (Exclusive Write PRAM) ne permet pas les écritures concurrentes, c'est-à-dire qu'à un moment donné, un seul processeur peut écrire dans la mémoire. Pour reproduire une opération d’écriture parallèle telle que décrite dans le modèle PRAM CW (Concurrent Write PRAM) en utilisant une EW PRAM, il faut adopter une stratégie pour éviter les conflits d'écriture.

**Algorithme parallèle proposé :**

L'algorithme parallèle pourrait être structuré comme suit :

  - **Phase de collecte des valeurs à combiner :**
  Chaque processeur propose sa valeur à combiner (par exemple, sa valeur à ajouter au compteur global).

  - **Phase de réduction parallèle :**
  Tous les processeurs s'organisent pour appliquer l'opération binaire associative (dans notre exemple, l'addition) sur les valeurs collectées. Cela se fait via une réduction binaire. Les valeurs sont combinées deux à deux jusqu'à obtenir une seule valeur résultante.

  - **Phase d'écriture :**
  Une fois que chaque processeur connaît sa valeur combinée, les valeurs combinées sont écrites dans la mémoire. Comme la EW PRAM ne permet qu'une seule écriture, les processeurs écrivent leur valeur dans un ordre spécifique (par exemple, selon un identifiant unique).

**Temps d'exécution parallèle :**

  - La phase de réduction binaire nécessite log2(n) étapes pour combiner n valeurs, chaque étape impliquant une opération de combinatoire associative. Cette phase prend donc un temps parallèle de O(log n).
  
  - La phase d'écriture prend O(1) puisque, une fois la valeur combinée, l'écriture est effectuée une seule fois.

**Temps total d'exécution parallèle :**
L'algorithme entier aurait une complexité en temps parallèle de O(log n) pour combiner les valeurs et ensuite O(1) pour l'écriture. Le temps d'exécution total est donc O(log n).

#### 3. Surcoût en temps parallèle pour une EW PRAM
Lorsqu'on exécute un algorithme conçu pour la PRAM CW sur une EW PRAM, il y a un surcoût en temps dû à l'absence d'écritures concurrentes.

**Surcoût en temps parallèle :**
Sur la PRAM CW, plusieurs processeurs peuvent écrire simultanément, ce qui permet une exécution plus rapide dans certains cas. Sur la EW PRAM, comme les écritures sont séquentielles, il faut utiliser des étapes supplémentaires pour combiner les résultats avant l'écriture, ce qui augmente le temps parallèle global.

Si l'algorithme sur la PRAM CW a une complexité de temps parallèle de T(n), et en considérant qu'il appartient à NC (temps poly-logarithmique avec un nombre polynomial de processeurs), le surcoût en temps pour une EW PRAM dépend principalement du temps nécessaire pour gérer les conflits d'écriture.

**Impact sur la complexité :**
Pour un algorithme avec une complexité PRAM CW de temps parallèle T(n) = O(log n), il y aura un léger surcoût dû à la réduction des écritures parallèles. Ce surcoût peut amener la complexité à O(log^2 n) dans le pire des cas, mais cela reste dans la classe NC, car un algorithme avec un temps parallèle poly-logarithmique reste dans cette classe. Ainsi, la nouvelle complexité en temps reste O(log^2 n), et l'algorithme reste toujours dans NC.

#### 1. Modèle fork-join d'OpenMP
Le modèle fork-join d'OpenMP signifie que l'exécution commence avec un seul thread, qui se fork (divise) en plusieurs threads lors d'une région parallèle. Après avoir exécuté le code parallèle, les threads se synchronisent et join (rejoignent) le thread principal pour continuer l'exécution séquentielle.

#### 2. Valeur de j et répartition des itérations de la boucle for
Valeur de j :
Dans la région parallèle, j est initialisé à omp_get_thread_num(), qui renvoie l'identifiant du thread courant. Comme il y a 4 threads, j prendra des valeurs de 0 à 3, une pour chaque thread.

**Instances de j :**
Chaque thread aura sa propre instance de j car la variable j est déclarée dans la région parallèle. Ainsi, chaque thread aura une copie locale de j, et sa valeur dépendra de l'identifiant du thread (0, 1, 2, ou 3).

**Répartition des itérations de la boucle for :**
Le pragma #pragma omp for indique que les itérations de la boucle for doivent être réparties entre les threads. OpenMP utilise une répartition statique par défaut, où chaque thread reçoit une portion des itérations. Par exemple, avec 4 threads et 8 itérations, chaque thread exécutera 2 itérations consécutives de la boucle.

**Sans la paire de {} délimitant la région parallèle :**
Si la paire de {} est omise, le code n'est plus exécuté en parallèle. La boucle for devient simplement une boucle séquentielle et sera exécutée par le thread principal, sans utilisation de #pragma omp parallel pour créer plusieurs threads.

#### 3. Sortie du code avec omp_set_num_threads(4)
Le programme utilise 4 threads, et les itérations de la boucle for sont réparties entre ces threads de manière statique. Voici le détail de l'exécution :

**Répartition des itérations :**
Le nombre total d'itérations est 8. Avec 4 threads, chaque thread obtient 2 itérations consécutives.

- Thread 0 reçoit les itérations 0 et 1.
- Thread 1 reçoit les itérations 2 et 3.
- Thread 2 reçoit les itérations 4 et 5.
- Thread 3 reçoit les itérations 6 et 7.
  
**Affichage des lignes :**
Chaque thread exécutera la boucle for pour ses propres itérations et affichera les lignes suivantes :

```mathematica
In FOR: Thread 0 on iteration 0, mod = 0
In FOR: Thread 0 on iteration 1, mod = 0
Thread 0 mod = 0
In FOR: Thread 1 on iteration 2, mod = 2
In FOR: Thread 1 on iteration 3, mod = 3
Thread 1 mod = 3
In FOR: Thread 2 on iteration 4, mod = 8
In FOR: Thread 2 on iteration 5, mod = 10
Thread 2 mod = 10
In FOR: Thread 3 on iteration 6, mod = 18
In FOR: Thread 3 on iteration 7, mod = 21
Thread 3 mod = 21
```

**Ordre des lignes :**
L'ordre des lignes de la boucle printf dans In FOR: Thread ... peut varier car l'exécution des threads n'est pas nécessairement ordonnée. Les threads peuvent terminer leurs itérations à des moments différents, donc l'affichage des résultats peut être dans un ordre non déterminé.

**Ligne "Thread X mod = ..." :**
Cette ligne sera affichée à la fin de chaque exécution de la boucle for par chaque thread, et son ordre dépendra également de l'ordre d'exécution des threads.

**Conclusion :**
Les lignes sont-elles toujours les mêmes ? Oui, les lignes d'affichage pour chaque thread sont toujours les mêmes, mais l'ordre des affichages peut changer d'une exécution à l'autre.
L'ordre des lignes est-il toujours identique ? Non, l'ordre des lignes dans printf("In FOR: Thread ...") peut varier, car les threads peuvent s'exécuter et afficher leurs résultats dans un ordre non déterminé.






