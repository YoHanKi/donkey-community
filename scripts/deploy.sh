#!/bin/bash

# AWS ECR에 로그인
$(aws ecr get-login-password --region $AWS_REGION | docker login --username AWS --password-stdin $ECR_REPOSITORY_URL)

# 도커 컴포즈 파일이 위치한 디렉토리로 이동
cd /home/ec2-user/action

# 현재 실행 중인 컨테이너를 중지 및 제거
docker-compose down

# ECR에서 최신 이미지를 가져와 컨테이너를 실행
docker-compose pull
docker-compose up -d