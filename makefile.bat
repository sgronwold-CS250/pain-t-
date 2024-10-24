call clear

call cd ./src
call javac --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml -cp ../lib/junit-4.13.2.jar ./*.java -d ../bin/
call cd ../

call javadoc --module-path ./lib --add-modules javafx.controls,javafx.swing,javafx.fxml -cp ./lib/junit-4.13.2.jar -d ./javadoc ./src/*.java

call cd ./bin
call java --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml -jar ./junit-platform-console-standalone-1.9.3.jar -cp ./ -c UnitTests
call java --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml Main
call cd ../
