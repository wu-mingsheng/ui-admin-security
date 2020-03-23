#!/usr/bin/env bash

set -u
set -e


# read -p "please input env profile:" -t 60 PROFILE

JAR_FILE="ui-admin-security-0.0.1-SNAPSHOT.jar"


sleep 3

# if [[ "${PROFILE}" == "dev" ]];then
#     echo "profile is dev"
# elif [[ "${PROFILE}" == "prod" ]];then
#     echo "profile is prod"
# else
#     echo "profile is err"
#     exit 2
# fi

## =================================================================
echo " ================== 开始maven打包: mvn clean package"

mvn clean package -Dmaven.test.skip=true

echo " ================== maven打包完成"
sleep 3

## ===========================================================================
IMAGE_NAME="springboot-admin"
IMAGE_ID=$(date +%Y%m%d%H%M)
IMAGE_REGISTRY="registry.cn-beijing.aliyuncs.com"
IMAGE_NAMESPACE="boe-com"
IMAGE_FULLNAME=$IMAGE_REGISTRY/$IMAGE_NAMESPACE/$IMAGE_NAME:$IMAGE_ID
echo "开始构建镜像: $IMAGE_FULLNAME"

# echo "qingclass@2019" | docker login registry.cn-beijing.aliyuncs.com -u 轻课海湾 --password-stdin
docker build -t $IMAGE_FULLNAME --build-arg JAR_FILE=$JAR_FILE .

echo " ================== 镜像构建完成"; sleep 3

## ============================================================================

echo " ================== 推送镜像到远程仓库"

REGISTRY_USERNAME="t_1516617822136_0451"
read -p "please input image registry password: " -t 60 REGISTRY_PASSWORD
# docker login
echo ${REGISTRY_PASSWORD:-"woms0613"} | docker login $IMAGE_REGISTRY -u $REGISTRY_USERNAME --password-stdin
docker push $IMAGE_FULLNAME


echo " ================== 镜像推送完成 ================== "


# docker push registry.cn-beijing.aliyuncs.com/prod-bigbay/$IMAGE_NAME:$IMAGE_ID

# docker run -d --rm -p 8080:8080 --name springboot-admin --link mysql:mysql -e MYSQL_HOST=mysql -e PROFILE=default -e JAVA_OPTS='-server -Xms1g -Xmx1g' domain/springboot-admin:${IMAGE_ID}








