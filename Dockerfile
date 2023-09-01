FROM openjdk:11-jdk

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /token

# Copia el JAR de tu aplicación al contenedor
COPY target/token-pensiones-0.0.1-SNAPSHOT.jar app.jar

# Expone el puerto en el que se ejecuta tu aplicación Spring Boot
EXPOSE 8080

# Comando para iniciar tu aplicación cuando se ejecute el contenedor
CMD ["java", "-jar", "app.jar"]