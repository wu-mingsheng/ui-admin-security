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

### 分离依赖和资源(老方式)

#FROM openjdk:8-jdk-alpine AS builder
#WORKDIR target/dependency
#ARG JAR_FILE=build/libs/*.jar
#COPY ${JAR_FILE} app.jar
#RUN jar -xf ./app.jar
#
#FROM openjdk:8-jre-alpine
#ARG DEPENDENCY=target/dependency
#ENV PROFILE=default
#ENV TZ=Asia/Shanghai
#ENV JAVA_OPTS="-Xms1g -Xmx1g"
#ENV MAIN_CLASS=com.boe.admin.uiadmin.UiAdminApplication
#RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
#WORKDIR /
#COPY --from=builder ${DEPENDENCY}/BOOT-INF/lib /app/lib
#COPY --from=builder ${DEPENDENCY}/META-INF /app/META-INF
#COPY --from=builder ${DEPENDENCY}/BOOT-INF/classes /app
#VOLUME /app/logs
#EXPOSE 8080
#ENTRYPOINT java -server ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=utf-8 -Dspring.profiles.active=${PROFILE} -cp app:app/lib/* ${MAIN_CLASS}
#CMD []


### 分离 新姿势

FROM adoptopenjdk:11-jre-hotspot as builder
WORKDIR application
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} application.jar
RUN java -Djarmode=layertools -jar application.jar extract


FROM adoptopenjdk:11-jre-hotspot
ENV PROFILE=default
ENV TZ=Asia/Shanghai
ENV JAVA_OPTS="-Xms1g -Xmx1g -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=."
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
WORKDIR application
COPY --from=builder application/dependencies/ ./
COPY --from=builder application/snapshot-dependencies/ ./
COPY --from=builder application/spring-boot-loader/ ./
# COPY --from=builder application/resources/ ./
COPY --from=builder application/application/ ./
VOLUME /application/logs
EXPOSE 8080
EXPOSE 45679
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -Dspring.profiles.active=${PROFILE} -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=utf-8 org.springframework.boot.loader.JarLauncher"]
CMD []








