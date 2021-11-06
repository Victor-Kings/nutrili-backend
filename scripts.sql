drop table nutritionist;
drop table patient;
drop table address;
drop table meal;
drop table food;
drop table client;
drop table user_nutrili;
drop table QUESTIONS;
drop table answers;

CREATE TABLE ADDRESS (
    idAddress serial NOT NULL,
    CEP VARCHAR(255) NOT NULL,
    stateAddress VARCHAR(255) NOT NULL,
    city VARCHAR(255) NOT NULL,
    neighborhood VARCHAR(255) NOT NULL,
    street VARCHAR(255) NOT NULL,
    number INTEGER,
    PRIMARY KEY (idAddress)
);
	
CREATE TABLE USER_NUTRILI(
    id serial NOT NULL,
    addressId serial NOT NULL,
    nameUser VARCHAR(255) NOT NULL,
    CPF VARCHAR(255) NOT NULL,
    gender VARCHAR(255) NOT NULL,
    birth DATE NOT NULL,
    phone VARCHAR(255) NOT NULL,
    email VARCHAR(255) NOT NULL,
    passwordUser VARCHAR(255) NOT NULL,
    linkImage VARCHAR(255) NOT NULL,
    FOREIGN KEY (addressId) REFERENCES ADDRESS(idAddress) ON DELETE CASCADE,
    PRIMARY KEY (id)
);

CREATE TABLE PATIENT(
    idPatient SERIAL NOT NULL,
    idUser SERIAL NOT NULL,
    height NUMERIC NOT NULL,
    weight NUMERIC NOT NULL,
    FOREIGN KEY (idUser) REFERENCES USER_NUTRILI(id) ON DELETE CASCADE,
    PRIMARY KEY (idPatient)
);

CREATE TABLE NUTRITIONIST (
    idNutritionist serial NOT null,
    idUser serial NOT NULL,
    CRN VARCHAR(255) NOT NULL,
    score INTEGER,
    FOREIGN KEY (idUser) REFERENCES USER_NUTRILI(id) ON DELETE CASCADE,
    PRIMARY KEY (idNutritionist)
);

CREATE TABLE CLIENT(
    idNutritionistFK serial NOT NULL,
    idPatientFK serial NOT NULL,
    FOREIGN KEY (idPatientFK) REFERENCES PATIENT(idPatient) ON DELETE CASCADE,
    FOREIGN KEY (idNutritionistFK) REFERENCES NUTRITIONIST(idNutritionist) ON DELETE CASCADE,
    PRIMARY KEY (idNutritionistFK,idPatientFK)
);

CREATE TABLE FOOD (
    idFood serial NOT null,
    name VARCHAR(255) NOT NULL,
    PRIMARY KEY (idFood)
);

CREATE TABLE MEAL (
    idMeal serial NOT null,
    date timestamp,
    idPatientFK serial NOT NULL,
    FOREIGN KEY (idPatientFK) REFERENCES PATIENT(idPatient) on update CASCADE,
    PRIMARY KEY (idMeal)
);

CREATE TABLE FOOD_MEAL(
    idFoodMeal serial not null,
    idFoodFK serial not null,
    idMealFK serial not null,
    FOREIGN KEY (idMealFK) REFERENCES MEAL(idMeal) on update CASCADE,
    FOREIGN KEY (idFoodFK) REFERENCES FOOD(idFood) on update CASCADE,
    PRIMARY KEY (idFoodMeal)
);

CREATE TABLE DAILY_DIET (
    idDailyDiet serial not null,
    NutricionistaID serial NOT null,
    PacienteID serial NOT null,
    breakfast VARCHAR(255) NOT NULL,
    lunch VARCHAR(255) NOT NULL,
    dinner VARCHAR(255) NOT NULL,
    afternoonCoffee VARCHAR(255) NOT NULL,
    dayOfWeek INTEGER,
    FOREIGN KEY (NutricionistaID) REFERENCES NUTRITIONIST(idNutritionist) on update CASCADE,
    FOREIGN KEY (PacienteID) REFERENCES PATIENT(idPatient) on update CASCADE,
    PRIMARY KEY (idDailyDiet)
);

CREATE TABLE DAILY_DIET_FOOD (
    idDailyDietFood serial not null,
    idDailyDietFK serial not null,
    idFoodFK serial not null,
    FOREIGN KEY (idFoodFK) REFERENCES FOOD(idFood) on update CASCADE,
    FOREIGN KEY (idDailyDietFK) REFERENCES DAILY_DIET(idDailyDiet) on update CASCADE,
    PRIMARY KEY (idDailyDietFood)
);

CREATE TABLE NUTRIENTS (
    idNutrients serial NOT null,
    nutrientsName VARCHAR(255) NOT NULL,
    linkImage VARCHAR(255) NOT NULL, 
    color VARCHAR(255) NOT NULL,
    nutrientsType VARCHAR(255) NOT NULL, 
    PRIMARY KEY (idNutrients)
);

CREATE TYPE typesAnswer as ENUM('bool','insert','checked','insertCustom');

CREATE TABLE QUESTIONS (
    idQuestion serial NOT null,
    question VARCHAR(255) not null,
	typeAnswer typesAnswer,
	unityMeasure VARCHAR(255),
	checkQuestions JSON,
    PRIMARY KEY (idQuestion)
);

CREATE TABLE ANSWERS (
 	idPatientFK serial NOT null,
 	idQuestionFK serial NOT null,
	FOREIGN KEY (idPatientFK) REFERENCES PATIENT(idPatient) ON DELETE CASCADE,
 	FOREIGN KEY (idQuestionFK) REFERENCES QUESTIONS(idQuestion) ON DELETE CASCADE,
	PRIMARY KEY (idPatientFK, idQuestionFK)
);
