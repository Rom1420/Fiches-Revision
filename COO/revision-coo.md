# 📘 Révision : Conception Logicielle

## 📝 Sommaire
- [📘 Révision : Conception Logicielle](#-révision--conception-logicielle)
  - [📝 Sommaire](#-sommaire)
  - [📊 UML](#-uml)
    - [🖼️ Diagrammes UML](#️-diagrammes-uml)
    - [🏗️ Cas d'utilisation](#️-cas-dutilisation)
      - [🔄 Notions de `include` et `extend`](#-notions-de-include-et-extend)
    - [📏 Diagrammes de classes](#-diagrammes-de-classes)
    - [🔄 Diagramme de séquence](#-diagramme-de-séquence)
  - [🔧 Design Patterns](#-design-patterns)
  - [](#)
  - [🥒 Cucumber](#-cucumber)
    - [📝 Gherkin et Scénarios](#-gherkin-et-scénarios)
      - [Exemple :](#exemple-)
    - [🔗 Intégration dans les tests](#-intégration-dans-les-tests)

---

## 📊 UML

### 🖼️ Diagrammes UML
Les diagrammes UML permettent de représenter différents aspects d'un système logiciel :

### 🏗️ Cas d'utilisation
Le diagramme de cas d'utilisation illustre les interactions entre les **acteurs** (utilisateurs externes) et les **systèmes**. Il permet de modéliser les fonctionnalités attendues.

#### 🔄 Notions de `include` et `extend`
- **Include** : le cas d'utilisation cible **doit** inclure un autre cas d'utilisation (partage de comportement obligatoire). Cela est utilisé pour **factoriser** des fonctionnalités communes entre plusieurs cas d'utilisation.
  
  > **Exemple** : Dans un système de réservation de vol, **Réserver un vol** inclut systématiquement le cas d'utilisation **Saisir informations de paiement**.

- **Extend** : un cas d'utilisation **peut** être étendu par un autre (optionnel). C'est utilisé pour ajouter un comportement supplémentaire dans des conditions spécifiques.
  
  > **Exemple** : **Réserver un vol** pourrait être étendu par **Utiliser des points de fidélité** si l'utilisateur a des points disponibles.


![alt text](image-8.png)

### 📏 Diagrammes de classes
Le diagramme de classes représente les **classes** du système, leurs **attributs**, **méthodes**, et leurs **relations** (association, héritage, etc.).
![alt text](image-6.png)

### 🔄 Diagramme de séquence
Le diagramme de séquence illustre comment les objets **interagissent entre eux** au cours du temps pour réaliser une opération.
![alt text](image-7.png)

---

## 🔧 Design Patterns

![alt text](image.png)
![alt text](image-1.png)
![alt text](image-2.png)
![alt text](image-3.png)
![alt text](image-4.png)
![alt text](image-5.png)
---

## 🥒 Cucumber

### 📝 Gherkin et Scénarios
**Cucumber** est un outil de tests basé sur des spécifications en langage naturel. Le langage **Gherkin** est utilisé pour écrire les **scénarios** de test, sous forme de :
- **Given** (état initial)
- **When** (action)
- **Then** (résultat attendu)

#### Exemple : 

```gherkin
  Feature: Réservation d'un vol

  Scenario: Réserver un vol avec succès
    Given un utilisateur est sur la page de recherche de vols
    When il recherche un vol de Paris à New York
    And il sélectionne un vol disponible
    And il entre ses informations de paiement
    Then la réservation est confirmée
    And un email de confirmation est envoyé
```

```java
  public class ReservationSteps {

    @Given("un utilisateur est sur la page de recherche de vols")
    public void utilisateurSurPageRechercheVols() {
        System.out.println("Utilisateur est sur la page de recherche de vols.");
    }

    @When("il recherche un vol de Paris à New York")
    public void rechercheVol() {
        System.out.println("Recherche d'un vol de Paris à New York.");
    }

    @When("il sélectionne un vol disponible")
    public void selectionVol() {
        System.out.println("Sélection d'un vol disponible.");
    }

    @When("il entre ses informations de paiement")
    public void entrerInfosPaiement() {
        System.out.println("Entrée des informations de paiement.");
    }

    @Then("la réservation est confirmée")
    public void confirmationReservation() {
        System.out.println("La réservation est confirmée.");
    }

    @Then("un email de confirmation est envoyé")
    public void emailConfirmation() {
        System.out.println("Un email de confirmation est envoyé.");
    }
}
```

### 🔗 Intégration dans les tests
Cucumber permet l'intégration des tests avec différents langages (Java, Ruby, etc.) et automatise les scénarios pour valider le comportement des applications.
