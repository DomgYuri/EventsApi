# Event Registration API [![NPM](https://img.shields.io/npm/l/react)](https://github.com/DomgYuri/EventsApi/tree/main?tab=MIT-1-ov-file) 
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![MySQL](https://img.shields.io/badge/MySQL-4479A1?style=for-the-badge&logo=mysql&logoColor=white)

## Description

This API is built using Java with the Spring Boot framework and integrates with a MySQL database. It allows the creation of events and user registration for those events. Additionally, users can register using a referral link that contains the ID of an already registered user.

## Technologies Used
* Java 17+
* Spring Boot
* Spring Data JPA
* MySQL

## Database configuration
1. Import

## Endpoints
### Create events
### POST/events

- Creates an event in the database, and as a return, a pretty name is generated.
```
{
  "title": "BootCamp React",
  "location": "Online",
  "price": 0.0,
  "startDate": "2025-03-15",
  "endDate": "2025-03-20",
  "startTime": "19:00:00",
  "endTime": "21:00:00"
}
```
## Response
- As a response, we have a new attribute called prettyname, which we will use to perform queries in the database.
```
{
    "id": 7,
    "title": "BootCamp React",
    "prettyName": "bootcamp-react",
    "location": "Online",
    "price": 0.0,
    "startDate": "2025-03-15",
    "endDate": "2025-03-20",
    "startTime": "19:00:00",
    "endTime": "21:00:00"
}
```





