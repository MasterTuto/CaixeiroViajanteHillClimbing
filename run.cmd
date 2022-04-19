@echo off
REM compile and run Main.java
javac Main.java
java Main
REM clear all .class recursively
del /q /s /f *.class