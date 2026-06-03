
   CREATE TABLE IF NOT EXISTS `account` (
    id int AUTO_INCREMENT  PRIMARY KEY,
     accountId varchar(100) NOT NULL,
     amount double NOT NULL,
     currency varchar(100) NOT NULL,
     eventTimestamp varchar(100) NOT NULL
   );