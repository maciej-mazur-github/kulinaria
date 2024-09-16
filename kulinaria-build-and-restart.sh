docker build . -t kulinaria
docker stop kulinaria || true
docker rm kulinaria || true
docker run -d -p 8081:8080 --name=kulinaria -e SPRING_PROFILES_ACTIVE=docker-dev --network ofertownia-network --restart unless-stopped kulinaria
docker cp ./images kulinaria:./images/