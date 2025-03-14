**Modélisation du Système d'Authentification avec Redis**

### 1. Stockage des informations dans Redis

#### **Clés et structures de stockage**
1. **Stockage des tokens par utilisateur et par appareil**  
   - Clé : `auth:token:<username>:<device_id>`  
   - Type : **String** avec expiration de 15 minutes (`EX 900`)  
   - Valeur : `UUID` du token  
   
   **Commande Redis :**  
   ```sh
   SET auth:token:romain:phone "random-token-123" EX 900
   ```

2. **Liste des appareils associés à un utilisateur**  
   - Clé : `auth:devices:<username>`  
   - Type : **Set**  
   - Valeur : Liste des `device_id`  
   
   **Commande Redis :**  
   ```sh
   SADD auth:devices:romain "phone" "laptop" "tablet"
   ```

3. **Stockage du compteur de tentatives de connexion**  
   - Clé : `auth:attempts:<username>:<device_id>`  
   - Type : **String** avec expiration de 15 minutes  
   - Valeur : Compteur des tentatives (max 5)  
   
   **Commande Redis :**  
   ```sh
   INCR auth:attempts:romain:phone
   EXPIRE auth:attempts:romain:phone 900
   ```

4. **Désactivation d’un token**  
   - Suppression de la clé associée au token  
   ```sh
   DEL auth:token:romain:phone
   ```

---

### 2. Nombre de clés utilisées
- **Minimum** : Un seul appareil par utilisateur → 3 clés (`token`, `devices`, `attempts`)
- **Maximum** : Un utilisateur avec plusieurs appareils → `3 * nombre d’appareils`

---

### 3. Tests et Ajustements
- **Création d’un utilisateur avec des appareils et des tokens**  
  ```sh
  SADD auth:devices:romain "phone" "laptop"
  SET auth:token:romain:phone "token-xyz" EX 900
  SET auth:token:romain:laptop "token-abc" EX 900
  ```

- **Vérifier l’authentification d’un appareil**  
  ```sh
  GET auth:token:romain:phone
  ```

- **Limiter les tentatives de connexion**  
  ```sh
  INCR auth:attempts:romain:phone
  TTL auth:attempts:romain:phone
  ```

- **Dissocier un appareil d’un utilisateur**  
  ```sh
  SREM auth:devices:romain "phone"
  DEL auth:token:romain:phone
  ```

---