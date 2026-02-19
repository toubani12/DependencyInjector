# Rapport du TP : Injection des Dépendances

**Nom :** TOUBANI

**Prénom :** Badr

**École :** ENSET

**Professeur :** YOUSSFI Mohamed

**Module :** Systèmes Distribués

---

## 1. Introduction
Ce projet a pour objectif de comprendre et de mettre en œuvre le mécanisme d'inversion de contrôle (IoC) et l'injection de dépendances (DI) en Java. Le code source explore différentes approches pour coupler les composants d'une application, allant du couplage fort (instanciation statique) au couplage faible via la réflexion et enfin l'utilisation du framework Spring (XML et Annotations).

## 2. Architecture de l'application
L'application respecte une architecture en couches pour séparer les responsabilités :

*   **Couche DAO (Data Access Object) :** Responsable de l'accès aux données.
    *   `IDao` : Interface définissant le contrat (`getData`).
    *   `DaoImpl` : Implémentation standard (simulée).
    *   `DaoImplV2` : Une extension ou nouvelle version de l'implémentation.
*   **Couche Métier :** Responsable de la logique métier.
    *   `IMetier` : Interface définissant les traitements (`calcul`).
    *   `MetierImpl` : Implémentation qui dépend de l'interface `IDao` pour fonctionner.
*   **Couche Présentation :** Contient les classes "main" pour tester les différentes méthodes d'injection.

## 3. Analyse des Méthodes d'Injection

### 3.1. Méthode 1 : Instanciation Statique (Couplage Fort)
**Fichier :** Pres1.java

Dans cette version, l'application crée elle-même ses dépendances en utilisant le mot-clé `new`.
```java
DaoImplV2 dao = new DaoImplV2();
MetierImpl metier = new MetierImpl(dao); // Injection via le constructeur
```
*   **Analyse :** C'est une approche avec un couplage fort. Si l'on veut changer l'implémentation de `IDao` (par exemple passer de `DaoImpl` à `DaoImplV2`), il faut modifier le code source de la classe `Pres1`. Ce n'est pas idéal pour la maintenance et l'évolutivité.

### 3.2. Méthode 2 : Instanciation Dynamique (Réflexion)
**Fichiers :** Pres2.java et config.txt

Cette version utilise la réflexion Java pour charger les classes et injecter les dépendances dynamiquement à l'exécution.
*   Le fichier `config.txt` contient les noms complets des classes à instancier (`com.badhacker.dao.impl.DaoImpl`, etc.).
*   Le code lit ce fichier, charge la classe avec `Class.forName()`, et l'instancie.

```java
// Extrait de Pres2.java
Class cDao = Class.forName(daoClassName);
IDao dao = (IDao) cDao.newInstance();
// ... instanciation métier et injection ...
```
*   **Analyse :** Cette méthode permet un couplage faible. On peut changer l'implémentation utilisée simplement en modifiant le fichier texte de configuration, sans recompiler le code source. C'est une forme primitive d'injection de dépendances "maison".

### 3.3. Méthode 3 : Injection avec Spring (Version XML)
**Fichiers :** PresSpringXML.java et config.xml

Ici, le framework Spring est utilisé pour gérer le cycle de vie des objets. La configuration se fait dans un fichier XML.
*   Le fichier `config.xml` déclare les beans (`dao`, `metier`) et comment ils sont liés (injection par constructeur via `<constructor-arg>`).

```xml
<!-- Extrait de config.xml -->
<bean id="r" class="com.badhacker.dao.impl.DaoImpl"></bean>
<bean id="metier" class="com.badhacker.metier.impl.MetierImpl">
    <constructor-arg ref="r"></constructor-arg>
</bean>
```
*   **Analyse :** C'est une approche standard et robuste. Le code Java est totalement découplé de la configuration. Si l'on souhaite changer d'implémentation DAO, il suffit de changer le paramètre `class` dans le fichier XML.

### 3.4. Méthode 4 : Injection avec Spring (Version Annotations)
**Fichiers :** PresSpringAnotation.java, DaoImpl.java, DaoImplV2.java, MetierImpl.java

Cette version utilise les annotations modernes de Spring directement dans le code Java.
*   **Annotations utilisées :**
    *   `@Repository` sur les classes DAO (`DaoImpl`, `DaoImplV2`).
    *   `@Service` sur la classe métier (`MetierImpl`).
    *   `@Autowired` (et implicitement injection par constructeur) pour injecter le DAO dans le Métier.
    *   `@Qualifier("d2")` : Utilisé dans `MetierImpl` pour spécifier quelle implémentation de `IDao` injecter exactement, car il y en a deux disponibles.

```java
// Extrait de MetierImpl.java
public MetierImpl(@Qualifier("d2") IDao dao) {
    this.dao = dao;
}
```
*   **Analyse :** C'est la méthode la plus courante aujourd'hui. Elle est concise mais réintroduit pour partie la configuration dans le code source (via les annotations comme `@Qualifier`). Cependant, elle simplifie grandement le développement rapide.

## 4. Étapes de réalisation (Historique des Commits)


1. **Initialisation du projet**
   - Création du projet Maven avec le fichier `pom.xml`.
   - Configuration des fichiers ignorés (`.gitignore`) et de l'environnement de développement.

2. **Mise en place de l'architecture de base**
   - Création des interfaces `IDao` et `IMetier` pour définir les contrats.
   - Implémentation des classes concrètes `DaoImpl` et `MetierImpl`.
   - Établissement de la dépendance entre la couche métier et la couche DAO.

3. **Instanciation statique et préparation à l'instanciation dynamique**
   - Création d'une deuxième implémentation DAO (`DaoImplV2`) pour simuler un changement de besoin.
   - Création de la classe `Pres1` démontrant l'injection de dépendances par instanciation statique (couplage fort).
   - Création du fichier `config.txt` contenant les noms des classes pour préparer l'instanciation dynamique.

4. **Implémentation de l'instanciation dynamique**
   - Développement de la classe `Pres2` qui lit le fichier `config.txt`.
   - Utilisation de l'API de réflexion Java (`Class.forName()`, `newInstance()`, `getMethod()`, `invoke()`) pour instancier les objets et injecter les dépendances dynamiquement, réalisant ainsi un couplage faible "fait maison".

5. **Intégration de Spring avec configuration XML**
   - Ajout des dépendances Spring dans le fichier `pom.xml`.
   - Création du fichier de configuration `config.xml` pour déclarer les beans et leurs dépendances.
   - Création de la classe `PresSpringXML` pour charger le contexte Spring (`ClassPathXmlApplicationContext`) et récupérer le bean métier.

6. **Transition vers les annotations Spring**
   - Introduction des annotations Spring (`@Component`, `@Autowired`, `@Qualifier`) directement dans le code source (`DaoImpl`, `DaoImplV2`, `MetierImpl`).
   - Création de la classe `PresSpringAnotation` utilisant `AnnotationConfigApplicationContext` pour tester cette nouvelle approche.

7. **Affinement des annotations Spring**
   - Remplacement de l'annotation générique `@Component` par des stéréotypes plus sémantiques : `@Repository` pour la couche d'accès aux données et `@Service` pour la couche métier.
   - Mise à jour du fichier `config.xml` pour inclure le scan des composants (`<context:component-scan>`).

## 5. Conclusion
Ce TP a permis de démontrer comment l'architecture logicielle peut évoluer d'un système rigide (couplage fort) vers un système souple et modulaire (injection de dépendances). L'utilisation de Spring (XML ou Annotations) facilite considérablement la gestion des composants et permet de respecter le principe "Open/Closed" (ouvert à l'extension, fermé à la modification).