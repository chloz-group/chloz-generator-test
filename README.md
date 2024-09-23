# Chloz test
## Standalone execution
### Run the application
```bash
./gradlew bootRun --args='--spring.profiles.active=swagger,test'
```
### Build & run the application artefact
Use the following command to build the application
```bash
./gradlew build
```
Use the following command to run the application
```bash
java -jar app/build/libs/app-1.0.0.jar --spring.profiles.active=swagger,test
```
## Docker 
### Build a Docker Image
```bash
./gradlew bootBuildImage --imageName=chloztest
```
### Run the docker container
```bash
docker container run -d -p 8488:8488 -e "SPRING_PROFILES_ACTIVE=swagger,test" --name chloztest -t chloztest
```
### Stop the container
```bash
docker container stop chloztest
```
### View the container logs
```bash
docker container logs -f chloztest
```
