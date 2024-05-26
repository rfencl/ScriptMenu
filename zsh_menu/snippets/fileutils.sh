#!/bin/zsh
#[ ! ${FILEUTILS_G} ] && FILEUTILS_G='FILEUTILS' || return 0

findFiles () {
  echo findSortedFiles
  local rootPath=$1
  files=( echo $(find -sf $rootPath) ) 
  for f in $files; do
    echo $f
  done
}

md5 () {
  local f=$1
  md5sum $f
}

