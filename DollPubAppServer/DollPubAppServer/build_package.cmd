cd %~dp0

call mvn clean package -Dmaven.test.skip=true -Ptest_T

pause



