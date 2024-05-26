#!/bin/zsh
# TIMESTAMPS
# ------------------------------------------------------
# Prints the current date and time in a variety of formats:
# The timestamps shouldn't be guarded 
# Mac OS is Unix based, date is a linux command. alias date=gdate in zshrc or change these to gdate.
# ------------------------------------------------------
now=$(gdate +"%m-%d-%Y %r")        # Returns: 06-14-2015 10:34:40 PM
datestamp=$(gdate +%Y-%m-%d)       # Returns: 2015-06-14
currentYear=$(gdate +%Y)           
sdatestamp=$(gdate +%Y%m%d)        # Returns: 20150614
timestamp=$(gdate +%Y%m%d_%H%M%S)  # Returns: 20150614_223440
stimestamp=$(gdate +%H%M%S)        # Returns: 223440
ssdatestamp=$(gdate +%m%d)         # Returns: 0614
ssdatestampyesterday=$(gdate -d "yesterday" +%m%d)    
hourstamp=$(gdate +%r)             # Returns: 10:34:40 PM
today=$(gdate +"%m-%d-%Y")         # Returns: 06-14-2015
yesterday=$(gdate -d "yesterday" +"%m-%d-%Y")
tomorrow=$(gdate -d "tomorrow" +"%m-%d-%Y")
longdate=$(gdate +"%a, %d %b %Y %H:%M:%S %z")  # Returns: Sun, 10 Jan 2016 20:47:53 -0500
gmtdate=$(gdate -u -R | sed 's/\+0000/GMT/') # Returns: Wed, 13 Jan 2016 15:55:29 GMT


[ ! ${DATEUTILS_G} ] && DATEUTILS_G='DATEUTILS' || return 0
#------------------------------------------------
# Removes the leading zero for months and days of the month
#------------------------------------------------
removeLeadingZero () {
    number=$1
    if [ "${number[1,1]}" == "0" ]; then
      number=${number[1,]1}
    fi
    echo $number
}

#------------------------------------------------
# Returns the last day of the given month
# If $2 == true then feb returns 29 days.
# getLastDayOfMonth 2 $(isLeapYear 2024) will be 29
#------------------------------------------------
getLastDayOfMonth () {
    local month=$(removeLeadingZero "$1")
    local leapYear="${2:-false}"
    case $month in 
      9|4|6|11) lastDay=30 ;;
      2) lastDay=28
         $leapYear && (( lastDay++ ))
         ;;
      *) lastDay=31 ;;
    esac
    echo $lastDay
}
#------------------------------------------------
# Given 4 digit year:
# $year = 2021;
# if (($year % 4 == 0 && $year % 100 != 0) || ($year % 400 == 0)) {
#  example using the echo'd boolean response
#  if [ $(isLeapYear 2000) ]; then echo yes it is; else echo no it is not; fi
#  example using the return value use -s to quiet the echo.
# if isLeapYear 2000 -s; then echo yes it is; else echo no it is not; fi
#------------------------------------------------
isLeapYear () {
    local year="$1"
    local silent=false;
    if [ "$2" == "-s" ]; then silent=true; else silent=false; fi
    if [ $(( $year % 4 )) -eq 0 ] && [ $(( $year % 100 )) -ne 0 ] || [ $(( $year % 400 )) -eq 0 ];
    then
        if ! $silent; then
        echo true;
        fi
        return 0;
    else
       if ! $silent; then
        echo false;
       fi
        return 1;
    fi
}
#------------------------------------------------
#
#------------------------------------------------
getYesterday () {
 local reportDate="$1"
 local reportMonth="${reportDate::2}"
 local reportDay="${reportDate:2}"
 if [ "01" == "$reportDay" ]; then
  yday=$(getLastDayOfMonth $(($(removeLeadingZero $reportMonth) - 1)))
  reportMonth=$((reportMonth - 1))
  if [ $reportMonth -lt 10 ]; then
    reportMonth=0$reportMonth
  fi
 else
  yday=$(( reportDay - 1 ))
 fi
 echo $reportMonth$yday
}

