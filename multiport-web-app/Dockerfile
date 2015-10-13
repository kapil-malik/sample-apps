FROM kmalik/ubuntu-java

ADD target/multiport-web-app.jar /multiport-web-app.jar

ENTRYPOINT ["/bin/bash", "-c", "java -jar multiport-web-app.jar $*"]
