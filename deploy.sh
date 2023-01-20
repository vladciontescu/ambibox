#!/bin/sh

git pull
cd ambibox
sudo ./mvnw clean package spring-boot:repackage
sudo java -jar target/ambibox-*.jar
