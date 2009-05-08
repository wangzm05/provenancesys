CONNECT TO ABPEL USER bpeluser;
-- The preceding line must be at the top of the file to work.
--
-- The user specified in the CONNECT statement must be the same as the user
-- that will connect to the database at runtime, because the user determines
-- the default schema used by DB2.

-- $Header: /Development/AEDevelopment/projects/ddl.org.activebpel.tasks/ddl/ActiveBPEL_Tasks-DB2.sql,v 1.17 2008/02/11 22:59:28 mford Exp $
--
-- Creates tables in DB2 for Human Workflow Task tables.
--

DROP TABLE AeB4PTask;
DROP TABLE AeB4PTaskACL;

-- Create the Task table
CREATE TABLE AeB4PTask (
   ProcessId DECIMAL(19, 0) NOT NULL,
   Name VARGRAPHIC(100) NOT NULL,
   TargetNamespace VARGRAPHIC(255),
   PresentationName VARGRAPHIC(64) NOT NULL,
   Summary VARGRAPHIC(255),
   CreationTimeMillis DECIMAL(19, 0) NOT NULL,
   State SMALLINT NOT NULL,
   Priority INT NOT NULL,
   Owner VARGRAPHIC(100),
   CompletionTimeMillis DECIMAL(19, 0),
   LastModifiedTimeMillis DECIMAL(19, 0),
   ExpirationDateMillis DECIMAL(19, 0),
   HasAttachments SMALLINT NOT NULL,
   HasComments SMALLINT NOT NULL,
   LastEscalatedTimeMillis DECIMAL(19, 0),
   HasRenderings SMALLINT NOT NULL,
   CreatedBy VARGRAPHIC(255),
   TaskInitiator VARCHAR(255),
   TaskStakeholders DBCLOB(5M),
   PotentialOwners DBCLOB(5M),
   BusinessAdministrators DBCLOB(5M),
   NotificationRecipients DBCLOB(5M),
   HasOutput SMALLINT,
   HasFault SMALLINT,
   IsSkipable SMALLINT,
   PrimarySearchBy VARCHAR(255),
   StartByMillis DECIMAL(19, 0),
   CompleteByMillis DECIMAL(19, 0),
   TaskType INT,
   ActivationTimeMillis DECIMAL(19, 0),
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId)
);

-- Index names in DB2 are limited to 18 characters.
--            123456789012345678 
CREATE INDEX AeB4PTskIdx on AeB4PTask(PresentationName, CreationTimeMillis, State, Priority, Owner, LastModifiedTimeMillis);
CREATE INDEX AeB4PTskByName on AeB4PTask(Name);
CREATE INDEX AeB4PTskByExpire on AeB4PTask(ExpirationDateMillis);
CREATE INDEX AeB4PTskByStartBy on AeB4PTask(StartByMillis);
CREATE INDEX AeB4PTskByComplete on AeB4PTask(CompleteByMillis);
CREATE INDEX AeB4PTskByEscalate on AeB4PTask(LastEscalatedTimeMillis);
CREATE INDEX AeB4PTskByPrimSrch on AeB4PTask(PrimarySearchBy);
CREATE INDEX AeB4PTskByType on AeB4PTask(TaskType);
CREATE INDEX AeB4PTskByActivate on AeB4PTask(ActivationTimeMillis);

-- Create the Task ACL table
CREATE TABLE AeB4PTaskACL (
   ProcessId DECIMAL(19, 0) NOT NULL,
   Name VARGRAPHIC(255) NOT NULL,
   Type SMALLINT NOT NULL,
   ExcludeFlag SMALLINT NOT NULL,
   GenericHumanRole INT NOT NULL,
   FOREIGN KEY (ProcessId) REFERENCES AeB4PTask(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId, Name, Type, ExcludeFlag, GenericHumanRole)
);

-- DB2.390 CREATE INDEX AeB4PTaskACLIdx on AeB4PTaskACL(ProcessId, Name, Type, ExcludeFlag, GenericHumanRole);

