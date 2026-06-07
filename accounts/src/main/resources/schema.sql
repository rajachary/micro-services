
   CREATE TABLE IF NOT EXISTS `account` (
     accountId varchar(100) NOT NULL PRIMARY KEY,
     amount double NOT NULL,
     currency varchar(100) NOT NULL,
     eventTimestamp varchar(100) NOT NULL
   );