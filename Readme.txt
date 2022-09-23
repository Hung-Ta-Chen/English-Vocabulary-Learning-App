Step1:
Clone the repo to local


Step2:
Create a database called "java_final" in MySQL WorkBench, 
then run the following MySQL commands to build two tables.

CREATE TABLE settable (name varchar(50), size int, description varchar(400)set_test);
CREATE TABLE wordtable(word varchar(45), chinese varchar(45), POS enum('n','v','adj','prep','adv','conj','interj','pronoun'));


Step3:
Run the following commands in Powershell in directory "java_app_project"

cd java_project
javac --module-path ../javafx-sdk-16/lib --add-modules=javafx.controls,javafx.fxml *.java
cd ..
java --module-path javafx-sdk-16/lib --add-modules=javafx.controls,javafx.fxml java_project.WordApp
