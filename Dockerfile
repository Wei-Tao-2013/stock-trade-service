FROM openjdk:11.0-slim
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY src ./src
CMD ["./mvnw", "package"]
COPY target/stock-trade-service-1.0.jar ./
CMD  java -jar stock-trade-service-1.0.jar
