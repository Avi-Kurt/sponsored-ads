# Sponsored Ads Project

Sponsored ads project for Criteo.

# JAR build

Currently, no Maven profiles included.

    mvn clean install

# Spring Profiles

    default (local)
    staging (test)
    production
    Optional: swagger (enable swagger-ui)
    Optional: h2 (enable H2 in memory database)

# Run

Make sure that you have JRE 17 and MySQL server 8.0 pre-installed.

    java -jar -Dspring.profiles.active=swagger,h2

# Swagger-UI

SwaggerUI exposed on management port 8081:

    http://localhost:8081/actuator/swagger-ui/index.html

with the respected active spring profile.

# H2 Database Console

H2 in memory Database Console exposed on port 9006:

    http://localhost:9006/h2-console

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
