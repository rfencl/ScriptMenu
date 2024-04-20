#!/usr/bin/ksh
[ ! ${SHAREDVARS_G} ] && SHAREDVARS_G='SHAREDVARS' || return 0

# ##################################################
# Shared variables
#
#
# ##################################################
#COLS=$(tput cols)
ARCHIVA_PROPS="$MENU_HOME/default.properties"
APP_VERSION_PROPS="$MENU_HOME/appversions.properties"
#-------------------------------------------------------------
# basename will throw an error if this isn't loaded by a script
# 
program="$0"
if [ '-' != ${program::1} ]; then
  scriptName=$(basename $0)
  scriptBasename="$(basename ${scriptName} .sh)"
fi


# TIMESTAMPS
# ------------------------------------------------------
# Prints the current date and time in a variety of formats:
#
# ------------------------------------------------------
now=$(LC_ALL=C date +"%m-%d-%Y %r")        # Returns: 06-14-2015 10:34:40 PM
datestamp=$(LC_ALL=C date +%Y-%m-%d)       # Returns: 2015-06-14
sdatestamp=$(LC_ALL=C date +%Y%m%d)        # Returns: 20150614
hourstamp=$(LC_ALL=C date +%r)             # Returns: 10:34:40 PM
timestamp=$(LC_ALL=C date +%Y%m%d_%H%M%S)  # Returns: 20150614_223440
today=$(LC_ALL=C date +"%m-%d-%Y")         # Returns: 06-14-2015
longdate=$(LC_ALL=C date +"%a, %d %b %Y %H:%M:%S %z")  # Returns: Sun, 10 Jan 2016 20:47:53 -0500
gmtdate=$(LC_ALL=C date -u -R | sed 's/\+0000/GMT/') # Returns: Wed, 13 Jan 2016 15:55:29 GMT


scriptVersion=v1.0
