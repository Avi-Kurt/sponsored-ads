FROM openjdk:17-alpine
ENV SPRING_PROFILES_ACTIVE="docker,swagger"
COPY target/sponsored-ads-0.0.1.jar sponsored-ads-0.0.1.jar
ENTRYPOINT ["java","-jar","/sponsored-ads-0.0.1.jar"]