FROM openjdk

WORKDIR /app
COPY src src
COPY org.eclipse.paho.client.mqttv3-1.2.5.jar .

RUN javac -d bin -cp org.eclipse.paho.client.mqttv3-1.2.5.jar -sourcepath src src/ru/linkplay/wirenhome/Main.java

CMD ["java", "-classpath", "org.eclipse.paho.client.mqttv3-1.2.5.jar:bin", "ru.linkplay.wirenhome.Main"]