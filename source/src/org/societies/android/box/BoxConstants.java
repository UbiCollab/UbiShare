/**
 * Copyright 2012 UbiCollab
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.societies.android.box;

/**
 * A collection of constants related to Box.
 * 
 * @author Kato
 */
public final class BoxConstants {
	
	/** The API key of Box. */
	public static final String API_KEY = "01tnjc9u8xtsozhyvpvilpzbm2396ohn";
	/** The auth-token flag returned from Box login. */
	public static final String AUTH_TOKEN_FLAG = "AUTH_TOKEN";
	/** The flag used to identify the Box API key. */
	public static final String API_KEY_FLAG = "API_KEY";

	/** The account userdata auth-token key. */
	public static final String ACCOUNT_USERDATA_AUTH_TOKEN = "authToken";
	/** The account sync frequency. */
	public static final long ACCOUNT_SYNC_FREQUENCY = 600;
	
	/** The name of the share preference file. */
	public static final String PREFERENCE_FILE = "preferences.box";
	/** The directory tree preference. */
	public static final String PREFERENCE_DIRECTORY_TREE = "dir_tree";
	/** The timestamp of the last sync. */
	public static final String PREFERENCE_LAST_SYNC = "last_sync";
	
}
