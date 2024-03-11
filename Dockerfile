FROM openjdk:17-alpine
WORKDIR /app
COPY target/system-management-0.1.jar /app/system-management-0.1.jar
EXPOSE 9080
CMD ["java", "-jar", "system-management-0.1.jar"]