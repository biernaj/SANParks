package com.abbyy.sanparks.mobile.ocr4;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.TypedArray;
import android.preference.ListPreference;
import android.util.AttributeSet;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.abbyy.sanparks.mobile.ocr4.utils.*;
import com.sanparks.sanscan.R;

/**
 * 
 * @author declanshanaghy http://blog.350nice.com/wp/archives/240 MultiChoice
 *         Preference Widget for Android
 * 
 * @contributor matiboy Added support for check all/none and custom _separator
 *              defined in XML. IMPORTANT: The following attributes MUST be
 *              defined (probably inside attr.xml) for the code to even compile
 *              <declare-styleable name="ListPreferenceMultiSelect"><attr format="string"
 *              name="_separator" /> </declare-styleable> Whether you decide to
 *              then use those attributes is up to you.
 * 
 */
@SuppressLint("ShowToast")
public class ListPreferenceMultiSelect extends ListPreference {

	public static interface Filter {
		public boolean isEntryVisible( CharSequence entry );
	}

	private static final String DEFAULT__SEPARATOR = ";";

	private String _separator;
	private boolean[] _clickedDialogEntryIndices;
	private Filter _filter;
	private boolean[] _filterMask;
	private int _filteredEntriesArrayLength;

	private int _checkedEntriesCount = 0;

	private int _minAvailableCheckedEntriesCount = 0;
	private String _errorMinEntriesCountReached;
	private String _errorMinEntriesCountExceeded;

	private int _maxAvailableCheckedEntriesCount = Integer.MAX_VALUE;
	private String _errorMaxEntriesCountReached;
	private String _errorMaxEntriesCountExceeded;

	// Constructor
	public ListPreferenceMultiSelect( final Context context, final AttributeSet attrs ) {
		super( context, attrs );

		final TypedArray attributes =
				context.obtainStyledAttributes( attrs, R.styleable.ListPreferenceMultiSelect );
		_separator = attributes.getString( R.styleable.ListPreferenceMultiSelect_separator );
		if( _separator == null ) {
			_separator = ListPreferenceMultiSelect.DEFAULT__SEPARATOR;
		}

		// Initialize the array of boolean to the same size as number of entries
		_clickedDialogEntryIndices = new boolean[getEntries().length];
		attributes.recycle();
	}

	public void setFilter( final Filter filter ) {
		_filter = filter;
	}

	/**
	 * Set constraint for checked entries count.
	 * This constraint is guaranteed to be not violated only during an interaction with the user.
	 * So, this constraint can be violated because
	 * 1) checked entries count doesn't fit to constraint bounds after view initialization;
	 * 2) checked entries count is less then {@code minAvailableCheckedEntriesCount} after entries filtration;
	 * When constraint is violated the warning message is shown.
	 * 
	 * @param minAvailableCheckedEntriesCount
	 *            Minimal checked entries count constraint.
	 * @param errorMinEntriesCountReached
	 *            Message to be shown when minimal entries count is reached.
	 *            Can be null. If null then no message is shown.
	 * @param errorMinEntriesCountExceeded
	 *            Message to be shown when minimal entries count is exceeded.
	 *            Can be null. If null then no message is shown.
	 * @param maxAvailableCheckedEntriesCount
	 *            Maximum checked entries count constraint.
	 * @param errorMaxEntriesCountReached
	 *            Message to be shown when maximum entries count is reached.
	 *            Can be null. If null then no message is shown.
	 * @param errorMaxEntriesCountExceeded
	 *            Message to be shown when maximum entries count is exceeded.
	 *            Can be null. If null then no message is shown.
	 * @throws IllegalArgumentException
	 *             (minAvailableCheckedEntriesCount < 0) or (maxAvailableCheckedEntriesCount < 0) or
	 *             (minAvailableCheckedEntriesCount > maxAvailableCheckedEntriesCount)
	 */
	public void setEntriesCountConstraint( final int minAvailableCheckedEntriesCount,
			final String errorMinEntriesCountReached, final String errorMinEntriesCountExceeded,
			final int maxAvailableCheckedEntriesCount, final String errorMaxEntriesCountReached,
			final String errorMaxEntriesCountExceeded ) throws IllegalArgumentException {
		if( minAvailableCheckedEntriesCount < 0 ) {
			throw new IllegalArgumentException(
					"minAvailableCheckedEntriesCount should be greater or equal then zero." );
		}
		if( maxAvailableCheckedEntriesCount < 0 ) {
			throw new IllegalArgumentException(
					"maxAvailableCheckedEntriesCount should be greater or equal then zero." );
		}
		if( minAvailableCheckedEntriesCount > maxAvailableCheckedEntriesCount ) {
			throw new IllegalArgumentException(
					"minAvailableCheckedEntriesCount should be less or equal then maxAvailableCheckedEntriesCount." );
		}

		_minAvailableCheckedEntriesCount = minAvailableCheckedEntriesCount;
		_errorMinEntriesCountReached = errorMinEntriesCountReached;
		_errorMinEntriesCountExceeded = errorMinEntriesCountExceeded;

		_maxAvailableCheckedEntriesCount = maxAvailableCheckedEntriesCount;
		_errorMaxEntriesCountReached = errorMaxEntriesCountReached;
		_errorMaxEntriesCountExceeded = errorMaxEntriesCountExceeded;
	}

