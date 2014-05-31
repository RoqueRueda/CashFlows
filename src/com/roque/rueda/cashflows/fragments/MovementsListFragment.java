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

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.roque.rueda.cashflows.R;
import com.roque.rueda.cashflows.R.layout;

/**
 * Activity used to present the list of cash movements.
 * 
 * @author Roque Rueda
 * @since 22/05/2014
 * @version 1.0
 * 
 */
public class MovementsListFragment extends Fragment {
	
	private static final String TAG = "MovementsListFragment";
	private static final boolean DEBUG = true;
	
	/**
	 * Constant used to retrieve the account id from the extras.
	 */
	public static final String ARG_ACCOUNT_ID = "AccountId";
	
	private long mIdAccount = -1;
	
	/**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public MovementsListFragment() {
    	// This is required by Android.
    }
	
	/**
	 * Called when the activity is created.
	 * @param saveInstanceState			State of the application.
	 */
	@Override
	public void onCreate(Bundle saveInstanceState) {
		super.onCreate(saveInstanceState);
		
		if (DEBUG) {
			Log.i(TAG, "== AccountMovementsActivity starting ==");
		}
		
		if (getArguments().containsKey(ARG_ACCOUNT_ID)) {
			// Get the id.
			mIdAccount = getArguments().getLong(ARG_ACCOUNT_ID);
		} else {
			if (DEBUG) {
				// Print a message when no Id is found.
				Log.w(TAG, "== No id is found for AccountMovementsActivity ==");
			}
			
			
		}
	}
	
	/**
	 * 
	 * Handle the creation of the view.
	 * 
	 * @param inflater 		Inflater used to generate the view
	 * @param container		View that is used as the parent of this fragment.
	 * @param savedInstanceState
	 * 									Arguments used to create the view.
	 * @return						View that will present this fragment.
	 */
	@Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
		
		if (DEBUG) {
			Log.i(TAG, "== onCreateView() ==");
		}
		
        View rootView = inflater.inflate(R.layout.cash_movements, container, false);
    	((TextView) rootView.findViewById(R.id.id_account)).setText("Account id: " + mIdAccount);
        
        return rootView;
    }
	
}
