#!/bin/sh

java -cp "lib/*" org.aspectj.tools.ajc.Main -source 1.8 -d bin -sourceroots src 
