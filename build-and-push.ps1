$IMAGE_NAME="bicyclerentalsystem-rest-api"
$TAG="dev"
$DOCKERHUB_NAME="baffepok"

$FULL_NAME="${DOCKERHUB_NAME}/${IMAGE_NAME}:${TAG}"

docker build -t $FULL_NAME .
docker push $FULL_NAME
