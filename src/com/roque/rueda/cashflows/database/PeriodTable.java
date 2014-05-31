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
 * for the Periods table.
 * 
 * @author Roque Rueda
 * @since 06/04/2014
 * @version 1.0
 *
 */
public interface PeriodTable extends BaseColumns {
	
	/**
	 * Table name to store the daily Period.
	 * <i>Table Name</i>.
	 */
	public static final String TABLE_PERIODS = "periods";
	public static final String FULL_ID = TABLE_PERIODS + "." + _ID;
	
	/**
	 * Column name used to store the start date of the period.
	 * <b>TYPE REAL</b>.
	 */
	public static final String START_DATE = "start_date";
	public static final String FULL_START_DATE = TABLE_PERIODS + "." + START_DATE;
	
	/**
	 * Column name used to store the end date of the period.
	 * <b>TYPE REAL</b>.
	 */
	public static final String END_DATE = "end_date";
	public static final String FULL_END_DATE = TABLE_PERIODS + "." + END_DATE;
	
	/**
	 * Column name used to store the name of the period.
	 * <b>TYPE REAL</b>.
	 */
	public static final String NAME = "name";
	public static final String FULL_NAME = TABLE_PERIODS + "." + NAME;
	
	/**
	 * Column name used to store the active value of the period of the period.
	 * <b>TYPE INTEGER</b>.
	 */
	public static final String ACTIVE = "active";
	public static final String FULL_ACTIVE = TABLE_PERIODS + "." + ACTIVE;
	
}
