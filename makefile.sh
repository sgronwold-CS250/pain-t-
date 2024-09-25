clear

cd ./src
javac --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml -cp ../lib/junit-4.13.2.jar ./*.java -d ../bin/
cd ..

cd ./bin
java --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml -jar ./junit-platform-console-standalone-1.9.3.jar -cp ./ -c UnitTests
java --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml Main
cd ../
