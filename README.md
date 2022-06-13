# App_SpringSecurity_MySQL_Thymleaf_BootStrap : Une application de démonstration de Spring Security(authentification, role), de thymeleaf, de BootStrap etc 

## Spring-Boot: <version>2.6.8</version>
## Spring-Security: <version>5.6.5</version>

### @Configuration
### @EnableWebSecurity
### SpingSecurityConfige --> extends WebSecurityConfigurerAdapter

## Démarrage d'Application 
## Étape 1: Cloner le code sur la branche `app`
## Étape 2: Configurer `application.properties` (url, username et password --> la connexion avec la bdd) 
### Étape 2: Créé une BDD avec MySQL(ou avec un autre), puis exécuter les requêtes dans `schema.sql` (dasn le dossier ressource/data) pour créer les tables
### Étape 3: Exécuter les requêtes dasn `data.sql` (dasn le dossier ressource/data) pour insérer les données 

### Les mots de passe enregistrés dans la base de données, sont cryptés (BCryptés), tous pareil: un, deux, trois et quatre, en chiffre arabique sans espace entre eux

### Captures d'écran de l'app 

![listPatients](https://user-images.githubusercontent.com/90509456/173341338-6c610bec-78b9-412f-a09a-0c77bce204b4.jpg)
![createNewPatient](https://user-images.githubusercontent.com/90509456/173341382-abd5ce05-f899-4df5-8dfc-1ba81d9cbdbd.jpg)
![AppUser](https://user-images.githubusercontent.com/90509456/173341404-4a08188f-d9f5-490d-9765-87924e1447ad.jpg)
![listRole](https://user-images.githubusercontent.com/90509456/173341429-7787b7e0-1104-4153-bb64-a8fac40275e3.jpg)
![createNewAppUser](https://user-images.githubusercontent.com/90509456/173341451-9bac9803-884d-4d97-973a-75f0b698f26e.jpg)
![AssociationRole_User](https://user-images.githubusercontent.com/90509456/173341484-83ef6e2d-5704-4b99-a6ee-d3a5f2958be6.jpg)
![AssociationRole_User_2](https://user-images.githubusercontent.com/90509456/173341505-22c51d34-ac85-4632-a6e6-dc8a4de86262.jpg)
