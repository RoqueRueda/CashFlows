/*
 * Copyright 2014 Roque Rueda.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.roque.rueda.cashflows.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import static android.provider.BaseColumns._ID;

import static com.roque.rueda.cashflows.database.MovementsTable.TABLE_MOVEMENTS;
import static com.roque.rueda.cashflows.database.MovementsTable.MOVEMENTS_AMOUNT;
import static com.roque.rueda.cashflows.database.MovementsTable.MOVEMENTS_DATE;
import static com.roque.rueda.cashflows.database.MovementsTable.MOVEMENTS_DESCRIPTION;
import static com.roque.rueda.cashflows.database.MovementsTable.MOVEMENTS_SING;
import static com.roque.rueda.cashflows.database.MovementsTable.ID_ACCOUNT;

import static com.roque.rueda.cashflows.database.PeriodTable.TABLE_PERIODS;
import static com.roque.rueda.cashflows.database.PeriodTable.START_DATE;
import static com.roque.rueda.cashflows.database.PeriodTable.NAME;
import static com.roque.rueda.cashflows.database.PeriodTable.END_DATE;
import static com.roque.rueda.cashflows.database.PeriodTable.ACTIVE;

import static com.roque.rueda.cashflows.database.AccountTable.TABLE_ACCOUNTS;
import static com.roque.rueda.cashflows.database.AccountTable.ACCOUNT_INITIAL_BALANCE;
import static com.roque.rueda.cashflows.database.AccountTable.ACCOUNT_NAME;
import static com.roque.rueda.cashflows.database.AccountTable.ACCOUNT_END_BALANCE;
import static com.roque.rueda.cashflows.database.AccountTable.PHOTO_NUMBER;
import static com.roque.rueda.cashflows.database.AccountTable.ID_PERIOD;

/**
 * 
 * This class is used to create or open the database of the application.
 * 
 * @author Roque Rueda
 * @since 06/04/2014
 * @version 1.0
 *
 */
public class CashFlowsOpenHelper extends SQLiteOpenHelper {
	
	private static final String TAG = "CashFlowOpenHelper";
	
	private SQLiteDatabase mDatabase;
	
	public static final String DATABASE_NAME = "cash_flows.db";
	public static final int INITIAL_DATABASE_VERSION = 1;
	public static final int FOREING_KEY_ERROR_VERSION = 2;
	public static final int PHOTO_NUMBER_ACCUNTS = 4;
	public static final int PHOTO_NUMBER_ERROR = 5;
	
	/**
	 * Current database version.
	 */
	public static final int DATABASE_VERSION = PHOTO_NUMBER_ERROR;
	
	/**
	 * Create sentence for the movements table.
	 */
	private static final String CREATION_TABLE_MOVEMENTS = 
			"CREATE TABLE " +	 TABLE_MOVEMENTS + "(" + 
					_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					MOVEMENTS_AMOUNT + " REAL NOT NULL," + 
					MOVEMENTS_DESCRIPTION + " TEXT," +
					MOVEMENTS_DATE + " DATETIME NOT NULL," + 
					MOVEMENTS_SING + " TEXT NOT NULL," +
					ID_ACCOUNT + " INTEGER NOT NULL REFERENCES " + TABLE_ACCOUNTS + "(" +_ID + "));";
	
	/**
	 * Create sentence for the accounts table.
	 */
//	private static final String CREATION_TABLE_ACCOUNTS = 
//			"CREATE TABLE " + TABLE_ACCOUNTS	+ " (" + 
//					_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
//					ACCOUNT_INITIAL_BALANCE + " REAL NOT NULL, " + 
//					ACCOUNT_NAME + " TEXT NO NULL," + 
//					ACCOUNT_END_BALANCE + " REAL," + 
//					ID_PERIOD + " INTEGER NOT NULL, " +
//					"FOREING KEY (" + ID_PERIOD + ") REFERENCES " + TABLE_PERIODS + "(" +_ID + "));";
	private static final String CREATION_TABLE_ACCOUNTS = 
			"CREATE TABLE " + TABLE_ACCOUNTS	+ " (" + 
					_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					ACCOUNT_INITIAL_BALANCE + " REAL NOT NULL, " + 
					ACCOUNT_NAME + " TEXT NO NULL," + 
					ACCOUNT_END_BALANCE + " REAL," + 
					PHOTO_NUMBER + " INTEGER," +
					ID_PERIOD + " INTEGER NOT NULL REFERENCES " + TABLE_PERIODS + "(" +_ID + "));";
	
