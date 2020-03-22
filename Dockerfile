FROM openjdk:11.0.6-jre
ARG JAR_FILE=*.jar
ENV PROFILE=default TZ=Asia/Shanghai JAVA_OPTS="-server -Xms1g -Xmx1g"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
WORKDIR /
ADD target/${JAR_FILE} /app.jar
ADD target/lib /lib
VOLUME /logs
EXPOSE 8080
ENTRYPOINT java ${JAVA_OPTS} -Djava.security.egd=file:/dev/./urandom -Dfile.encoding=utf-8 -Dspring.profiles.active=${PROFILE} -jar /app.jar
CMD []