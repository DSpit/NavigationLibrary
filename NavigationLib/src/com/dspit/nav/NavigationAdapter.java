

package com.dspit.nav;

import java.util.ArrayList;

/**
 * This class provides a basic implementation of the the {@link Navigatable}
 *  interface. This implementation tries to focus on being able to override a 
 * single main function (i.e. {@link #addContent(Node)}, {@link #nav(Node)} and 
 * {@link #removeContent(Node)}) to get desired effects and stuff.
 * 
 * @author David Boivin (Spit)
 */
abstract public class NavigationAdapter implements Navigatable {
	
// Members ----------------------------------------------------------------- //
	
	private Node mHome;
	private ArrayList<Node> mContent;
	
// Constructors ------------------------------------------------------------ //
	
	/**
	 * Constructor which initializes the members of this class. If the content
	 * is null, then the contentArray member will be initialized with a new 
	 * array.
	 * 
	 * @param home The Home Navigation Node.
	 * @param content The other content Nodes
	 */
	public NavigationAdapter(Node home, ArrayList<Node> content){
		
		mHome = home;
		mContent = (content != null)? content : new ArrayList<Node>();
	}
	
	/**
	 * Constructor which initializes the home member with the given home node
	 * and the content to null (because no content given).
	 * 
	 * @param home The home navigation node.
	 */
	public NavigationAdapter(Node home){
		this(home, null);
	}
	
// Overrides --------------------------------------------------------------- //

	@Override
	public Node getHome() {
		return mHome;
	}

	@Override
	public Node getContent(int index) throws IndexOutOfBoundsException {
		
		if(!this.isIndexInRange(index)){
			throw new IndexOutOfBoundsException("The index used to search for "
							+"a Navigation Node is out of Bounds.");
		}
		
		return mContent.get(index);
	}

	@Override
	public ArrayList<Node> getContent() {
		return mContent;
	}

	/**
	 * @see #getCurrentNode()
	 */
	@Override
	public int getCurrentNodeIndex() {
		return mContent.indexOf(this.getCurrentNode());
	}

	@Override
	public void setHome(Node home) {
		mHome = home;
	}

	/**
	 * If the index is out of bounds then the content will be added to the end 
	 * the the array by default. 
	 */
	@Override
	public void addContent(int index, Node content) {
		
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
	 * @see #addContent(int, Node)
	 */
	@Override
	public void addContent(Node content) {
		this.addContent(-1, content);
	}

	/**
	 * @see #addContent(int,Node)
	 */
	@Override
	public void addContent(ArrayList<Node> content) {
		mContent.addAll(content);
	}
	
	/**
	 * @see #removeContent(Node)
	 */
	@Override
	public void removeAllContent() {
		
		for(Node content : mContent){
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
	public boolean removeContent(Node content) {
		
		//makes sure this node isn't being displayed as it's being removed.
		if(this.getCurrentNode() == content){
			this.navHome();
		}
		
		//removes the node and returns success boolean
		return mContent.remove(content);
	}

	/**
	 * @see #removeContent(Node))
	 */
	@Override
	public void removeContent(int index) throws IndexOutOfBoundsException{
		
		if(!this.isIndexInRange(index)){
			throw new IndexOutOfBoundsException("The index to "
					+ "remove a Navigation Node from the Root out of bounds.");
		}
		
		this.removeContent(mContent.get(index));
	}
	
	/**
	 * @see #nav(Node)
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
	 * @see #nav(Node)
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
