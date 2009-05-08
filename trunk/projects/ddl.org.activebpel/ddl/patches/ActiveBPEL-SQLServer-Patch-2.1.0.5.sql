-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.4 to version 2.1.0.5
-- -----------------------------------------------------------------------------
USE ActiveBPEL;
GO

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.5 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
GO
