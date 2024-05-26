#!/bin/zsh

[ ! ${SHAREDVARS_G} ] && SHAREDVARS_G='SHAREDVARS' ||  return 0 

# ##################################################
# Shared variables
#
# ##################################################
COLS=$(tput cols)
ARTIFACTORY_PROPS="$MENU_HOME/default.properties"
APP_VERSION_PROPS="$MENU_HOME/appversions.properties"
#-------------------------------------------------------------
# basename will throw an error if this isn't loaded by a script
#
# echo loading sharedVariables
program="$0"

isMac() {
  [[ 'Darwin' == $(uname -a | awk '{print $1}') ]] 
}

# menu index conversions to allow more than nine
indexes=(0 1 2 3 4 5 6 7 8 9 a b c d e f g h i j k l m n o p q r s t u v w x y z A B C D E F G H I J K L M N O P Q R S T U V W X Y Z)
currentWorkspace='1'
workspaces=(IdeaProjects IdeaProjects_Archive)
# THISHOST
# ------------------------------------------------------
# Will print the current hostname of the computer the script
# is being run on.
# ------------------------------------------------------
thisHost=$(hostname)
scriptVersion='v0.01'   # harden versions for remote access

getScriptVersion() {
  echo $scriptVersion
}
