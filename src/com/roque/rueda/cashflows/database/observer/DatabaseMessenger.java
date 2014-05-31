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
 * Basic interface used to notify a list of observer.
 * 
 * @author Roque Rueda
 * @since 17/04/2014
 * @version 1.0
 * 
 */
public interface DatabaseMessenger {
	
	/**
	 * Register a new object into the list of observers to be notify by this instance.
	 * @param observer Observer that will be add to the list.
	 */
	void register(DataBaseObserver observer);
	
	/**
	 * Removes a existing observer from the list of observers to be notified
	 * by this instance.
	 * @param observer Observer that will be removed of the list.
	 */
	void unregister(DataBaseObserver observer);
	
	/**
	 * Sends a notification to the list of observers with the table
	 * name that has been change.
	 * @param tableName Name of the table that has been change.
	 */
	void sendNotification(String tableName);
	
	/**
	 * Notify the list of observer when a change on the database
	 * has been made.
	 */
	void sendNotification();

}
