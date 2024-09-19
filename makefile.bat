call clear

call cd ./src
call javac --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml ./*.java -d ../bin/
call cd ..

call cd ./bin
call java --module-path ../lib --add-modules javafx.controls,javafx.swing,javafx.fxml Main
call cd ../