	@Override
	public CharSequence[] getEntries() {
		CharSequence[] entries = super.getEntries();
		if( _filter != null ) {
			// Allocate array for filter mask
			_filterMask = new boolean[entries.length];
			// Filter entries and store filter mask for getEntriesValues()
			final ArrayList<CharSequence> filteredEntries = new ArrayList<CharSequence>();
			int i = 0;
			for( final CharSequence entry : entries ) {
				final boolean isEntryVisible = _filter.isEntryVisible( entry );
				_filterMask[i++] = isEntryVisible;
				if( isEntryVisible ) {
					filteredEntries.add( entry );
				}
			}
			_filteredEntriesArrayLength = filteredEntries.size();
			entries = new CharSequence[_filteredEntriesArrayLength];
			filteredEntries.toArray( entries );
		}
		return entries;
	}

	@Override
	public CharSequence[] getEntryValues() {
		CharSequence[] entryValues = super.getEntryValues();
		if( _filterMask != null ) {
			final CharSequence[] filteredEntryValues = new CharSequence[_filteredEntriesArrayLength];
			int j = 0;
			for( int i = 0; i < entryValues.length; ++i ) {
				if( _filterMask[i] ) {
					filteredEntryValues[j++] = entryValues[i];
				}
			}
			entryValues = filteredEntryValues;
		}
		return entryValues;
	}

	@Override
	public void setEntries( final CharSequence[] entries ) {
		super.setEntries( entries );
		// Initialize the array of boolean to the same size as number of entries
		_clickedDialogEntryIndices = new boolean[entries.length];
	}

	public ListPreferenceMultiSelect( final Context context ) {
		this( context, null );
	}

	@Override
	protected void onPrepareDialogBuilder( final Builder builder ) {
		final CharSequence[] entries = getEntries();
		final CharSequence[] entryValues = getEntryValues();
		if( entries == null || entryValues == null || entries.length != entryValues.length ) {
			throw new IllegalStateException(
					"ListPreference requires an entries array and an entryValues array which are both the same length" );
		}

		restoreCheckedEntries();
		builder.setMultiChoiceItems( entries, _clickedDialogEntryIndices,
				new DialogInterface.OnMultiChoiceClickListener() {
					@Override
					public void onClick( final DialogInterface dialog, final int which, final boolean val ) {
						if( val ) {
							if( _checkedEntriesCount < _maxAvailableCheckedEntriesCount ) {
								++_checkedEntriesCount;
								_clickedDialogEntryIndices[which] = true;

								if( _checkedEntriesCount < _minAvailableCheckedEntriesCount ) {
									if( _errorMinEntriesCountExceeded != null ) {
										final Context context = ( (AlertDialog) dialog ).getContext();
										Toast.makeText( context, _errorMinEntriesCountExceeded, Toast.LENGTH_LONG ).show();
									}
								}
							} else {
								// Maximum checked entries count constraint is violated.
								// The list entry is already checked and we need to uncheck it back.
								final Context context = ( (AlertDialog) dialog ).getContext();
								final ListView listView = ( (AlertDialog) dialog ).getListView();
								_clickedDialogEntryIndices[which] = false;
								listView.setItemChecked( which, false );
								if( _errorMaxEntriesCountReached != null ) {
									Toast.makeText( context, _errorMaxEntriesCountReached, Toast.LENGTH_SHORT ).show();
								}
							}
						} else {
							if( _checkedEntriesCount > _minAvailableCheckedEntriesCount ) {
								--_checkedEntriesCount;
								_clickedDialogEntryIndices[which] = false;

								if( _checkedEntriesCount > _maxAvailableCheckedEntriesCount ) {
									if( _errorMaxEntriesCountExceeded != null ) {
										final Context context = ( (AlertDialog) dialog ).getContext();
										Toast.makeText( context, _errorMaxEntriesCountExceeded, Toast.LENGTH_LONG ).show();
									}
								}
							} else {
								// Minimal checked entries count constraint is violated.
								// The list entry is already unchecked and we need to check it back.
								final Context context = ( (AlertDialog) dialog ).getContext();
								final ListView listView = ( (AlertDialog) dialog ).getListView();
								_clickedDialogEntryIndices[which] = true;
								listView.setItemChecked( which, true );
								if( _errorMinEntriesCountReached != null ) {
									Toast.makeText( context, _errorMinEntriesCountReached, Toast.LENGTH_SHORT ).show();
								}
							}
						}
					}
				} );
	}

