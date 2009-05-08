-- -----------------------------------------------------------------------------
-- SQL Patch script to upgrade the DB from version 2.1.0.4 to version 2.1.0.5
-- -----------------------------------------------------------------------------

-- Change the type of the AeProcessJournal table's EntryType column
ALTER TABLE AeProcessJournal MODIFY ( EntryType NUMBER(3) );
COMMIT;

-- --------------------------------------
-- Update the DB version number.
-- --------------------------------------
UPDATE AeMetaInfo SET PropertyValue = '2.1.0.5 ActiveBPEL Enterprise' WHERE PropertyName = 'Version';
COMMIT;
