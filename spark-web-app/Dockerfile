FROM aether/ubuntu-spark:14.04-1.4.2-attributes

ADD target/spark-web-app.jar /spark-web-app.jar

ENTRYPOINT ["/bin/sh", "-c", "$SPARK_HOME/bin/spark-submit --class com.kmalik.sample.SparkWebApp spark-web-app.jar $*"]
