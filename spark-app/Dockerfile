FROM aether/ubuntu-spark:14.04-1.4.2-attributes

ADD target/spark-app.jar /spark-app.jar

ENTRYPOINT ["/bin/sh", "-c", "$SPARK_HOME/bin/spark-submit --class com.kmalik.sample.SparkApp spark-app.jar $*"]
