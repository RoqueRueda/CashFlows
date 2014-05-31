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

import android.provider.BaseColumns;

/**
 * 
 * Contains the constants to handle the database communication
 * for the Account table.
 * 
 * @author Roque Rueda
 * @since 06/04/2014
 * @version 1.0
 *
 */
public interface AccountTable extends BaseColumns{

	/**
	 * Table name to store accounts of the user.
	 * <i>Table Name</i>.
	 */
	public static final String TABLE_ACCOUNTS = "accounts";
	
	/**
	 * Column name used to store the id of the account.
	 * <b>TYPE REAL</b>.
	 */
	public static final String FULL_ID = TABLE_ACCOUNTS + "." + _ID;
	
	/**
	 * Column name used to store the initial balance for the account.
	 * <b>TYPE REAL</b>.
	 */
	public static final String ACCOUNT_INITIAL_BALANCE= "initial_balance";
	
	/**
	 * Column name used to store the initial balance for the account.
	 * <b>TYPE REAL</b>.
	 */
	public static final String FULL_INITIAL_BALANCE = TABLE_ACCOUNTS + "." + ACCOUNT_INITIAL_BALANCE;
	
	/**
	 * Column name used to store the name of the account.
	 * <b>TYPE TEXT</b>.
	 */
	public static final String ACCOUNT_NAME = "name";
	
	/**
	 * Column name used to store the name of the account.
	 * <b>TYPE TEXT</b>.
	 */
	public static final String FULL_ACCOUNT_INITIAL_BALANCE = TABLE_ACCOUNTS + "." + ACCOUNT_NAME;
	
	/**
	 * Column name used to store the end balance of the table.
	 * <b>TYPE REAL</b>.
	 */
	public static final String ACCOUNT_END_BALANCE = "end_balance";
	
	/**
	 * Column name used to store the end balance of the table.
	 * <b>TYPE REAL</b>.
	 */
	public static final String FULL_ACCOUNT_END_BALANCE = TABLE_ACCOUNTS + "." + ACCOUNT_END_BALANCE;
	
	/**
	 * Column name used to store the photo number of the account.
	 * <b>TYPE INTEGER</b>.
	 */
	public static final String PHOTO_NUMBER = "photo_number";
	
	/**
	 * Column name used to store the full photo number of the account.
	 * <b>TYPE INTEGER</b>.
	 */
	public static final String FULL_ACCOUNT_NUMBER = TABLE_ACCOUNTS + "." + PHOTO_NUMBER;
	
	
	/**
	 * Column id used to relate an account with a corresponding period.
	 * <b>TYPE INTEGER</b>.
	 */
	public static final String ID_PERIOD = "id_period";
	
	/**
	 * Column id used to relate an account with a corresponding period.
	 * <b>TYPE INTEGER</b>.
	 */
	public static final String FULL_ID_PERIOD = TABLE_ACCOUNTS + "." + ID_PERIOD;
	
}
