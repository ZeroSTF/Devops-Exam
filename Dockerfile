# Utiliser une image de base Java
FROM openjdk:17-jdk-alpine

# Définir le répertoire de travail
WORKDIR /app

# Copier le JAR du répertoire target vers l'image Docker
COPY target/tp-foyer-5.0.0.jar app.jar

# Exposer le port de l'application
EXPOSE 8087

# Commande pour exécuter l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app/app.jar"]