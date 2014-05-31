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
package com.roque.rueda.cashflows.database.observer;

/**
 * 
 * Interface used to define the basic methods of a database observer.
 * 
 * @author Roque Rueda
 * @since 17/04/2014
 * @version 1.0
 * 
 */
public interface DataBaseObserver {
	
	/**
	 * Notify the observer that a change on a table has been made.
	 * @param tableName Name of the table that was change.
	 */
	void notifyTableChange(String tableName);
	
	/**
	 * Notify that a database change has happen and the information
	 * should be re query.
	 */
	void notifyDatabaseChange();
	
}
