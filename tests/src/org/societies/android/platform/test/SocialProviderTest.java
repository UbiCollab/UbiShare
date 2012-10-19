/**
 * Copyright (c) 2011, SOCIETIES Consortium (WATERFORD INSTITUTE OF TECHNOLOGY (TSSG), HERIOT-WATT UNIVERSITY (HWU), SOLUTA.NET 
 * (SN), GERMAN AEROSPACE CENTRE (Deutsches Zentrum fuer Luft- und Raumfahrt e.V.) (DLR), Zavod za varnostne tehnologije
 * informacijske dru�be in elektronsko poslovanje (SETCCE), INSTITUTE OF COMMUNICATION AND COMPUTER SYSTEMS (ICCS), LAKE
 * COMMUNICATIONS (LAKE), INTEL PERFORMANCE LEARNING SOLUTIONS LTD (INTEL), PORTUGAL TELECOM INOVA��O, SA (PTIN), IBM Corp., 
 * INSTITUT TELECOM (ITSUD), AMITEC DIACHYTI EFYIA PLIROFORIKI KAI EPIKINONIES ETERIA PERIORISMENIS EFTHINIS (AMITEC), TELECOM 
 * ITALIA S.p.a.(TI),  TRIALOG (TRIALOG), Stiftelsen SINTEF (SINTEF), NEC EUROPE LTD (NEC))
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following
 * conditions are met:
 *
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 *
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 *    disclaimer in the documentation and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT 
 * SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package org.societies.android.platform.test;

import org.societies.android.api.cis.SocialContract;
import org.societies.android.platform.SocialProvider;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.test.ProviderTestCase2;
import android.test.RenamingDelegatingContext;
import android.test.mock.MockContentResolver;


/**
 * @author Babak.Farshchian@sintef.no
 *
 */
public class SocialProviderTest extends ProviderTestCase2<SocialProvider> {
	
	//This is the mock resolver that will do all the tests:
	private MockContentResolver resolver;
	private TestDataPopulator testPop;
	private RenamingDelegatingContext context;
	//This is the mock context where the provider runs:
	//private IsolatedContext context;
	
	public SocialProviderTest(){
		super(SocialProvider.class, SocialContract.AUTHORITY.getAuthority());
	}

	/* (non-Javadoc)
	 * @see android.test.ProviderTestCase2#setUp()
	 */
	@Override
	protected void setUp() throws Exception {
		// TODO Auto-generated method stub
		super.setUp();
		
		//Delegate context so that files and DBs during 
		//testing get a test_ prefix:
		//Also store the value of the context:
		context = new RenamingDelegatingContext(getContext(), "test_");
		//Store the value of the mock resolver:
		resolver = getMockContentResolver();
		//context = getMockContext();
		resolver.addProvider(SocialContract.AUTHORITY.getAuthority(), getProvider());
	}

	/* (non-Javadoc)
	 * @see android.test.AndroidTestCase#tearDown()
	 */
	@Override
	protected void tearDown() throws Exception {
		// TODO Auto-generated method stub
		super.tearDown();
	}
	
/**
 * Tests whether a Me record can be inserted and can be retrieved
 * using the GLOBAL_ID field.  
 */
public void testInsertMeQueryWithGlobalID(){
		testPop= new TestDataPopulator(resolver);
		testPop.populate();

		//Create local ContentValues to hold the initial membership data.
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Me.GLOBAL_ID , "ID1@societies.org");
		initialValues.put(SocialContract.Me.NAME , "ID1");
		initialValues.put(SocialContract.Me.DISPLAY_NAME , "ID1"); //Optional
		initialValues.put(SocialContract.Me.USER_NAME , "ID1");
		initialValues.put(SocialContract.Me.PASSWORD , "ID1");
		initialValues.put(SocialContract.Me.ORIGIN , "SOCIETIES");
		
		//Call insert in SocialProvider to initiate insertion.
		//resolver.insert(SocialContract.Me.CONTENT_URI, initialValues);
		
