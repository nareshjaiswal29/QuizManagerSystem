CREATE TABLE STUDENTS_RESULT (ID int not null auto_increment, NAME varchar(50), QUIZ varchar(50),TOTAL_MARKS int);

CREATE TABLE ANSWERS_MAP (ID int not null auto_increment,QUESTION_ID int,CORRECT_OPTION int) 
 
CREATE TABLE QUIZ(ID int not null auto_increment, QUIZ_NAME  varchar(30) ) 
 
CREATE TABLE QUESTION(ID INT PRIMARY KEY AUTO_INCREMENT , SUBJECT VARCHAR(2000), TOPICS VARCHAR(1000), DIFFICULTY INT, OPTION_ONE varchar(30), OPTION_TWO varchar(30), OPTION_THREE varchar(30), 
OPTION_FOUR varchar(30), QUIZ_ID INT, QUESTIONTYPE varchar(30));