@echo off
set _CLASSPATH=.

rem for %%i in (*.jar) do call classpath.bat %%i
rem for %%i in (..\*.jar) do call classpath.bat %%i
rem for %%i in (..\lib\*.jar) do call classpath.bat %%i
rem #bug The input line is too long.

set _CLASSPATH=.;..\*;..\lib\*;..\..\*;..\..\lib\*;..\..\..\*;..\..\..\lib\*;..\..\..\lib-provided\*

rem @echo Using classpath:%_CLASSPATH%

java -Xmn96m -Xms192m -Xmx192m -Xss128k -XX:PermSize=64m -XX:MaxPermSize=64m -XX:MaxPermSize=64m -cp %_CLASSPATH% -XX:+UseParNewGC -XX:+DisableExplicitGC -Djava.net.preferIPv4Stack=true -verbose:gc org.openyu.socklet.bootstrap.server.ServerBootstrap org/openyu/mix/bootstrap/server/applicationContext-system.xml

set _CLASSPATH=.