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
package org.societies.android.platform.entity;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Base class of all entities.
 * 
 * @author Kato
 */
public abstract class Entity {
	
	private static final String TAG = "Entity";
	
	/** The format in which properties should be serialized. */
	protected static final String SERIALIZE_FORMAT = "%s=%s\n";
	
	/**
	 * Gets a list of entities of the specified type.
	 * @param entityClass The class of the entity.
	 * @param resolver The content resolver.
	 * @param contentUri The URL to the content to retrieve.
	 * @param projection A list of which columns to return, or null for all columns.
	 * @param selection A filter declaring which rows to return, or null for all rows.
	 * @param selectionArgs The replacement values for any ?s in the selection filter.
	 * @param sortOrder The sort order, or null for default order.
	 * @return A list of entities of the specified type.
	 */
	protected static <E extends Entity> List<E> getEntities(
			Class<E> entityClass,
			ContentResolver resolver,
			Uri contentUri,
			String[] projection,
			String selection,
			String[] selectionArgs,
			String sortOrder) {
		List<E> entities = new ArrayList<E>();
		
		Cursor cursor = null;
		try {
			cursor = resolver.query(contentUri, projection, selection, selectionArgs, sortOrder);
			
			if (cursor.moveToFirst()) {
				for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
					E entity = entityClass.newInstance();
					entity.populate(cursor);
					
					entities.add(entity);
				}
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			if (cursor != null)
				cursor.close();
		}
		
		return entities;
	}
	
	/**
	 * Gets the value of the specified column as a string.
	 * @param cursor The database cursor.
	 * @param columnName The name of the column.
	 * @return The value of the specified column as a string.
	 * @throws IllegalArgumentException If the specified column does not exist.
	 */
	protected static String getString(Cursor cursor, String columnName)
			throws IllegalArgumentException {
		return cursor.getString(cursor.getColumnIndexOrThrow(columnName));
	}
	
	/**
	 * Gets the value of the specified column as an integer.
	 * @param cursor The database cursor.
	 * @param columnName The name of the column.
	 * @return The value of the specified column as an integer.
	 * @throws IllegalArgumentException If the specified column does not exist.
	 */
	protected static int getInt(Cursor cursor, String columnName)
			throws IllegalArgumentException {
		return cursor.getInt(cursor.getColumnIndexOrThrow(columnName));
	}
	
	/**
	 * Gets the value of the specified column as a boolean.
	 * @param cursor The database cursor.
	 * @param columnName The name of the column.
	 * @return The value of the specified column as a boolean.
	 * @throws IllegalArgumentException If the specified column does not exist.
	 */
	protected static boolean getBoolean(Cursor cursor, String columnName)
			throws IllegalArgumentException {
		return getInt(cursor, columnName) == 1;
	}
	
	/**
	 * Populates the entity with the values from the specified cursor.
	 * @param cursor The database cursor.
	 */
	protected abstract void populate(Cursor cursor);
	
	/**
	 * Gets the values of the entity.
	 * @return A mapping between property name and value.
	 */
	protected abstract ContentValues getEntityValues();
	
	/**
	 * Gets the content URL of the entity.
	 * @return The content URL of the entity.
	 */
	protected abstract Uri getContentUri();
	
	/**
	 * Updates the entity in the database.
	 * @param resolver The content resolver.
	 * @return The number of rows updated. Should always be 1.
	 */
	public int update(ContentResolver resolver) {
		Uri contentUri = ContentUris.withAppendedId(getContentUri(), getId());
		
		return resolver.update(contentUri, getEntityValues(), null, null);
	}
	
	/**
	 * Deletes the entity in the database.
	 * @param resolver The content resolver.
	 * @return The number of rows deleted. Should always be 1.
	 */
	public int delete(ContentResolver resolver) {
		Uri contentUri = ContentUris.withAppendedId(getContentUri(), getId());
		
		return resolver.delete(contentUri, null, null);
	}
	
	/**
	 * Inserts the entity into the database.
	 * @param resolver The content resolver.
	 * @return The URL to the newly inserted entity.
	 */
	public Uri insert(ContentResolver resolver) {
		return resolver.insert(getContentUri(), getEntityValues());
	}
	
	/**
	 * Serializes the entity into a string.
	 * @return A string representation of the entity.
	 */
	public abstract String serialize();
	
	/**
	 * Gets the local ID of the entity.
	 * @return The local ID of the entity.
	 */
	public abstract int getId();
	
	/**
	 * Gets the global ID of the entity.
	 * @return The global ID of the entity.
	 */
	public abstract String getGlobalId();
	
	/**
	 * Sets the global ID of the entity.
	 * @param globalId The global ID of the entity.
	 */
	public abstract void setGlobalId(String globalId);
}
