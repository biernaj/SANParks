package com.sanparks.sanscan;

import com.sanparks.scanDB.tblXavia;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

/**
 * An activity representing a list of Scan Entries. This activity has different
 * presentations for handset and tablet-size devices. On handsets, the activity
 * presents a list of items, which when touched, lead to a {@link CheckListActivity}. 
 * On tablets, the activity presents the list of items and item details side-by-side 
 * using two vertical panes.
 * <p>
 * The activity makes heavy use of fragments. The list of items is a
 * {@link EntryListFragment} and the item details (if present) is a
 * {@link CheckListFragment}.
 * <p>
 * This activity also implements the required
 * {@link EntryListFragment.Callbacks} interface to listen for item selections.
 */
public class EntryListActivity extends Activity implements EntryListFragment.Callbacks {

	/**
	 * Whether or not the activity is in two-pane mode, i.e. running on a tablet
	 * device.
	 */
	private boolean mTwoPane;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.activity_entry_list);

		if (findViewById(R.id.check_list_container) != null) {
			// The detail container view will be present only in the
			// large-screen layouts (res/values-large and
			// res/values-sw600dp). If this view is present, then the
			// activity should be in two-pane mode.
			mTwoPane = true;

			// In two-pane mode, list items should be given the
			// 'activated' state when touched.
			((EntryListFragment) getFragmentManager().findFragmentById(
					R.id.entry_list)).setActivateOnItemClick(true);
		}

		tblXavia.registerXaviaUnit(this);


		
		// TODO: If exposing deep links into your app, handle intents here.
	}

	/**
	 * Callback method from {@link CheckListFragment.Callbacks} indicating that
	 * the item with the given ID was selected.
	 */
	@Override
	public void onItemSelected(String id) {
		if (mTwoPane) {
			// In two-pane mode, show the detail view in this activity by
			// adding or replacing the detail fragment using a
			// fragment transaction.
			Bundle arguments = new Bundle();
			
			arguments.putString(CheckListFragment.ARG_ITEM_ID, id);
			
			CheckListFragment fragment = new CheckListFragment();

			fragment.setArguments(arguments);

			getFragmentManager().beginTransaction().replace(R.id.check_list, fragment).commit();

		} else {
			// In single-pane mode, simply start the detail activity
			// for the selected item ID.
			Intent detailIntent = new Intent(this, CheckListActivity.class);
			
			detailIntent.putExtra(CheckListFragment.ARG_ITEM_ID, id);
			startActivity(detailIntent);
		}
	}
	
	public void cmdAddNewEntry (View view) {
	    Intent detailIntent = new Intent(this, WizardEntryStartActivity.class);
	    startActivity(detailIntent);
	}
	
}
