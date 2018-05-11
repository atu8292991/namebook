git pull
mvn package docker:build -DskipTests=true
docker stop namebook
docker rm namebook
docker run -p 8080:8080 --name namebook -d -t atu/namebook