## Synchronisation (cuda.synchronize())
En CUDA, le calcul sur le GPU peut être asynchrone, ce qui signifie que les threads peuvent continuer à travailler sans attendre que d'autres threads aient terminé leur travail. 

La fonction ``cuda.synchronize()`` est utilisée pour forcer la synchronisation, c'est-à-dire **attendre que toutes les opérations** précédentes sur le GPU soient **terminées** avant de continuer l'exécution du code sur le CPU. 

```python
    start = timer()
    computeInt[blocksPerGrid, threadsPerBlock](a, n)  # Lancement du kernel sur le GPU
    cuda.synchronize()  # Attendre que toutes les opérations GPU se terminent
    dt = timer() - start  # Mesurer le temps écoulé
```

 ```python
    # Création des événements
    start_event = cuda.event()
    end_event = cuda.event()

    # Enregistrement du début de l'exécution
    start_event.record()

    # Lancement du kernel sur le GPU
    computeInt[blocksPerGrid, threadsPerBlock](a, n)

    # Enregistrement de la fin de l'exécution
    end_event.record()

    # Attendre que l'exécution du GPU soit terminée avant de calculer l'écart
    cuda.synchronize()

    # Calcul du temps écoulé entre les deux événements
    elapsed_time = cuda.event_elapsed_time(start_event, end_event)

    print(f"Temps d'exécution (en millisecondes) : {elapsed_time} ms")
```


"""
###  Memory transfer
In this exercise you will instantiate an array on the host, send it to the device. A kernel will write in the array and finally the host will get the data back.

Instantiate an array of size 32 on the host and fill it with 0
Write the code to send the array to the device
Write a kernel where each thread write its local ID in the corresponding array cell. For example, thread with local ID 4 will do array[4]=4
Write the code to copy back the array after the execution of the kernel and print its content
Call your kernel with a grid size of 1 and and 32 threads. Is it working?"""

```python
from numba import cuda
import numpy as np


@cuda.jit
def kernel(array):
    global_id = cuda.grid(1)
    array[global_id] = global_id


def run(size):
    threadsPerBlock = size
    blocksPerGrid = 1
    print("threadsPerBlock ", threadsPerBlock)
    print("blocksPerGrid", blocksPerGrid)
    A = np.zeros(size, dtype=np.int32)
    d_A = cuda.to_device(A)
    kernel[blocksPerGrid, threadsPerBlock](d_A)
    cuda.synchronize()
    A = d_A.copy_to_host()
    print(A)


run(32)
```


### Memory transfer
In this exercise you will instantiate an array on the host, send it to the device. A kernel will write in the array and finally the host will get the data back.

