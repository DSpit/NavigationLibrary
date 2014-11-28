

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
public class NavigationAdapter implements Navigatable {
	
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
	public NavigationAdapter(NavNode home, ArrayList<NavNode> content){
		
		mHome = home;
		mContent = (content != null)? content : new ArrayList<NavNode>();
		mCurrent = mHome;
	}
	
	/**
	 * Constructor which initializes the home member with the given home node
	 * and the content to null (because no content given).
	 * 
	 * @param home The home navigation node.
	 */
	public NavigationAdapter(NavNode home){
		this(home, null);
	}
	
// Overrides --------------------------------------------------------------- //

	@Override
	public NavNode getHome() {
		return mHome;
	}

	@Override
	public NavNode getContent(int index) throws IndexOutOfBoundsException {
		
		if(!this.isIndexInRange(index)){
			throw new IndexOutOfBoundsException("The index used to search for "
							+"a Navigation NavNode is out of Bounds.");
		}
		
		return mContent.get(index);
	}

	@Override
	public ArrayList<NavNode> getContent() {
		return mContent;
	}

	@Override
	public NavNode getCurrentNode() {
		return mCurrent;
	}
	
	/**
	 * @see #getCurrentNode()
	 */
	@Override
	public int getCurrentNodeIndex() {
		return mContent.indexOf(this.getCurrentNode());
	}

	@Override
	public void setHome(NavNode home) {
		mHome = home;
	}

	/**
	 * If the index is out of bounds then the content will be added to the end 
	 * the the array by default. 
	 */
	@Override
	public void addContent(int index, NavNode content) {
		
		//handles index numbers which are too small
		if(0 > index){
			this.addContent(0, content);
			return;
		}
		
		//handles index numbers which are too large
		if(mContent.size() <= index){
			mContent.add(content);
		}
		
		//handles index numbers that are just right
		mContent.add(index, content);
		
		//this is the end of the Goldilocks and the 3 Bears method
	}

	/**
	 * @see #addContent(int, NavNode)
	 */
	@Override
	public void addContent(NavNode content) {
		this.addContent(-1, content);
	}

	/**
	 * @see #addContent(int,NavNode)
	 */
	@Override
	public void addContent(ArrayList<NavNode> content) {
		mContent.addAll(content);
	}
	
	/**
	 * @see #removeContent(NavNode)
	 */
	@Override
	public void removeAllContent() {
		
		for(NavNode content : mContent){
			this.removeContent(content);
		}
	}

	/**
	 * To make sure that this method does not de-sync the data and the 
	 * graphical environment, if the current content which is being shown is 
	 * the content to remove, then this method will call {@link #navHome()}.
	 * 
	 * @see #navHome()
	 */
	@Override
	public boolean removeContent(NavNode content) {
		
		//makes sure this node isn't being displayed as it's being removed.
		if(this.getCurrentNode() == content){
			this.navHome();
		}
		
		//removes the node and returns success boolean
		return mContent.remove(content);
	}

	/**
	 * @see #removeContent(NavNode))
	 */
	@Override
	public void removeContent(int index) throws IndexOutOfBoundsException{
		
		if(!this.isIndexInRange(index)){
			throw new IndexOutOfBoundsException("The index to "
					+ "remove a Navigation NavNode from the Root out of bounds.");
		}
		
		this.removeContent(mContent.get(index));
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
	
	/**
	 * @see #nav(NavNode)
	 */
	@Override
	public void nav(int index) throws IndexOutOfBoundsException {
		
		if(!this.isIndexInRange(index)){
			throw new IndexOutOfBoundsException("The index used to navigate " + 
					"is out of bounds.");
		}
		
		this.nav(mContent.get(index));
	}

	/**
	 * @see #nav(NavNode)
	 */
	@Override
	public void navHome() {
		this.nav(mHome);
	}

	/**
	 * Uses algorithm <code>this.nav(currentNodeIndex + 1 
	 * % totalArrayLength)</code><br> 
	 * <br>The Modulo makes sure that in the case where currentNodeIndex is at
	 * totalArrayLength, the navNext() loops back to the beginning.
	 * 
	 * @see #nav(int)
	 */
	@Override
	public void navNext() {
		this.nav((this.getCurrentNodeIndex() + 1) % mContent.size());

	}

	/**
	 * Uses Algorithm <code>this.nav((currentNodeIndex + totalArrayLength) - 1 
	 * % totalArrayLength)</code><br> 
	 * <br> The Added totalArrayLength is to mitigate the -1 that happens in 
	 * the case where currentNodeIndex is 0. The Modulo provides the rest of
	 * the functionality to fix the resultant number so that it is within range
	 * of the content array.
	 * 
	 * @see #nav(int)
	 */
	@Override
	public void navPrev() {
		this.nav((this.getCurrentNodeIndex() + mContent.size() - 1) % 
				mContent.size());

	}

	/**
	 * This method should be overridden if there is need to do something prior 
	 * to the closing of the entire system.
	 */
	@Override
	public void exit() {
		System.exit(0);
	}
	
// Private Methods --------------------------------------------------------- //
	
	/**
	 * Algorithm to determine whether a given index is within range of
	 * the content array.
	 * 
	 * @param index The index to check.
	 * 
	 * @return <b>true</b> if the index is within range <br> <b>false</b> if it 
	 * 				is out of range.
	 */
	private boolean isIndexInRange(int index){
		
		if(0 <= index && index < mContent.size()){
			return true;
		}
		
		return false;
		
	}
}
