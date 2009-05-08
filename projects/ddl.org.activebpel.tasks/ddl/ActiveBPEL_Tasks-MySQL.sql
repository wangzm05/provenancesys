-- $Header: /Development/AEDevelopment/projects/ddl.org.activebpel.tasks/ddl/ActiveBPEL_Tasks-MySQL.sql,v 1.15 2008/02/07 22:18:23 PJayanetti Exp $
--
-- Creates tables in MySQL for Human Workflow Task tables.
--
USE ActiveBPEL;

-- Create the Task table
CREATE TABLE AeB4PTask (
   ProcessId BIGINT UNSIGNED NOT NULL,
   Name VARCHAR(255) NOT NULL,
   TargetNamespace VARCHAR(255),
   PresentationName VARCHAR(64) NOT NULL,
   Summary VARCHAR(255),
   CreationTimeMillis BIGINT UNSIGNED NOT NULL,
   State TINYINT NOT NULL,
   Priority INT NOT NULL,
   Owner VARCHAR(255),
   CompletionTimeMillis BIGINT UNSIGNED,
   LastModifiedTimeMillis BIGINT UNSIGNED,
   ExpirationDateMillis BIGINT UNSIGNED,
   HasAttachments TINYINT NOT NULL,
   HasComments TINYINT NOT NULL,
   LastEscalatedTimeMillis BIGINT UNSIGNED,
   HasRenderings TINYINT NOT NULL,
   CreatedBy VARCHAR(255),
   TaskInitiator VARCHAR(255),
   TaskStakeholders LONGTEXT,
   PotentialOwners LONGTEXT,
   BusinessAdministrators LONGTEXT,
   NotificationRecipients LONGTEXT,
   HasOutput TINYINT,
   HasFault TINYINT,
   IsSkipable TINYINT,
   PrimarySearchBy VARCHAR(255),
   StartByMillis BIGINT,
   CompleteByMillis BIGINT,
   TaskType INT,
   ActivationTimeMillis BIGINT,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId)
) TYPE = INNODB;

CREATE INDEX AeB4PTaskIndex on AeB4PTask(PresentationName(50), CreationTimeMillis, State, Priority, Owner(50), LastModifiedTimeMillis);
CREATE INDEX AeB4PTaskByName on AeB4PTask(Name);
CREATE INDEX AeB4PTaskByExpire on AeB4PTask(ExpirationDateMillis);
CREATE INDEX AeB4PTaskByStartBy on AeB4PTask(StartByMillis);
CREATE INDEX AeB4PTaskByComplete on AeB4PTask(CompleteByMillis);
CREATE INDEX AeB4PTaskByEscalate on AeB4PTask(LastEscalatedTimeMillis);
CREATE INDEX AeB4PTaskByPrimSrch on AeB4PTask(PrimarySearchBy);
CREATE INDEX AeB4PTaskByType on AeB4PTask(TaskType);
CREATE INDEX AeB4PTaskByActivate on AeB4PTask(ActivationTimeMillis);

-- Create the Task ACL table
CREATE TABLE AeB4PTaskACL (
   ProcessId BIGINT UNSIGNED NOT NULL,
   Name VARCHAR(255) NOT NULL,
   Type TINYINT NOT NULL,
   ExcludeFlag TINYINT NOT NULL,
   GenericHumanRole INT NOT NULL,
   FOREIGN KEY (ProcessId) REFERENCES AeB4PTask(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId, Name, Type, ExcludeFlag, GenericHumanRole)
) TYPE = INNODB;