	public String[] parseStoredValue( final CharSequence val ) {
		if( "".equals( val ) ) {
			return null;
		} else {
			return ( (String) val ).split( _separator );
		}
	}

	private void restoreCheckedEntries() {
		final CharSequence[] entryValues = getEntryValues();

		// Explode the string read in sharedpreferences
		final String[] vals = parseStoredValue( getValue() );

		if( vals != null ) {
			final List<String> valuesList = Arrays.asList( vals );
			// for ( int j=0; j < vals.length; j++ ) {
			// TODO: Check why the trimming... Can there be some random spaces
			// added somehow? What if we want a value with trailing spaces, is
			// that an issue?
			// String val = vals[j].trim();
			_checkedEntriesCount = 0;
			for( int i = 0; i < entryValues.length; i++ ) {
				final CharSequence entry = entryValues[i];
				if( valuesList.contains( entry ) ) {
					_clickedDialogEntryIndices[i] = true;
					++_checkedEntriesCount;
				}
			}
			if( _checkedEntriesCount < _minAvailableCheckedEntriesCount ) {
				if( _errorMinEntriesCountExceeded != null ) {
					final Context context = getContext();
					Toast.makeText( context, _errorMinEntriesCountExceeded, Toast.LENGTH_LONG );
				}
			} else if( _checkedEntriesCount > _maxAvailableCheckedEntriesCount ) {
				if( _errorMaxEntriesCountExceeded != null ) {
					final Context context = getContext();
					Toast.makeText( context, _errorMaxEntriesCountExceeded, Toast.LENGTH_LONG );
				}
			}
			// }
		}
	}

	@Override
	protected void onDialogClosed( final boolean positiveResult ) {
		// super.onDialogClosed(positiveResult);
		final ArrayList<String> values = new ArrayList<String>();

		final CharSequence[] entryValues = getEntryValues();
		if( positiveResult && entryValues != null ) {
			for( int i = 0; i < entryValues.length; i++ ) {
				if( _clickedDialogEntryIndices[i] == true ) {
					final String val = (String) entryValues[i];
					values.add( val );
				}
			}

			// TODO is fix correct?
			if( callChangeListener( values ) ) {
				setValue( StringUtils.join( values, _separator ) );
			}
		}
	}

	// TODO: Would like to keep this static but _separator then needs to be put
	// in by hand or use default _separator ";"...
	/**
	 * 
	 * @param straw
	 *            String to be found
	 * @param haystack
	 *            Raw string that can be read direct from preferences
	 * @param _separator
	 *            _separator string. If null, static default _separator will be
	 *            used
	 * @return boolean True if the straw was found in the haystack
	 */
	public static boolean contains( final String straw, final String haystack, String _separator ) {
		if( _separator == null ) {
			_separator = ListPreferenceMultiSelect.DEFAULT__SEPARATOR;
		}
		final String[] vals = haystack.split( _separator );
		for( final String val : vals ) {
			if( val.equals( straw ) ) {
				return true;
			}
		}
		return false;
	}
}
