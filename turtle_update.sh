#read -p "Enter your Archiva logon: " user
#read -s -p "Enter your Archiva password: " pw
ARCHIVA_LOGON=rickf@powin.com
ARCHIVA_PASSWORD=@rch1v@1234
echo
#TURTLE_VERSION='2.23.28'
TURTLE_VERSION='2.26.76'

read -e -p "0.4 Hit enter to execute." EXECUTE


echo 'Downloading 'turtle' '${TURTLE_VERSION}
wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/turtle/${TURTLE_VERSION}/turtle-${TURTLE_VERSION}.war -O ./turtle-${TURTLE_VERSION}.war
sudo service tomcat stop
sleep 30
sudo rm -rf /opt/tomcat/webapps/turtle* && sudo mv ./turtle-${TURTLE_VERSION}.war /opt/tomcat/webapps/turtle.war
sudo service tomcat start && sudo tail -f /opt/tomcat/logs/catalina.out
