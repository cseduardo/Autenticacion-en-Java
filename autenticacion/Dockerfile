FROM eclipse-temurin:21-jdk-jammy AS builder
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY . .
RUN ./mvnw dependency:resolve
COPY src ./src
RUN ./mvnw package -DskipTests

# ARG JAVA_OPTS
# ENV JAVA_OPTS=$JAVA_OPTS
# COPY target/autenticacion-0.0.1-SNAPSHOT.jar autenticacion.jar
# EXPOSE 3000
FROM eclipse-temurin:21-jdk-jammy
WORKDIR /app
COPY --from=builder /app/target/*.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
# For Spring-Boot project, use the entrypoint below to reduce Tomcat startup time.
#ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -jar autenticacion.jar"]
