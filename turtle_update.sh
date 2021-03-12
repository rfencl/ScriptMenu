#read -p "0.0 Enter your Archiva logon [$ARCHIVA_LOGON_DEF]: " ARCHIVA_LOGON
#read -s -p "0.1 Enter your Archiva password: " ARCHIVA_PASSWORD
ARCHIVA_LOGON=rickf@powin.com
ARCHIVA_PASSWORD=@rch1v@
echo
#TURTLE_VERSION='2.23.28'
TURTLE_VERSION='2.23.71'

read -e -p "0.4 Hit enter to execute." EXECUTE


echo 'Downloading 'turtle' '${TURTLE_VERSION}
wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/turtle/${TURTLE_VERSION}/turtle-${TURTLE_VERSION}.war -O ./turtle-${TURTLE_VERSION}.war
sudo service tomcat stop
sleep 30
sudo rm -rf /opt/tomcat/webapps/turtle* && sudo mv ./turtle-${TURTLE_VERSION}.war /opt/tomcat/webapps/turtle.war
sudo service tomcat start && sudo tail -f /opt/tomcat/logs/catalina.out
