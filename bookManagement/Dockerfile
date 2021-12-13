FROM openjdk

WORKDIR /src

COPY target/bookManagement-0.0.1-SNAPSHOT /src/bookManagement.jar

ENTRYPOINT ["java", "-jar", "bookManagement.jar"]