# 개요
    - https://docs.docker.com/engine/reference/builder/

# 무작정 따라하기
    - 스프링부트용 도커파일 구성
        - basic 형태, 빌드된 상황에서 이미지 구성
        - step 1 : 프로젝트 준비
            - compose/backend를 복사하여 docker-file/backend 위치

        - step 2 : 빌드             
            - 프로젝트 하위로 이동
                - cd ~/docker-file/backend
            - ./gradlew clean build
        
        - step 3 : Dockerfile 생성
            - ~/backend/Dockerfile
            ```
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
            ```
        - 도커 이미지 생성 (커스텀 이미지)
            - 현재 위치
                - ~/docker-file/backend
            - 이미지 생성
                - docker build -t <이미지명> <Dockerfile의위치>
                - docker build -t backend .
            - 컨테이너 구성
                - docker run -itd -p 8080:8080 --name backend backend
            - 접속확인(브라우저)
                - http://localhost:8080/api/test/home