FROM java:8
ADD eureka-master.jar eureka-master.jar
RUN bash -c 'touch /eureka-master.jar'
ENTRYPOINT ["java","-jar","/eureka-master.jar"]
