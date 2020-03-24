### 胖jar方式
## FROM openjdk:11.0.6-jre
#FROM openjdk:8-jdk-alpine
## RUN addgroup -S spring && adduser -S spring -G spring
## USER spring:spring
#ARG JAR_FILE=build/libs/*.jar
#ENV PROFILE=default TZ=Asia/Shanghai JAVA_OPTS="-server -Xms1g -Xmx1g"
#RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#WORKDIR /
#ADD ${JAR_FILE} /app.jar
#VOLUME /logs
#EXPOSE 8080
#ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=utf-8 -Dspring.profiles.active=${PROFILE} -jar /app.jar
#CMD []

### 分离依赖和资源

FROM openjdk:8-jdk-alpine AS builder
WORKDIR target/dependency
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} app.jar
RUN jar -xf ./app.jar

FROM openjdk:8-jre-alpine
ARG DEPENDENCY=target/dependency
ENV PROFILE=default
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms1g -Xmx1g"
ENV MAIN_CLASS=com.boe.admin.uiadmin.UiAdminApplication
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
WORKDIR /
COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
VOLUME /app/logs
EXPOSE 8080
ENTRYPOINT java -server ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=utf-8 -Dspring.profiles.active=${PROFILE} -cp app:app/lib/* ${MAIN_CLASS}
CMD []











