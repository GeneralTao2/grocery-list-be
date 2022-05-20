FROM amazoncorretto:11
WORKDIR /data/grocery-list
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} /data/grocery-list/app.jar
EXPOSE 8080
CMD ["java", "-jar","/data/grocery-list/app.jar"]
