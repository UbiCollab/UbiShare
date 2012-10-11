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
import org.societies.android.platform.LocalDBAdapter;
import org.societies.android.platform.SocialDataSet;
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
		RenamingDelegatingContext context 
        = new RenamingDelegatingContext(getContext(), "test_");
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
	}

/**
 * Tests whether a Me record can be inserted and can be retrieved
 * using the GLOBAL_ID field.  
 */
public void testInsertMeQueryWithGlobalID(){
		//Create local ContentValues to hold the initial membership data.
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Me.GLOBAL_ID , "babak@societies.org");
		initialValues.put(SocialContract.Me.NAME , "Babak Farshchian");
		initialValues.put(SocialContract.Me.DISPLAY_NAME , "Babak Societies"); //Optional
		initialValues.put(SocialContract.Me.USER_NAME , "babak@societies.org");
		initialValues.put(SocialContract.Me.PASSWORD , "XYz125");
		initialValues.put(SocialContract.Me.ORIGIN , "societies.org");
		
		//Call insert in SocialProvider to initiate insertion.
		resolver.insert(SocialContract.Me.CONTENT_URI, initialValues);
		
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
		String selection = SocialContract.Membership.GLOBAL_ID + " = 'babak@societies.org'";
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
	}
//	public void testDelete(){
//	//TODO: probably add a CisRecord, then delete it.	
//		assertFalse(true);
//		
//	}

	/**
	 * Test to see if information about the user is retrievable
	 * from {@link SocialProvider}.
	 * 
	 <ul>
	 * <li>create ContentValues based on an entry in {@link SocialDataSet}.</li>
	 * <li>Query {@link SocialProvider} based on GLOBAL_ID.</li>
	 * <li>Check to see if it is the same as added in {@link SocialDataSet}.</li>
	 * </ul> 
	 *  
	 */
	public void testQueryMe(){
	
		 // 1- create ContentValues for comparison person:
		//FIXME: based on test data. Needs to be independent from test data.
	
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Me.GLOBAL_ID , "knut@redcross.org");
		initialValues.put(SocialContract.Me.NAME , "Knut Roedhale");
		initialValues.put(SocialContract.Me.DISPLAY_NAME , "knut@redcross");
		initialValues.put(SocialContract.Me.USER_NAME , "knutr");
		initialValues.put(SocialContract.Me.PASSWORD , "XYZ123");
		initialValues.put(SocialContract.Me.ORIGIN , "Red Cross");
	    resolver.insert(SocialContract.Me.CONTENT_URI, initialValues);
		 // 2- read a person with the same global_id from
		//  from {@link SocialProvider}:
		
		String[] projection ={
				SocialContract.Me.GLOBAL_ID,
				SocialContract.Me.NAME,
				SocialContract.Me.DISPLAY_NAME,
				SocialContract.Me.USER_NAME,
				SocialContract.Me.PASSWORD,
				SocialContract.Me.ORIGIN
			};
		//Select based on global_id:
		//String selection = SocialContract.Me.GLOBAL_ID + "=?";
		//String[] selectionArgs = new String[]{"knut@redcross.org"};
		String selection = SocialContract.Me.GLOBAL_ID + "='knut@redcross.org'";
		Cursor cursor = resolver.query(SocialContract.Me.CONTENT_URI,
				projection, selection, null, null);
		
		//4- Fail if the person data was not found.
		//   If cursor is empty it is not null but its length is zero
		//   meaning moveToFirst will return false.
		assertFalse(cursor == null);
		//if (cursor == null)	return;
		assertTrue(cursor.moveToFirst());
		if (!cursor.moveToFirst()) return;
		//cursor.moveToNext();
		//5- Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Me.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.Me.NAME , cursor.getString(1));
		returnedValues.put(SocialContract.Me.DISPLAY_NAME , cursor.getString(2));
		returnedValues.put(SocialContract.Me.USER_NAME , cursor.getString(3));
		returnedValues.put(SocialContract.Me.PASSWORD , cursor.getString(4));
		returnedValues.put(SocialContract.Me.ORIGIN , cursor.getString(5));
		assertEquals(returnedValues,initialValues);
	}

	public void testUpdateMe(){
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Me.GLOBAL_ID , "babak@societies.org");
		initialValues.put(SocialContract.Me.NAME , "Babak Farshchian");
		initialValues.put(SocialContract.Me.DISPLAY_NAME , "Babak F");
		
		resolver.update(SocialContract.Me.CONTENT_URI , 
				initialValues, null, null);		
		
		String[] projection ={
				SocialContract.Me.GLOBAL_ID,
				SocialContract.Me.NAME,
				SocialContract.Me.DISPLAY_NAME
				};
		
		String selection = SocialContract.Me._ID + " = 1";
		Cursor cursor = resolver.query(SocialContract.Me.CONTENT_URI,
				projection, selection, null, null);
		
		//4- Fail if the CIS was not returned.
		assertFalse(cursor == null);
		if (cursor == null)	return;
		if (!cursor.moveToFirst()) return;
		//5- Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Me.GLOBAL_ID , cursor.getString(0));
		returnedValues.put(SocialContract.Me.NAME , cursor.getString(1));
		returnedValues.put(SocialContract.Me.DISPLAY_NAME , cursor.getString(2));
		assertEquals(returnedValues,initialValues);
	
	}

	/**
	 * Test whether Communities record can be inserted and can
	 * be retrieved using the _ID field.
	 */
	public void testInsertCommunityQueryWithID(){
		//Create local ContentValues to hold the community data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Communities.NAME , "SOCIETIES football team");
		initialValues.put(SocialContract.Communities.OWNER_ID , "babak@societies.org");
		initialValues.put(SocialContract.Communities.TYPE , "sports");
		initialValues.put(SocialContract.Communities.DESCRIPTION , "Hurray!!");
		initialValues.put(SocialContract.Communities.ORIGIN , "SOCIETIES");
		
		//Call insert in SocialProvider to initiate insertion:
		//Store _ID for later query:
		Uri newCommunityUri= 
				resolver.insert(SocialContract.Communities.CONTENT_URI, 
						initialValues);
		
		//Get the newly inserted community from SocialProvider:
		//Columns to get:
		String[] projection ={
				SocialContract.Communities.NAME,
				SocialContract.Communities.OWNER_ID,
				SocialContract.Communities.TYPE,
				SocialContract.Communities.DESCRIPTION,
				SocialContract.Communities.ORIGIN
			};
		//WHERE _id = ID of the newly created CIS:
		String selection = SocialContract.Communities._ID + " = " +
			newCommunityUri.getLastPathSegment();
		//Run the query:
		Cursor cursor = resolver.query(SocialContract.Communities.CONTENT_URI,
				projection, selection, null, null);
		
		//Fail if cursor is null:
		assertFalse(cursor == null);
		//if (cursor == null)	return;
		//Succeed if cursor is not empty:
		assertTrue(cursor.moveToFirst());
		//Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Communities.NAME , cursor.getString(0));
		returnedValues.put(SocialContract.Communities.OWNER_ID , cursor.getString(1));
		returnedValues.put(SocialContract.Communities.TYPE , cursor.getString(2));
		returnedValues.put(SocialContract.Communities.DESCRIPTION , cursor.getString(3));
		returnedValues.put(SocialContract.Communities.ORIGIN , cursor.getString(4));
		assertEquals(returnedValues,initialValues);

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
		//Create local ContentValues to hold the community data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Communities.NAME , "SOCIETIES football team");
		initialValues.put(SocialContract.Communities.OWNER_ID , "babak@societies.org");
		initialValues.put(SocialContract.Communities.TYPE , "sports");
		initialValues.put(SocialContract.Communities.DESCRIPTION , "Hurray!!");
		initialValues.put(SocialContract.Communities.ORIGIN , "SOCIETIES");
		
		//Call insert in SocialProvider to initiate insertion:
		//   Get _ID back and store it.
		Uri newCommunityUri= 
				resolver.insert(SocialContract.Communities.CONTENT_URI, initialValues);
		
		//Get the newly inserted community from SocialProvider:
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
		String selection = SocialContract.Communities._ID + " = " +
			newCommunityUri.getLastPathSegment();
		//Run the query:
		Cursor cursor = resolver.query(SocialContract.Communities.CONTENT_URI,
				projection, selection, null, null);
		
		//Fail if cursor is null:
		assertFalse(cursor == null);
		//if (cursor == null)	return;
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
		initialValues.put(SocialContract.Communities.GLOBAL_ID , "Pending");
		assertEquals(returnedValues,initialValues);
	}
	/**
	 * Test whether Communities record can be inserted and can
	 * be retrieved using the _ID field.
	 */
	public void testInsertServiceQueryWithID(){
		//Create local ContentValues to hold the service data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Services.NAME , "My service");
		initialValues.put(SocialContract.Services.OWNER_ID , "babak@societies.org");
		initialValues.put(SocialContract.Services.TYPE , "disaster");
		initialValues.put(SocialContract.Services.DESCRIPTION , "good service");
		initialValues.put(SocialContract.Services.APP_TYPE , "App");
		initialValues.put(SocialContract.Services.ORIGIN , "SOCIETIES");
		initialValues.put(SocialContract.Services.AVAILABLE , "yes");
		initialValues.put(SocialContract.Services.DEPENDENCY , "iJacket");
		initialValues.put(SocialContract.Services.CONFIG , "xyz");
		initialValues.put(SocialContract.Services.URL , "https://github.com/downloads/UbiCollab/UbiShare/SocialProvider-v01.apk");
		//Call insert in SocialProvider to initiate insertion:
		//Store _ID for later query:
		Uri newServiceUri= 
				resolver.insert(SocialContract.Services.CONTENT_URI, 
						initialValues);
		
		//Get the newly inserted community from SocialProvider:
		//Columns to get:
		String[] projection ={
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
		String selection = SocialContract.Services._ID + " = " +
			newServiceUri.getLastPathSegment();
		//Run the query:
		Cursor cursor = resolver.query(SocialContract.Services.CONTENT_URI,
				projection, selection, null, null);
		
		//Fail if cursor is null:
		assertFalse(cursor == null);
		//Succeed if cursor is not empty:
		assertTrue(cursor.moveToFirst());
		//Fail if the return data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Services.NAME , cursor.getString(0));
		returnedValues.put(SocialContract.Services.OWNER_ID , cursor.getString(1));
		returnedValues.put(SocialContract.Services.TYPE , cursor.getString(2));
		returnedValues.put(SocialContract.Services.DESCRIPTION , cursor.getString(3));
		returnedValues.put(SocialContract.Services.APP_TYPE , cursor.getString(4));
		returnedValues.put(SocialContract.Services.ORIGIN , cursor.getString(5));
		returnedValues.put(SocialContract.Services.AVAILABLE , cursor.getString(6));
		returnedValues.put(SocialContract.Services.DEPENDENCY , cursor.getString(7));
		returnedValues.put(SocialContract.Services.CONFIG , cursor.getString(8));
		returnedValues.put(SocialContract.Services.URL , cursor.getString(9));
		assertEquals(returnedValues,initialValues);

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
		//Create local ContentValues to hold the service data:
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Services.NAME , "My service");
		initialValues.put(SocialContract.Services.OWNER_ID , "babak@societies.org");
		initialValues.put(SocialContract.Services.TYPE , "disaster");
		initialValues.put(SocialContract.Services.DESCRIPTION , "good service");//Optional
		initialValues.put(SocialContract.Services.APP_TYPE , "App");
		initialValues.put(SocialContract.Services.ORIGIN , "SOCIETIES");
		initialValues.put(SocialContract.Services.AVAILABLE , "yes");
		initialValues.put(SocialContract.Services.DEPENDENCY , "iJacket");//Optional
		initialValues.put(SocialContract.Services.CONFIG , "xyz");//Optional
		initialValues.put(SocialContract.Services.URL , "https://github.com/downloads/UbiCollab/UbiShare/SocialProvider-v01.apk");//Optional
		
		//Call insert in SocialProvider to initiate insertion:
		//   Get _ID back and store it.
		Uri newServiceUri= 
				resolver.insert(SocialContract.Services.CONTENT_URI, initialValues);
		
		//Get the newly inserted community from SocialProvider:
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
		String selection = SocialContract.Services._ID + " = " +
			newServiceUri.getLastPathSegment();
		//Run the query:
		Cursor cursor = resolver.query(SocialContract.Services.CONTENT_URI,
				projection, selection, null, null);
		
		//Fail if cursor is null:
		assertFalse(cursor == null);
		//if (cursor == null)	return;
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
		
		initialValues.put(SocialContract.Services.GLOBAL_ID , "Pending");
		assertEquals(returnedValues,initialValues);
	}
