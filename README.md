# Equiron test
Equiron skills test project. It is about Java, SpringMVC and web micro-services.

Tha aim of the project is to build a web micro service that accepts documents like the following:
```
{
  "seller":"123534251",
  "customer":"648563524",
  "products":[{"name":"milk","code":"2364758363546"},{"name":"water","code":"3656352437590"}]
}
```
and reports any errors of validation. All fields in the document are required. 

## Requirements
To build and run this projects you need Java JDK 8 update 161+, Git Git-2.15.1.2+ and Maven 3.5.4+.

## Building
Building tool - Maven requires `JAVA_HOME` environment variable to be defined and to point to
jdk location. Please, ensure that it is defined and points to jdk location on disk. For example
`C:\Program Files\Java\jdk1.8.0_161`.

To build the project from source, use the following commands:
```
git clone https://github.com/marat-gainullin/equiron-test.git
cd equiron-test
mvn package
```

## Running
If you want to run the project as a standalone micro-service, then use the following command:
```
java -jar ./target/equiron-test-0.0.1-SNAPSHOT-standalone.war
```
This will run standalone micro-service based on embedded tomcat. It will listen http on `8080` port.
The service creates endpoint which accepts `POST` requests at `/deals` uri and `GET` requests at `/deals/{deal-key}` uri.
 
You can customize the port number with java system property as follows:
```
java -Dhttp.port=8888 -jar ./target/equiron-test-0.0.1-SNAPSHOT-standalone.war
```

## Contained mode
If you want to build regular web application for deploy to any servlet container, than use the following command:
```
mvn package -Pcontained
```
After that, regular war file `target/equiron-test-0.0.1-SNAPSHOT.war` will be created.

## QA & Reporting
To generate code coverage report, use the following command:
```
mvn package
```
and navigate to `./target/site/jacoco/index.html` with your favorite browser.

If you want to generate checkstyle and other reports, use the following command:
```
mvn site
```
and navigate to `./target/site/index.html` with your favorite browser.
Specific QA reports are located at `./target/site/checkstyle.html`, `./target/site/cpd.html` and `./target/site/pmd.html`.

