#!/usr/bin/env bash

set -u
set -e






## =================================================================
echo " ================== 开始打包";sleep 3

# mvn clean package -Dmaven.test.skip=true
gradle build
echo " ================== 打包完成"


## ===========================================================================
JAR_FILE="build/libs/*.jar"
IMAGE_NAME="springboot-admin"
IMAGE_ID=$(date +%Y%m%d%H%M)
IMAGE_REGISTRY="registry.cn-beijing.aliyuncs.com"
IMAGE_NAMESPACE="boe-com"
IMAGE_FULLNAME=$IMAGE_REGISTRY/$IMAGE_NAMESPACE/$IMAGE_NAME:$IMAGE_ID
echo "开始构建镜像: $IMAGE_FULLNAME"; sleep 3


docker build -t $IMAGE_FULLNAME --build-arg JAR_FILE=$JAR_FILE .

echo " ================== 镜像构建完成"

## ============================================================================

echo " ================== 推送镜像到远程仓库"; sleep 3

REGISTRY_USERNAME="t_1516617822136_0451"
read -p "please input image registry password: " -t 60 REGISTRY_PASSWORD
# docker login
echo ${REGISTRY_PASSWORD:-"woms0613"} | docker login $IMAGE_REGISTRY -u $REGISTRY_USERNAME --password-stdin
docker push $IMAGE_FULLNAME


echo " ================== 镜像推送完成 ================== ";sleep 3

## =====================================================================

echo " ========================== 删除本地none镜像"

docker rmi $(docker images | grep "none" | awk '{print $3}')
# docker rmi $IMAGE_FULLNAME
echo " ============================ 本地none镜像删除完成"







