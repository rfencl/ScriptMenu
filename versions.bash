#!/bin/bash
COLS=$(tput cols)

cntrPrompt () {
  local prompt="Software versions installed are: "      
  centerOn=$prompt  # global
  [[ $# -ne 0 ]]  && centerOn=$1 && printf "\n%*s" $[$COLS/2-${#centerOn}] ; echo -e "${BOLD}$1"
  printf "\n%*s" $[$COLS/2-${#centerOn}] ; echo -e "${BOLD}${prompt}"
}

cntr () {
  printf "%*s" $[$COLS/2-${#centerOn}] 
}

turtle=$(echo -ne Turtle : '\t\t'&& curl  -sk https://localhost:8443/turtle/status | head -n2 | grep 'Version' | tail -n 1 | cut -c9-)
cob=$(echo -ne Coblynau : '\t'&& curl -sk https://localhost:8443/coblynau/status | tail -n2 | grep Version | cut -c10-)
kobold=$(echo -ne Kobold : '\t\t'&& curl -sk https://localhost:8443/kobold/status | tail -n2 | grep Version | cut -c10-)
knocker=$(echo -ne Knocker : '\t\t'&& curl -sk https://localhost:8443/knocker/status | grep Version | cut -c9-)
primrose=$(echo -ne Primrose : '\t'&& curl -sk https://localhost:8443/primrose/status | grep Version | cut -c9-)

clear
cntrPrompt ; echo
cntr ; echo -e $turtle
cntr ; echo -e $cob
cntr ; echo -e $kobold
cntr ; echo -e $knocker
cntr ; echo -e $primrose
cntr ; echo -e $(curl -ks http://localhost:8080/turtle/lastcall.txt | grep 'stackDefinitionName')
