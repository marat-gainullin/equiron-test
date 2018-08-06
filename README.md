# Equiron test

## Building
To build hte projects from source, check it out from GitHub:
```
git clone https://github.com/marat-gainullin/equiron-test.git
```

After that use maven to build the project:
```
cd equiron-test
mvn package
```

## Reporting
If you want to generate checkstyle and other reports, use the following command:
```
mvn site
```
and navigate to ./target/site/index.html with your favorite browser.

If you want to generate code coverage report, use the following command:
```
mvn package
```
and navigate to ./target/site/jacoco/index.html with your favorite browser.

## Running
If you want to run the project as a standalone micro-service, then use the following command:
```
mvn package
java -jar .\target\equiron-test-0.0.1-SNAPSHOT-standalone.war
```

## Contained mode
If you want to build regular web application i.e. *.war, than use the following commands:
```
mvn package -Pcontained
```
After that, regular war file target/equiron-test-0.0.1-SNAPSHOT.war will be created.