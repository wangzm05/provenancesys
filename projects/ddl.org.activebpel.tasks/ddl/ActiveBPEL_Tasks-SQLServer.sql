-- $Header: /Development/AEDevelopment/projects/ddl.org.activebpel.tasks/ddl/ActiveBPEL_Tasks-SQLServer.sql,v 1.14 2008/02/07 22:18:23 PJayanetti Exp $
--
-- Creates tables in SQLServer for Human Workflow Task tables.
--
USE ActiveBPEL;
GO

-- Create the Task table
CREATE TABLE AeB4PTask (
   ProcessId BIGINT NOT NULL,
   Name NVARCHAR(150) NOT NULL,
   TargetNamespace NVARCHAR(255),
   PresentationName NVARCHAR(64) NOT NULL,
   Summary NVARCHAR(255),
   CreationTimeMillis BIGINT NOT NULL,
   State TINYINT NOT NULL,
   Priority INT NOT NULL,
   Owner NVARCHAR(150),
   CompletionTimeMillis BIGINT,
   LastModifiedTimeMillis BIGINT,
   ExpirationDateMillis BIGINT,
   HasAttachments TINYINT NOT NULL,
   HasComments TINYINT NOT NULL,
   LastEscalatedTimeMillis BIGINT,
   HasRenderings TINYINT NOT NULL,
   CreatedBy NVARCHAR(255),
   TaskInitiator VARCHAR(255),
   TaskStakeholders NTEXT,
   PotentialOwners NTEXT,
   BusinessAdministrators NTEXT,
   NotificationRecipients NTEXT,
   HasOutput TINYINT,
   HasFault TINYINT,
   IsSkipable TINYINT,
   PrimarySearchBy NVARCHAR(255),
   StartByMillis BIGINT,
   CompleteByMillis BIGINT,
   TaskType INT,
   ActivationTimeMillis BIGINT,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId)
);

CREATE INDEX AeB4PTaskIndex on AeB4PTask(PresentationName, CreationTimeMillis, State, Priority, Owner, LastModifiedTimeMillis);
CREATE INDEX AeB4PTaskByName on AeB4PTask(Name);
CREATE INDEX AeB4PTaskByExpire on AeB4PTask(ExpirationDateMillis);
CREATE INDEX AeB4PTaskByStartBy on AeB4PTask(StartByMillis);
CREATE INDEX AeB4PTaskByComplete on AeB4PTask(CompleteByMillis);
CREATE INDEX AeB4PTaskByEscalate on AeB4PTask(LastEscalatedTimeMillis);
CREATE INDEX AeB4PTaskByPrimSrch on AeB4PTask(PrimarySearchBy);
CREATE INDEX AeB4PTaskByType on AeB4PTask(TaskType);
CREATE INDEX AeB4PTaskByActivate on AeB4PTask(ActivationTimeMillis);


-- Create the Task ACL table (for potential owners)
CREATE TABLE AeB4PTaskACL (
   ProcessId BIGINT NOT NULL,
   Name NVARCHAR(255) NOT NULL,
   Type TINYINT NOT NULL,
   ExcludeFlag TINYINT NOT NULL,
   GenericHumanRole INT NOT NULL,
   FOREIGN KEY (ProcessId) REFERENCES AeB4PTask(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId, Name, Type, ExcludeFlag, GenericHumanRole)
);
