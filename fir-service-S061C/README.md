# fir-service-example

## Install JDK 17

The Project must be executed in a JDK 17 environment by setting the JAVA_HOME environment variable

```shell
export JAVA_HOME=/Library/Java/JavaVirtualMachines/openjdk-17.jdk/Contents/Home
```

## Install dependent artifacts in local maven repository

```shell
mvn install:install-file -Dfile=pom.xml -DgroupId=tw.com.firstbank.fcbcore -DartifactId=fcbframework -Dversion=1.0.0 -Dpackaging=pom -DpomFile=pom.xml
mvn install:install-file -Dfile=fcbframework-core\fcbframework-core-1.0.0.jar -DgroupId=tw.com.firstbank.fcbcore.fcbframework -DartifactId=fcbframework-core -Dversion=1.0.0 -Dpackaging=jar -DpomFile=fcbframework-core\pom.xml
mvn install:install-file -Dfile=fcbframework-core-extensions-transaction\fcbframework-core-extensions-transaction-1.0.0.jar -DgroupId=tw.com.firstbank.fcbcore.fcbframework -DartifactId=fcbframework-core-extensions-transaction -Dversion=1.0.0 -Dpackaging=jar -DpomFile=fcbframework-core-extensions-transaction\pom.xml
mvn install:install-file -Dfile=fcbframework-adapters-rest\fcbframework-adapters-rest-1.0.0.jar -DgroupId=tw.com.firstbank.fcbcore.fcbframework -DartifactId=fcbframework-adapters-rest -Dversion=1.0.0 -Dpackaging=jar -DpomFile=fcbframework-adapters-rest\pom.xml
mvn install:install-file -Dfile=fcbframework-adapters-rabbitmq\fcbframework-adapters-rabbitmq-1.0.0.jar -DgroupId=tw.com.firstbank.fcbcore.fcbframework -DartifactId=fcbframework-adapters-rabbitmq -Dversion=1.0.0 -Dpackaging=jar -DpomFile=fcbframework-adapters-rabbitmq\pom.xml
mvn install:install-file -Dfile=fcbframework-adapters-jms\fcbframework-adapters-jms-1.0.0.jar -DgroupId=tw.com.firstbank.fcbcore.fcbframework -DartifactId=fcbframework-adapters-jms -Dversion=1.0.0 -Dpackaging=jar -DpomFile=fcbframework-adapters-jms\pom.xml
mvn install:install-file -Dfile=fcbframework-adapters-soap\fcbframework-adapters-soap-1.0.0.jar -DgroupId=tw.com.firstbank.fcbcore.fcbframework -DartifactId=fcbframework-adapters-soap -Dversion=1.0.0 -Dpackaging=jar -DpomFile=fcbframework-adapters-soap\pom.xml
```

# Package fir-service-example module

Packaging jar

```shell
mvn clean package

or
mvn -P dev  clean package
mvn -P sit  clean package
mvn -P uat  clean package
mvn -P prod clean package
```

# Run fir-service-example

```shell
java -jar target/fir-service-example-1.0.0.jar

or
java -jar target/fir-service-example-1.0.0.jar --spring.profiles.active=dev
java -jar target/fir-service-example-1.0.0.jar --spring.profiles.active=sit
java -jar target/fir-service-example-1.0.0.jar --spring.profiles.active=uat
java -jar target/fir-service-example-1.0.0.jar --spring.profiles.active=prod
```

