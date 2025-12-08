# JAVA-Raytracer

Moteur de lancer de rayons en Java : support des formes de base (sphère, plan, triangle), éclairages directionnels et ponctuels, matériaux diffus et spéculaires, ombres portées et rendu intégration-testé.

## Sommaire
- Présentation
- Prérequis et installation (Java, Maven)
- Cloner et compiler
- Lancer les tests
- Exécuter le raytracer
- Comparer des images (outil `imgcompare`)
- Arborescence utile
- Dépannage rapide

## Présentation
Le projet fournit :
- Tracé de rayons avec sphères, plans et triangles
- Éclairage Lambert + Blinn-Phong, ombres douces via décalage epsilon
- Parser de scènes texte validant la cohérence des paramètres
- Intégration testée de bout en bout (pipeline caméra → image)

## Prérequis et installation
- JDK 17 ou plus récent
- Maven 3.8.9 ou plus récent

### Installer Java (Windows)
1. Téléchargez un JDK 17 (Temurin ou Oracle) et installez-le.
2. Ajoutez `JAVA_HOME` vers le dossier d’installation et ajoutez `%JAVA_HOME%\bin` au `PATH`.
3. Vérifiez :
```powershell
java -version
```

### Installer Maven (Windows)
1. Téléchargez l’archive sur https://maven.apache.org/download.cgi et décompressez-la (ex. `C:\dev\apache-maven-3.9.9`).
2. Ajoutez `%MAVEN_HOME%\bin` au `PATH` (`MAVEN_HOME` pointant vers le dossier Maven).
3. Vérifiez :
```powershell
mvn -v
```

## Compiler après extraction
Après avoir extrait l’archive ZIP dans un dossier, ouvrez un terminal dans ce dossier puis lancez :
```powershell
mvn clean compile
```

## Lancer les tests
Tests unitaires et d’intégration (math, formes, parser, pipeline de rendu) :
```powershell
mvn test
```
Exécuter un test précis :
```powershell
mvn test -Dtest=SphereTest
mvn test -Dtest=RendererIntegrationTest
```
Couverture (JaCoCo) :
```powershell
mvn clean test jacoco:report
```
Rapport : `target/site/jacoco/index.html`.

## Exécuter le raytracer
Lancement interactif (demande les chemins et paramètres) :
```powershell
mvn -Dexec.mainClass=raytracer.Main exec:java
```
Exemple de scènes fournies : `src/main/resources/jalon2/test1.scene` (et `test2.scene`, … `test7.scene`).

## Comparer des images (outil `imgcompare`)
Permet de comparer deux images (référence vs. rendu) :
```powershell
mvn -Dexec.mainClass=imgcompare.Main exec:java
```
Suivez les invites pour fournir les chemins des deux images.

## Arborescence utile
```
src/
  main/java/raytracer/
    RayTracer.java        (coeur du moteur)
    Renderer.java         (rendu image)
    Scene.java            (gestion scène, shading)
    math/                 (Point, Vector, Color, AbstractVec3)
    shape/                (Shape, Sphere, Plane, Triangle)
    light/                (AbstractLight, DirectionalLight, PointLight)
    parsing/              (SceneFileParser, SceneParseException)
  main/java/imgcompare/   (outil de comparaison d’images)
  resources/jalon2/*.scene (scènes d’exemple)
  test/java/              (tests JUnit)
```

## Dépannage rapide
- Maven ne trouve pas Java : vérifiez `java -version` et que `JAVA_HOME` et `PATH` pointent vers le JDK, pas un JRE.
- Erreur de téléchargement Maven : vérifier le proxy ou réessayer plus tard (`mvn -U` pour forcer la mise à jour des dépendances).
- Tests qui échouent : relisez la trace dans le terminal, repérez le test concerné, ajustez la scène ou le code selon le message.