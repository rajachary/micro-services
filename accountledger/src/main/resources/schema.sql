
   CREATE TABLE IF NOT EXISTS `customer` (
    id int AUTO_INCREMENT  PRIMARY KEY,
     eventId varchar(100) NOT NULL,
     accountId varchar(100) NOT NULL,
     type varchar(100) NOT NULL,
     amount double NOT NULL,
     currency varchar(100) NOT NULL,
     eventTimestamp varchar(100) NOT NULL,
     batchId  varchar,
     source varchar
   );