		//Try to query the newly inserted CIS from SocialProvider.
		//What columns to get:
		String[] projection ={
				SocialContract.Me.GLOBAL_ID,
				SocialContract.Me.NAME,
				SocialContract.Me.DISPLAY_NAME,
				SocialContract.Me.USER_NAME,
				SocialContract.Me.PASSWORD,
				SocialContract.Me.ORIGIN
			};
		//WHERE _id = ID of the newly created CIS:
		String selection = SocialContract.Membership.GLOBAL_ID + " = 'ID1@societies.org'";
		Cursor cursor = resolver.query(SocialContract.Me.CONTENT_URI,
				projection, selection, null, null);
		
		//Fail if the cursor is null:
		assertFalse(cursor == null);
		//if (cursor == null)	return;
		//Succeed if cursor is not empty:
		assertTrue(cursor.moveToFirst());
		//if (!cursor.moveToFirst()) return;
		//Fail if the record data are not correct:
		//Create new ContentValues object based on returned record:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Me.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.Me.NAME , cursor.getString(1));
		returnedValues.put(SocialContract.Me.DISPLAY_NAME, cursor.getString(2));
		returnedValues.put(SocialContract.Me.USER_NAME , cursor.getString(3));
		returnedValues.put(SocialContract.Me.PASSWORD , cursor.getString(4));
		returnedValues.put(SocialContract.Me.ORIGIN , cursor.getString(5));
		assertEquals(returnedValues,initialValues);
		cursor.close();
	}
//	public void testDelete(){
//	//TODO: probably add a CisRecord, then delete it.	
//		assertFalse(true);
//		
//	}

	/**
 * Test if you can read the people from test data.
 */
