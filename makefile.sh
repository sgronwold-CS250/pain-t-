clear

cd ./src
javac --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml ./*.java -d ../bin/
cd ..

cd ./bin
java --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml Main
cd ../
