package com.sanparks.sanscan;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * A fragment representing a single Check detail screen. This fragment is either
 * contained in a {@link CheckListActivity} in two-pane mode (on tablets) or a
 * {@link CheckDetailActivity} on handsets.
 */
public class CheckDetailFragment extends Fragment 
	{
	/**
	 * The fragment argument representing the item ID that this fragment
	 * represents.
	 */
	public static final String ARG_ITEM_ID = "item_id";
	public CHECK_TYPE m_eCheckType;

	private ContentEntry.CheckItem mItem;

//	public CheckDetailFragment(CHECK_DETAIL_TYPE dtlType) {
//		m_eCheckType = dtlType;
//	}

    /**
	 * Mandatory empty constructor for the fragment manager to instantiate the
	 * fragment (e.g. upon screen orientation changes).
	 */
	public CheckDetailFragment() { }

	@Override
	public void onCreate(Bundle savedInstanceState) 
		{
		super.onCreate(savedInstanceState);

		if (getArguments().containsKey(ARG_ITEM_ID)) 
			{
			// Load the dummy content specified by the fragment
			// arguments. In a real-world scenario, use a Loader
			// to load content from a content provider.
			mItem = ContentEntry.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
			}
		}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
		int resource = 0;
		
		switch (m_eCheckType)
			{
//			case VEHICLE:
//				resource = R.layout.fragment_check_detail_vehicle;
//				break;
//			case DRIVER_PILOT:
//				resource = R.layout.fragment_check_detail_driver;
//				break;
//			case PASSENGER:
//				resource = R.layout.fragment_check_detail_passenger;
//				break;
//			case WILDCARD:
//				resource = R.layout.fragment_check_detail_wildcard;
//				break;
//			case RESERVATION:
//				resource = R.layout.fragment_check_detail_wildcard;
//				break;
//			case WEAPON:
//				resource = R.layout.fragment_check_detail_weapon;
//				break;
			default:
				return null;
			}

		//		View rootView = inflater.inflate(R.layout.fragment_check_detail, container, false);
//		View rootView = inflater.inflate(resource, container, false);

		// Show the content as text in a TextView.
//		if (mItem != null) 
//			((TextView) rootView.findViewById(R.id.check_detail)).setText(mItem.content);
//
//		return rootView;
		}
	
	}
