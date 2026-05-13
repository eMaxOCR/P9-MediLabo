<img width="1280" height="552" alt="image" src="https://github.com/user-attachments/assets/6b6524c9-ab0b-4751-8747-7123299aecbb" />

# P9-MediLabo-Solution
Application de suivi clinique permettant aux médecins de suivre leurs patients en y renseignant les informations médicales.
Les informations renseignées permettent un calcul prédictif du risque de diabète. 

## Architecture

<img width="600" height="610" alt="image" src="https://github.com/user-attachments/assets/3503ba39-ccba-4390-b02b-bcc8a0732932" />

- **Gateway Service** : Routage et centralisation.
- **Medilabo UI** : Interface utilisateur (Front-end).
- **Patient Service** : Gestion des informations personnelles des patients (Base de données : MySQL).
- **Note Service** : Gestion de l'historique des notes des praticiens (Base de données NoSQL : MongoDB).
- **Assessment Service** : Calcul du risque de diabète.

## Prérequis

**Variables d'environnement** :
Pour permettre l'exécution des services, vous devez définir les variables d'environnement nécessaires ou dans le cas de DOCKER, configurer directement sur votre système ou créer un fichier .env à la racine du projet contenant les clés suivantes :
(Pour une première execution/test)
 - DB_USERNAME=root
 - DB_PASSWORD=admin
 - S2S_USERNAME=system-user
 - S2S_PASSWORD=admin

## Greencode
L'objectif est de réduire l'empreinte environnementale en minimisant la consommation des ressources matérielles (CPU, RAM, Stockage) et énergétiques (Réseau) sur l'ensemble du cycle de vie du logiciel.

#### Architecture Microservices : 
* Permet d'allumer et de ne faire travailler que les modules nécessaires, évitant de charger une application géante en mémoire vive.
#### Optimisation des types : 
* Remplacer des types de données lourds par des types plus simples (ex: utiliser un petit entier pour le genre au lieu d'une longue chaîne de texte).
#### Normalisation 3NF : 
* Structuration stricte de la base de données MySQL. L'élimination des redondances allège le volume de stockage matériel et diminue l'énergie nécessaire lors des requêtes.

### Plusieurs pistes d'amélioration ont été identifiées pour réduire l'empreinte énergétique et matérielle de l'application :

#### Efficience
* Introduire un système de cache pour les rapports de risque validés.
  L'absence de modification des notes cliniques bloque le déclenchement d'un nouveau scan des mots-clés, économisant les cycles CPU.

#### Gestion de la mémoire
* Exécuter un profilage via VisualVM ou JProfiler en environnement de test. L'objectif est d'identifier les fuites de mémoire (Memory Leaks) et d'optimiser l'allocation du "Heap" Java.
