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
 * for the cash movements table.
 * 
 * @author Roque Rueda
 * @since 06/04/2014
 * @version 1.0
 *
 */
public interface MovementsTable extends BaseColumns {
	
	/**
	 * Table name to store the daily cash movements.
	 * <i>Table Name</i>.
	 */
	public static final String TABLE_MOVEMENTS = "money_movements";
	public static final String FULL_ID = TABLE_MOVEMENTS + "." + _ID;
	
	/**
	 * Column name to storage the amount of money that
	 * is move.<b>TYPE REAL</b>.
	 */
	public static final String MOVEMENTS_AMOUNT = "amount";
	public static final String FULL_MOVEMENTS_AMOUNT = TABLE_MOVEMENTS + "." + MOVEMENTS_AMOUNT;
	
	/**
	 * Column name to storage a user description of the movement.
	 * <b>TYPE TEXT</b>.
	 */
	public static final String MOVEMENTS_DESCRIPTION = "description";
	public static final String FULL_MOVEMENTS_DESCRIPTION = TABLE_MOVEMENTS + "."
	+ MOVEMENTS_DESCRIPTION;
	
	/**
	 * Column name to storage the date of the movement.
	 * <b>TYPE DATETIME</b>.
	 */
	public static final String MOVEMENTS_DATE = "date";
	public static final String FULL_MOVEMENTS_DATE = TABLE_MOVEMENTS + "." + MOVEMENTS_DATE;
	
	/**
	 * Column name to storage the sing of the operation,
	 * this means if it was an income or a expense.
	 * <b>TYPE TEXT</b>.
	 */
	public static final String MOVEMENTS_SING = "sing";
	public static final String FULL_MOVEMENTS_SING = TABLE_MOVEMENTS + "." + MOVEMENTS_SING;
	
	/**
	 * Column name used to related a movement with a corresponding
	 * Account entity. <b>TYPE INTEGER</b>.
	 */
	public static final String ID_ACCOUNT = "id_account";
	public static final String FULL_ID_ACCOUNT = TABLE_MOVEMENTS + "." + ID_ACCOUNT;
	
}
