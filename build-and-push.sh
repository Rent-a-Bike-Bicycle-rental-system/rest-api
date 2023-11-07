#!/bin/bash

IMAGE_NAME="rest-api"
TAG="dev"
DOCKERHUB_NAME="bicyclerentalsystem"

FULL_NAME="${DOCKERHUB_NAME}/${IMAGE_NAME}:${TAG}"

docker build -t "${FULL_NAME}" .
docker push "${FULL_NAME}"