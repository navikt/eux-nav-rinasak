package:
	mvnd clean install

run-jar:
	java -jar eux-nav-rinasak-webapp/target/eux-nav-rinasak.jar

run: package run-jar
