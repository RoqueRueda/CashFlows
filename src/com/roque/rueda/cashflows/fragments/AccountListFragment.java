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
package com.roque.rueda.cashflows.fragments;

import java.util.LinkedHashSet;

import com.roque.rueda.android.messenger.ListItemClickNotification;
import com.roque.rueda.cashflows.R;
import com.roque.rueda.cashflows.adapters.AccountAdapter;
import com.roque.rueda.cashflows.database.observer.DataBaseObserver;
import com.roque.rueda.cashflows.database.observer.DatabaseMessenger;
import com.roque.rueda.cashflows.loader.AccountLoader;

import android.app.Activity;
import android.database.Cursor;
import android.os.Bundle;
import android.support.v4.app.ListFragment;
import android.support.v4.app.LoaderManager.LoaderCallbacks;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Fragment used to display a list of accounts that are
 * related to the user.
 * 
 * @author Roque Rueda
 * @since 06/05/2014
 * @version 1.0
 * 
 */
public class AccountListFragment extends ListFragment implements 
		LoaderCallbacks<Cursor>,	DatabaseMessenger{

	// Tag for this class.
	private static final String TAG = "AccountListFragment";
	private static final boolean DEBUG = true;
	
	// The loader id, this is unique for the ListFragment and LoaderManager.
	private static final int LOADER_ID = 1;
	
	// Used to store the observers.
	private LinkedHashSet<DataBaseObserver> mObservers;
	
	/**
	 * Loader that loads the information of the accounts.
	 */
	private AccountLoader mLoader;
	
	/**
	 * Adapter used to display the information.
	 */
	private AccountAdapter mAdapter;
	
	/**
	 * The object who cares when an item of this list is clicked.
	 */
	private ListItemClickNotification careTaker;
	
	/**
	 * Called when the activity is created.
	 * @param savedInstanceState		Bundle that contains all the 
	 * 
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		// Create an empty adapter.
		mAdapter = new AccountAdapter(getActivity(), null);
		setEmptyText(getActivity().getResources().getString(R.string.loading_accounts));
		
		// Set the data to the list.
		setListAdapter(mAdapter);
		setListShown(false);
		
		if (DEBUG) {
			Log.i(TAG, "== Calling initLoader() ==");
			
			if (getLoaderManager().getLoader(LOADER_ID) == null) {
				Log.i(TAG, "== Initialize new Loader ==");
			} else {
				Log.i(TAG, "== Reuse an existing Loader ==");
			}
			
		}
		
		// Store all the database observers.
		mObservers = new LinkedHashSet<DataBaseObserver>();
		
		// Check if there's an existing loader searching by the Id.
		// If we have a loader it will be reuse.
		mLoader = (AccountLoader) getLoaderManager().initLoader(LOADER_ID, null, this);
		register(mLoader);
		
		setListerners();
	}
	
	/**
	 * Set the listeners for this ListView.
	 */
	private void setListerners() {
		ListView lv = getListView();
		
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setDivider(getResources().getDrawable(R.color.divider_blue));
		lv.setDividerHeight(1);
	}
	
	/**
	 * Handles the event when this fragment gets attached.
	 * @param activity 				Parent activity that is used to contain this fragment.
	 */
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		// Activity that contain this fragment should implement ListItemClickNotification.
		if (!(activity instanceof ListItemClickNotification)) {
			Log.wtf(TAG, "== onAttach(). The parent activity must implement ListItemClickNotification. ==");
			throw new IllegalStateException("The parent activity must implement ListItemClickNotification.");
		}
		
		careTaker = (ListItemClickNotification) activity;
	}
	
	
	/**
	 * This method handle the click of each item click. 
	 * @param l		{@link ListView} source of the event.
	 * @param v		{@link View} the object that was clicked.
	 * @param id		Identifier of the item that was press.
	 */
	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		super.onListItemClick(l, v, position, id);
		
		// TODO: Get the id.
		
		careTaker.onItemSelected(id);
	}
	
	/////////////////////////////////////////////////////////////////////
	// LoaderCallbacks Interface members..
	///////////////////////////////////////////////////////////////////

	/**
	 * Creates an instance of the Loader used in this activity. 
	 * @param id			Identifier of the loader.
	 * @param args		Arguments used to create the loader.
	 */
	@Override
	public Loader<Cursor> onCreateLoader(int id, Bundle args) {
		if (DEBUG) {
			Log.i(TAG, "== onCreateLoader() Creating a new Account Loader. ==");
		}
		
		AccountLoader loader = new AccountLoader(getActivity());
		return loader;
	}

	/**
	 * Called when the load completes this is when we can set the data
	 * in our adapter to present the information to the user.
	 * 
	 * @param loader 			Loader that have complete the operation.
	 * @param data				Data that has been load.
	 */
	@Override
	public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
		if (DEBUG) {
			Log.i(TAG, "== onLoadFinished()  ==");
		}
		
		mAdapter.changeCursor(data);
		
		if (isResumed()) {
			setListShown(true);
		} else {
			setListShownNoAnimation(true);
		}
	}

	/**
	 * Called when the loader is reset, in this case we should release the data
	 * that we have on our adapter.
	 * 
	 * @param loader			Loader that will be reset.
	 */
	@Override
	public void onLoaderReset(Loader<Cursor> loader) {
		if (DEBUG) {
			Log.i(TAG, "== onLoaderReset() ==");
		}
		
		mAdapter.changeCursor(null);
	}
	
	/////////////////////////////////////////////////////////////////////
	// DataBase Messenger Interface members.
    ///////////////////////////////////////////////////////////////////

	/**
	 * Register a new observer to receive the calls when a database change.
	 * @param observer Instance observer that will be notify by this Messenger.
	 */
	@Override
	public void register(DataBaseObserver observer) {
		mObservers.add(observer);
	}

	/**
	 * Unregister a database observer in order to stop receive calls from
	 * changes on the database.
	 * @param observer Instance observer that will be unregister from this messenger.
	 */
	@Override
	public void unregister(DataBaseObserver observer) {
		mObservers.remove(observer);
	}

	/**
	 * Notify to the database observer that a table has change.
	 * @param tableName Name of the table that was changed.
	 */
	@Override
	public void sendNotification(String tableName) {
		for (DataBaseObserver observer : mObservers) {
			observer.notifyTableChange(tableName);
		}
	}

	/**
	 * Notify all the register observers that a database change has been made.
	 */
	@Override
	public void sendNotification() {
		for (DataBaseObserver observer : mObservers) {
			observer.notifyDatabaseChange();
		}
	}
	
}
