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
			SocialContract.Me.GLOBAL_ID + " text, " +
			SocialContract.Me.NAME + " text," +
			SocialContract.Me.DISPLAY_NAME + " text," +
			SocialContract.Me.USER_NAME + " text," +
			SocialContract.Me.PASSWORD + " text," +
			SocialContract.Me.CREATION_DATE + " integer," +
			SocialContract.Me.LAST_MODIFIED_DATE + " integer," +
			SocialContract.Me.ACCOUNT_NAME + " text," +
			SocialContract.Me.ACCOUNT_TYPE + " text," +
			SocialContract.Me.DELETED + " integer," +
			SocialContract.Me.DIRTY + " integer," +
			SocialContract.Me.SYNC1 + " text," +
			SocialContract.Me.SYNC2 + " text," +
			SocialContract.Me.SYNC3 + " text," +
			SocialContract.Me.SYNC4 + " integer," +
			SocialContract.Me.SYNC5 + " integer," +
			SocialContract.Me.SYNC6 + " integer );";

	public static final String PEOPLE_TABLE_CREATE = "create table if not exists " + PEOPLE_TABLE_NAME
			+ " (" + 
			SocialContract.People._ID + " integer primary key autoincrement, " +
			SocialContract.People.GLOBAL_ID + " text, " +
			SocialContract.People.NAME + " text," +
			SocialContract.People.DESCRIPTION + " text," +
			SocialContract.People.EMAIL + " text," +
			SocialContract.People.RELEVANCE + " integer,"+
			SocialContract.People.CREATION_DATE + " integer," +
			SocialContract.People.LAST_MODIFIED_DATE + " integer," +
			SocialContract.People.ACCOUNT_NAME + " text," +
			SocialContract.People.ACCOUNT_TYPE + " text," +
			SocialContract.People.DELETED + " integer," +
			SocialContract.People.DIRTY + " integer," +
			SocialContract.People.SYNC1 + " text," +
			SocialContract.People.SYNC2 + " text," +
			SocialContract.People.SYNC3 + " text," +
			SocialContract.People.SYNC4 + " integer," +
			SocialContract.People.SYNC5 + " integer," +
			SocialContract.People.SYNC6 + " integer );";

	public static final String COMMUNITIES_TABLE_CREATE = "create table if not exists " + COMMUNITIES_TABLE_NAME
			+ " (" + 
			SocialContract.Communities._ID + " integer primary key autoincrement, " +
			SocialContract.Communities._ID_OWNER + " integer," +
			SocialContract.Communities.GLOBAL_ID + " text, " +
			SocialContract.Communities.NAME + " text," +
			SocialContract.Communities.OWNER_ID + " text," +
			SocialContract.Communities.TYPE + " text," +
			SocialContract.Communities.DESCRIPTION + " text," +
			SocialContract.Communities.RELEVANCE + " integer," +
			SocialContract.Communities.CREATION_DATE + " integer," +
			SocialContract.Communities.LAST_MODIFIED_DATE + " integer," +
			SocialContract.Communities.ACCOUNT_NAME + " text," +
			SocialContract.Communities.ACCOUNT_TYPE + " text," +
			SocialContract.Communities.DELETED + " integer," +
			SocialContract.Communities.DIRTY + " integer," +
			SocialContract.Communities.SYNC1 + " text," +
			SocialContract.Communities.SYNC2 + " text," +
			SocialContract.Communities.SYNC3 + " text," +
			SocialContract.Communities.SYNC4 + " integer," +
			SocialContract.Communities.SYNC5 + " integer," +
			SocialContract.Communities.SYNC6 + " integer );";

	public static final String SERVICES_TABLE_CREATE = "create table if not exists " + SERVICES_TABLE_NAME
			+ " (" + 
			SocialContract.Services._ID + " integer primary key autoincrement, " +
			SocialContract.Services._ID_OWNER + " integer," +
			SocialContract.Services.GLOBAL_ID + " text, " +
			SocialContract.Services.NAME + " text," +
			SocialContract.Services.OWNER_ID + " text," +
			SocialContract.Services.TYPE + " text," +
			SocialContract.Services.DESCRIPTION + " text," +
			SocialContract.Services.APP_TYPE + " text," +
			SocialContract.Services.AVAILABLE + " integer," +
			SocialContract.Services.DEPENDENCY + " text," +
			SocialContract.Services.CONFIG + " text," +
			SocialContract.Services.URL + " text," +
			SocialContract.Services.RELEVANCE + " integer," +
			SocialContract.Services.CREATION_DATE + " integer," +
			SocialContract.Services.LAST_MODIFIED_DATE + " integer," +
			SocialContract.Services.ACCOUNT_NAME + " text," +
			SocialContract.Services.ACCOUNT_TYPE + " text," +
			SocialContract.Services.DELETED + " integer," +
			SocialContract.Services.DIRTY + " integer," +
			SocialContract.Services.SYNC1 + " text," +
			SocialContract.Services.SYNC2 + " text," +
			SocialContract.Services.SYNC3 + " text," +
			SocialContract.Services.SYNC4 + " integer," +
			SocialContract.Services.SYNC5 + " integer," +
			SocialContract.Services.SYNC6 + " integer );";

	public static final String RELATIONSHIP_TABLE_CREATE = "create table if not exists " + RELATIONSHIP_TABLE_NAME
			+ " (" + 
			SocialContract.Relationship._ID + " integer primary key autoincrement, " +
			SocialContract.Relationship.GLOBAL_ID + " text, " +
			SocialContract.Relationship._ID_P1 + " integer," +
			SocialContract.Relationship._ID_P2 + " integer," +			
			SocialContract.Relationship.GLOBAL_ID_P1 + " text," +
			SocialContract.Relationship.GLOBAL_ID_P2 + " text," +
			SocialContract.Relationship.TYPE + " text," +
			SocialContract.Relationship.CREATION_DATE + " integer," +
			SocialContract.Relationship.LAST_MODIFIED_DATE + " integer," +
			SocialContract.Relationship.ACCOUNT_NAME + " text," +
			SocialContract.Relationship.ACCOUNT_TYPE + " text," +
			SocialContract.Relationship.DELETED + " integer," +
			SocialContract.Services.DIRTY + " integer," +
			SocialContract.Services.SYNC1 + " text," +
			SocialContract.Services.SYNC2 + " text," +
			SocialContract.Services.SYNC3 + " text," +
			SocialContract.Services.SYNC4 + " integer," +
			SocialContract.Services.SYNC5 + " integer," +
			SocialContract.Services.SYNC6 + " integer );";

	public static final String MEMBERSHIP_TABLE_CREATE = "create table if not exists " + MEMBERSHIP_TABLE_NAME
			+ " (" + 
			SocialContract.Membership._ID + " integer primary key autoincrement, " +
			SocialContract.Membership.GLOBAL_ID + " text, " +
			SocialContract.Membership._ID_MEMBER + " integer," +
			SocialContract.Membership._ID_COMMUNITY + " integer," +
			SocialContract.Membership.GLOBAL_ID_MEMBER + " text," +
			SocialContract.Membership.GLOBAL_ID_COMMUNITY + " text," +
			SocialContract.Membership.TYPE + " text not null," +
			SocialContract.Membership.CREATION_DATE + " integer," +
			SocialContract.Membership.LAST_MODIFIED_DATE + " integer," +
			SocialContract.Membership.ACCOUNT_NAME + " text," +
			SocialContract.Membership.ACCOUNT_TYPE + " text," +
			SocialContract.Membership.DELETED + " integer," +
			SocialContract.Services.SYNC1 + " text," +
			SocialContract.Services.SYNC2 + " text," +
			SocialContract.Services.SYNC3 + " text," +
			SocialContract.Services.SYNC4 + " integer," +
			SocialContract.Services.SYNC5 + " integer," +
			SocialContract.Services.SYNC6 + " integer );";

	public static final String SHARING_TABLE_CREATE = "create table if not exists " + SHARING_TABLE_NAME
			+ " (" + 
			SocialContract.Sharing._ID + " integer primary key autoincrement, " +
			SocialContract.Sharing.GLOBAL_ID + " text, " +
			SocialContract.Sharing._ID_SERVICE + " integer," +
			SocialContract.Sharing._ID_OWNER + " integer, " +
			SocialContract.Sharing._ID_COMMUNITY + " integer," +			
			SocialContract.Sharing.GLOBAL_ID_SERVICE + " text," +
			SocialContract.Sharing.GLOBAL_ID_OWNER + " text, " +
			SocialContract.Sharing.GLOBAL_ID_COMMUNITY + " text," +
			SocialContract.Sharing.TYPE + " text," +
			SocialContract.Sharing.CREATION_DATE + " integer," +
			SocialContract.Sharing.LAST_MODIFIED_DATE + " integer," +
			SocialContract.Sharing.ACCOUNT_NAME + " text," +
			SocialContract.Sharing.ACCOUNT_TYPE + " text," +
			SocialContract.Sharing.DELETED + " integer," +
			SocialContract.Services.SYNC1 + " text," +
			SocialContract.Services.SYNC2 + " text," +
			SocialContract.Services.SYNC3 + " text," +
			SocialContract.Services.SYNC4 + " integer," +
			SocialContract.Services.SYNC5 + " integer," +
			SocialContract.Services.SYNC6 + " integer );";

	public static final String PEOPLE_ACTIVITIY_TABLE_CREATE = "create table if not exists " + PEOPLE_ACTIVITIY_TABLE_NAME
			+ " (" + 
			SocialContract.PeopleActivity._ID + " integer primary key autoincrement, " +
			SocialContract.PeopleActivity.GLOBAL_ID + " text, " +
			SocialContract.PeopleActivity.GLOBAL_ID_FEED_OWNER + " text," +
			SocialContract.PeopleActivity.ACTOR + " text," +
			SocialContract.PeopleActivity.OBJECT + " text," +
			SocialContract.PeopleActivity.VERB + " text," +
			SocialContract.PeopleActivity.TARGET + " text," +
			SocialContract.PeopleActivity.RELEVANCE + " integer," +
			SocialContract.PeopleActivity.CREATION_DATE + " integer," +
			SocialContract.PeopleActivity.LAST_MODIFIED_DATE + " integer," +
			SocialContract.PeopleActivity.ACCOUNT_NAME + " text," +
			SocialContract.PeopleActivity.ACCOUNT_TYPE + " text," +
			SocialContract.PeopleActivity.DELETED + " integer," +
			SocialContract.PeopleActivity.SYNC1 + " text," +
			SocialContract.PeopleActivity.SYNC2 + " text," +
			SocialContract.PeopleActivity.SYNC3 + " text," +
			SocialContract.PeopleActivity.SYNC4 + " integer," +
			SocialContract.PeopleActivity.SYNC5 + " integer," +
			SocialContract.PeopleActivity.SYNC6 + " integer );";

	public static final String COMMUNITIES_ACTIVITIY_TABLE_CREATE = "create table if not exists " + COMMUNITIES_ACTIVITIY_TABLE_NAME
			+ " (" + 
			SocialContract.CommunityActivity._ID + " integer primary key autoincrement, " +
			SocialContract.CommunityActivity.GLOBAL_ID + " text, " +
			SocialContract.CommunityActivity._ID_FEED_OWNER + " integer," +
			SocialContract.CommunityActivity.GLOBAL_ID_FEED_OWNER + " text," +
			SocialContract.CommunityActivity.ACTOR + " text," +
			SocialContract.CommunityActivity.OBJECT + " text," +
			SocialContract.CommunityActivity.VERB + " text," +
			SocialContract.CommunityActivity.TARGET + " text," +
			SocialContract.CommunityActivity.RELEVANCE + " integer," +
			SocialContract.CommunityActivity.CREATION_DATE + " integer," +
			SocialContract.CommunityActivity.LAST_MODIFIED_DATE + " integer," +
			SocialContract.CommunityActivity.ACCOUNT_NAME + " text," +
			SocialContract.CommunityActivity.ACCOUNT_TYPE + " text," +
			SocialContract.CommunityActivity.DELETED + " integer," +
			SocialContract.CommunityActivity.SYNC1 + " text," +
			SocialContract.CommunityActivity.SYNC2 + " text," +
			SocialContract.CommunityActivity.SYNC3 + " text," +
			SocialContract.CommunityActivity.SYNC4 + " integer," +
			SocialContract.CommunityActivity.SYNC5 + " integer," +
			SocialContract.CommunityActivity.SYNC6 + " integer );";

	public static final String SERVICES_ACTIVITIY_TABLE_CREATE = "create table if not exists " + SERVICES_ACTIVITIY_TABLE_NAME
			+ " (" + 
			SocialContract.ServiceActivity._ID + " integer primary key autoincrement, " +
			SocialContract.ServiceActivity.GLOBAL_ID + " text, " +
			SocialContract.ServiceActivity._ID_FEED_OWNER + " integer," +
			SocialContract.ServiceActivity.GLOBAL_ID_FEED_OWNER + " text," +
			SocialContract.ServiceActivity.ACTOR + " text," +
			SocialContract.ServiceActivity.OBJECT + " text," +
			SocialContract.ServiceActivity.VERB + " text," +
			SocialContract.ServiceActivity.TARGET + " text," +
			SocialContract.ServiceActivity.RELEVANCE + " integer," +
			SocialContract.ServiceActivity.CREATION_DATE + " integer," +
			SocialContract.ServiceActivity.LAST_MODIFIED_DATE + " integer," +
			SocialContract.ServiceActivity.ACCOUNT_NAME + " text," +
			SocialContract.ServiceActivity.ACCOUNT_TYPE + " text," +
			SocialContract.ServiceActivity.DELETED + " integer," +
			SocialContract.ServiceActivity.SYNC1 + " text," +
			SocialContract.ServiceActivity.SYNC2 + " text," +
			SocialContract.ServiceActivity.SYNC3 + " text," +
			SocialContract.ServiceActivity.SYNC4 + " integer," +
			SocialContract.ServiceActivity.SYNC5 + " integer," +
			SocialContract.ServiceActivity.SYNC6 + " integer );";
}
