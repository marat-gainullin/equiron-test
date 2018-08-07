# Equiron test
Equiron skills test project. It is about Java, SpringMVC and web micro-services.

## Requirements
To build and run this projects you need Java 8 update 161+, Git Git-2.15.1.2+ and Maven 3.5.4+.

## Building
To build the project from source, use the following commands:
```
git clone https://github.com/marat-gainullin/equiron-test.git
cd equiron-test
mvn package
```

## Running
If you want to run the project as a standalone micro-service, then use the following command:
```
java -jar .\target\equiron-test-0.0.1-SNAPSHOT-standalone.war
```

## Contained mode
If you want to build regular web application for deploy to any servlet container, than use the following command:
```
mvn package -Pcontained
```
After that, regular war file target/equiron-test-0.0.1-SNAPSHOT.war will be created.

## Reporting & QA
If you want to generate checkstyle and other reports, use the following command:
```
mvn site
```
and navigate to `./target/site/index.html` with your favorite browser.
Specific QA reports are located at `./target/site/checkstyle.html`, `./target/site/cpd.html` and `./target/site/pmd.html`.

If you want to generate code coverage report, use the following command:
```
mvn package
```
and navigate to `./target/site/jacoco/index.html` with your favorite browser.
