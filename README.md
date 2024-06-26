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

Run with JRE 17:

    java -jar "-Dspring.profiles.active=swagger" .\sponsored-ads-0.0.1.jar

# Run with Docker

Run with Docker engine 25.0.3:

    docker-compose.yml

# Swagger-UI

SwaggerUI exposed on management port 8081:

    http://localhost:8081/criteo/api/v1/management/actuator/swagger-ui/index.html

with the respected active spring profile.

# H2 Database Console

H2 in memory Database Console exposed on port 9006:

    http://localhost:8080/criteo/api/v1/management/h2-console

with the respected active spring profile.

# API request examples

Create campaign request:

    curl -X 'POST' \
    'http://localhost:8080/criteo/api/v1/seller/create-campaign?name=snacks&startDate=2024-04-14T12%3A30%3A22&bid=400' \
    -H 'accept: application/json' \
    -H 'Content-Type: application/json' \
    -d '[
            {
                "title": "bamba",
                "category": "snacks",
                "price": 20.0
            },
            {
                "title": "cheetos",
                "category": "snacks",
                "price": 22.5
            },
            {
                "title": "chocolate",
                "category": "snacks",
                "price": 30.1
            },
            {
                "title": "krembo",
                "category": "snacks",
                "price": 8.9
            }
    ]'

Create campaign response:

    {
        "payload": {
        "name": "snacks",
        "startDate": "2024-04-14 12:30:22",
        "endDate": "2024-04-24 12:30:22",
        "bid": 400,
        "products": [
                        {
                            "title": "bamba",
                            "category": "snacks",
                            "price": 20,
                            "serialNumber": "88fbd51e-f5ac-11ee-b6e2-00e04c682031"
                        },
                        {
                            "title": "cheetos",
                            "category": "snacks",
                            "price": 22.5,
                            "serialNumber": "88fbdd80-f5ac-11ee-b6e2-00e04c682031"
                        },
                        {
                            "title": "chocolate",
                            "category": "snacks",
                            "price": 30.1,
                            "serialNumber": "88fbec75-f5ac-11ee-b6e2-00e04c682031"
                        },
                        {
                            "title": "krembo",
                            "category": "snacks",
                            "price": 8.9,
                            "serialNumber": "88fbf9a7-f5ac-11ee-b6e2-00e04c682031"
                        }
                    ]
        },
        "code": 0
    }

Serve ad request:

    curl -X 'GET' \
        'http://localhost:8080/criteo/api/v1/ad/serve?category=snacks' \
        -H 'accept: application/json' 

Serve ad response:

    {
        "payload": {
            "title": "bamba",
            "category": "snacks",
            "price": 20,
            "serialNumber": "88fbd51e-f5ac-11ee-b6e2-00e04c682031"
        },
        "code": 0
    }