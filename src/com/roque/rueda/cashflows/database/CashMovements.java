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

import static android.provider.BaseColumns._ID;
import static com.roque.rueda.cashflows.database.MovementsTable.MOVEMENTS_AMOUNT;
import static com.roque.rueda.cashflows.database.MovementsTable.MOVEMENTS_DATE;
import static com.roque.rueda.cashflows.database.MovementsTable.MOVEMENTS_DESCRIPTION;
import static com.roque.rueda.cashflows.database.MovementsTable.MOVEMENTS_SING;
import static com.roque.rueda.cashflows.database.MovementsTable.ID_ACCOUNT;
import static com.roque.rueda.cashflows.database.MovementsTable.TABLE_MOVEMENTS;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.util.Log;

/**
 * 
 * This class handles the cash movements records.
 * Allows to use and store information for any
 * cash movements.
 * 
 * @author Roque Rueda
 * @since 06/04/2014
 * @version 1.0
 *
 */
public class CashMovements {
	
	private static final String TAG = "CashMovements";
	
	private final CashFlowsOpenHelper mDatabaseOpenHelper;
	private Context mContext;
	
	/**
	 * List of columns that are used to retrieve information from the database.
	 */
	private String[] mCashColumns = new String[]{ _ID, 
			MOVEMENTS_AMOUNT, MOVEMENTS_DATE, MOVEMENTS_DESCRIPTION, MOVEMENTS_SING };
	
	/**
	 * Creates an instance of this class with the context
	 * of this application. Fills the values for the database
	 * open helper.
	 */
	public CashMovements(Context context) {
		mContext = context;
		mDatabaseOpenHelper = new CashFlowsOpenHelper(mContext);
	}
	
	/**
	 * Select a set of cash movements based on a date.
	 * @return Gets the list of cash movements for a day.
	 */
	public Cursor getTodayCashMovements(Date date) {
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
		String todayDate = sdf.format(date);
		return db.query(false, TABLE_MOVEMENTS, mCashColumns, 
				MOVEMENTS_DATE + " BETWEEN ? AND ?", 
				new String[]{ todayDate + " 00:00:00", todayDate + " 23:59:59" },
				null, null, MOVEMENTS_DATE + " DESC", null);
	}
	
	/**
	 * Get all the movements for a specific account.
	 * @param idPeriod Identifier of the period.
	 * @param idAccount Identifier of the account.
	 * @return Cursor instance with the cash movements.
	 */
	public Cursor getCashMovements(int idPeriod, int idAccount) {
		
		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		qb.setTables(PeriodTable.TABLE_PERIODS + " INNER JOIN " + AccountTable.TABLE_ACCOUNTS + 
				" ON " + AccountTable.ID_PERIOD + " = " + PeriodTable.FULL_ID + " INNER JOIN " + 
				MovementsTable.TABLE_MOVEMENTS + " ON " + MovementsTable.ID_ACCOUNT + "=" +
				AccountTable.FULL_ID);
		
		String orderBy = MovementsTable.MOVEMENTS_DATE + " DESC";
		
		SQLiteDatabase db = mDatabaseOpenHelper.getReadableDatabase();
		return qb.query(db, null, PeriodTable.ACTIVE + " = 1", null, null, null, orderBy);
		
	}
	
	/**
	 * Save a new cash movement into the database with
	 * the given arguments.
	 * 
	 * @param amount Amount of the operation.
	 * @param movementDescription Description of the movement.
	 * @param movementSing Sing that indicates if this is a positive or negative movement.
	 * @param movementDate Date of this movement.
	 * @return True if the movement can be store on the database, otherwise returns false.
	 */
	@SuppressLint("SimpleDateFormat")
	public boolean saveCashMovement(double amount, String movementDescription,
			String movementSing, Date movementDate, long idAccount) {
		
		try{
			// Wrap the values to be used as parameters on the insert method.
			ContentValues values = new ContentValues();
			values.put(MOVEMENTS_AMOUNT, amount);
			values.put(MOVEMENTS_DESCRIPTION, movementDescription);
			values.put(MOVEMENTS_SING, movementSing);
			values.put(ID_ACCOUNT, idAccount);
			// Simple date format help us to convert the data to a correct string.
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			values.put(MOVEMENTS_DATE, dateFormat.format(movementDate));
			
			// Get a writable database to modify the database information.
			SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
			
			long id = db.insertOrThrow(TABLE_MOVEMENTS, null, values);
			
			if(id != -1) {
				// if the new id it's different from -1 no error have occur.
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqlEx) {
			Log.w(TAG, "An error happen during cash movement save method, " + sqlEx.getMessage());

			return false;
		}
	}
	
	/**
	 * Edit a cash movement, this method edit all the values on the cash movement except for
	 * the primary key which is used to search and edit the proper movement.
	 * @param id Identifier for the cash movement.
	 * @param amount New amount that will update the current amount of this cash movement.
	 * @param movementDescription New description for the cash movement.
	 * @param movementSing New sing of the cash movement.
	 * @param movementDate New date for the current cash movement.
	 * @return true if the cash movement can be updated otherwise returns false.
	 */
	@SuppressLint("SimpleDateFormat")
	public boolean editCashMovement(long id, double amount, String movementDescription,
			String movementSing, Date movementDate) {
		
		try {
			// Wrap the values passing as argument.
			ContentValues values = new ContentValues();
			values.put(MOVEMENTS_AMOUNT, amount);
			values.put(MOVEMENTS_DESCRIPTION, movementDescription);
			values.put(MOVEMENTS_SING, movementSing);
			// Simple date format help us to convert the data to a correct string.
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			values.put(MOVEMENTS_DATE, dateFormat.format(movementDate));
			Long helperId = Long.valueOf(id);
			
			// Get a writable database to modify the database information.
			SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
			int rowsAffected = db.update(TABLE_MOVEMENTS, values, _ID + " = ?", new String[]{helperId.toString()});
			
			if(rowsAffected > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqlEx) {
			// This is the error log, we will take this as false.
			Log.w(TAG, "An error happen during cash movement edit method, " + sqlEx.getMessage());
			return false;
		}
	}
	
	/**
	 * Delete a cash movement base on the long id.
	 * @param id Identifier of the cash movement
	 * @return true if the record can be deleted 
	 * 	otherwise returns false.
	 */
	public boolean deleteCashMovement(long id) {
		
		try {
			Long helperId = Long.valueOf(id);
			// Get a writable database to modify the database information.
			SQLiteDatabase db = mDatabaseOpenHelper.getWritableDatabase();
			int affectedRows = db.delete(TABLE_MOVEMENTS, _ID + " = ?" , new String[]{ helperId.toString() });
			if (affectedRows > 0) {
				return true;
			} else {
				return false;
			}
		} catch (SQLException sqlEx) {
			// Log the error.
			Log.w(TAG, "An error happen during cash movement deleteCashMovement, " + sqlEx.getMessage());
			return false;
		}
	}

}