	/**
	 * Create sentence for the periods table.
	 */
	private static final String CREATION_TABLE_PERIODS = 
			"CREATE TABLE " +	TABLE_PERIODS + " (" + 
					_ID + " INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
					START_DATE + " DATETIME NOT NULL," + 
					END_DATE + " DATETIME NOT NULL," +
					NAME + " TEXT NOT NULL," + 
					ACTIVE + " INTEGER NOT NULL);" ;

	/**
	 * Creates a SQLite open helper instance with the context
	 * of the application.
	 * @param context Context of the current application.
	 */
	public CashFlowsOpenHelper(Context context) {
		super(context, DATABASE_NAME, null, DATABASE_VERSION);
	}

	/**
	 * Called when the database is created for the first time. 
	 * This is where the creation of tables and the initial population of the tables should happen.
	 * @param db SQLite database.
	 */
	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.w(TAG, "Crating the databse of the application.");
		
		// Set the database object to future references.
		mDatabase = db;
		
		// Executes a script used to generate the initial tables table.
		mDatabase.execSQL(CREATION_TABLE_PERIODS);
		mDatabase.execSQL(CREATION_TABLE_ACCOUNTS);
		mDatabase.execSQL(CREATION_TABLE_MOVEMENTS);
		
		AccountManager manager = new AccountManager();
		manager.initialLoad(db);
	}
	
	/**
	 *  Called when the database needs to be upgraded. The implementation should
	 *  use this method to drop tables, add tables, or do anything else it needs to 
	 *  upgrade to the new schema version.
	 *  The SQLite ALTER TABLE documentation can be found here. If you add new 
	 *  columns you can use ALTER TABLE to insert them into a live table. If you 
	 *  rename or remove columns you can use ALTER TABLE to rename the old 
	 *  table, then create the new table and then populate the new table with the 
	 *  contents of the old table.
	 *  This method executes within a transaction. If an exception is thrown, 
	 *  all changes will automatically be rolled back.
	 *  
	 *  @param db SQLite database.
	 *  @param oldVersion number that indicates the old version of the database.
	 *  @param newVersion number that indicates the new version of the database.
	 *  
	 */
	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		Log.w(TAG, "Upgrading to from " + oldVersion + " database version to " + newVersion + " version.");
		
		/*
		 * NOTE: This switch statement is designed to handle cascading database
		 * updates, the update will start at the initial version and continue
		 * to the latest version. Use break if you want to drop and delete all
		 * the information.
		 * 
		 */
		int version = oldVersion;
		
		switch (version) {
			case INITIAL_DATABASE_VERSION:
				// Initial version we don't do anything.
				break;
				
			case FOREING_KEY_ERROR_VERSION:
				// Delete tables and create them again.
				break;
				
			case PHOTO_NUMBER_ACCUNTS:
				// Delete account table.
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERIODS);
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_ACCOUNTS);
				db.execSQL("DROP TABLE IF EXISTS " + TABLE_MOVEMENTS);
				
				onCreate(db);
				break;
				
			case PHOTO_NUMBER_ERROR:
				// Delete account table.
				break;
		}
		
	}
	
	@Override
	public void onConfigure(SQLiteDatabase db) {
		
		// Executes a pragma sentence to active foreign keys.
		if (!db.isReadOnly()) {
			db.execSQL("PRAGMA foreing_keys = ON;");
		}
	}

}
