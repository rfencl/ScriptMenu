#!/bin/bash
clear
INPUT_STRING='hello'
while [ $INPUT_STRING != "0" ]
do
	echo "This script only works on a turtle box"
	echo "Current tomcat8 state"
	echo "type 0 to quit"
	echo "type 1 to stop tomcat8 (q to exit the status display)"
	echo "type 2 to start tomcat8 (q to exit the status display)"
	echo "type 3 to restart tomcat8"
	echo
	echo "type 4 to list the state of FromStringToPhoenixInternal in /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml"
	echo "type d to change FromStringToPhoenixInternal in log4j2.xml to debug"
	echo "type i to change FromStringToPhoenixInternal in log4j2.xml to info"
	echo "type t to change FromStringToPhoenixInternal in log4j2.xml to trace"
	echo
	echo "type 7 to copy the current turtle.log to home/powin and change its owner to powin"
	echo "type 8 to delete the current turtle.log"
	echo "type 9 to tail the turtle.log file"
	echo "type a for a tomcat8 restart deleting logs and catalina tail"
	echo
	read INPUT_STRING
# some code here
	case $INPUT_STRING in
		0)
			exit
			;;
		1)
			clear
			echo "Stopping tomcat"
			service tomcat8 stop
			service tomcat8 status
			echo
			;;
		2)
			clear
			echo "Starting tomcat"
			service tomcat8 start
			service tomcat8 status
			echo
			;;
		3)
			echo "restarting tomcat"
			service tomcat restart
			;;
		4)
			echo "Current state of FromStringToPhoenixInternal in log4j2.xml"
			sudo grep "FromStringToPhoenixInternal" /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			echo
			;;
		d)
			clear
			echo "Change FromStringToPhoenixInternal in log4j2.xml to debug"
			sudo sed -i '/.FromStringToPhoenixInternal/ s/info/debug/' /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			sudo sed -i '/.FromStringToPhoenixInternal/ s/trace/debug/' /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			sudo grep "FromStringToPhoenixInternal" /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			echo
			;;
		i)
			clear
			echo "Change FromStringToPhoenixInternal in log4j2.xml to info"
			sudo sed -i '/.FromStringToPhoenixInternal/ s/debug/info/' /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			sudo sed -i '/.FromStringToPhoenixInternal/ s/trace/info/' /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			sudo grep "FromStringToPhoenixInternal" /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			echo
			;;
		t)
			clear
			echo "Change FromStringToPhoenixInternal in log4j2.xml to trace"
			sudo sed -i '/.FromStringToPhoenixInternal/ s/debug/trace/' /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			sudo sed -i '/.FromStringToPhoenixInternal/ s/info/trace/' /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			sudo grep "FromStringToPhoenixInternal" /var/lib/tomcat8/webapps/turtle/WEB-INF/classes/log4j2.xml
			echo
			;;
		7)
			echo "Copying the turthe log to /home/powin"
			sudo cp /var/log/tomcat8/turtle.log /home/powin
			echo "changing the ownership of turtle.log to powin"
			sudo chown powin:powin /home/powin/turtle.log
			echo
		;;
		8)
			echo "Removing /var/log/tomcat8/turtle.log"
			sudo rm  /var/log/tomcat8/turtle.log
			echo
			;;
		9)
			echo "Tail the turtle.log file"
			sudo tail -f  /var/log/tomcat8/turtle.log
			echo
			;;
		a)
			echo "catalina tail (stop tomcat8, delete logs, start tomcat8)"
			sudo service tomcat stop &&  sudo rm -rf /var/log/tomcat8/* && sudo service tomcat start && sudo tail -f /opt/tomcat/logs/catalina.out

			echo
			;;
	esac
done
