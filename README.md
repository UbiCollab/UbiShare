UbiShare
========

Share ubiquitously!

In order to use UbiShare see [this wiki](https://github.com/UbiCollab/UbiCollabSDK/wiki).

#Change log
##Version 0.4.4
* Fixed Box.com synching. Fixed rouge updates. Only fetch updates that has type "added" or "updated".

##Version 0.4.3
* Fixed community synchronization bug where Box.com does not give you updates when a community is created.
* Added a full sync button to Box.com account page.

##Version 0.4.2
* Added pull request from Kato with some Box fixes.
* Using SDK 0.4.2.
##Version 0.4.1
* New: Removed initial population of database. Now you need to run [PopulateUbiShare](https://github.com/UbiCollab/Examples) to populate the DB.
* New: Add a default local account to Me the first time UbiShare is installed. The only fields that are set are ACCOUNT_TYPE (set to not defined) and ACCOUNT_NAME (set to local). The idea is that you can use UbiShare locally also before you add Box or other accounts. Fixes #43.
* Modified: Box synchronization is now set to 15 seconds intervals.
* Fix: Unnecessary icons are removed now. Only one icon when you install UbiShare. Fixes #41.

##Version 0.4.0
* New: Use of dirty flag. DIRTY_FLAG has to be set to 1 when a record in a DB is changed in order for synch adapter to sync it to the service. After a successful sync, DIRTY_FLAG is changed back to 0.
* New: Person has a new field USER_NAME.


##Version 0.3.2:
* Minor pudates to default values in the SQLite DB.
* Added one test for testing default values in SWLite DB.
* Merged pull request from Kato.

##Version 0.3.1: 
* Updated DB creation code to reflect constraints in the latest SocialContract documentation. Now all properties get default values in the DB upon table creation, according to SocialProvider documentation. Fixes #30. 
* Removed code from SocialProvider where dates were set if not specified in insert(). Now this is done in the DB.
* Added _ID_PEOPLE to Me table as a foreign key to look up the owner of the account in the People table. Fixes #35.
* Added a DESCRIPTION field to relationship, membership, and sharing. Fixes #9.

##Version 0.3.0:
* Added support for Box.net synchronization of communities.
