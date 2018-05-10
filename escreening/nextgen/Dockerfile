# Format: FROM    repository[:version]
#FROM app2.vaftl.us/vamis/centos7-jdk8-tomcat
#FROM tomcat:jre8
#FROM /centos7-openjdk8-tomcat
#FROM centos7-openjdk8-tomcat
#FROM /centos7-openjdk8-tomcat

# Format: MAINTAINER Name <email@addr.ess>
MAINTAINER WMSS Developer <wmss@longviewinc.com>

# ----- Set default tomcat configuration -----

COPY ../target/escreening-1.0.0-BUILD-SNAPSHOT.war $CATALINA_HOME/webapps/escreening.war

WORKDIR $CATALINA_HOME/bin

# ./catalina.sh start
CMD ["./catalina.sh", "run"]
