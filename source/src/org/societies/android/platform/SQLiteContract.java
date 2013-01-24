package org.societies.android.platform;

import org.societies.android.api.cis.SocialContract;

public final class SQLiteContract {
	//Basic DB file management:
	public static final String DB_NAME = "societies.db";
	public static final String DB_PATH = "/data/data/org.societies.android.platform/databases/";
	//TODO: This will be a preference and not a constant:
	public static final int DB_VERSION = 2;
	
	//Tables and table names
	public static final String ME_TABLE_NAME = "me";
	public static final String COMMUNITIES_TABLE_NAME = "communities";
	public static final String PEOPLE_TABLE_NAME = "people";
	public static final String SERVICES_TABLE_NAME = "services";
	public static final String RELATIONSHIP_TABLE_NAME = "relationships";
	public static final String MEMBERSHIP_TABLE_NAME = "memberships";
	public static final String SHARING_TABLE_NAME = "sharings";
	public static final String PEOPLE_ACTIVITIY_TABLE_NAME = "people_activities";
	public static final String COMMUNITIES_ACTIVITIY_TABLE_NAME = "communities_activities";
	public static final String SERVICES_ACTIVITIY_TABLE_NAME = "services_activities";
	

	//SQL commands for creating tables upon DB creation:
	public static final String ME_TABLE_CREATE = "create table if not exists " + ME_TABLE_NAME
			+ " (" + 
			SocialContract.Me._ID + " integer primary key autoincrement, " +
			SocialContract.Me._ID_PEOPLE + " integer not null default -1," +
			SocialContract.Me.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.Me.NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Me.DISPLAY_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Me.USER_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Me.PASSWORD + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Me.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Me.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Me.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Me.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.Me.DELETED + " integer not null default 0," +
			SocialContract.Me.DIRTY + " integer not null default 0," +
			SocialContract.Me.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Me.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Me.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Me.SYNC4 + " integer," +
			SocialContract.Me.SYNC5 + " integer," +
			SocialContract.Me.SYNC6 + " integer );";

	public static final String PEOPLE_TABLE_CREATE = "create table if not exists " + PEOPLE_TABLE_NAME
			+ " (" + 
			SocialContract.People._ID + " integer primary key autoincrement, " +
			SocialContract.People.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.People.NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.DESCRIPTION + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.EMAIL + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.EMAIL2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.EMAIL3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.PHONE + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.PHONE2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.PHONE3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.ADDRESS + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.RELEVANCE + " integer,"+
			SocialContract.People.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.People.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.People.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.People.DELETED + " integer not null default 0," +
			SocialContract.People.DIRTY + " integer not null default 0," +
			SocialContract.People.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.People.SYNC4 + " integer," +
			SocialContract.People.SYNC5 + " integer," +
			SocialContract.People.SYNC6 + " integer );";

	public static final String COMMUNITIES_TABLE_CREATE = "create table if not exists " + COMMUNITIES_TABLE_NAME
			+ " (" + 
			SocialContract.Communities._ID + " integer primary key autoincrement, " +
			SocialContract.Communities._ID_OWNER + " integer not null default -1," +
			SocialContract.Communities.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.Communities.NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Communities.OWNER_ID + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Communities.TYPE + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Communities.DESCRIPTION + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Communities.RELEVANCE + " integer," +
			SocialContract.Communities.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Communities.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Communities.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Communities.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.Communities.DELETED + " integer not null default 0," +
			SocialContract.Communities.DIRTY + " integer not null default 0," +
			SocialContract.Communities.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Communities.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Communities.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Communities.SYNC4 + " integer not null default -1," +
			SocialContract.Communities.SYNC5 + " integer not null default -1," +
			SocialContract.Communities.SYNC6 + " integer not null default -1 );";

