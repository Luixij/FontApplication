# Usar una imagen base de OpenJDK
FROM openjdk:17-jdk-alpine

# Establecer el directorio de trabajo
WORKDIR /app

# Copiar el archivo WAR generado en el contenedor
COPY target/font-0.0.1-SNAPSHOT.war /app/font.war

# Comando para ejecutar la aplicación
CMD ["java", "-jar", "/app/font.war"]
