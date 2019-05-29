CREATE TABLE member(
    id INT(32)  AUTO_INCREMENT PRIMARY KEY,
    firstName VARCHAR(32) NOT NULL,
     lastName VARCHAR(32)NOT NULL,
     phoneNumber INT(10)NOT NULL,
     status ENUM('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',
     email VARCHAR(100)NOT NULL UNIQUE,
     type ENUM('SEEKER','SITTER')NOT NULL,
     address VARCHAR(256)NOT NULL,
     password VARCHAR(32)NOT NULL,
     lastModified timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
CREATE TABLE seeker(
    memberId INT(32) PRIMARY KEY,
    noOfChildren INT NOT NULL,
    spouse VARCHAR(32) NOT NULL,
    lastModified timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (memberId) REFERENCES member(id)
);
CREATE TABLE sitter(
    memberId INT(32) PRIMARY KEY,
    yearsOfExp INT NOT NULL,
    expectedPay DOUBLE NOT NULL,
    lastModified timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (memberId) REFERENCES member(id)
 );
 CREATE TABLE job(
    id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(32) NOT NULL,
    postedBy INT(32) NOT NULL,
    startDateTime DATE NOT NULL,
    endDateTime DATE NOT NULL,
    payPerHour DOUBLE NOT NULL,
    status enum('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',
    lastModified timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (postedBy) REFERENCES member(id)
    );
  CREATE TABLE application(
    id int(32) AUTO_INCREMENT PRIMARY KEY,
    jobId int(32) NOT NULL,
    memberId int(32) NOT NULL,
    expectedPay DOUBLE NOT NULL,
    status enum('ACTIVE','INACTIVE') DEFAULT 'ACTIVE',
    lastModified timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (memberId) REFERENCES member(id),
    FOREIGN KEY (jobId) REFERENCES job(id)
  );