-- $Header: /Development/AEDevelopment/projects/ddl.org.activebpel.tasks/ddl/ActiveBPEL_Tasks-Oracle.sql,v 1.15 2008/04/03 22:23:32 PJayanetti Exp $
--
-- Creates tables in Oracle for Human Workflow Task tables.
--

-- Create the Task table
CREATE TABLE AeB4PTask (
   ProcessId INT NOT NULL,
   Name VARCHAR(255) NOT NULL,
   TargetNamespace VARCHAR(255),
   PresentationName VARCHAR(64) NOT NULL,
   Summary VARCHAR(255),
   CreationTimeMillis INT NOT NULL,
   State NUMBER(1) NOT NULL,
   Priority INT NOT NULL,
   Owner VARCHAR(255),
   CompletionTimeMillis INT,
   LastModifiedTimeMillis INT,
   ExpirationDateMillis INT,
   HasAttachments NUMBER(1) NOT NULL,
   HasComments NUMBER(1) NOT NULL,
   LastEscalatedTimeMillis INT,
   HasRenderings NUMBER(1) NOT NULL,
   CreatedBy VARCHAR(255),
   TaskInitiator VARCHAR(255),
   TaskStakeholders CLOB,
   PotentialOwners CLOB,
   BusinessAdministrators CLOB,
   NotificationRecipients CLOB,
   HasOutput NUMBER(1),
   HasFault NUMBER(1),
   IsSkipable NUMBER(1),
   PrimarySearchBy VARCHAR(255),
   StartByMillis INT,
   CompleteByMillis INT,
   TaskType INT,
   ActivationTimeMillis INT,
   FOREIGN KEY (ProcessId) REFERENCES AeProcess(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId)
)
;

CREATE INDEX AeB4PTaskIndex on AeB4PTask(PresentationName, CreationTimeMillis, State, Priority, Owner, LastModifiedTimeMillis)
;
CREATE INDEX AeB4PTaskByName on AeB4PTask(Name)
;
CREATE INDEX AeB4PTaskByExpire on AeB4PTask(ExpirationDateMillis)
;
CREATE INDEX AeB4PTaskByStartBy on AeB4PTask(StartByMillis)
;
CREATE INDEX AeB4PTaskByComplete on AeB4PTask(CompleteByMillis)
;
CREATE INDEX AeB4PTaskByEscalate on AeB4PTask(LastEscalatedTimeMillis)
;
CREATE INDEX AeB4PTaskByPrimSrch on AeB4PTask(PrimarySearchBy)
;
CREATE INDEX AeB4PTaskByType on AeB4PTask(TaskType)
;
CREATE INDEX AeB4PTaskByActivate on AeB4PTask(ActivationTimeMillis)
;

-- Create the Task ACL table (for potential owners)
CREATE TABLE AeB4PTaskACL (
   ProcessId INT NOT NULL,
   Name VARCHAR(255) NOT NULL,
   Type NUMBER(1) NOT NULL,
   ExcludeFlag NUMBER(1) NOT NULL,
   GenericHumanRole INT NOT NULL,
   FOREIGN KEY (ProcessId) REFERENCES AeB4PTask(ProcessId) ON DELETE CASCADE,
   PRIMARY KEY (ProcessId, Name, Type, ExcludeFlag, GenericHumanRole)
)
;

COMMIT
;
