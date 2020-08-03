@echo off
For /f "tokens=2-4 delims=/ " %%a in ('date /t') do (set mydate=%%c%%a%%b)
set mydate=%mydate: =0%

For /f "tokens=1-2 delims=/:" %%a in ("%TIME%") do (set mytime=%%a%%b)
set mytime=%mytime: =0%

SET timestamp=%mydate%%mytime%
echo Publishing with time stamp : %timestamp% >> push_outputs.txt
docker tag mic-affectation-api 10.3.4.18:5000/mic-affectation-api:%timestamp% >> push_outputs.txt
docker push 10.3.4.18:5000/mic-affectation-api:%timestamp% >> push_outputs.txt