	public static final String SERVICES_TABLE_CREATE = "create table if not exists " + SERVICES_TABLE_NAME
			+ " (" + 
			SocialContract.Services._ID + " integer primary key autoincrement, " +
			SocialContract.Services._ID_OWNER + " integer not null default -1," +
			SocialContract.Services.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.Services.NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.OWNER_ID + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.TYPE + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.DESCRIPTION + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.APP_TYPE + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.AVAILABLE + " integer not null default 0," +
			SocialContract.Services.DEPENDENCY + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.CONFIG + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.URL + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.RELEVANCE + " integer not null default -1," +
			SocialContract.Services.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Services.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Services.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.Services.DELETED + " integer not null default 0," +
			SocialContract.Services.DIRTY + " integer not null default 0," +
			SocialContract.Services.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Services.SYNC4 + " integer not null default -1," +
			SocialContract.Services.SYNC5 + " integer not null default -1," +
			SocialContract.Services.SYNC6 + " integer not null default -1 );";

	public static final String RELATIONSHIP_TABLE_CREATE = "create table if not exists " + RELATIONSHIP_TABLE_NAME
			+ " (" + 
			SocialContract.Relationship._ID + " integer primary key autoincrement, " +
			SocialContract.Relationship.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.Relationship._ID_P1 + " integer not null default -1," +
			SocialContract.Relationship._ID_P2 + " integer not null default -1," +			
			SocialContract.Relationship.GLOBAL_ID_P1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Relationship.GLOBAL_ID_P2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Relationship.TYPE + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Relationship.DESCRIPTION + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Relationship.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Relationship.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Relationship.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Relationship.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.Relationship.DELETED + " integer not null default 0," +
			SocialContract.Relationship.DIRTY + " integer not null default 0," +
			SocialContract.Relationship.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Relationship.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Relationship.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Relationship.SYNC4 + " integer not null default -1," +
			SocialContract.Relationship.SYNC5 + " integer not null default -1," +
			SocialContract.Relationship.SYNC6 + " integer not null default -1 );";

	public static final String MEMBERSHIP_TABLE_CREATE = "create table if not exists " + MEMBERSHIP_TABLE_NAME
			+ " (" + 
			SocialContract.Membership._ID + " integer primary key autoincrement, " +
			SocialContract.Membership.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.Membership._ID_MEMBER + " integer not null default -1," +
			SocialContract.Membership._ID_COMMUNITY + " integer not null default -1," +
			SocialContract.Membership.GLOBAL_ID_MEMBER + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Membership.GLOBAL_ID_COMMUNITY + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Membership.TYPE + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Membership.DESCRIPTION + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Membership.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Membership.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Membership.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Membership.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.Membership.DELETED + " integer not null default 0," +
			SocialContract.Membership.DIRTY + " integer not null default 0," +
			SocialContract.Membership.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Membership.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Membership.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Membership.SYNC4 + " integer not null default -1," +
			SocialContract.Membership.SYNC5 + " integer not null default -1," +
			SocialContract.Membership.SYNC6 + " integer not null default -1 );";

	public static final String SHARING_TABLE_CREATE = "create table if not exists " + SHARING_TABLE_NAME
			+ " (" + 
			SocialContract.Sharing._ID + " integer primary key autoincrement, " +
			SocialContract.Sharing.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.Sharing._ID_SERVICE + " integer not null default -1," +
			SocialContract.Sharing._ID_OWNER + " integer not null default -1, " +
			SocialContract.Sharing._ID_COMMUNITY + " integer not null default -1," +			
			SocialContract.Sharing.GLOBAL_ID_SERVICE + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Sharing.GLOBAL_ID_OWNER + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Sharing.GLOBAL_ID_COMMUNITY + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Sharing.TYPE + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Sharing.DESCRIPTION + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Sharing.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Sharing.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.Sharing.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Sharing.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.Sharing.DELETED + " integer not null default 0," +
			SocialContract.Sharing.DIRTY + " integer not null default 0," +
			SocialContract.Sharing.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Sharing.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Sharing.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.Sharing.SYNC4 + " integer not null default -1," +
			SocialContract.Sharing.SYNC5 + " integer not null default -1," +
			SocialContract.Sharing.SYNC6 + " integer not null default -1 );";