public void testInsertPeopleWithGlobalID(){
	//First, build the test DB:
	testPop= new TestDataPopulator(resolver);
	testPop.populate();
	
	//Then build a local contentvalues to hold the comparison data: 
	ContentValues initialValues = new ContentValues();
	initialValues.put(SocialContract.People.GLOBAL_ID , "ID1@societies.org");
	initialValues.put(SocialContract.People.NAME , "Person1");
	initialValues.put(SocialContract.People.EMAIL , "Person1");
	initialValues.put(SocialContract.People.ORIGIN , "SOCIETIES");
	initialValues.put(SocialContract.People.DESCRIPTION , "Person1");

	//Then build a query towards the DB with the comparison data 
	//as query parameters:
	String[] projection ={
			SocialContract.People.GLOBAL_ID,
			SocialContract.People.NAME,
			SocialContract.People.EMAIL,
			SocialContract.People.ORIGIN,
			SocialContract.People.DESCRIPTION
		};
	String selection = SocialContract.People.GLOBAL_ID + " = 'ID1@societies.org'";
	
	//run the query:
	Cursor cursor = resolver.query(SocialContract.People.CONTENT_URI,
			projection, selection, null, null);
	//Check if the cursor is valid:
	assertFalse(cursor == null);
	assertTrue(cursor.moveToFirst());
	
	//Compare to initial values:
	ContentValues returnedValues = new ContentValues();
	returnedValues.put(SocialContract.People.GLOBAL_ID , cursor.getString(0));
	returnedValues.put(SocialContract.People.NAME , cursor.getString(1));
	returnedValues.put(SocialContract.People.EMAIL , cursor.getString(2));
	returnedValues.put(SocialContract.People.ORIGIN , cursor.getString(3));
	returnedValues.put(SocialContract.People.DESCRIPTION , cursor.getString(4));
	assertEquals(returnedValues,initialValues);
	cursor.close();
}

	/**
	 * Test whether Communities record can be inserted and can
	 * be retrieved using the GLOBAL_ID field.
	 * 
	 * Note that when a community is inserted its GLOBAL_ID is set
	 * to "Pending" and is later set to a proper GLOBAL_ID by a sync
	 * adapter. So this test should handle both cases.
	 */
	public void testInsertCommunityQueryWithGlobalID(){
		//First, build the test DB:
		testPop= new TestDataPopulator(resolver);
		testPop.populate();

		//Create local ContentValues to hold the community data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Communities.GLOBAL_ID , "Community1@facebook.com");
		initialValues.put(SocialContract.Communities.NAME , "Community1");
		initialValues.put(SocialContract.Communities.OWNER_ID , "ID1@societies.org");
		initialValues.put(SocialContract.Communities.TYPE , "Community1");
		initialValues.put(SocialContract.Communities.DESCRIPTION , "Community1");
		initialValues.put(SocialContract.Communities.ORIGIN , "Facebook");
		
		
		//Get the community from SocialProvider:
		//Columns to get:
		String[] projection ={
				SocialContract.Communities.GLOBAL_ID,
				SocialContract.Communities.NAME,
				SocialContract.Communities.OWNER_ID,
				SocialContract.Communities.TYPE,
				SocialContract.Communities.DESCRIPTION,
				SocialContract.Communities.ORIGIN
			};
		//WHERE _id = ID of the newly created CIS:
		String selection = SocialContract.Communities.GLOBAL_ID + " = 'Community1@facebook.com'" ;
		//Run the query:
		Cursor cursor = resolver.query(SocialContract.Communities.CONTENT_URI,
				projection, selection, null, null);
		
		//Fail if cursor is null:
		assertFalse(cursor == null);
		//Succeed if cursor is not empty:
		assertTrue(cursor.moveToFirst());
		//Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Communities.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.Communities.NAME , cursor.getString(1));
		returnedValues.put(SocialContract.Communities.OWNER_ID , cursor.getString(2));
		returnedValues.put(SocialContract.Communities.TYPE , cursor.getString(3));
		returnedValues.put(SocialContract.Communities.DESCRIPTION , cursor.getString(4));
		returnedValues.put(SocialContract.Communities.ORIGIN , cursor.getString(5));
		//Since this community is just created we can assume it
		//is still pending: 		
		//TODO: Find a more elegant way to test this.
		//initialValues.put(SocialContract.Communities.GLOBAL_ID , "Pending");
		assertEquals(returnedValues,initialValues);
		cursor.close();
	}
	/**
	 * Test whether Services record can be inserted and can
	 * be retrieved using the GLOBAL_ID field.
	 * 
	 * Note that when a service is inserted its GLOBAL_ID is set
	 * to "Pending" and is later set to a proper GLOBAL_ID by a sync
	 * adapter. So this test should handle both cases.
	 */
	public void testInsertServiceQueryWithGlobalID(){
		//First, build the test DB:
		testPop= new TestDataPopulator(resolver);
		testPop.populate();

		//Create local ContentValues to hold the service data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Services.GLOBAL_ID , "Service1@play.google.com");
		initialValues.put(SocialContract.Services.TYPE , "Service1");
		initialValues.put(SocialContract.Services.NAME , "Service1");
		initialValues.put(SocialContract.Services.OWNER_ID, "Person3@facebook.com");
		initialValues.put(SocialContract.Services.ORIGIN, "Google Play");
		initialValues.put(SocialContract.Services.DESCRIPTION , "Service1");
		initialValues.put(SocialContract.Services.AVAILABLE, "false");
		initialValues.put(SocialContract.Services.DEPENDENCY, "Service4@ubicollab.org");
		initialValues.put(SocialContract.Services.CONFIG, "Service1");
		initialValues.put(SocialContract.Services.URL, "Service1");
		
		//Get the community from SocialProvider:
		//Columns to get:
		String[] projection ={
				SocialContract.Services.GLOBAL_ID,
				SocialContract.Services.NAME,
				SocialContract.Services.OWNER_ID,
				SocialContract.Services.TYPE,
				SocialContract.Services.DESCRIPTION,
				SocialContract.Services.APP_TYPE,
				SocialContract.Services.ORIGIN,
				SocialContract.Services.AVAILABLE,
				SocialContract.Services.DEPENDENCY,
				SocialContract.Services.CONFIG,
				SocialContract.Services.URL
			};
		//WHERE _id = ID of the newly created CIS:
		String selection = SocialContract.Services.GLOBAL_ID + " = 'Service1@play.google.com'";
		//Run the query:
		Cursor cursor = resolver.query(SocialContract.Services.CONTENT_URI,
				projection, selection, null, null);
		
		//Fail if cursor is null:
		assertFalse(cursor == null);
		//Succeed if cursor is not empty:
		assertTrue(cursor.moveToFirst());
		//Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Services.GLOBAL_ID, cursor.getString(0));
		returnedValues.put(SocialContract.Services.NAME , cursor.getString(1));
		returnedValues.put(SocialContract.Services.OWNER_ID , cursor.getString(2));
		returnedValues.put(SocialContract.Services.TYPE , cursor.getString(3));
		returnedValues.put(SocialContract.Services.DESCRIPTION , cursor.getString(4));
		returnedValues.put(SocialContract.Services.APP_TYPE , cursor.getString(5));
		returnedValues.put(SocialContract.Services.ORIGIN , cursor.getString(6));
		returnedValues.put(SocialContract.Services.AVAILABLE , cursor.getString(7));
		returnedValues.put(SocialContract.Services.DEPENDENCY , cursor.getString(8));
		returnedValues.put(SocialContract.Services.CONFIG , cursor.getString(9));
		returnedValues.put(SocialContract.Services.URL , cursor.getString(10));
		//Since this community is just created we can assume it
		//is still pending: 		
		//TODO: Find a more elegant way to test this.
		
	//	initialValues.put(SocialContract.Services.GLOBAL_ID , "Pending");
		assertEquals(returnedValues,initialValues);
		cursor.close();
	}
