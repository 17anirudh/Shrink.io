
# shrink.io
Full Stack application which shortens any working website link, into a short link (currently offline due to budget)

## Overview
shrink.io is a full stack web application built with the SVFM stack (Spring Boot with Thymeleaf, Vanilla JS, Flask with SQLAlchemy, and MySQL). It shortens long URLs into encrypted short links using UUID + Base62 key generation and redirects users to the original URL.

This is fully deployed in cloud PaaS [Railway](https://railway.com), and is cloud-native, containerized microservices having shared persistant mySQL database.

## Screenshot
![App Screenshot](/shrink.io.png)

## Features
- Web interface built using Spring Boot (Thymeleaf) and Vanilla JS for smooth user experience
- Uses UUID + Base62 encoding for fast, non-sequential short URL generation
- Lightweight Flask framwork handles redirection independently
- Dockerized and deployed on Railway with persistent MySQL backend

## Flowchart
![Flowchat](/shrink.io%20flowchart.png)

## Tech Stack
- Data Processing: UUID, Base62
- Frontend: Vanilla JS (along with HTML, CSS)
- Database: MySQL
- Backend: Spring-boot and Flask
- Deployment: Docker and Railways


## Installation
You can install either from github or locally pull from online docker image

## 1. Through Github  
#### Prerequisites:
-  Java 21 (openjdk 21.0.7, precisely) installed
-  Python 3 (Python 3.13.3, precisely) installed 
- Git (for installation)
```dir
  git clone https://github.com/17anirudh/Shrink.io.git
```
- __Firstly,__ install required modules and then run the  application which will run in  (No need to access/open it)
- __⚠️ IMPORTANT:__ Do not alter the port number manually for flask, it needs to be deafult [port:5400](localhost:5400)

  ```dir
  pip install -r flask/requirements.txt
  python flask/app.py
  ```
- __Secondly,__ we run our main spring-boot app which uses an embedded __tomcat__ web server running in [port:8100](localhost:8100)
  ```dir
  cd spring
  ```
  ```spring
  ./mvnw spring-boot:run
  ```
  If you want to run in a different port, then
  ```spring
  ./mvnw spring-boot:run -Dspring-boot.run.arguments="--server.port=<enter-your-port-number>
  ```
- To close/stop the apps
  ```dir
  Just type Ctrl+C in both terminals
  ```

## 2. Through Docker
#### Prerequisites:
-  Docker 

These are the 2 images you have to use
- [17veed/sql-spring:offline](https://hub.docker.com/repository/docker/17veed/sql-spring/general)
- [17veed/sql-flask:1.0](https://hub.docker.com/repository/docker/17veed/sql-flask/general)


Using __docker run__
  - You can remove -d to see live logs
  - You can assign a different port in <your-desired-port> only for spring-boot
  - __⚠️ IMPORTANT:__ Do not change the port number for FLASK-IMAGE, always keep it [localhost:5400](localhost:5400) 

  ```bash
  docker run 17veed/sql-spring:offline -d -p <your-desired-port>:8100 --name <image-name-spring>
  docker run 17veed/sql-flask:1.0 -d -p 5400:5400 --name <image-name-flask>
  ```
Using __docker pull__ 
```bash
docker pull 17veed/sql-spring:offline
docker pull 17veed/sql-flask:1.0
docker run <image-id1> -d -p <your-desired-port>:8100 --name <image-name-spring>
docker run <image-id2> -d -p 5400:5400 --name <image-name-flask>
```
  - To stop and remove the containers and images
  ```bash
  docker stop <container-id1> <conatiner2>
  docker container prune -f
  docker rmi <image-name-flask> <image-name-spring>
  ```
  docker container prune removes all (also other non-project related) conatiners, therefore, you can use docker rm instead of _docker container prune -f_
  ```bash
  docker stop <container-id1> <conatiner2>
  docker rm <container-id1> <conatiner2>
  docker rmi <image-name-flask> <image-name-spring>
  ```