	public static final String PEOPLE_ACTIVITIY_TABLE_CREATE = "create table if not exists " + PEOPLE_ACTIVITIY_TABLE_NAME
			+ " (" + 
			SocialContract.PeopleActivity._ID + " integer primary key autoincrement, " +
			SocialContract.PeopleActivity.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.PeopleActivity.GLOBAL_ID_FEED_OWNER + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.PeopleActivity.ACTOR + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.PeopleActivity.OBJECT + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.PeopleActivity.VERB + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.PeopleActivity.TARGET + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.PeopleActivity.RELEVANCE + " integer not null default -1," +
			SocialContract.PeopleActivity.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.PeopleActivity.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.PeopleActivity.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.PeopleActivity.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.PeopleActivity.DELETED + " integer not null default 0," +
			SocialContract.PeopleActivity.DIRTY + " integer not null default 0," +
			SocialContract.PeopleActivity.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.PeopleActivity.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.PeopleActivity.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.PeopleActivity.SYNC4 + " integer not null default -1," +
			SocialContract.PeopleActivity.SYNC5 + " integer not null default -1," +
			SocialContract.PeopleActivity.SYNC6 + " integer not null default -1 );";

	public static final String COMMUNITIES_ACTIVITIY_TABLE_CREATE = "create table if not exists " + COMMUNITIES_ACTIVITIY_TABLE_NAME
			+ " (" + 
			SocialContract.CommunityActivity._ID + " integer primary key autoincrement, " +
			SocialContract.CommunityActivity.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.CommunityActivity._ID_FEED_OWNER + " integer not null default -1," +
			SocialContract.CommunityActivity.GLOBAL_ID_FEED_OWNER + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.CommunityActivity.ACTOR + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.CommunityActivity.OBJECT + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.CommunityActivity.VERB + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.CommunityActivity.TARGET + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.CommunityActivity.RELEVANCE + " integer not null default -1," +
			SocialContract.CommunityActivity.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.CommunityActivity.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.CommunityActivity.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.CommunityActivity.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.CommunityActivity.DELETED + " integer not null default 0," +
			SocialContract.CommunityActivity.DIRTY + " integer not null default 0," +
			SocialContract.CommunityActivity.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.CommunityActivity.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.CommunityActivity.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.CommunityActivity.SYNC4 + " integer not null default -1," +
			SocialContract.CommunityActivity.SYNC5 + " integer not null default -1," +
			SocialContract.CommunityActivity.SYNC6 + " integer not null default -1 );";

	public static final String SERVICES_ACTIVITIY_TABLE_CREATE = "create table if not exists " + SERVICES_ACTIVITIY_TABLE_NAME
			+ " (" + 
			SocialContract.ServiceActivity._ID + " integer primary key autoincrement, " +
			SocialContract.ServiceActivity.GLOBAL_ID + " text not null default "+SocialContract.GLOBAL_ID_PENDING+"," +
			SocialContract.ServiceActivity._ID_FEED_OWNER + " integer not null default -1," +
			SocialContract.ServiceActivity.GLOBAL_ID_FEED_OWNER + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.ServiceActivity.ACTOR + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.ServiceActivity.OBJECT + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.ServiceActivity.VERB + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.ServiceActivity.TARGET + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.ServiceActivity.RELEVANCE + " integer not null default -1," +
			SocialContract.ServiceActivity.CREATION_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.ServiceActivity.LAST_MODIFIED_DATE + " integer not null default "+SocialContract.DEFAULT_NOW_DATE+"," +
			SocialContract.ServiceActivity.ACCOUNT_NAME + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.ServiceActivity.ACCOUNT_TYPE + " text not null default " +SocialContract.ACCOUNT_TYPE_LOCAL+"," +
			SocialContract.ServiceActivity.DELETED + " integer not null default 0," +
			SocialContract.ServiceActivity.DIRTY + " integer not null default 0," +
			SocialContract.ServiceActivity.SYNC1 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.ServiceActivity.SYNC2 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.ServiceActivity.SYNC3 + " text not null default " +SocialContract.VALUE_NOT_DEFINED+"," +
			SocialContract.ServiceActivity.SYNC4 + " integer not null default -1," +
			SocialContract.ServiceActivity.SYNC5 + " integer not null default -1," +
			SocialContract.ServiceActivity.SYNC6 + " integer not null default -1 );";
}
