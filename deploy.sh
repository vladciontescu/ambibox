#!/bin/sh

git pull
sudo ./mvnw clean package spring-boot:repackage
sudo java -jar target/ambibox-*.jar
