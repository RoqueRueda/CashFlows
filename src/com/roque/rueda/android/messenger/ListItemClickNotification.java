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
package com.roque.rueda.android.messenger;

/**
 * Interface used to notify the container activity when a list view item is selected.
 * 
 * @author Roque Rueda
 * @since 26/05/2014
 * @version 1.0
 * 
 */
public interface ListItemClickNotification {
	
    /**
     * Callback for when an item has been selected.
     */
    public void onItemSelected(long itemId);
}
