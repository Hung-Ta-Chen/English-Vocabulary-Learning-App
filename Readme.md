## Introduction
A JavaFX app that help memorizing vocabublary.<br>

## Features
1. Customize your own directories and word sets.
2. Able to specify the translation and POS of the word on the flashcard.
3. Quiz option in every word sets.

## Implementation Details
1. Utilize SceneBuilder and CSS to design the user interface of the app.
2. Use JDBC to connnect the MySQL database and save/fetch data.
3. Implement the feature of dynamica scene switching and component update with JAvaFx.

## How to Execute the Code
Step1: Clone the repo to local.<br>

Step2: Create a database called "java_final" in MySQL WorkBench, then run the following MySQL commands to build two tables.<br>
       """<br>
       CREATE TABLE settable (name varchar(50), size int, description varchar(400)set_test);<br>
       CREATE TABLE wordtable(word varchar(45), chinese varchar(45), POS enum('n','v','adj','prep','adv','conj','interj','pronoun'));<br>
       """<br>

Step3: Run the following commands in Powershell in master dir.<br>
       """<br>
       cd java_project<br>
       javac --module-path ../javafx-sdk-16/lib --add-modules=javafx.controls,javafx.fxml *.java<br>
       cd ..<br>
       java --module-path javafx-sdk-16/lib --add-modules=javafx.controls,javafx.fxml java_project.WordApp<br>
       """<br>