/**
	 * Add a membership, then check and see if it was added correctly.
	 */
	public void testInsertMembershipSimple(){
		//1- Create local ContentValues to hold the initial membership data.
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , "babak@societies.org");
		initialValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY , "football.societies.org");
		initialValues.put(SocialContract.Membership.TYPE , "member");
		
		//2- Call insert in SocialProvider to initiate insertion
		Uri newCommunityUri= 
				resolver.insert(Uri.parse(SocialContract.AUTHORITY_STRING+
						SocialContract.UriPathIndex.MEMBERSHIP), 
						initialValues);
		
		//3- Get the newly inserted CIS from SocialProvider.
		//What to get:
		String[] projection ={
				SocialContract.Membership.GLOBAL_ID_MEMBER,
				SocialContract.Membership.GLOBAL_ID_COMMUNITY,
				SocialContract.Membership.TYPE
			};
		//WHERE _id = ID of the newly created CIS:
		String selection = SocialContract.Membership._ID + " = " +
			newCommunityUri.getLastPathSegment();
		Cursor cursor = resolver.query(SocialContract.Membership.CONTENT_URI,
				projection, selection, null, null);
		
		//4- Fail if the CIS was not returned.
		assertFalse(cursor == null);
		if (cursor == null)	return;
		if (!cursor.moveToFirst()) return;
		//5- Fail if the CIS data are not correct:
		//Create new ContentValues object based on returned CIS:
		ContentValues returnedValues = new ContentValues();
		returnedValues.put(SocialContract.Membership.GLOBAL_ID_MEMBER , cursor.getString(0));
		returnedValues.put(SocialContract.Membership.GLOBAL_ID_COMMUNITY , cursor.getString(1));
		returnedValues.put(SocialContract.Membership.TYPE , cursor.getString(2));
		assertEquals(returnedValues,initialValues);
	}
}
