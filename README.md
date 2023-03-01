### 실행방법

intellij에서 gradle로 빌드하여 실행하거나

프로젝트 폴더에서 **gradlew build** 명령어로 빌드한후

\build\libs 폴더내에서 jar 파일을 실행합니다. 



기본은 default 설정으로 실행되며 h2 DB로 실행됩니다.(dev, test도 동일.)

production 은 java -jar -Dspring.profiles.active=prod 로 실행하며 MySQL로 실행됩니다.

application.yaml에 설정값이 있습니다.



### 구현

여행, 도시의 CRUD API와 사용자별 도시를 조회하는 API를 구현하였습니다



### 구조

기본적으로 도메인을 중심으로 패키지구조를 나누었고 

여행과 도시의 연관관계가 다대다의 관계라고 생각하여

중간에 CItyTravel이라는 연결 테이블을 두어 일대다의 관계로 나누었습니다.

DB 맵핑은 JPA를 사용하였습니다.



### 테스트케이스

테스트는 컨트롤러, 서비스, 레포지토리로 각각 나누어 단위테스트를 진행하였습니다.





