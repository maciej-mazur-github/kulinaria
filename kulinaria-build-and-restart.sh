docker build . -t kulinaria
docker stop kulinaria || true
docker rm kulinaria || true
docker run -d -p 8081:8080 --network ofertownia-network --restart unless-stopped kulinaria