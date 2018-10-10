FROM jboss/wildfly
ADD appbuild/RestWebServices/target/RestWebServices-0.8-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
ADD appbuild/SoapWebServices/target/SoapWebServices-0.8-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/
ADD appbuild/PolishedDemo/target/PolishedDemo-0.8-SNAPSHOT.war /opt/jboss/wildfly/standalone/deployments/

