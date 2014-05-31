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

import java.util.LinkedHashSet;

/**
 * 
 * This class is used to watch the changes over the SQLite database.
 * 
 * @author Roque Rueda
 * @since 17/04/2014
 * @version 1.0
 * 
 */
public class SQLiteMessenger implements DatabaseMessenger {

	// Stores the observers.
	private LinkedHashSet<DataBaseObserver> observers;
	
	/**
	 * Creates an instance with the default values.
	 */
	public SQLiteMessenger() {
		observers = new LinkedHashSet<DataBaseObserver>();
	}
	
	/**
	 * Register a new object into the list of observers to be notify by this instance.
	 * @param observer Observer that will be add to the list.
	 */
	@Override
	public void register(DataBaseObserver observer) {
		
		// No duplicate objects.
		if(observers.contains(observer)) {
			return;
		}
		
		observers.add(observer);
	}

	/**
	 * Removes a existing observer from the list of observers to be notified
	 * by this instance.
	 * @param observer Observer that will be removed of the list.
	 */
	@Override
	public void unregister(DataBaseObserver observer) {
		
		// We can't delete the observer because it's not on the list.
		if (!observers.contains(observer)) {
			return;
		}
		
		observers.remove(observer);
	}

	/**
	 * Sends a notification to the list of observers with the table
	 * name that has been change.
	 * @param tableName Name of the table that has been change.
	 */
	@Override
	public void sendNotification(String tableName) {
		for (DataBaseObserver obs : observers) {
			obs.notifyTableChange(tableName);
		}
	}

	/**
	 * Notify the list of observer when a change on the database
	 * has been made.
	 */
	@Override
	public void sendNotification() {
		for (DataBaseObserver obs : observers) {
			obs.notifyDatabaseChange();
		}
	}

}
