while true
do
let itemCnt=1
clear
cntrPrompt 'Main Menu (v0.01)'
cntr ; echo -e  "${RED}${indexes[itemCnt]}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" list directory; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" list all; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" list with permissions; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn5; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn6; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn7; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn8; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn9; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn10; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn11; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn12; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" Submenu2; ((itemCnt++))
read -sk1
let labelCnt=1
let curDwn=$((itemCnt+1))
case "$REPLY" in
${indexes[$((labelCnt++))]}) clear; return 0;;
${indexes[$((labelCnt++))]}) ls;curDwn=1;;
${indexes[$((labelCnt++))]}) ls -a;curDwn=1;;
${indexes[$((labelCnt++))]}) ls -lart;curDwn=1;;
${indexes[$((labelCnt++))]}) fn5;curDwn=1;;
${indexes[$((labelCnt++))]}) fn6;curDwn=1;;
${indexes[$((labelCnt++))]}) fn7;curDwn=1;;
${indexes[$((labelCnt++))]}) fn8;curDwn=1;;
${indexes[$((labelCnt++))]}) fn9;curDwn=1;;
${indexes[$((labelCnt++))]}) fn10;curDwn=1;;
${indexes[$((labelCnt++))]}) fn11;curDwn=1;;
${indexes[$((labelCnt++))]}) fn12;curDwn=1;;
${indexes[$((labelCnt++))]}) menu2;;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
