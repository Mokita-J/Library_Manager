# Library_Manager (October 2019 - December 2019)

## About
This project simulates a simple library management software. This program mantains information about the availability of books and DVD's and their details. It also allows users to request and keep track of the number of days they have to return these items. This program also manages all of the library users' files, in which their details can be checked. If any user fails to comply to the library rules, for example, doesn't return a book on time, he/she won't be able to request more works until a fine is paid.

## Requirements
Java 1.8+ must be installed.

## Installing via Git
git clone https://github.com/Mokita-J/Library_Manager.git


## Run by typing:
cd Library_Manager
export CLASSPATH=./m19-app/m19-app.jar:./m19-core/m19-core.jar:./po-uuilib/po-uuilib.jar:.
make
java m19.app.App

## Run tests by typing:
./runtests.sh

In case you get this error bash: ./runtests.sh: Permission denied run

chmod +x runtests.sh

This project was in cooperation with [@MargaridaMoreira](https://github.com/MargaridaMoreira). :wink: Check her out!
