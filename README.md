## ```Spring boot(server-side) - react(client-side) - oauth2 - jwt``` [![Build Status](https://app.travis-ci.com/beomsun1234/springboot-react-oauth2-jwt.svg?branch=main)](https://app.travis-ci.com/beomsun1234/springboot-react-oauth2-jwt)

-----

- ### 클라이언트가 로그인을 요청 하면(구글,카카오, 네이버) 서버는 인증(로그인)이 성공한 클라이언트에게 jwt토큰 발급 후 토큰을 발급한다. 인증된 유저는 발급된 토큰을 가지고 인가된 api 접근
<br>


- ### ~~```Nginx 사용```~~
- nginx로 모든 client 요청이 들어옴(80포트로 유저와 통신) - /api 요청시 8080포트로 요청해서 해당 값을 가지고옴




- ### ~~```AWS EC2에 배포 완료(feat. AWS RDS)```~~

- ### ~~```travis ci```~~ 

    - travis ci로 빌드를 성고하기 위해 완전 삽질 했다.... 밑에 오류로 계속 삽질했다...

            계속 해당 오류가 발생했다....
            HelloOauth2JwtApplicationTests > contextLoads() FAILED
            java.lang.IllegalStateException at DefaultCacheAwareContextLoaderDelegate.java:132
            Caused by: org.springframework.beans.factory.UnsatisfiedDependencyException at ConstructorResolver.java:800
            Caused by: org.springframework.beans.factory.BeanCreationException at AutowiredAnnotationBeanPostProcessor.java:405
            Caused by: java.lang.IllegalArgumentException at PropertyPlaceholderHelper.java:180


        로컬 환경에서는 너무 잘되는데.. 이유가 무엇일까?... 로컬 환경에서 HelloOauth2JwtApplicationTests는 통과했는데 빌드시 해당 테스트를 통과 못한다... 임시방편으로 해당코드를 주석처리 하니 빌드가 성공했다...
            
        

- ### NEXT -> ```무중단 배포``` 

