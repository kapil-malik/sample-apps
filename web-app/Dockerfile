FROM kmalik/ubuntu-java

ADD target/web-app.jar /web-app.jar

ENTRYPOINT ["/bin/bash", "-c", "java -jar web-app.jar $*"]
