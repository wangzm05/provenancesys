-- $Header: /Development/AEDevelopment/projects/ddl.org.activebpel.tasks/ddl/AeTask4.0-ActiveBPEL_Tasks-MySQL.sql,v 1.1 2008/02/07 22:18:23 PJayanetti Exp $
--
-- Creates tables in MySQL for Human Workflow Task tables.
--
USE ActiveBPEL;

-- Create the Task table
CREATE TABLE AeTask (
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
   RenderType VARCHAR(100),
   RenderURN VARCHAR(255),
   RenderEmbed TINYINT,
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

CREATE INDEX AeTaskIndex on AeTask(PresentationName(50), CreationTimeMillis, State, Priority, Owner(50), LastModifiedTimeMillis);
CREATE INDEX AeTaskByName on AeTask(Name);
CREATE INDEX AeTaskByExpire on AeTask(ExpirationDateMillis);
CREATE INDEX AeTaskByStartBy on AeTask(StartByMillis);
CREATE INDEX AeTaskByComplete on AeTask(CompleteByMillis);
CREATE INDEX AeTaskByEscalate on AeTask(LastEscalatedTimeMillis);
CREATE INDEX AeTaskByPrimSrch on AeTask(PrimarySearchBy);
CREATE INDEX AeTaskByType on AeTask(TaskType);
CREATE INDEX AeTaskByActivate on AeTask(ActivationTimeMillis);

-- Create the Task ACL table
CREATE TABLE AeTaskACL (
   ProcessId BIGINT UNSIGNED NOT NULL,
   Name VARCHAR(255) NOT NULL,
   Type TINYINT NOT NULL,
   ExcludeFlag TINYINT NOT NULL,
   GenericHumanRole INT NOT NULL,
   FOREIGN KEY (ProcessId) REFERENCES AeTask(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId, Name, Type, ExcludeFlag, GenericHumanRole)
) TYPE = INNODB;
