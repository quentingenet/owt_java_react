# OpenWeightTracker

***OpenWeightTracker (OWT)*** is a free and opensource application under Copyleft.
<br>This is a simple and easy way to have a look on your  weight ! 
It respect your data, and respect your privacy.

## Technologies

 **OWT** is developed, for the backend, in **Java(JDK17)** with the **framework Spring** with Spring Boot, Data/JPA, and Spring Security.
<br>Concerning front end, application is developed with **React**.


## To run this application...
For the moment, if you want to run project, you must import project's file in your favorite IDE and run it with the 'MainApp' class for the backend and run the app React for the frontend.
You should clone the main branch which is the most updated branch with the final version of OWT app.
In a first time, you must use the **"SignIn"** functionality to create your first user in the app. After doing it, you can login to the app with the user which has just been created and save your weights.


## Application build details : Database structure diagram
![MPD diagram](https://github.com/quentingenet/owt/blob/main/owt-back/docs/OWT_MCD.png)

## API routes description

### AUTHENTICATION

**Se connecter :**

**POST** /api/v1/login

### USER MANAGEMENT

**ADMIN ONLY - Obtenir la liste de tous les users inscrit :**

**GET** /api/v1/users/all

**Ajouter un nouvel user :**

**POST** /api/v1/users/add

**Supprimer un utilisateur :**

**DELETE** /api/v1/users/{idUser}

**Modifier les données initiale d'un user :**

**PATCH** /api/v1/users/update/initialdata/{idUser}

**Enclencher le reset son password par un envoi de mail à user après avoir renseigné son email :**

**POST** /api/v1/users/resetPassword

**Changer son mot de passe :**

**POST** /api/v1/users/changePassword

### HOME / DASHBOARD

**Consulter sa dashboard :**

**GET** /api/v1/home

### WEIGHTS

**Consulter les poids :**

**GET** /api/v1/weights/all

**Ajouter un poids :**

**POST** /api/v1/weights/

**Modifier un poids existant :**

**PATCH** /api/v1/weights/update/{idWeight}

**Supprimer un poids existant :**

**DELETE** /api/v1/weights/{idWeight}

## Ideas and possible improvement
There are possible improvements and ideas... Your suggestions are welcome !

## How to contribute ?
Contributions are welcome, and merge request also ! 
<br>Only a requirement, be a lover of free software and accept copyleft philosophy !

## Author
* Quentin GENET

