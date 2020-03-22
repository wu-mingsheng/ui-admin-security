#！/bin/bash

set -u
set -e


# read -p "please input env profile:" -t 60 PROFILE

JAR_FILE="ui-admin-security-0.0.1-SNAPSHOT.jar"

IMAGE_DOMAIN="domain"
IMAGE_NAME="springboot-admin"
IMAGE_ID=$(date +%Y%m%d%H%M)

# if [[ "${PROFILE}" == "dev" ]];then
#     echo "profile is dev"
# elif [[ "${PROFILE}" == "prod" ]];then
#     echo "profile is prod"
# else
#     echo "profile is err"
#     exit 2
# fi

mvn clean package -Dmaven.test.skip=true
# echo "qingclass@2019" | docker login registry.cn-beijing.aliyuncs.com -u 轻课海湾 --password-stdin
docker build -t $IMAGE_DOMAIN/$IMAGE_NAME:$IMAGE_ID --build-arg JAR_FILE=$JAR_FILE .
# docker push registry.cn-beijing.aliyuncs.com/prod-bigbay/$IMAGE_NAME:$IMAGE_ID

# docker run -d --rm -p 8080:8080 --name springboot-admin --link mysql:mysql -e MYSQL_HOST=mysql -e PROFILE=default -e JAVA_OPTS='-server -Xms1g -Xmx1g' domain/springboot-admin:${IMAGE_ID}








