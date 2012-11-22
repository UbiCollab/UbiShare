package org.societies.android.platform.test;

import org.societies.android.api.cis.SocialContract;

import android.content.ContentValues;
import android.test.mock.MockContentResolver;

/**
 * This class populates a SQLite database for social provider
 * testing. All the JUnit tests for UbiShare operate on this
 * model. A graphical representation of the model is uploaded
 * to GitHub under folder /doc.
 * 
 * @author Babak Farshchian
 *
 */
public class TestDataPopulator {
	//private ISocialAdapter adapter = null;
	private MockContentResolver resolver;
	
	public TestDataPopulator(MockContentResolver _resolver){
		resolver = _resolver;
	}
	/**
	 * When you call this method in a test, a database should be
	 * created that contains the test data model.
	 * 
	 * @return nothing
	 */
	public boolean populate(){
   		populateMe();
   		populatePeople();
   		populateCommunities();
   		populateServices();
   		populateRelationships();
   		populateMemberships();
   		populateSharing();
   		populatePeopleActivities();
   		populateCommunityActivities();
   		populateServiceActivities();
   		return true;
	}
    private void populateMe(){
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Me.GLOBAL_ID , "ID1@societies.org");
		initialValues.put(SocialContract.Me.NAME , "ID1");
		initialValues.put(SocialContract.Me.DISPLAY_NAME , "ID1");
		initialValues.put(SocialContract.Me.USER_NAME , "ID1");
		initialValues.put(SocialContract.Me.PASSWORD , "ID1");
		initialValues.put(SocialContract.Me.ACCOUNT_TYPE , "SOCIETIES");
		resolver.insert(SocialContract.Me.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Me.GLOBAL_ID , "ID2@facebook.org");
		initialValues.put(SocialContract.Me.NAME , "ID2");
		initialValues.put(SocialContract.Me.DISPLAY_NAME , "ID2");
		initialValues.put(SocialContract.Me.USER_NAME , "ID2");
		initialValues.put(SocialContract.Me.PASSWORD , "ID2");
		initialValues.put(SocialContract.Me.ACCOUNT_TYPE , "Facebook");
		resolver.insert(SocialContract.Me.CONTENT_URI, initialValues);
	}
	private void populatePeople() {
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.People.GLOBAL_ID , "ID1@societies.org");
		initialValues.put(SocialContract.People.NAME , "Person1");
		initialValues.put(SocialContract.People.EMAIL , "Person1");
		initialValues.put(SocialContract.People.ACCOUNT_TYPE , "SOCIETIES");
		initialValues.put(SocialContract.People.DESCRIPTION , "Person1");
		resolver.insert(SocialContract.People.CONTENT_URI, initialValues);
		
		initialValues.clear();

		initialValues.put(SocialContract.People.GLOBAL_ID , "Person2@pending");
		initialValues.put(SocialContract.People.NAME , "Person2");
		initialValues.put(SocialContract.People.EMAIL , "Person2");
		initialValues.put(SocialContract.People.ACCOUNT_TYPE , "private");
		initialValues.put(SocialContract.People.DESCRIPTION , "Person2");
		resolver.insert(SocialContract.People.CONTENT_URI, initialValues);
		
		initialValues.clear();

		initialValues.put(SocialContract.People.GLOBAL_ID , "Person3@facebook.com");
		initialValues.put(SocialContract.People.NAME , "Person3");
		initialValues.put(SocialContract.People.EMAIL , "Person3");
		initialValues.put(SocialContract.People.ACCOUNT_TYPE , "Facebook");
		initialValues.put(SocialContract.People.DESCRIPTION , "Person3");
		resolver.insert(SocialContract.People.CONTENT_URI, initialValues);

		initialValues.clear();

		initialValues.put(SocialContract.People.GLOBAL_ID , "Person4@google.com");
		initialValues.put(SocialContract.People.NAME , "Person4");
		initialValues.put(SocialContract.People.EMAIL , "Person4");
		initialValues.put(SocialContract.People.ACCOUNT_TYPE , "Google+");
		initialValues.put(SocialContract.People.DESCRIPTION , "Person4");
		resolver.insert(SocialContract.People.CONTENT_URI, initialValues);

		initialValues.clear();

		initialValues.put(SocialContract.People.GLOBAL_ID , "Person5@twitter.com");
		initialValues.put(SocialContract.People.NAME , "Person5");
		initialValues.put(SocialContract.People.EMAIL , "Person5");
		initialValues.put(SocialContract.People.ACCOUNT_TYPE , "Twitter");
		initialValues.put(SocialContract.People.DESCRIPTION , "Person5");
		resolver.insert(SocialContract.People.CONTENT_URI, initialValues);

		initialValues.clear();

		initialValues.put(SocialContract.People.GLOBAL_ID , "Person6@pending");
		initialValues.put(SocialContract.People.NAME , "Person6");
		initialValues.put(SocialContract.People.EMAIL , "Person6");
		initialValues.put(SocialContract.People.ACCOUNT_TYPE , "private");
		initialValues.put(SocialContract.People.DESCRIPTION , "Person6");
		resolver.insert(SocialContract.People.CONTENT_URI, initialValues);
	}
	private void populateCommunities(){
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Communities.GLOBAL_ID , "Community1@facebook.com");
		initialValues.put(SocialContract.Communities.TYPE , "Community1");
		initialValues.put(SocialContract.Communities.NAME , "Community1");
		initialValues.put(SocialContract.Communities.OWNER_ID, "ID1@societies.org");
		initialValues.put(SocialContract.Communities.ACCOUNT_TYPE, "Facebook");
		initialValues.put(SocialContract.Communities.DESCRIPTION , "Community1");
		resolver.insert(SocialContract.Communities.CONTENT_URI, initialValues);

		initialValues.clear();
	
		initialValues.put(SocialContract.Communities.GLOBAL_ID , "Community2@societies.org");
		initialValues.put(SocialContract.Communities.TYPE , "Community2");
		initialValues.put(SocialContract.Communities.NAME , "Community2");
		initialValues.put(SocialContract.Communities.OWNER_ID, "Person3@facebook.com");
		initialValues.put(SocialContract.Communities.ACCOUNT_TYPE, "SOCIETIES");
		initialValues.put(SocialContract.Communities.DESCRIPTION , "Community2");
		resolver.insert(SocialContract.Communities.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Communities.GLOBAL_ID , "Community3@pending");
		initialValues.put(SocialContract.Communities.TYPE , "Community3");
		initialValues.put(SocialContract.Communities.NAME , "Community3");
		initialValues.put(SocialContract.Communities.OWNER_ID, "ID1@societies.org");
		initialValues.put(SocialContract.Communities.ACCOUNT_TYPE, "Facebook");
		initialValues.put(SocialContract.Communities.DESCRIPTION , "Community3");
		resolver.insert(SocialContract.Communities.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Communities.GLOBAL_ID , "Community4@google.com");
		initialValues.put(SocialContract.Communities.TYPE , "Community4");
		initialValues.put(SocialContract.Communities.NAME , "Community4");
		initialValues.put(SocialContract.Communities.OWNER_ID, "Person4@google.com");
		initialValues.put(SocialContract.Communities.ACCOUNT_TYPE, "Google+");
		initialValues.put(SocialContract.Communities.DESCRIPTION , "Community4");
		resolver.insert(SocialContract.Communities.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Communities.GLOBAL_ID , "Community5@pending");
		initialValues.put(SocialContract.Communities.TYPE , "Community5");
		initialValues.put(SocialContract.Communities.NAME , "Community5");
		initialValues.put(SocialContract.Communities.OWNER_ID, "ID1@societies.org");
		initialValues.put(SocialContract.Communities.ACCOUNT_TYPE, "Box.com");
		initialValues.put(SocialContract.Communities.DESCRIPTION , "Community5");
		resolver.insert(SocialContract.Communities.CONTENT_URI, initialValues);
	}
	private void populateServices() {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Services.GLOBAL_ID , "Service1@play.google.com");
		initialValues.put(SocialContract.Services.TYPE , "Service1");
		initialValues.put(SocialContract.Services.APP_TYPE , "Service1");
		initialValues.put(SocialContract.Services.NAME , "Service1");
		initialValues.put(SocialContract.Services.OWNER_ID, "Person3@facebook.com");
		initialValues.put(SocialContract.Services.ACCOUNT_TYPE, "Google Play");
		initialValues.put(SocialContract.Services.DESCRIPTION , "Service1");
		initialValues.put(SocialContract.Services.AVAILABLE, "false");
		initialValues.put(SocialContract.Services.DEPENDENCY, "Service4@ubicollab.org");
		initialValues.put(SocialContract.Services.CONFIG, "Service1");
		initialValues.put(SocialContract.Services.URL, "Service1");
		resolver.insert(SocialContract.Services.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Services.GLOBAL_ID , "Service2@play.google.com");
		initialValues.put(SocialContract.Services.TYPE , "Service2");
		initialValues.put(SocialContract.Services.APP_TYPE , "Service1");
		initialValues.put(SocialContract.Services.NAME , "Service2");
		initialValues.put(SocialContract.Services.OWNER_ID, "ID1@societies.org");
		initialValues.put(SocialContract.Services.ACCOUNT_TYPE, "Google Play");
		initialValues.put(SocialContract.Services.DESCRIPTION , "Service2");
		initialValues.put(SocialContract.Services.AVAILABLE, "true");
		initialValues.put(SocialContract.Services.DEPENDENCY, "na");
		initialValues.put(SocialContract.Services.CONFIG, "Service2");
		initialValues.put(SocialContract.Services.URL, "Service2");
		resolver.insert(SocialContract.Services.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Services.GLOBAL_ID , "Service3@societies.org");
		initialValues.put(SocialContract.Services.TYPE , "Service3");
		initialValues.put(SocialContract.Services.APP_TYPE , "Service1");
		initialValues.put(SocialContract.Services.NAME , "Service3");
		initialValues.put(SocialContract.Services.OWNER_ID, "ID1@societies.org");
		initialValues.put(SocialContract.Services.ACCOUNT_TYPE, "SOCIETIES.org");
		initialValues.put(SocialContract.Services.DESCRIPTION , "Service3");
		initialValues.put(SocialContract.Services.AVAILABLE, "false");
		initialValues.put(SocialContract.Services.DEPENDENCY, "na");
		initialValues.put(SocialContract.Services.CONFIG, "Service3");
		initialValues.put(SocialContract.Services.URL, "Service3");
		resolver.insert(SocialContract.Services.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Services.GLOBAL_ID , "Service4@ubicollab.org");
		initialValues.put(SocialContract.Services.TYPE , "Service4");
		initialValues.put(SocialContract.Services.APP_TYPE , "Service1");
		initialValues.put(SocialContract.Services.NAME , "Service4");
		initialValues.put(SocialContract.Services.OWNER_ID, "ID1@societies.org");
		initialValues.put(SocialContract.Services.ACCOUNT_TYPE, "UbiCollab.org");
		initialValues.put(SocialContract.Services.DESCRIPTION , "Service4");
		initialValues.put(SocialContract.Services.AVAILABLE, "false");
		initialValues.put(SocialContract.Services.DEPENDENCY, "na");
		initialValues.put(SocialContract.Services.CONFIG, "Service4");
		initialValues.put(SocialContract.Services.URL, "Service4");
		resolver.insert(SocialContract.Services.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Services.GLOBAL_ID , "Service5@amazon.com");
		initialValues.put(SocialContract.Services.TYPE , "Service5");
		initialValues.put(SocialContract.Services.APP_TYPE , "Service1");
		initialValues.put(SocialContract.Services.NAME , "Service5");
		initialValues.put(SocialContract.Services.OWNER_ID, "Person3@facebook.com");
		initialValues.put(SocialContract.Services.ACCOUNT_TYPE, "Amazon.com");
		initialValues.put(SocialContract.Services.DESCRIPTION , "Service5");
		initialValues.put(SocialContract.Services.AVAILABLE, "false");
		initialValues.put(SocialContract.Services.DEPENDENCY, "na");
		initialValues.put(SocialContract.Services.CONFIG, "Service5");
		initialValues.put(SocialContract.Services.URL, "Service5");
		resolver.insert(SocialContract.Services.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Services.GLOBAL_ID , "Service6@amazon.com");
		initialValues.put(SocialContract.Services.TYPE , "Service6");
		initialValues.put(SocialContract.Services.APP_TYPE , "Service1");
		initialValues.put(SocialContract.Services.NAME , "Service6");
		initialValues.put(SocialContract.Services.OWNER_ID, "Person4@google.com");
		initialValues.put(SocialContract.Services.ACCOUNT_TYPE, "Amazon.com");
		initialValues.put(SocialContract.Services.DESCRIPTION , "Service6");
		initialValues.put(SocialContract.Services.AVAILABLE, "true");
		initialValues.put(SocialContract.Services.DEPENDENCY, "na");
		initialValues.put(SocialContract.Services.CONFIG, "Service6");
		initialValues.put(SocialContract.Services.URL, "Service6");
		resolver.insert(SocialContract.Services.CONTENT_URI, initialValues);

	}
	private void populateRelationships() {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Relationship.GLOBAL_ID , "rel1xyz@pending");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P1 , "ID1@societies.org");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P2, "Person2@pending");
		initialValues.put(SocialContract.Relationship.TYPE , "ID1 Person2");
		initialValues.put(SocialContract.Relationship.ACCOUNT_TYPE, "private");
		resolver.insert(SocialContract.Relationship.CONTENT_URI, initialValues);
		
		initialValues.clear();

		initialValues.put(SocialContract.Relationship.GLOBAL_ID , "rel2xyz@facebook.com");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P1 , "ID1@societies.org");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P2, "Person3@facebook.com");
		initialValues.put(SocialContract.Relationship.TYPE , "friend");
		initialValues.put(SocialContract.Relationship.ACCOUNT_TYPE, "Facebook");
		resolver.insert(SocialContract.Relationship.CONTENT_URI, initialValues);
		
		initialValues.clear();

		initialValues.put(SocialContract.Relationship.GLOBAL_ID , "rel3xyz@google.com");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P1 , "ID1@societies.org");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P2, "Person4@google.com");
		initialValues.put(SocialContract.Relationship.TYPE , "circled");
		initialValues.put(SocialContract.Relationship.ACCOUNT_TYPE, "Google+");
		resolver.insert(SocialContract.Relationship.CONTENT_URI, initialValues);
		
		initialValues.clear();
		
		initialValues.put(SocialContract.Relationship.GLOBAL_ID , "rel4xyz@twitter.com");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P1 , "ID1@societies.org");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P2, "Person5@twitter.com");
		initialValues.put(SocialContract.Relationship.TYPE , "follower");
		initialValues.put(SocialContract.Relationship.ACCOUNT_TYPE, "Twitter");
		resolver.insert(SocialContract.Relationship.CONTENT_URI, initialValues);
	}
	private void populateMemberships() {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Membership.GLOBAL_ID , "mem1xyz@facebook.com");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , "ID1@societies.org");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY, "Community1@facebook.com");
		initialValues.put(SocialContract.Membership.TYPE , "mem1xyz");
		initialValues.put(SocialContract.Membership.ACCOUNT_TYPE, "Facebook");
		resolver.insert(SocialContract.Membership.CONTENT_URI, initialValues);
		
		initialValues.clear();
		
		initialValues.put(SocialContract.Membership.GLOBAL_ID , "mem2xyz@societies.org");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , "ID1@societies.org");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY, "Community2@societies.org");
		initialValues.put(SocialContract.Membership.TYPE , "mem2xyz");
		initialValues.put(SocialContract.Membership.ACCOUNT_TYPE, "SOCIETIES");
		resolver.insert(SocialContract.Membership.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Membership.GLOBAL_ID , "mem3xyz@societies.org");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , "Person3@facebook.com");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY, "Community2@societies.org");
		initialValues.put(SocialContract.Membership.TYPE , "mem3xyz");
		initialValues.put(SocialContract.Membership.ACCOUNT_TYPE, "SOCIETIES");
		resolver.insert(SocialContract.Membership.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Membership.GLOBAL_ID , "mem4xyz@facebook.com");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , "Person3@facebook.com");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY, "Community1@facebook.com");
		initialValues.put(SocialContract.Membership.TYPE , "mem4xyz");
		initialValues.put(SocialContract.Membership.ACCOUNT_TYPE, "Facebook");
		resolver.insert(SocialContract.Membership.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Membership.GLOBAL_ID , "mem5xyz@facebook.com");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , "Person5@twitter.com");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY, "Community2@societies.org");
		initialValues.put(SocialContract.Membership.TYPE , "mem5xyz");
		initialValues.put(SocialContract.Membership.ACCOUNT_TYPE, "SOCIETIES");
		resolver.insert(SocialContract.Membership.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Membership.GLOBAL_ID , "mem6xyz@google.com");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , "Person4@google.com");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY, "Community4@google.com");
		initialValues.put(SocialContract.Membership.TYPE , "mem5xyz");
		initialValues.put(SocialContract.Membership.ACCOUNT_TYPE, "Google");
		resolver.insert(SocialContract.Membership.CONTENT_URI, initialValues);
	}
	private void populateSharing() {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Sharing.GLOBAL_ID , "sha1xyz@facebook.com");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_SERVICE , "Service1@play.google.com");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_OWNER , "Person3@facebook.com");		
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_COMMUNITY, "Community1@facebook.com");
		initialValues.put(SocialContract.Sharing.TYPE , "Service1");
		initialValues.put(SocialContract.Sharing.ACCOUNT_TYPE, "Facebook");
		resolver.insert(SocialContract.Sharing.CONTENT_URI, initialValues);
		
		initialValues.clear();
		
		initialValues.put(SocialContract.Sharing.GLOBAL_ID , "sha2xyz@facebook.com");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_SERVICE , "Service5@amazon.com");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_OWNER , "Person3@facebook.com");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_COMMUNITY, "Community1@facebook.com");
		initialValues.put(SocialContract.Sharing.TYPE , "Service5");
		initialValues.put(SocialContract.Sharing.ACCOUNT_TYPE, "Facebook");
		resolver.insert(SocialContract.Sharing.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Sharing.GLOBAL_ID , "sha3xyz@societies.org");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_SERVICE , "Service6@amazon.com");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_OWNER , "Person4@google.com");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_COMMUNITY, "Community2@societies.org");
		initialValues.put(SocialContract.Sharing.TYPE , "Service6");
		initialValues.put(SocialContract.Sharing.ACCOUNT_TYPE, "SOCIETIES");
		resolver.insert(SocialContract.Sharing.CONTENT_URI, initialValues);
	}
	private void populatePeopleActivities() {
		ContentValues initialValues = new ContentValues();

		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID , "Activity1@facebook.com");
		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID_FEED_OWNER , "Person3@facebook.com");
		initialValues.put(SocialContract.PeopleActivity.ACTOR, "Actor1");
		initialValues.put(SocialContract.PeopleActivity.OBJECT , "Object1");
		initialValues.put(SocialContract.PeopleActivity.VERB, "Verb1");
		initialValues.put(SocialContract.PeopleActivity.TARGET, "Target1");
		initialValues.put(SocialContract.PeopleActivity.ACCOUNT_TYPE, "Facebook");
		resolver.insert(SocialContract.PeopleActivity.CONTENT_URI, initialValues);

		initialValues.clear();

		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID , "Activity2@google.com");
		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID_FEED_OWNER , "Person4@google.com");
		initialValues.put(SocialContract.PeopleActivity.ACTOR, "Actor2");
		initialValues.put(SocialContract.PeopleActivity.OBJECT , "Object2");
		initialValues.put(SocialContract.PeopleActivity.VERB, "Verb2");
		initialValues.put(SocialContract.PeopleActivity.TARGET, "Target2");
		initialValues.put(SocialContract.PeopleActivity.ACCOUNT_TYPE, "Google");
		resolver.insert(SocialContract.PeopleActivity.CONTENT_URI, initialValues);

		initialValues.clear();

		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID , "Activity3@pending");
		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID_FEED_OWNER , "ID1@societies.org");
		initialValues.put(SocialContract.PeopleActivity.ACTOR, "Actor3");
		initialValues.put(SocialContract.PeopleActivity.OBJECT , "Object3");
		initialValues.put(SocialContract.PeopleActivity.VERB, "Verb3");
		initialValues.put(SocialContract.PeopleActivity.TARGET, "Target3");
		initialValues.put(SocialContract.PeopleActivity.ACCOUNT_TYPE, "SOCIETIES");
		resolver.insert(SocialContract.PeopleActivity.CONTENT_URI, initialValues);
	}
	private void populateCommunityActivities() {
		ContentValues initialValues = new ContentValues();

		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID , "Activity1@facebook.com");
		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID_FEED_OWNER , "Community1@facebook.com");
		initialValues.put(SocialContract.CommunityActivity.ACTOR, "Actor1");
		initialValues.put(SocialContract.CommunityActivity.OBJECT , "Object1");
		initialValues.put(SocialContract.CommunityActivity.VERB, "Verb1");
		initialValues.put(SocialContract.CommunityActivity.TARGET, "Target1");
		initialValues.put(SocialContract.CommunityActivity.ACCOUNT_TYPE, "Facebook");
		resolver.insert(SocialContract.CommunityActivity.CONTENT_URI, initialValues);

		initialValues.clear();

		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID , "Activity2@Box.com");
		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID_FEED_OWNER , "Community5@Box.com");
		initialValues.put(SocialContract.CommunityActivity.ACTOR, "Actor2");
		initialValues.put(SocialContract.CommunityActivity.OBJECT , "Object2");
		initialValues.put(SocialContract.CommunityActivity.VERB, "Verb2");
		initialValues.put(SocialContract.CommunityActivity.TARGET, "Target2");
		initialValues.put(SocialContract.CommunityActivity.ACCOUNT_TYPE, "Box.com");
		resolver.insert(SocialContract.CommunityActivity.CONTENT_URI, initialValues);
		
	}
	private void populateServiceActivities() {
		ContentValues initialValues = new ContentValues();

		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID , "Activity1@google.com");
		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID_FEED_OWNER , "Service1@google.com");
		initialValues.put(SocialContract.ServiceActivity.ACTOR, "Actor1");
		initialValues.put(SocialContract.ServiceActivity.OBJECT , "Object1");
		initialValues.put(SocialContract.ServiceActivity.VERB, "Verb1");
		initialValues.put(SocialContract.ServiceActivity.TARGET, "Target1");
		initialValues.put(SocialContract.ServiceActivity.ACCOUNT_TYPE, "Google");
		resolver.insert(SocialContract.ServiceActivity.CONTENT_URI, initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID , "Activity2@societies.org");
		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID_FEED_OWNER , "Service3@societies.org");
		initialValues.put(SocialContract.ServiceActivity.ACTOR, "Actor2");
		initialValues.put(SocialContract.ServiceActivity.OBJECT , "Object2");
		initialValues.put(SocialContract.ServiceActivity.VERB, "Verb2");
		initialValues.put(SocialContract.ServiceActivity.TARGET, "Target2");
		initialValues.put(SocialContract.ServiceActivity.ACCOUNT_TYPE, "SOCIETIES");
		resolver.insert(SocialContract.ServiceActivity.CONTENT_URI, initialValues);
	}

}
