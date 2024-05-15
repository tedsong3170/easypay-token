# 토큰시스템

## 1. ERD
## 1.1 결제시스템
![결제ERD.jpg](%EA%B2%B0%EC%A0%9CERD.jpg)

## 1.2 토큰시스템
![토큰ERD.jpg](%ED%86%A0%ED%81%B0ERD.jpg)

## 2. 기동방법
```shell
$ docker-compose -p payment up -d
```

- 결제시스템 : 8080 포트사용
- 토큰시스템 : 8081 포트사용
- 승인시스템 : 8082 포트사용
