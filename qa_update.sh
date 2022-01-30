#read -e -p "0.0 Enter your Archiva logon [$ARCHIVA_LOGON_DEF]: " ARCHIVA_LOGON
#read -e -s -p "0.1 Enter your Archiva password: " ARCHIVA_PASSWORD
ARCHIVA_LOGON=rickf@powin.com
ARCHIVA_PASSWORD=@rch1v@1246
echo
#KOBOLD_VERSION='2.21.20'
KOBOLD_VERSION='2.23.10'
#COB_VERSION='2.21.6'
COB_VERSION='2.23.5'
#PRIMROSE_VERSION='2.21.0'
PRIMROSE_VERSION='2.23.0'
#KNOCKER_VERSION='2.21.4'
KNOCKER_VERSION='2.23.3'
#TURTLE_VERSION='2.21.60'
TURTLE_VERSION='2.23.27'

read -e -p "0.4 Hit enter to execute." EXECUTE


echo 'Downloading 'kobold' '${KOBOLD_VERSION}
wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/kobold/${KOBOLD_VERSION}/kobold-${KOBOLD_VERSION}.war -O kobold-${KOBOLD_VERSION}.war

echo 'Downloading 'coblynau' '${COB_VERSION}
wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/coblynau/${COB_VERSION}/coblynau-${COB_VERSION}.war -O coblynau-${COB_VERSION}.war

echo 'Downloading 'primrose' '${PRIMROSE_VERSION}
wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/primrose/${PRIMROSE_VERSION}/primrose-${PRIMROSE_VERSION}.war -O primrose-${PRIMROSE_VERSION}.war

echo 'Downloading 'knocker' '${KNOCKER_VERSION}
wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/knocker/${KNOCKER_VERSION}/knocker-${KNOCKER_VERSION}.war -O knocker-${KNOCKER_VERSION}.war

echo 'Downloading 'turtle' '${TURTLE_VERSION}
wget --user=${ARCHIVA_LOGON} --password ${ARCHIVA_PASSWORD} https://archiva.powindev.com/repository/internal/com/powin/turtle/${TURTLE_VERSION}/turtle-${TURTLE_VERSION}.war -O turtle-${TURTLE_VERSION}.war

read -e -p "0.5 Hit enter to deploy." EXECUTE

echo 'Halting Tomcat, Moving apps into place, Restarting Tomcat.'
sudo service tomcat stop 
sleep 30 
sudo rm -rf /opt/tomcat/webapps/kobold* && sudo mv ~/kobold-${KOBOLD_VERSION}.war /opt/tomcat/webapps/kobold.war  
sudo rm -rf /opt/tomcat/webapps/coblynau* && sudo mv ~/coblynau-${COB_VERSION}.war /opt/tomcat/webapps/coblynau.war  
sudo rm -rf /opt/tomcat/webapps/primrose* && sudo mv ~/primrose-${PRIMROSE_VERSION}.war /opt/tomcat/webapps/primrose.war
sudo rm -rf /opt/tomcat/webapps/knocker* && sudo mv ~/knocker-${KNOCKER_VERSION}.war /opt/tomcat/webapps/knocker.war 
sudo rm -rf /opt/tomcat/webapps/turtle* && sudo mv ~/turtle-${TURTLE_VERSION}.war /opt/tomcat/webapps/turtle.war 

sudo service tomcat start && sudo tail -f /opt/tomcat/logs/catalina.out