/**
	 * Add a membership, then check and see if it was added correctly.
	 */
	public void testInsertRelationshipWithGlobalID(){
		//First, build the test DB:
		testPop= new TestDataPopulator(resolver);
		testPop.populate();

		//1- Create local ContentValues to hold the initial membership data.
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Relationship.GLOBAL_ID , "rel1xyz@pending");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P1 , "ID1@societies.org");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P2, "Person2@pending");
		initialValues.put(SocialContract.Relationship.TYPE , "ID1 Person2");
		initialValues.put(SocialContract.Relationship.ORIGIN, "private");
		
		
		//3- Get the newly inserted CIS from SocialProvider.
		//What to get:
		String[] projection ={
				SocialContract.Relationship.GLOBAL_ID,
				SocialContract.Relationship.GLOBAL_ID_P1,
				SocialContract.Relationship.GLOBAL_ID_P2,
				SocialContract.Relationship.TYPE,
				SocialContract.Relationship.ORIGIN
			};
		//WHERE _id = ID of the newly created CIS:
		String selection = SocialContract.Relationship.GLOBAL_ID + " = 'rel1xyz@pending'";
		
		Cursor cursor = resolver.query(SocialContract.Relationship.CONTENT_URI,
				projection, selection, null, null);
		
		//4- Fail if the CIS was not returned.
		assertFalse(cursor == null);
		assertTrue(cursor.moveToFirst());
		//5- Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Relationship.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.Relationship.GLOBAL_ID_P1 , cursor.getString(1));
		returnedValues.put(SocialContract.Relationship.GLOBAL_ID_P2 , cursor.getString(2));
		returnedValues.put(SocialContract.Relationship.TYPE , cursor.getString(3));
		returnedValues.put(SocialContract.Relationship.ORIGIN , cursor.getString(4));
		assertEquals(returnedValues,initialValues);
		cursor.close();
	}
	public void testInsertMembershipWithGlobalID(){
		//First, build the test DB:
		testPop= new TestDataPopulator(resolver);
		testPop.populate();

		//1- Create local ContentValues to hold the initial membership data.
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Membership.GLOBAL_ID , "mem1xyz@facebook.com");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , "ID1@societies.org");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY, "Community1@facebook.com");
		initialValues.put(SocialContract.Membership.TYPE , "mem1xyz");
		initialValues.put(SocialContract.Membership.ORIGIN, "Facebook");
		
		
		//3- Get the newly inserted CIS from SocialProvider.
		//What to get:
		String[] projection ={
				SocialContract.Membership.GLOBAL_ID,
				SocialContract.Membership.GLOBAL_ID_MEMBER,
				SocialContract.Membership.GLOBAL_ID_COMMUNITY,
				SocialContract.Membership.TYPE,
				SocialContract.Membership.ORIGIN
			};
		//WHERE _id = ID of the newly created CIS:
		String selection = SocialContract.Membership.GLOBAL_ID + " = 'mem1xyz@facebook.com'";
		
		Cursor cursor = resolver.query(SocialContract.Membership.CONTENT_URI,
				projection, selection, null, null);
		
		//4- Fail if the CIS was not returned.
		assertFalse(cursor == null);
		assertTrue(cursor.moveToFirst());
		//5- Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Membership.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , cursor.getString(1));
		returnedValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY , cursor.getString(2));
		returnedValues.put(SocialContract.Membership.TYPE , cursor.getString(3));
		returnedValues.put(SocialContract.Membership.ORIGIN , cursor.getString(4));
		assertEquals(returnedValues,initialValues);
		cursor.close();
	}
	public void testInsertSharingWithGlobalID(){
		//First, build the test DB:
		testPop= new TestDataPopulator(resolver);
		testPop.populate();

		//1- Create local ContentValues to hold the initial membership data.
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Sharing.GLOBAL_ID , "sha1xyz@facebook.com");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_SERVICE , "Service1@play.google.com");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_OWNER , "Person3@facebook.com");		
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_COMMUNITY, "Community1@facebook.com");
		initialValues.put(SocialContract.Sharing.TYPE , "Service1");
		initialValues.put(SocialContract.Sharing.ORIGIN, "Facebook");
		
		
		//3- Get the newly inserted CIS from SocialProvider.
		//What to get:
		String[] projection ={
				SocialContract.Sharing.GLOBAL_ID,
				SocialContract.Sharing.GLOBAL_ID_SERVICE,
				SocialContract.Sharing.GLOBAL_ID_OWNER,
				SocialContract.Sharing.GLOBAL_ID_COMMUNITY,
				SocialContract.Sharing.TYPE,
				SocialContract.Sharing.ORIGIN
			};
		//WHERE _id = ID of the newly created CIS:
		String selection = SocialContract.Sharing.GLOBAL_ID + " = 'sha1xyz@facebook.com'";
		
		Cursor cursor = resolver.query(SocialContract.Sharing.CONTENT_URI,
				projection, selection, null, null);
		
		//4- Fail if the CIS was not returned.
		assertFalse(cursor == null);
		assertTrue(cursor.moveToFirst());
		//5- Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Sharing.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.Sharing.GLOBAL_ID_SERVICE , cursor.getString(1));
		returnedValues.put(SocialContract.Sharing.GLOBAL_ID_OWNER , cursor.getString(2));
		returnedValues.put(SocialContract.Sharing.GLOBAL_ID_COMMUNITY , cursor.getString(3));
		returnedValues.put(SocialContract.Sharing.TYPE , cursor.getString(4));
		returnedValues.put(SocialContract.Sharing.ORIGIN , cursor.getString(5));
		assertEquals(returnedValues,initialValues);
		cursor.close();
	}
	public void testInsertPeopleActivityWithGlobalID(){
		//First, build the test DB:
		testPop= new TestDataPopulator(resolver);
		testPop.populate();

		//1- Create local ContentValues to hold the initial membership data.
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID , "Activity1@facebook.com");
		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID_FEED_OWNER , "Person3@facebook.com");
		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID_ACTOR, "Actor1");
		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID_OBJECT , "Object1");
		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID_VERB, "Verb1");
		initialValues.put(SocialContract.PeopleActivity.GLOBAL_ID_TARGET, "Target1");
		initialValues.put(SocialContract.PeopleActivity.ORIGIN, "Facebook");
		
		
		//3- Get the newly inserted CIS from SocialProvider.
		//What to get:
		String[] projection ={
				SocialContract.PeopleActivity.GLOBAL_ID,
				SocialContract.PeopleActivity.GLOBAL_ID_FEED_OWNER,
				SocialContract.PeopleActivity.GLOBAL_ID_ACTOR,
				SocialContract.PeopleActivity.GLOBAL_ID_OBJECT,
				SocialContract.PeopleActivity.GLOBAL_ID_VERB,
				SocialContract.PeopleActivity.GLOBAL_ID_TARGET,
				SocialContract.PeopleActivity.ORIGIN
			};
		//WHERE _id = ID of the newly created CIS:
		String selection = SocialContract.PeopleActivity.GLOBAL_ID + " = 'Activity1@facebook.com'";
		
		Cursor cursor = resolver.query(SocialContract.PeopleActivity.CONTENT_URI,
				projection, selection, null, null);
		
		//4- Fail if the CIS was not returned.
		assertFalse(cursor == null);
		assertTrue(cursor.moveToFirst());
		//5- Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.PeopleActivity.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.PeopleActivity.GLOBAL_ID_FEED_OWNER , cursor.getString(1));
		returnedValues.put(SocialContract.PeopleActivity.GLOBAL_ID_ACTOR , cursor.getString(2));
		returnedValues.put(SocialContract.PeopleActivity.GLOBAL_ID_OBJECT , cursor.getString(3));
		returnedValues.put(SocialContract.PeopleActivity.GLOBAL_ID_VERB , cursor.getString(4));
		returnedValues.put(SocialContract.PeopleActivity.GLOBAL_ID_TARGET , cursor.getString(5));
		returnedValues.put(SocialContract.PeopleActivity.ORIGIN , cursor.getString(6));
		assertEquals(returnedValues,initialValues);
		cursor.close();
	}
	public void testInsertCommunityActivityWithGlobalID(){
		testPop= new TestDataPopulator(resolver);
		testPop.populate();
		//Then build a local contentvalues to hold the comparison data: 
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID , "Activity1@facebook.com");
		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID_FEED_OWNER , "Community1@facebook.com");
		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID_ACTOR, "Actor1");
		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID_OBJECT , "Object1");
		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID_VERB, "Verb1");
		initialValues.put(SocialContract.CommunityActivity.GLOBAL_ID_TARGET, "Target1");
		initialValues.put(SocialContract.CommunityActivity.ORIGIN, "Facebook");

		//Then build a query towards the DB with the comparison data 
		//as query parameters:
		String[] projection ={
				SocialContract.CommunityActivity.GLOBAL_ID,
				SocialContract.CommunityActivity.GLOBAL_ID_FEED_OWNER,
				SocialContract.CommunityActivity.GLOBAL_ID_ACTOR,
				SocialContract.CommunityActivity.GLOBAL_ID_OBJECT,
				SocialContract.CommunityActivity.GLOBAL_ID_VERB,
				SocialContract.CommunityActivity.GLOBAL_ID_TARGET,
				SocialContract.CommunityActivity.ORIGIN
			};
		String selection = SocialContract.CommunityActivity.GLOBAL_ID + " = 'Activity1@facebook.com'";
		
		//run the query:
		Cursor cursor = resolver.query(SocialContract.CommunityActivity.CONTENT_URI,
				projection, selection, null, null);
		//Check if the cursor is valid:
		assertFalse(cursor == null);
		assertTrue(cursor.moveToFirst());
		//Compare to initial values:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.CommunityActivity.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.CommunityActivity.GLOBAL_ID_FEED_OWNER , cursor.getString(1));
		returnedValues.put(SocialContract.CommunityActivity.GLOBAL_ID_ACTOR , cursor.getString(2));
		returnedValues.put(SocialContract.CommunityActivity.GLOBAL_ID_OBJECT , cursor.getString(3));
		returnedValues.put(SocialContract.CommunityActivity.GLOBAL_ID_VERB , cursor.getString(4));
		returnedValues.put(SocialContract.CommunityActivity.GLOBAL_ID_TARGET , cursor.getString(5));
		returnedValues.put(SocialContract.CommunityActivity.ORIGIN, cursor.getString(6));
		
		assertEquals(returnedValues,initialValues);
		cursor.close();
		
		
	}
	public void testInsertServiceActivityWithGlobalID(){
		testPop= new TestDataPopulator(resolver);
		testPop.populate();
		//Then build a local contentvalues to hold the comparison data: 
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID , "Activity1@google.com");
		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID_FEED_OWNER , "Service1@google.com");
		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID_ACTOR, "Actor1");
		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID_OBJECT , "Object1");
		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID_VERB, "Verb1");
		initialValues.put(SocialContract.ServiceActivity.GLOBAL_ID_TARGET, "Target1");
		initialValues.put(SocialContract.ServiceActivity.ORIGIN, "Google");

		//Then build a query towards the DB with the comparison data 
		//as query parameters:
		String[] projection ={
				SocialContract.ServiceActivity.GLOBAL_ID,
				SocialContract.ServiceActivity.GLOBAL_ID_FEED_OWNER,
				SocialContract.ServiceActivity.GLOBAL_ID_ACTOR,
				SocialContract.ServiceActivity.GLOBAL_ID_OBJECT,
				SocialContract.ServiceActivity.GLOBAL_ID_VERB,
				SocialContract.ServiceActivity.GLOBAL_ID_TARGET,
				SocialContract.ServiceActivity.ORIGIN
			};
		String selection = SocialContract.ServiceActivity.GLOBAL_ID + " = 'Activity1@google.com'";
		
		//run the query:
		Cursor cursor = resolver.query(SocialContract.ServiceActivity.CONTENT_URI,
				projection, selection, null, null);
		//Check if the cursor is valid:
		assertFalse(cursor == null);
		assertTrue(cursor.moveToFirst());
		//Compare to initial values:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.ServiceActivity.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.ServiceActivity.GLOBAL_ID_FEED_OWNER , cursor.getString(1));
		returnedValues.put(SocialContract.ServiceActivity.GLOBAL_ID_ACTOR , cursor.getString(2));
		returnedValues.put(SocialContract.ServiceActivity.GLOBAL_ID_OBJECT , cursor.getString(3));
		returnedValues.put(SocialContract.ServiceActivity.GLOBAL_ID_VERB , cursor.getString(4));
		returnedValues.put(SocialContract.ServiceActivity.GLOBAL_ID_TARGET , cursor.getString(5));
		returnedValues.put(SocialContract.ServiceActivity.ORIGIN, cursor.getString(6));
		
		assertEquals(returnedValues,initialValues);
		cursor.close();
		
		
	}

	/**
	 * Tests whether a Me record can be inserted and can be retrieved
	 * using the _ID field.  
	 */
	public void testInsertMeQueryWithID(){
			//Create local ContentValues to hold the initial membership data.
			ContentValues initialValues = new ContentValues();
			initialValues.put(SocialContract.Me.GLOBAL_ID , "babak@societies.org");
			initialValues.put(SocialContract.Me.NAME , "Babak Farshchian");
			initialValues.put(SocialContract.Me.DISPLAY_NAME , "Babak Societies"); //Optional
			initialValues.put(SocialContract.Me.USER_NAME , "babak@societies.org");
			initialValues.put(SocialContract.Me.PASSWORD , "XYz125");
			initialValues.put(SocialContract.Me.ORIGIN , "societies.org");
			
			//Call insert in SocialProvider to initiate insertion.
			//   Get _ID back and store it.
			Uri newMeUri= 
					resolver.insert(SocialContract.Me.CONTENT_URI, 
							initialValues);
			
			//Try to query the newly inserted CIS from SocialProvider.
			//What columns to get:
			String[] projection ={
					SocialContract.Me.GLOBAL_ID,
					SocialContract.Me.NAME,
					SocialContract.Me.DISPLAY_NAME,
					SocialContract.Me.USER_NAME,
					SocialContract.Me.PASSWORD,
					SocialContract.Me.ORIGIN
				};
			//WHERE _id = ID of the newly created CIS:
			String selection = SocialContract.Membership._ID + " = " +
				newMeUri.getLastPathSegment();
			Cursor cursor = resolver.query(SocialContract.Me.CONTENT_URI,
					projection, selection, null, null);
			
			//Fail if the cursor is null:
			assertFalse(cursor == null);
			//if (cursor == null)	return;
			//Succeed if cursor is not empty:
			assertTrue(cursor.moveToFirst());
			//if (!cursor.moveToFirst()) return;
			//Fail if the record data are not correct:
			//Create new ContentValues object based on returned record:
			ContentValues returnedValues = new ContentValues();
			returnedValues.put(SocialContract.Me.GLOBAL_ID , cursor.getString(0));
			returnedValues.put(SocialContract.Me.NAME , cursor.getString(1));
			returnedValues.put(SocialContract.Me.DISPLAY_NAME, cursor.getString(2));
			returnedValues.put(SocialContract.Me.USER_NAME , cursor.getString(3));
			returnedValues.put(SocialContract.Me.PASSWORD , cursor.getString(4));
			returnedValues.put(SocialContract.Me.ORIGIN , cursor.getString(5));
			assertEquals(returnedValues,initialValues);
			cursor.close();
		}
}
