FROM jboss/wildfly
ADD /var/jenkins_home/.m2/repository/com/securepaas/demo/RestWebServices/0.8-SNAPSHOT/RestWebServices-0.8-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
ADD /var/jenkins_home/.m2/repository/com/securepaas/demo/SoapWebServices/0.8-SNAPSHOT/SoapWebServices-0.8-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
ADD /var/jenkins_home/.m2/repository/com/securepaas/demo/PolishedDemo/0.8-SNAPSHOT/PolishedDemo-0.8-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

