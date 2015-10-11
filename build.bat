@echo off

cls

set classpath=

javac.exe -d ./bin ./src/ru/mail/teodorgig/mididermi/base/*.java -classpath ./bin
javac.exe -d ./bin ./src/ru/mail/teodorgig/mididermi/common/*.java -classpath ./bin
javac.exe -d ./bin ./src/ru/mail/teodorgig/mididermi/database/*.java -classpath ./bin
javac.exe -d ./bin ./src/ru/mail/teodorgig/mididermi/providers/*.java -classpath ./bin
javac.exe -d ./bin ./src/ru/mail/teodorgig/mididermi/server/*.java -classpath ./bin
javac.exe -d ./bin ./src/ru/mail/teodorgig/mididermi/client/*.java -classpath ./bin

copy .\src\client.html .\bin\client.html
copy .\src\database.properties .\bin\database.properties

cd ./bin

start rmiregistry.exe

echo RMI registry started ...

start java.exe -cp .;../lib/postgresql-8.3-603.jdbc4.jar -Djava.rmi.server.codebase=file:/C:/TEMP/ -Djava.rmi.server.hostname=127.0.0.1 -Djava.security.policy=../wideopen.policy eu.veldsoft.basakaza.umculo.server.MIDIDERMIServer -MINPOOL 2 -MAXPOOL 30 -MINEPOCHS 1 -MAXEPOCHS 5 -LR 1 -LT 1 -LD -LF -SD -SF

echo RMI server application registered ...

pause

start appletviewer.exe -J-Djava.rmi.server.codebase=http://127.0.0.1/ -J-Djava.security.policy=../wideopen.policy client.html

echo RMI clinet application(s) started ...

pause

cd ./ru/mail/teodorgig/mididermi/client
del *.class
cd..
rd client

cd server
del *.class
cd..
rd server

cd common
del *.class
cd..
rd common

cd providers
del *.class
cd..
rd providers

cd database
del *.class
cd..
rd database

cd base
del *.class
cd..
rd base

cd ..
rd mididermi
cd ..
rd teodorgig
cd ..
rd mail
cd ..
rd ru
del client.html
del database.properties
cd ..

echo Binary directory clean ...

@echo on
