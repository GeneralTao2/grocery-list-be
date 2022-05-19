FROM amazoncorretto:11
WORKDIR /data/grocery-list
COPY target/spring_grocery_shop-1.0.0.jar /data/grocery-list
EXPOSE 8080
CMD ["java", "-jar","/data/grocery-list/spring_grocery_shop-1.0.0.jar"]