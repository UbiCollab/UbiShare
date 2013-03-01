/**
 * Copyright (c) 2011, SOCIETIES Consortium (WATERFORD INSTITUTE OF TECHNOLOGY (TSSG), HERIOT-WATT UNIVERSITY (HWU), SOLUTA.NET 
 * (SN), GERMAN AEROSPACE CENTRE (Deutsches Zentrum fuer Luft- und Raumfahrt e.V.) (DLR), Zavod za varnostne tehnologije
 * informacijske družbe in elektronsko poslovanje (SETCCE), INSTITUTE OF COMMUNICATION AND COMPUTER SYSTEMS (ICCS), LAKE
 * COMMUNICATIONS (LAKE), INTEL PERFORMANCE LEARNING SOLUTIONS LTD (INTEL), PORTUGAL TELECOM INOVAÇÃO, SA (PTIN), IBM Corp., 
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
package org.societies.android.platform;

import java.util.Date;

import org.societies.android.api.cis.SocialContract;

import android.content.ContentValues;

/**
 * This is a class that fills in {@link SocialProvider} with some test data.
 * 
 * @author Babak dot Farshchian at sintef dot no
 *
 */
public class SocialDataSet {

	private ISocialAdapter adapter = null;
	public SocialDataSet(ISocialAdapter _adapter){
		adapter = _adapter;
	}
	public boolean populate(){
   		//populateMe();
   		populatePeople();
   		populateCommunities();
   		//populateServices();
   		//populateRelationships();
   		populateMemberships();
   		//populateSharing();
   		//populatePeopleActivities();
   		populateCommunityActivities();
   		//populateServiceActivities();
   		return true;
	}
    private void populateMe(){
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.Me.GLOBAL_ID , "knut@redcross.org");
		initialValues.put(SocialContract.Me.NAME , "Knut Roedhale");
		initialValues.put(SocialContract.Me.DISPLAY_NAME , "knut@redcross");
		initialValues.put(SocialContract.Me.USER_NAME , "knutr");
		initialValues.put(SocialContract.Me.PASSWORD , "XYZ123");
		adapter.insertMe(initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Me.GLOBAL_ID , "knutr@facebook.com");
		initialValues.put(SocialContract.Me.NAME , "Knut R");
		initialValues.put(SocialContract.Me.DISPLAY_NAME , "Knut@FB");
		initialValues.put(SocialContract.Me.USER_NAME , "KnutRFacebook");
		initialValues.put(SocialContract.Me.PASSWORD , "XYZ123");
		adapter.insertMe(initialValues);
	}
	private void populatePeople() {
		ContentValues initialValues = new ContentValues();
		initialValues.put(SocialContract.People.GLOBAL_ID , SocialContract.GLOBAL_ID_PENDING);
		initialValues.put(SocialContract.People.NAME , "Kato St�len");
		initialValues.put(SocialContract.People.EMAIL , "kato.stoelen@gmail.com");
		initialValues.put(SocialContract.People.DESCRIPTION , "Kato@BOX");
		initialValues.put(SocialContract.People.CREATION_DATE, new Date().getTime() / 1000);
		initialValues.put(SocialContract.People.LAST_MODIFIED_DATE, new Date().getTime() / 1000);
		adapter.insertPeople(initialValues);
		
		initialValues.clear();

		initialValues.put(SocialContract.People.GLOBAL_ID , SocialContract.GLOBAL_ID_PENDING);
		initialValues.put(SocialContract.People.NAME , "Babak Farshchian");
		initialValues.put(SocialContract.People.EMAIL , "babak@farshchian.com");
		initialValues.put(SocialContract.People.DESCRIPTION , "Babak@BOX");
		initialValues.put(SocialContract.People.CREATION_DATE, new Date().getTime() / 1000);
		initialValues.put(SocialContract.People.LAST_MODIFIED_DATE, new Date().getTime() / 1000);
		adapter.insertPeople(initialValues);
	}
	private void populateCommunities(){
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Communities.GLOBAL_ID , SocialContract.GLOBAL_ID_PENDING);
		initialValues.put(SocialContract.Communities.TYPE , "test");
		initialValues.put(SocialContract.Communities.NAME , "TestCommunity1");
		initialValues.put(SocialContract.Communities._ID_OWNER, 1);
		initialValues.put(SocialContract.Communities.DESCRIPTION , "A test community");
		initialValues.put(SocialContract.Communities.CREATION_DATE, new Date().getTime() / 1000);
		initialValues.put(SocialContract.Communities.LAST_MODIFIED_DATE, new Date().getTime() / 1000);
		adapter.insertCommunities(initialValues);

		/*initialValues.clear();
	
		initialValues.put(SocialContract.Communities.GLOBAL_ID , SocialContract.GLOBAL_ID_PENDING);
		initialValues.put(SocialContract.Communities.TYPE , "test");
		initialValues.put(SocialContract.Communities.NAME , "TestCommunity2");
		initialValues.put(SocialContract.Communities._ID_OWNER, 0);
		initialValues.put(SocialContract.Communities.DESCRIPTION , "A second test community");
		initialValues.put(SocialContract.Communities.CREATION_DATE, new Date().getTime() / 1000);
		initialValues.put(SocialContract.Communities.LAST_MODIFIED_DATE, new Date().getTime() / 1000);
		adapter.insertCommunities(initialValues);*/

	}
	private void populateServices() {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Services.GLOBAL_ID , "s1xyz.societies.org");
		initialValues.put(SocialContract.Services.TYPE , "Disaster");
		initialValues.put(SocialContract.Services.NAME , "iJacket");
		initialValues.put(SocialContract.Services.OWNER_ID, "knut@redcross.org");
		initialValues.put(SocialContract.Services.DESCRIPTION , "A service to communicate with rescuer jacket");
		initialValues.put(SocialContract.Services.AVAILABLE, "false");
		initialValues.put(SocialContract.Services.DEPENDENCY, "iJacketClient");
		initialValues.put(SocialContract.Services.CONFIG, "jacket version");
		initialValues.put(SocialContract.Services.URL, "http://files.ubicollab.net/apk/iJacket.apk");
		adapter.insertServices(initialValues);

		initialValues.clear();
		
		initialValues.put(SocialContract.Services.GLOBAL_ID , "s2xyz.societies.org");
		initialValues.put(SocialContract.Services.TYPE , "Disaster");
		initialValues.put(SocialContract.Services.NAME , "iJacketClient");
		initialValues.put(SocialContract.Services.OWNER_ID, "knut@redcross.org");
		initialValues.put(SocialContract.Services.DESCRIPTION , "A service to communicate with iJacket");
		initialValues.put(SocialContract.Services.AVAILABLE, "false");
		initialValues.put(SocialContract.Services.DEPENDENCY, "iJacket");
		initialValues.put(SocialContract.Services.CONFIG, "iJacket version");
		initialValues.put(SocialContract.Services.URL, "http://files.ubicollab.net/apk/iJacketClient.apk");

		adapter.insertServices(initialValues);		
	}
	private void populateRelationships() {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Relationship.GLOBAL_ID , "rel1xyz.societies.org");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P1 , "knut@redcross.org");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P2, "tor@redcross.org");
		initialValues.put(SocialContract.Relationship.TYPE , "friends");
		adapter.insertRelationship(initialValues);
		
		initialValues.clear();

		initialValues.put(SocialContract.Relationship.GLOBAL_ID , "rel2xyz.societies.org");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P1 , "thomas@societies.org");
		initialValues.put(SocialContract.Relationship.GLOBAL_ID_P2, "jacqueline@societies.org");
		initialValues.put(SocialContract.Relationship.TYPE , "friends");
		adapter.insertRelationship(initialValues);
	}
	private void populateMemberships() {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Membership.GLOBAL_ID, SocialContract.GLOBAL_ID_PENDING);
		initialValues.put(SocialContract.Membership._ID_MEMBER , 1);
		initialValues.put(SocialContract.Membership._ID_COMMUNITY, 1);
		initialValues.put(SocialContract.Membership.TYPE , "coordinator");
		initialValues.put(SocialContract.Membership.CREATION_DATE, new Date().getTime() / 1000);
		initialValues.put(SocialContract.Membership.LAST_MODIFIED_DATE, new Date().getTime() / 1000);
		adapter.insertMembership(initialValues);
		
		initialValues.clear();
		
		initialValues.put(SocialContract.Membership.GLOBAL_ID, SocialContract.GLOBAL_ID_PENDING);
		initialValues.put(SocialContract.Membership._ID_MEMBER , 2);
		initialValues.put(SocialContract.Membership._ID_COMMUNITY, 1);
		initialValues.put(SocialContract.Membership.TYPE , "member");
		initialValues.put(SocialContract.Membership.CREATION_DATE, new Date().getTime() / 1000);
		initialValues.put(SocialContract.Membership.LAST_MODIFIED_DATE, new Date().getTime() / 1000);
		adapter.insertMembership(initialValues);
	}
	private void populateSharing() {
		ContentValues initialValues = new ContentValues();
		
		initialValues.put(SocialContract.Sharing.GLOBAL_ID , "sha1xyz.redcross.org");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_SERVICE , "s1xyz.societies.org");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_COMMUNITY, "c1xyz.societies.org");
		initialValues.put(SocialContract.Sharing.TYPE , "Monitor");
		adapter.insertSharing(initialValues);
		
		initialValues.clear();
		
		initialValues.put(SocialContract.Sharing.GLOBAL_ID , "sha2xyz.redcross.org");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_SERVICE , "s2xyz.societies.org");
		initialValues.put(SocialContract.Sharing.GLOBAL_ID_COMMUNITY, "c1xyz.societies.org");
		initialValues.put(SocialContract.Sharing.TYPE , "Full access");
		adapter.insertSharing(initialValues);
	}
	private void populatePeopleActivities() {
		// TODO Auto-generated method stub
		
	}
	private void populateCommunityActivities() {
		ContentValues values = new ContentValues();
		
		values.put(SocialContract.CommunityActivity.GLOBAL_ID, SocialContract.GLOBAL_ID_PENDING);
		values.put(SocialContract.CommunityActivity._ID_FEED_OWNER, 1);
		values.put(SocialContract.CommunityActivity.ACTOR, "Actor");
		values.put(SocialContract.CommunityActivity.OBJECT, "Object");
		values.put(SocialContract.CommunityActivity.VERB, "Verb");
		values.put(SocialContract.CommunityActivity.TARGET, "Target");
		values.put(SocialContract.CommunityActivity.CREATION_DATE, new Date().getTime() / 1000);
		values.put(SocialContract.CommunityActivity.LAST_MODIFIED_DATE, new Date().getTime() / 1000);
		adapter.insertCommunityActivity(values);
		
	}
	private void populateServiceActivities() {
		// TODO Auto-generated method stub
		
	}
}
