package org.societies.android.platform.entity;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentResolver;
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
	 * Gets the value of the specified column as a boolean.
	 * @param cursor The database cursor.
	 * @param columnName The name of the column.
	 * @return The value of the specified column as a boolean.
	 * @throws IllegalArgumentException If the specified column does not exist.
	 */
	protected static boolean getBoolean(Cursor cursor, String columnName)
			throws IllegalArgumentException {
		return cursor.getInt(cursor.getColumnIndexOrThrow(columnName)) == 1;
	}
	
	/**
	 * Populates the entity with the values from the specified cursor.
	 * @param cursor The database cursor.
	 */
	protected abstract void populate(Cursor cursor);
	
	/**
	 * Updates the entity in the database.
	 * @param resolver The content resolver.
	 */
	public abstract void update(ContentResolver resolver);
	
	/**
	 * Deletes the entity in the database.
	 * @param resolver The content resolver.
	 */
	public abstract void delete(ContentResolver resolver);
	
	/**
	 * Inserts the entity into the database.
	 * @param resolver The content resolver.
	 */
	public abstract void insert(ContentResolver resolver);
	
	/**
	 * Serializes the entity into a string.
	 * @return A string representation of the entity.
	 */
	public abstract String serialize();
	
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
