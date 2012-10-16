package org.societies.android.sync.box;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

/**
 * Model class representing a database entity.
 * 
 * @author Kato
 */
public class DatabaseEntity {
	
	private static final String TAG = "DatabaseEntity";
	private static final String SERIALIZE_FORMAT = "%s=%s\n";
	private static final String ID_COLUMN = "_id";

	public ContentValues mColumnValues;
	
	/**
	 * Initializes a new database entity.
	 */
	public DatabaseEntity() {
		mColumnValues = new ContentValues();
	}
	
	/**
	 * Serializes the database entity to a string.
	 * @return The serialized database entity.
	 */
	public String serialize() {
		String serialized = new String();
		
		for (Map.Entry<String, Object> column : mColumnValues.valueSet())
			serialized += String.format(
					SERIALIZE_FORMAT, column.getKey(), column.getValue());
		
		return serialized;
	}
	
	/**
	 * Inserts the database entity into the database.
	 * @param context The context to use.
	 * @param contentUri The URL of the table to insert into.
	 * @return The URI to the inserted row.
	 */
	public Uri insert(Context context, Uri contentUri) {
		return context.getContentResolver().insert(contentUri, mColumnValues);
	}
	
	/**
	 * Updates the database entity in the database.
	 * @param context The context to use.
	 * @param contentUri The URL of the table to update.
	 * @param selection A filter declaring which rows to update.
	 * @param selectionArgs The replacement values for any ?s in the selection filter.
	 * @return The number of rows updated.
	 */
	public int update(Context context, Uri contentUri, String selection, String[] selectionArgs) {
		return context.getContentResolver().update(
				contentUri, mColumnValues, selection, selectionArgs);
	}
	
	/**
	 * Checks whether the database entity already exists in the database.
	 * @param context The context to use.
	 * @param contentUri The URL of the content to retrieve.
	 * @param matchColumn The name of the column to match. The caller has to make sure
	 * the specified value refer to an actual column (and prevent SQL injection).
	 * @return The local ID of the existing entity, or null if it does not exist.
	 */
	public String exists(Context context, Uri contentUri, String matchColumn) {
		Cursor cursor = null;
		String existingId = null;
		
		try {
			cursor = context.getContentResolver().query(
					contentUri,
					new String[] { ID_COLUMN },
					String.format("%s=?", matchColumn),
					new String[] { mColumnValues.getAsString(matchColumn) },
					null);
			
			if (cursor.moveToFirst()) {
				existingId = cursor.getString(cursor.getColumnIndex(ID_COLUMN));
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			if (cursor != null)
				cursor.close();
		}
		
		return existingId;
	}
	
	/**
	 * Deletes the specified rows from the database.
	 * @param context The context to use.
	 * @param contentUri The URL to the table from where to delete.
	 * @param selection A filter declaring which rows to delete.
	 * @param selectionArgs The replacement values for any ?s in the selection filter.
	 * @return The number of rows deleted.
	 */
	public static int delete(
			Context context,
			Uri contentUri,
			String selection,
			String[] selectionArgs
	) {
		return context.getContentResolver().delete(contentUri, selection, selectionArgs);
	}
	
	/**
	 * Gets the database entities from the specified table that matches the query.
	 * @param context The context to use.
	 * @param contentUri The URL to the content to retrieve.
	 * @param projection A list of which columns to return, or null for all columns.
	 * @param selection A filter declaring which rows to return, or null for all rows.
	 * @param selectionArgs The replacement values for any ?s in the selection filter.
	 * @param sortOrder The sort order, or null for default order.
	 * @return The list of database entities matching the query.
	 */
	public static List<DatabaseEntity> getEntities(
			Context context,
			Uri contentUri,
			String[] projection,
			String selection,
			String[] selectionArgs,
			String sortOrder
	) {
		List<DatabaseEntity> entities = new ArrayList<DatabaseEntity>();
		
		Cursor cursor = null;
		try {
			cursor = context.getContentResolver().query(
					contentUri, projection, selection, selectionArgs, sortOrder);
			cursor.moveToFirst();
			
			for (boolean hasItem = cursor.moveToFirst(); hasItem; hasItem = cursor.moveToNext()) {
				DatabaseEntity entity = new DatabaseEntity();
				
				for (int i = 0; i < cursor.getColumnCount(); i++)
					entity.mColumnValues.put(cursor.getColumnName(i), cursor.getString(i));
				
				entities.add(entity);
			}
		} catch (Exception e) {
			Log.e(TAG, e.getMessage(), e);
		} finally {
			if (cursor != null)
				cursor.close();
		}
		
		return entities;
	}
}
