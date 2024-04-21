while true
do
let itemCnt=0
clear
cntrPrompt "$title"
cntr ; echo -e  "${RED}${indexes[itemCnt]}: Exit";   ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" Launch Midnight Commander; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" Launch HTOP; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" Launch Archive Manager; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" Launch meld; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn5; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn6; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn7; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn8; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn9; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn10; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn11; ((itemCnt++))
cntr ; echo -e "${LTCYAN}${indexes[itemCnt]}:" fn12; ((itemCnt++))
read -sn1
let labelCnt=0
let curDwn=$((itemCnt+1))
case "$REPLY" in
${indexes[$((labelCnt++))]})  clear; exit 0;;
${indexes[$((labelCnt++))]})  (mc);;
${indexes[$((labelCnt++))]})  (htop);;
${indexes[$((labelCnt++))]})  (file-roller);;
${indexes[$((labelCnt++))]})  (meld);;
${indexes[$((labelCnt++))]})  (fn5);curDwn=1;;
${indexes[$((labelCnt++))]})  (fn6);curDwn=1;;
${indexes[$((labelCnt++))]})  (fn7);curDwn=1;;
${indexes[$((labelCnt++))]})  (fn8);curDwn=1;;
${indexes[$((labelCnt++))]})  (fn9);curDwn=1;;
${indexes[$((labelCnt++))]})  (fn10);curDwn=1;;
${indexes[$((labelCnt++))]})  (fn11);curDwn=1;;
${indexes[$((labelCnt++))]})  (fn12);curDwn=1;;
 *) curDwn=1 ;;
esac
moveCursorDown $curDwn ; cntr ; getKey $curDwn
done
