UbiShare
========

Share ubiquitously!

In order to use UbiShare see https://github.com/UbiCollab/UbiCollabSDK/wiki

#Change log

##Version 0.3.1: 
* Updated DB creation code to reflect constraints in the latest SocialContract documentation. Now all properties get default values in the DB upon table creation, according to SocialProvider documentation. Fixes #30. 
* Removed code from SocialProvider where dates were set if not specified in insert(). Now this is done in the DB.
* Added _ID_PEOPLE to Me table as a foreign key to look up the owner of the account in the People table. Fixes #35.
* Added a DESCRIPTION field to relationship, membership, and sharing. Fixes #9.

##Version 0.3.0:
* Added support for Box.net synchronization of communities.
