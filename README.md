# Installation

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
  - __⚠️ IMPORTANT:__ Do not change the port number for FLASK-IMAGE, always keep it [localhost:5000](localhost:5400) 

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

## Screenshots
![App Screenshot](/shrink.io.png)

