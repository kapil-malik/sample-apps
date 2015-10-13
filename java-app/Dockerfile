FROM kmalik/ubuntu-java

ADD target/java-app.jar /java-app.jar

ENTRYPOINT ["/bin/bash", "-c", "java -jar java-app.jar $*"]
