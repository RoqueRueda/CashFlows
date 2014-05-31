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
package com.roque.rueda.cashflows;

import com.roque.rueda.android.messenger.ListItemClickNotification;
import com.roque.rueda.cashflows.fragments.MovementsListFragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

/**
 * Main activity of the cash flows.
 * 
 * @author Roque Rueda
 * @since 06/04/2014
 * @version 1.0
 * 
 */
public class MainActivity extends FragmentActivity
		implements ListItemClickNotification {

	private static final String TAG = "MainActivity";
	private static final boolean DEBUG = true;
	
	// Is this activity showing two, pane.
	private boolean mIsTwoPane;
	
	/**
	 * 
	 * This method handles the creation of the activity. Base
	 * on the activity life cycle.
	 * @param savedInstanceState		Arguments used to create
	 * 													the activity.
	 */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		if (DEBUG) {
			Log.i(TAG, "== Starting the application! ==");
		}
		
		if (findViewById(R.id.movement_container) != null) {
			// The detail container view will be present only in the
            // large-screen layouts (res/values-large and
            // res/values-sw600dp). If this view is present, then the
            // activity should be in two-pane mode.
			
			mIsTwoPane = true;
			
			// In two-pane mode, list items should be given the
            // 'activated' state when touched.
//			((NewListFragment) getSupportFragmentManager()
//                    .findFragmentById(R.id.new_list))
//                    .setActivateOnItemClick(true);
		}
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	/////////////////////////////////////////////////////////////////////
	// LoaderCallbacks Interface members..
	///////////////////////////////////////////////////////////////////
	
	/**
     * Callback for when an item has been selected on a inner account list.
     */
	@Override
	public void onItemSelected(long itemId) {
		
		// If this activity is running as two pane show the information on the side.
		// otherwise show a new activity.
		
		if (mIsTwoPane) {
			// In two-pane mode, show the detail view in this activity by
            // adding or replacing the detail fragment using a
            // fragment transaction.
			
			
		} else {
			// In single-pane mode, simply start the detail activity
            // for the selected item ID.
			
			Intent cashMovements = new Intent(this, MovementsActivity.class);
			cashMovements.putExtra(MovementsListFragment.ARG_ACCOUNT_ID,
					itemId);
			startActivity(cashMovements);
		}
		
//		Toast.makeText(this, "Selected id:" + itemId, Toast.LENGTH_SHORT).show();
	}

}
