CONNECT TO ABPEL USER bpeluser;
-- The preceding line must be at the top of the file to work.

-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.4 to version 2.1.0.5
-- -----------------------------------------------------------------------------

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.5 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
