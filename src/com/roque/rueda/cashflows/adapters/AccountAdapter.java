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
package com.roque.rueda.cashflows.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.roque.rueda.cashflows.R;
import com.roque.rueda.cashflows.database.AccountTable;
import com.roque.rueda.cashflows.model.Account;
import com.roque.rueda.cashflows.viewholder.AccountView;

/**
 * Adapter for the accounts that set the views to present account
 * information on the screen for the list view widget.
 * 
 * @author Roque Rueda
 * @since 08/05/2014
 * @version 1.0
 * 
 */
public class AccountAdapter extends CursorAdapter {

	private Context mContext;
	private final LayoutInflater mInflater;
	private static int sCursorFlags = 0;
	
	
	/**
	 * Creates an Account Adapter used to present the information
	 * of the accounts.
	 * @param context 		The context of the application.
	 * @param c 					The cursor from which to get the data. 
	 * 									The cursor is already moved to the correct position.
	 */
	public AccountAdapter(Context context, Cursor c) {
		super(context, c, sCursorFlags);
		
		mContext = context;
		mInflater = LayoutInflater.from(mContext);
		
	}
	
	
	/**
	 * Bind an existing view to the data pointed to by cursor.
	 * @param view 		Existing view, returned earlier by newView
	 * @param ctx 			Interface to application's global information
	 * @param cursor		The cursor from which to get the data. 
	 * 								The cursor is already moved to the correct position.
	 * 
	 */
	@Override
	public void bindView(View view, Context ctx, Cursor cursor) {
		
		Account account = new Account();
		account.id = cursor.getLong(cursor.getColumnIndex(AccountTable._ID));
		account.initialBalance = cursor.getDouble(cursor.getColumnIndex(AccountTable.ACCOUNT_INITIAL_BALANCE));
		account.endBalance = cursor.getDouble(cursor.getColumnIndex(AccountTable.ACCOUNT_END_BALANCE));
		account.photoNumber = cursor.getLong(cursor.getColumnIndex(AccountTable.PHOTO_NUMBER));
		account.name = cursor.getString(cursor.getColumnIndex(AccountTable.ACCOUNT_NAME));
		
		AccountView holder = (AccountView) view.getTag();
		holder.setModel(account);
	}

	
	/**
	 * Makes a new view to hold the data pointed to by cursor.
	 * 
	 * @param context			Interface to application's global information.
	 * @param cursor 			The cursor from which to get the data. 
	 * 									The cursor is already moved to the correct position.
	 *  @param parent 		The parent to which the new view is attached to.
	 * 
	 */
	@Override
	public View newView(Context context, Cursor cursor, ViewGroup parent) {
		final View view = mInflater.inflate(R.layout.account_list_item_nice, parent, false);
		// View holder that is used to avoid calls to find view by Id.
		AccountView holder = new AccountView(view, context.getResources());
		view.setTag(holder);
		return view;
	}
	

}
