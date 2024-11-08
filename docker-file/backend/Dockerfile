# 리눅스이미지 준비, 자바17버전이 설치되어 있어야함
FROM openjdk:17-jdk-slim

# 호스트 OS상에 존재하는 프로젝트 내에 빌드된 jar 파일 컨테이너에 카피
COPY ./build/libs/*SNAPSHOT.jar /usr/app/app.jar

# 컨테이너 내에 작업 디렉토리 지정 => 위치 체크 않해도 됨
WORKDIR /usr/app

# 스프링부트의 서비스 포트 지정 : 8080
EXPOSE 8080

# 컨테이너가 구동되면, 자동으로 실행될 명령어
# 서버 가동
CMD [ "java", "-jar", "app.jar" ]