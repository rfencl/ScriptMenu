# Shell menu system for bash and zsh.
1. Perform common commands via a menu to avoid typing. 
2. Easily add your own functions and menu items
3. Organize your menus into sub-menus
4. Create menus for different classes of commands
5. Useful for remote access via ssh.
6. Keeps all your shell source in one place.

# Motivation
I use a lot of shell scripts in my daily work, I found that remembering the names of the scripts and their parameters got to be annoying. 
The usual examples of menus in bash or zsh were too maintenance intensive, it was hard to add another entry (Shott p.419)
Now when I have a useful script, it is just easier to add it to a menu.

Tip: I have multiple menus specific to a functional domain. I just keep them running in a shell window all the time.

# References
I had avoided learning bash my entire career until I came across the book "The Linux Command Line by William Shotts" is is available as a free download.
![image](https://linuxcommand.org/images/lcl2_front_new.png)

https://sourceforge.net/projects/linuxcommand/

If you are new to linux and shell scripting, I highly recommend working through this book.
I based this tool on Shotts description of menus on pg 419.

The companion site has many more bash references.
https://linuxcommand.org/

# System Configuration
Define a shell variable in you login shell .bash_aliases, .bashrc or .zshrc called MENU_HOME [ export MENU_HOME=path to this script ]

# Basic Layout
The menu system has a set of functions organized in files. The menus can be organized in a hierarchical fashion with the file  "menu" defined as the main entry point.
Functions and sub menus can be ordered any way you like, the example folder structure is open to change as you see fit. This is just shell script.


I put the menu definition in a file and the implementing functions in another to help keep things organized.

Defining a menu is easy here is an example.

The menu items are listed in the menuItems array and the actions (functions to call) are entered in the functions array.
Here we are just launch other apps. external scripts can be launched or functions in files that are sourced.
```
main () {
  menuItems=('Launch Midnight Commander' 'Launch HTOP' )
  functions=('(mc)' '(htop)'  )
  genMenu "Main Menu ($scriptVersion)" "${menuItems[@]}" "${functions[@]}"
}
```
![image](https://github.com/rfencl/ScriptMenu/assets/2704939/e7af5b15-ecfc-4497-b36a-62147f92c8b7)

## Displaying data returned from a function

In the previous example other applications were launched from the menu and when the application is closed the menu is redisplayed.<br>
To display text in a function add ;curDwn=1 to the function array. This will prompt the user to press a key to redisplay the menu.<br>
Here '5' was pressed to execute fn5 which echoed its name to the console. Without the ';curDwn=1' appended to the function call the output <br>
would be overwritten immediately.
```
functions=('(mc)' '(htop)'  '(fn1);curDwn=1')
```

![image](https://github.com/rfencl/ScriptMenu/assets/2704939/68164af8-cb0d-4a05-8af3-21d720349ec6)

