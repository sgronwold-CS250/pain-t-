cd ./src
javac --module-path ../lib --add-modules javafx.controls,javafx.swing ./*.java -d ../bin/
cd ..

cd ./bin
java --module-path ../lib --add-modules javafx.controls,javafx.swing Main
cd ../