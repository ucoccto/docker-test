# 기본명령
    - 클라이언트/서버 측이 채택한 아킥텍쳐를 보여줌
        - docker version
            - 클라이언트(데탑용), 서버(도커엔진, 내부에존재, 리눅스)
    - 도커에 현재 현황 정보 제공 (실행환경, 컨테이너수, 도커 버전, 스토리,..)
        - docker system info
        - 디스크 이용 상황
            - docker system df

# 로그인/로그아웃
    - docker login
    - docker logout
    - 사용법 알려줘 
        - docker --help

# 이미지
    - 도커 허브로부터 컨터이너를 생성할수 있는 이미지를 관리(push/pull/rm/build/...)
    - syntax
        - docker image 2차명령어 [옵션] 이미지명[:태그명]
        - 옵션, 태그명(버전표기가 된) 생략가능
    
    - 허브에서 해당 이미지를 검색하여 로컬PC(도커 클라이언트가존재하는)에 다운
        - 아래 명령은 결과가 동일함 태그 생략 => :latest
        - docker image pull ubuntu
        - docker image pull ubuntu:latest

    - pull한 이미지 목록 확인
        - docker image ls
        - docker image ls -a
        - 고유 식별값 확인
            - docker image ls -a --digests
            - 값을 자르지 말고 있는 그대로 다 보여줘라
            - docker image ls -a --digests --no-trunc

    - 이미지 상세(inspect) 정보 보기
        - docker image inspect 이미지[:태그]
        - docker image inspect ubuntu

    - 도커 허브내 검색 -> 이미지 찾기
        - docker search [옵션] <키워드>
        - docker search nginx
        - 탑 3만 보여줘
        - docker search nginx --limit 3
    
    - 이미지 삭제
        - docker image rm ubuntu
    
    - 이미지 생성
        - 기존 이미지를 기반으로 생성
            - 이미지 다운로드 -> nginx 
                - docker image pull nginx
            - 생성
                - docker image tag 이미지명 <도커허브상사용자명=아이디>/커스텀이미지명:버전
                - docker image tag nginx edu1ucoc/nginx_v:1.0

        - 기존 이미지를 기반으로 컨테이너를 생성 -> 커스텀작업 -> 이미지 생성
        - 도커 파일을 통해서 생성

    - 이미지 도커 허브에 배포
        - docker image push <도커허브상사용자명=아이디>/커스텀이미지명:버전
        - docker image push edu1ucoc/nginx_v:1.0


# 컨테이너
    - 개요
        - 이미지를 기반으로 스냅샷을 생성하면 => 이것이 컨테이너가 된다
        - 구동/운영이 가능한 상태
        - 삭제 수행시 컨테이너 내부 모든 데이터는 삭제
            - 볼륨혹은 cp 명령을 통해서 외부로 백업, 관리 가능함
            - 볼륨 섹션에서 확인

    - 일반절차
        - 이미지 pull -> 컨테이너 create -> start -> 사용 -> stop -> rm
        - steps 
            - 대상 이미지는 nginx
            - 이미지 pull
                - docker image pull nginx
            - 컨테이너 create
                - docker container create -p 80:80 --name web nginx
            - 컨테이너 start
                - 실행중인 컨테이너 확인
                    - docker container ps
                - 가동
                    - docker container start web
                    - 브라우저 접속 -> http://localhost
                    - 가동 확인
                        - docker container ps 
                - 명령어에서 컨테이너로 진입하는 input/output 설정 X
                    - 도커 클라이언트 > 컨테이너 > nginx 클릭 > exec 진입
                        - 프록시 관련 설정 확인 -> 나중에 도커파일/도커컴포즈에서 조정 예상(nginx<->frontend, nginx<->backend)
                        ```
                            # cat /etc/nginx/conf.d/default.conf                             
                        ```

            - 컨테이너 stop
                - docker container stop web

            - 컨테이너 로그
                - docker container logs web

            - 컨테이너 rm
                - docker container rm web


    - 올인원 방식 -> run
        - 포그라운드 : 터미널이 중단되면 컨테이너 가동도 중단된다
            - docker container run -it -p 80:80 --name web nginx
                - -i : 입력단, stdin
                - -t : 출력단, tty
                - -p : 호스트에서접근포트:컨테이너내부에서사용
                - 터미널 중단 => 컨테이너 가동 중단
                - 다시가동(start) => 입출력이 보이진 않는다(백그라운드)
                    - 컨테이너 접속
                        - docker container exec -it web bash
                        ```
                            root@d74c73596282:/# 
                        ```
        - (*)백드라운드 => -d
            - docker container run -itd -p 80:80 --name web nginx
            - 컨테이너 접근 
                - docker container exec -it web bash
            - 컨테이너 상세정보(inspect)
                - docker container inspect web
                ```
                    # json 형태로 각종 정보, 이미지, 마운트, 네트워크등등 제공
                    [
                        ... 
                    ]
                ```

# 네트워크
    - 도커 컴포즈 구성시 각 컨테이들은 모두 같은 네트워크로 상에 구성되게 조정
        - 내부 ip로 상호관 연동 가능

    - 기본적으로 3개의 네트워크가 지원
        - docker network ls

        ```
            c:\Users\user\Documents\ci_cd_projects\docker>docker network ls
            NETWORK ID     NAME      DRIVER    SCOPE
            1c7f63fbf270   bridge    bridge    local
            9a7d7ee53eb2   host      host      local
            abc7227300b8   none      null      local
        
            - docker container inspect web
            ```
                ...
                "Networks": {
                    "bridge": {   <= 네트워크 관련 내용
                        "IPAMConfig": null,
                        "Links": null,
                ...
            ```
    
    - 커스텀 생성 
        - docker network create --driver=bridge web-net
        - 해당되는 네트워크만 출력
            - docker network ls --filter driver=bridge
    
    - 네트워크를 컨테이너에 연결
        - 방법
            - 컨테이너 생성시 지정
                - docker run .... --net <네트워크명> ...
            - 컨테이너 생성후 연결
    
    - 컨테이너 생성후 연결 테스트
        - docker network connect [옵션] <네트워크> <컨테이너>
            - docker network connect web-net web
        - 네트워크 정보 확인
            - docker container inspect web
            - 기존 정보 존재, 신규 네트워크 추가
        - 연결해제
            - docker network disconnect bridge web
            - 정보확인
                - docker container inspect web
    - 네트워크 제거
        - docker network disconnect web-net web
        - docker container inspect web
        - docker network rm web-net
        - 원래 존재하던 네트워크 추가
            - docker network connect bridge web

# 볼륨