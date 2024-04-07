# Sponsored Ads Project

Sponsored ads project for Criteo. <br><br>
NOTE:
<br>
Please pre-install JRE17 and MySQL server 8.0 <br>
if you want to run the project locally. <br>
or <br>
Docker engine version 25.0.3 <br>
if you want to run the project in a container.

# JAR build

Currently, no Maven profiles included.

    mvn clean install

# Spring Profiles

    default (local)
    staging (test)
    production
    Optional: swagger (enable swagger-ui)
    Optional: h2 (enable H2 in memory database)

# Run Locally

Create user in database. (MySQL server 8.0)

    username: sponsored-ads-service
    password: J3gfOpi94ShWmAOC3a6FFiles6s

Run with JRE 17.

    java -jar -Dspring.profiles.active=swagger

# Swagger-UI

SwaggerUI exposed on management port 8081:

    http://localhost:8081/actuator/swagger-ui/index.html

with the respected active spring profile.

# H2 Database Console

H2 in memory Database Console exposed on port 9006:

    http://localhost:8080/h2-console

with the respected active spring profile.

# API request examples

Create campaign

    curl -X 'POST' \
    'http://localhost:8080/seller/create-campaign?name=fruits%20ads&startDate=2024-06-20T12%3A30%3A10&bid=100' \
    -H 'accept: application/json' \
    -H 'Content-Type: application/json' \
    -d '[
            {
                "title": "banana",
                "category": "fruits",
                "price": 10.5
            },
            {
                "title": "apple",
                "category": "fruits",
                "price": 5.5
            },
            {
                "title": "orange",
                "category": "fruits",
                "price": 8.5
            }
        ]'

Serve ad

    curl -X 'GET' \
        'http://localhost:8080/ad/serve?category=fruits' \
        -H 'accept: application/json' 
