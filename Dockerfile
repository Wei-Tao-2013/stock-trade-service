FROM openjdk:11.0-slim
WORKDIR /app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
COPY src ./src
RUN ./mvnw clean package
RUN ./mvnw dependency:go-offline
CMD ["java", "-jar", "target/stock-trade-service-1.0.jar" ]

