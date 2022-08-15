@echo off

:: read N arguements, append them to arguementString
setlocal enabledelayedexpansion
FOR %%A IN (%*) DO (
	set arguementString=!arguementString!%%A 
)

:: use java to execute the jar file, pass the arguementString with the user inputs
java -jar webAnalysis.jar %arguementString%