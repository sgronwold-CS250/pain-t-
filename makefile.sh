cd ./src
javac --module-path ../lib --add-modules javafx.controls ./*.java -d ../bin/
cd ..

cd ./bin
java --module-path ../lib --add-modules javafx.controls Main
cd ../