package com.sanparks.scanDB;

public enum E_VISITOR_MODE 
	{
	DRIVER		("Driver", 		E_DISPLAY_CONTEXT.ALL),		// first person in a group 
	PILOT		("Pilot", 		E_DISPLAY_CONTEXT.ALL),		// first person in a group
	CREW		("Crew", 		E_DISPLAY_CONTEXT.ALL),		// further people related to first person

	PASSENGER	("Passenger", 	E_DISPLAY_CONTEXT.ALL), 	// further people related to first person

	PEDESTRIAN	("Pedestrian", 	E_DISPLAY_CONTEXT.ALL),

	ALL			("All", 		E_DISPLAY_CONTEXT.QUERY);

	private String						name;
	private E_DISPLAY_CONTEXT 			display_context;
	public final static E_VISITOR_MODE 	values[] = values();

	E_VISITOR_MODE(String description, E_DISPLAY_CONTEXT displayContext) 
		{
		name = description;
		display_context = displayContext;
		}
	
	public String getName() 
		{ 
		return name; 
		}
	
	public E_DISPLAY_CONTEXT getDisplayContext () 
		{ 
		return display_context; 
		}
	}	

