abtest
======

A/B test router that assigns users their groups based on configured weights

Configuration:
src/main/resources/configuration.yml

Build:
mvn clean package

Run:
java -jar target/abtest.jar server src/main/resources/configuration.yml

