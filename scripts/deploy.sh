#!/bin/bash

# AWS ECR에 로그인
docker login -u AWS -p $(aws ecr get-login-password --region $AWS_REGION) $ECR_REPOSITORY_URL

# 압축 해제
cd /home/ec2-user/deployment
unzip -o $GITHUB_SHA.zip

# 도커 컴포즈 파일이 있는 디렉토리로 이동
cd /home/ec2-user/deployment/docker

# 현재 실행 중인 컨테이너를 중지 및 제거
docker compose down

# ECR에서 최신 이미지를 가져와 컨테이너를 실행
docker compose pull
ECR_REPOSITORY_URL=$ECR_REPOSITORY_URL docker-compose up -d
