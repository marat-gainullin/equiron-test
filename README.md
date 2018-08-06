# equiron-test


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
