# 프로젝트 구성
    - backend
        - https://start.spring.io/ 진입
        - demo => backend 입력수정(프로젝트명 변경)
        - Dependencies 에 라이브러리 추가(기본만)
            - Lombok
            - Spring Boot DevTools
            - Spring Web
            - Thymeleaf 
        - generator 버튼 클릭 > 다운로드 > 압축해제 > 해당 폴더 comspose 밑으로 이동
        - 참고
            - extension > springboot 검색 > VMware 에서 2개 설치
                - springboot extension...
                - springboot tools
                - MS것 추가 설치 가능
                    - spring 검색후 2개 설치
        - 기본 엔드 포인트 구성
        ```
            
        ```
    
    - frontend
        - 현재 위치 : ~/compose
        - npx create-react-app frontend
        - node_modules 때문에 git 업데이트 항목이 많아질수 있음
            - node_modules 삭제 -> 잠시후 -> git 업데이트값 수정
            - 실패한다면 => 캐싱 제거
            - /compose/frontend/.gitignore 복사
            - /.gitignore 붙여넣기
                ```
                    # 변경전
                    /node_modules
                    # 변경후
                    **/node_modules
                ```
        - cd /compose/frontend
        - npm install