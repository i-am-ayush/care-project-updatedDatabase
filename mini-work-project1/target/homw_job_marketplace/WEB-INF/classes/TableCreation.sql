CREATE TABLE member(
    id INT(32)  AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(32),
     lastName VARCHAR(32),
     phoneNumber INT(10),
     email VARCHAR(100),
     type ENUM('SEEKER','SITTER'),
     address VARCHAR(256)
);
CREATE TABLE seeker(
    memberId INT(32) PRIMARY KEY,
    noOfChildren INT,
    spouse VARCHAR(32),
    FOREIGN KEY (memberId) REFERENCES member(id)
);
CREATE TABLE sitter(
    memberId INT(32) PRIMARY KEY,
    yearsOfExp INT,
    expectedPay DOUBLE,
    FOREIGN KEY (memberId) REFERENCES member(id)
 );
 CREATE TABLE job(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(32),
    postedBy INT(32),
    startDateTime DATE,
    endDateTime DATE,
    payPerHour DOUBLE,
    FOREIGN KEY (postedBy) REFERENCES member(id)
    );
  CREATE TABLE application(
    id int(32) AUTO_INCREMENT PRIMARY KEY,
    jobId int,
    memberId int(32),
    expectedPay DOUBLE,
    FOREIGN KEY (memberId) REFERENCES member(id),
    FOREIGN KEY (jobId) REFERENCES job(id)
  );