Instantiate an array of size 32 on the host and fill it with 0
Write the code to send the array to the device
Write a kernel where each thread write its local ID in the corresponding array cell. For example, thread with local ID 4 will do array[4]=4
Write the code to copy back the array after the execution of the kernel and print its content
Call your kernel with a grid size of 1 and and 32 threads. Is it working?"""

```python
from numba import cuda
import numpy as np


@cuda.jit
def kernel(array):
    global_id = cuda.grid(1)
    if global_id < array.shape[0]:
        array[global_id] = global_id


def run(size, threads):
    threadsPerBlock = threads
    blocksPerGrid = 1
    print("threadsPerBlock ", threadsPerBlock)
    print("blocksPerGrid", blocksPerGrid)
    A = np.zeros(size, dtype=np.int32)
    d_A = cuda.to_device(A)
    kernel[blocksPerGrid, threadsPerBlock](d_A)
    cuda.synchronize()
    A = d_A.copy_to_host()
    print(A)


run(30, 32)
```

### Larger array
``` python
from numba import cuda
import numpy as np


@cuda.jit
def kernel(array):
    global_id = cuda.grid(1)
    if global_id < array.shape[0]:
        array[global_id] = global_id


def run(size, threads):
    threadsPerBlock = threads
    blocksPerGrid = (size + threadsPerBlock - 1) // threadsPerBlock
    print("threadsPerBlock ", threadsPerBlock)
    print("blocksPerGrid", blocksPerGrid)
    A = np.zeros(size, dtype=np.int32)
    d_A = cuda.to_device(A)
    kernel[blocksPerGrid, threadsPerBlock](d_A)
    cuda.synchronize()
    A = d_A.copy_to_host()
    print(A)


run(50, 32)
```

### Transformation image

```python
from numba import cuda
import numba as nb
import numpy as np
import time
from PIL import Image
from timeit import default_timer as timer
import math
import sys

@cuda.jit
def RGBToBWKernel(source, destination, offset):
    height = source.shape[1]
    width = source.shape[0]
    #offset =8 
    x,y = cuda.grid(2)
    if (x<width and y<height) :
        r_x= (x+offset)%width
        r_y= (y+offset)%height
        # ( (0.3 * R) + (0.59 * G) + (0.11 * B) )
        destination[r_x,r_y]=np.uint8(0.3*source[r_x,r_y,0]+0.59*source[r_x,r_y,1]+0.11*source[r_x,r_y,2])



def gpu_run(imagetab,threadsperblock, blockspergrid ):
        print("Sending image to device ", end=" ")
        #start = timer()
        s_image = cuda.to_device(imagetab)
        d_image = cuda.device_array((imagetab.shape[0],imagetab.shape[1],3),dtype = np.uint8)
        
        
        for off in range(33,1,-1):
            print("--- offset ---", off)
            runs = 6
            result =np.zeros(runs, dtype=np.float32)
            for i in range(runs):
                print("Executing kernel  ", end=" ")
                start = timer()
                RGBToBWKernel[blockspergrid, threadsperblock](s_image, d_image,off) 
                cuda.synchronize()
                dt = timer() - start
                print(" ", dt, " s")
                result[i]=dt
                
            # dt = timer() - start
            #print(" ", dt, " s")
            print("Average :", threadsperblock[0],off, np.average(result[1:]))
        
        output=d_image.copy_to_host()
        return output
        
def cpu_run(source):
    output=np.empty_like(source)
    height = source.shape[1]
    width = source.shape[0]
    print("Executing on CPU   ", end=" ")
    start = timer()
    for x in range(width):
        for y in range(height):
            output[x,y]=np.uint8(0.3*source[x,y,0]+0.59*source[x,y,1]+0.11*source[x,y,2])
    dt = timer() - start
    print(" ", dt, " s")        
    return output
            
def compute_threads_and_blocks(imagetab):
    threadsperblock = (8,8)
    if len(sys.argv) ==4:
        threadsperblock=(int(sys.argv[3]),int(sys.argv[3]))
    width, height = imagetab.shape[:2]
    blockspergrid_x = math.ceil(width / threadsperblock[0])
    blockspergrid_y = math.ceil(height / threadsperblock[1])
    blockspergrid = (blockspergrid_x, blockspergrid_y)
    print("Thread blocks ", threadsperblock)
    print("Grid ", blockspergrid)
    return threadsperblock,blockspergrid
    
    
if len(sys.argv) < 3:
    print("Usage: ", sys.argv[0]," <inputFile> <outputFile>")
    quit(-1)
    
inputFile = sys.argv[1]
outputFile=sys.argv[2]


    im = Image.open(inputFile)
    imagetab = np.array(im)

    threadsperblock,blockspergrid=compute_threads_and_blocks(imagetab)
    output=gpu_run(imagetab, threadsperblock, blockspergrid)
    # output=cpu_run(imagetab)
    m = Image.fromarray(output) #.convert('RGB')
    m.save(outputFile)
```

# Transformation d'image avec CUDA et Numba

Ce script Python utilise **CUDA avec Numba** pour appliquer une **transformation d'image** sur GPU.  
Il convertit une image couleur **RGB en niveaux de gris**, tout en appliquant un **décalage (`offset`)** sur les pixels.  

---

## **1. Chargement de l'image**
- L'image est chargée avec **PIL** et convertie en un tableau NumPy.
- **Dimensions de l’image** : `width × height × 3` (3 canaux pour R, G, B).

---

## **2. Définition du kernel CUDA (`RGBToBWKernel`)**
- Exécuté en parallèle sur **GPU**.
- Convertit chaque pixel en **niveau de gris** en appliquant la formule :  
  \[
  \text{gray} = 0.3 \times R + 0.59 \times G + 0.11 \times B
  \]
- Chaque thread traite un pixel, mais avec un **décalage (`offset`)** pour repositionner l'écriture.

---

## **3. Exécution sur le GPU (`gpu_run`)**
- Envoie l’image sur le **GPU**.
- Lance le kernel CUDA avec des **grilles et blocs de threads**.
- **Plusieurs essais** sont effectués avec **différents offsets** pour mesurer la performance.
- La synchronisation CUDA garantit que chaque itération est bien terminée avant de mesurer le temps d’exécution.

---

## **4. Exécution sur le CPU (`cpu_run`)**
- Même transformation en niveaux de gris mais en **boucle for classique** (beaucoup plus lent que CUDA).
- Permet de comparer les performances avec l’exécution GPU.

---

## **5. Calcul du nombre de threads et blocs (`compute_threads_and_blocks`)**
- Définit une grille de threads adaptée à la taille de l’image.
- Par défaut, les **blocs** font **8×8** threads.
- Ajuste le nombre total de **blocs** pour couvrir toute l’image.

---

## **6. Exécution principale**
- **Lit l'image d'entrée**.
- **Calcule les blocs et threads** pour CUDA.
- **Exécute le kernel sur GPU**.
- **Convertit le résultat en image et l’enregistre**.

---

## **🔹 Points importants**
- **Pourquoi CUDA ?** → Beaucoup plus rapide qu'une boucle `for` sur CPU.
- **Pourquoi un `offset` ?** → Peut améliorer l’accès mémoire, mais ici son effet exact reste à analyser.
- **Pourquoi tester plusieurs fois ?** → Pour avoir une **moyenne des performances** et éviter les fluctuations.

Ce script est un bon exemple d'optimisation GPU en utilisant **CUDA avec Numba** pour accélérer le traitement d’image. 🚀
