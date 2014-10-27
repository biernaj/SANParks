package com.sanparks.sanscan;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;


/**
 * An activity representing a list of Weapons. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a {@link WeaponListActivity}. 
 * On tablets, the activity presents the list of items and item details side-by-side 
 * using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link WeaponListFragment} and the item details (if present) is a
 * {@link WeaponDetailFragment}.
 * <p>
 * This activity also implements the required
 * {@link WeaponListFragment.Callbacks} interface to listen for item selections.
 */
public class WeaponListActivity extends Activity implements WeaponListFragment.Callbacks {

		/**
		 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
		 * device.
		 */
		private boolean mTwoPane;

		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			setContentView(R.layout.activity_weapon_list);

			if (findViewById(R.id.weapon_list_container) != null) {
				// The detail container view will be present only in the
				// large-screen layouts (res/values-large and
				// res/values-sw600dp). If this view is present, then the
				// activity should be in two-pane mode.
				mTwoPane = true;

				// In two-pane mode, list items should be given the
				// 'activated' state when touched.
				((EntryListFragment) getFragmentManager().findFragmentById(
						R.id.weapon_list)).setActivateOnItemClick(true);
			}

			// TODO: If exposing deep links into your app, handle intents here.
		}

		/**
		 * Callback method from {@link WeaponListFragment.Callbacks} indicating that
		 * the item with the given ID was selected.
		 */
		@Override
		public void onItemSelected(String id) {
			if (mTwoPane) {
				// In two-pane mode, show the detail view in this activity by
				// adding or replacing the detail fragment using a
				// fragment transaction.
				Bundle arguments = new Bundle();
				
				arguments.putString(WeaponListFragment.ARG_ITEM_ID, id);
				
				WeaponListFragment fragment = new WeaponListFragment();

				fragment.setArguments(arguments);

				getFragmentManager().beginTransaction().replace(R.id.weapon_list, fragment).commit();

			} else {
				// In single-pane mode, simply start the detail activity
				// for the selected item ID.
				Intent detailIntent = new Intent(this, WeaponListActivity.class);
				
				detailIntent.putExtra(WeaponListFragment.ARG_ITEM_ID, id);
				startActivity(detailIntent);
			}
		}
}
