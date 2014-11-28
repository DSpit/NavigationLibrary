

package com.dspit.nav;

import java.util.ArrayList;

/**
 * This class provides a basic implementation of the the {@link Navigatable}
 *  interface. This implementation tries to focus on being able to override a 
 * single main function (i.e. {@link #addContent(NavNode)}, {@link #nav(NavNode)} and 
 * {@link #removeContent(NavNode)}) to get desired effects and stuff.
 * 
 * This navigation scheme is one where the graphics part of the program must 
 * rely on the navigation system to know what to display. This implementation
 * of {@link Navigatable} provides a prebuilt basic navigation system for any
 * graphical navigation design.
 * 
 * @author David Boivin (Spit)
 */
public class Navigation extends NavigationAdapter {
	
// Members ----------------------------------------------------------------- //
	
	private NavNode mHome;
	private ArrayList<NavNode> mContent;
	private NavNode mCurrent;
	
// Constructors ------------------------------------------------------------ //
	
	/**
	 * Constructor which initializes the members of this class. If the content
	 * is null, then the contentArray member will be initialized with a new 
	 * array.
	 * 
	 * @param home The Home Navigation NavNode.
	 * @param content The other content Nodes
	 */
	public Navigation(NavNode home, ArrayList<NavNode> content){
		super(home,content);
		
		mCurrent = home;
	}
	
	/**
	 * Constructor which initializes the home member with the given home node
	 * and the content to null (because no content given).
	 * 
	 * @param home The home navigation node.
	 */
	public Navigation(NavNode home){
		this(home, null);
	}

// IMplementations --------------------------------------------------------- //

	@Override
	public NavNode getCurrentNode() {
		return mCurrent;
	}
	
	@Override
	public boolean nav(NavNode node) {
		
		if(!mContent.contains(node)){
			return false;
		}
		
		//makes sure that the node within the system is set to the currentNode
		//value.
		mCurrent = mContent.get(mContent.indexOf(node));
		
		return false;
	}
}