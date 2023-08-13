# AWS S3 Put object with localstack

testing AWS S3 Put object with localstack
logstack으로 로컬환경에서 AWS S3 sdk로 구현된 기능을 통합테스트합니다.

## Prerequisites

- docker
- docker compose
- java
- nodejs

## How to run

### Run localstack with docker compose
```bash
cd docker && docker-compose up -d
```
If the compose script successfully is executed, then localstack is running on http://localhost:4566.

You can see the log like below when remove the -d option.
```bash
s3_localstack  | LocalStack version: 2.2.1.dev
s3_localstack  | LocalStack Docker container id: d93256eee1bd
s3_localstack  | LocalStack build date: 2023-08-08
s3_localstack  | LocalStack build git hash: 46a62fb9
s3_localstack  |
s3_localstack  | 2023-08-08T20:18:03.512  INFO --- [-functhread3] hypercorn.error            : Running on https://0.0.0.0:4566 (CTRL + C to quit)
s3_localstack  | 2023-08-08T20:18:03.512  INFO --- [-functhread3] hypercorn.error            : Running on https://0.0.0.0:4566 (CTRL + C to quit)
s3_localstack  | Ready.
s3_localstack  | Init localstack
s3_localstack  | 2023-08-08T20:18:04.246  INFO --- [   asgi_gw_0] localstack.request.aws     : AWS s3.CreateBucket => 200
s3_localstack  | make_bucket: rosie-bucket
```
> rosie-bucket is a bucket name that is used in this project. And you can change it in the docker/docker-compose.yml file.

### Run backend(Spring boot) application

Spring boot application will run on http://localhost:8080.
And this application will upload a file to localstack s3 by using aws sdk.

```bash
cd ../backend && ./gradlew bootRun
```

### Run frontend(React) application
```bash
npm install yarn
```

```bash
yarn install && yarn start
```


## Classical way - through Spring Boot application


## Improved way - directly upload to S3 (In progress..)

<img src="./img.png">

## References
https://docs.localstack.cloud/overview/
https://tech.inflab.com/202202-integration-test-with-localstack/
https://aws.amazon.com/ko/blogs/compute/uploading-to-amazon-s3-directly-from-a-web-or-mobile-application/
