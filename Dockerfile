FROM jboss/wildfly
ADD /opt/wildfly/standalone/deployments/PolishedDemo-0.8-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
ADD /opt/wildfly/standalone/deployments/RestWebServices-0.8-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
ADD /opt/wildfly/standalone/deployments/SoapWebServices-0.8-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

