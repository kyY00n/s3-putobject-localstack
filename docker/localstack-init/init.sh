#!/bin/sh
echo "Init localstack"
awslocal s3 mb s3://rosie-bucket
awslocal s3api put-bucket-cors --bucket rosie-bucket --cors-configuration file://$(pwd)/cors-config.json
