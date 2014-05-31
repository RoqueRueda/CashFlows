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
package com.roque.rueda.cashflows.loader;

import android.support.v4.content.AsyncTaskLoader;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;

import com.roque.rueda.cashflows.database.AccountManager;
import com.roque.rueda.cashflows.database.observer.DataBaseObserver;

/**
 * Account loader this class is responsible for fetching the information
 * from the database.
 * 
 * @author Roque Rueda
 * @since 06/04/2014
 * @version 1.0
 *
 */
public class AccountLoader extends AsyncTaskLoader<Cursor> implements DataBaseObserver {

	private static final String TAG = "AccountLoader";
	private static final boolean DEBUG = true;
	
	/**
	 * Determines whether this class is observing for changes.
	 */
	private boolean mIsObserving = false;
	
	/**
	 * Reference to the data.
	 */
	private Cursor mAccounts;
	
	/**
	 * Creates an instance of this class with the given context.
	 * @param context Context of the application.
	 */
	public AccountLoader(Context context) {
		super(context);
		// Since a Loader can be use on several Activity's we
		// are not going to hold a reference to the context
		// directly. We can get the reference using getContext().
	}

	/**
	 * Method use to load the data on a background thread, generates
	 * the {@code Cursor} that we will use to hold the data and pass to the
	 * client.
	 * @return {@code Cursor} with accounts.
	 */
	@Override
	public Cursor loadInBackground() {
		
		if (DEBUG) {
			Log.i(TAG, "== loadInBackground() start to load the accounts. ==");
		}
		
		AccountManager manager = new AccountManager(getContext());
		Cursor data = manager.getAccountsForCurrentPeriod();
		
		return data;
	}
	
	/**
	 * Used to display new data to the client. Super class is used
	 * to deliver the data to the registered listeners. The listener
	 * forward data to the client using the call to onLoadFinished.
	 * 
	 */
	@Override
	public void deliverResult(Cursor data) {

		// We need to override this method also in order to
		// deliver the data to the clients. Also we can use
		// several methods to know if this call comes from a
		// reset status, cancel and so on.
		
		
		if (isReset()) {
			if (DEBUG) {
				Log.i(TAG, "== Warning! A call to deliver reult with a query come on a reset status. ==");
			}
			// The loader has been reset; ignore the result and clean the data.
			if (data != null) {
				releaseResources(data);				
			}
			return;
		}
		
		// This set is used to prevent destroy of the instance from the garbage collector.
		Cursor oldData = mAccounts;
		mAccounts = data;
		
		if (isStarted()) {
			// Deliver the results of the client.
			super.deliverResult(data);
		}
		
		// Invalidate the old data as we don't need this anymore.
		if(oldData != null && oldData != data) {
			if (DEBUG) {
				Log.i(TAG, "== deliverResult() releasing old data... ==");
			}
			
			releaseResources(oldData);
		}
	}
	
	/**
	 * Helper method used to release the resources of this Loader.
	 * @param data Data that will be release.
	 */
	private void releaseResources(Cursor data) {
		// Release resources.
		if (data != null) {
			data.close();
			data = null;
		}
	}
	
	/**
	 * Handles a request to start this Loader.
	 */
	@Override
	protected void onStartLoading() {
		
		if (DEBUG) {
			Log.i(TAG, "== onStartLoading() ==");
		}
		
		if (mAccounts != null) {
			// Deliver the previously loaded data immediately.
			deliverResult(mAccounts);
		}
		
		if (!mIsObserving) {
			mIsObserving = true;
		}
		
		// This method is used to notify if a change has happen.
		if (takeContentChanged()) {
			// When our Observer detects a event it will call to
			// onContentChanged on this instance, this will cause that
			// the next call to takeContentChanged() return true.
			// In this case or if the current data is null we force a new 
			// load.
			if (DEBUG) {
				Log.i(TAG, "== A new change has been submit, starting force load... ==");
			}
			
			// This method actually loads the data.
			forceLoad();
		} else if (mAccounts == null) {
			
			if (DEBUG) {
				Log.i(TAG, "== No accounts found, starting force load. ==");
			}
			
			// This method actually loads the data.
			forceLoad();
			
		}		
	}
	
	/**
	 * Called when the loader is going to stop.
	 */
	@Override
	protected void onStopLoading() {
		if (DEBUG) {
			Log.i(TAG, "== onStopLoading() Stoping the account loader... ==");
		}
		
		// Stop any current load.
		cancelLoad();
	}
	
	/**
	 * Called when the loader will be reset.
	 */
	@Override
	protected void onReset() {
		if (DEBUG) {
			Log.i(TAG, "== onReset() Reset for account loader... ==");
		}
		
		// We need to make sure that the loaders is stopped.
		onStopLoading();
		
		if (mAccounts != null) {
			releaseResources(mAccounts);
			mAccounts = null;
		}
		
		if (mIsObserving) {
			// We need to stop checking for database changes.
			mIsObserving = false;
		}
		
	}
	
	/**
	 * Request to cancel any current task on the loader.
	 */
	@Override
	public void onCanceled(Cursor data) {
		if (DEBUG) {
			Log.i(TAG, "== onCanceled() Cancel account loader. ==");
		}
		
		// Cancel the current load.
		super.onCanceled(data);
		
		// Release resources.
		releaseResources(data);
	}
	
	/**
	 * Request to load the data on this Loader.
	 */
	@Override
	public void forceLoad() {
		if (DEBUG) {
			Log.i(TAG, "== forceLoad() Loading accounts... ==");
		}
		
		super.forceLoad();
	}
	
	/** 
	 * Method used to get a notification when a change
	 * on the table is made.
	 * 
	 */
	@Override
	public void notifyTableChange(String tableName) {
		// Send a notification that the contend has being changed.
		if (mIsObserving) {
			onContentChanged();
		}
		
	}

	/**
	 * Method used to notify for any change on the database. 
	 */
	@Override
	public void notifyDatabaseChange() {
		if (mIsObserving) {
			onContentChanged();
		}
	}
	
}
