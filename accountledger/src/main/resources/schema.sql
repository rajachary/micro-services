
   CREATE TABLE IF NOT EXISTS `accountsledger` (
     eventId varchar(100) NOT NULL PRIMARY KEY,
     accountId varchar(100) NOT NULL,
     type varchar(100) NOT NULL,
     amount double NOT NULL,
     currency varchar(100) NOT NULL,
     eventTimestamp varchar(100) NOT NULL,
     batchId  varchar(100),
     source varchar(100)
   );