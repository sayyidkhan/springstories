# springstories
Bookstore Backend built with Java Springboot Application
Built with:

- Java 17
- Java Spring 3.3.1
- MariaDB

===

## Pre-requisites:

- Docker
- Java 17
- Maven installed and configured
- IntelliJ 2020 and above

## How to Install Dependencies Before Working on the App

### PART 1: Installing and Running the MariaDB Database Instance
1. Git clone from: [https://github.com/sayyidkhan/springstories](https://github.com/sayyidkhan/springstories)
2. After downloading, assuming that Docker is installed, open a terminal and navigate into the `docker/mariaDB` folder, don't do anything yet.
3. Navigate into the `docker/DOCKER_README.md`
   3a. Follow the instructions to "start the MariaDB database"
   3b. If you would like to do other activities with the database, you may refer to the other documentation steps.

### PART 2: Installing Java Spring Boot and Running the Application
1. Open another terminal, in the root directory of the project, assuming that Maven is installed, run `mvn clean install` in the current directory. This will clean all existing Maven repositories and install all the relevant dependencies for the project.
2. To start the Spring Boot application from the command line, run:
   ```bash
   mvn spring-boot:run
