

package com.dspit.nav;

import java.util.ArrayList;

/**
 * This is an interface which suggests methods that are needed to navigate in
 * a multi page program. This interface provides a basic set of very useful 
 * methods for implementing different navigation schemes. <br>
 * <b>Note: </b>This interface uses the {@link NavNode} interface to define a 
 * navigation node, or in other words, a specific page which can be navigated 
 * through within the implementing class of Navigatable.
 * 
 * @author David Boivin (Spit)
 */
public interface Navigatable {
	
// Getters ----------------------------------------------------------------- //
	
	/**
	 * Method which returns the home page, which is another way of saying
	 * either the menu or the starting page.
	 * 
	 * @return a Navigation NavNode which is defined to be the home page.
	 */
	public NavNode getHome();
	
	/**
	 * Method which returns a specific sub navigation node based on the index
	 * given.  
	 * 
	 * @param index The index of a specific node to return based on the array
	 * returned by the {@link #getContent()} method.
	 * 
	 * @return The Navigation NavNode which is at the given index.
	 * 
	 * @throws IndexOutOfBoundsException if the index is either less than 0 or
	 * 			if the index is greater than {@link #getContent()}.size.
	 */
	public NavNode getContent(int index) throws IndexOutOfBoundsException;
	
	/**
	 * Returns an array of all the sub navigation nodes stored within this
	 * Navigatable instance. This method should not include the Home NavNode, 
	 * which can (and should) be accessed using the {@link #getHome()} method.
	 * 
	 * @return An array of Navigation Nodes which are stored in this navigation
	 * root.
	 */
	public ArrayList<NavNode> getContent();
	
	/**
	 * Returns the NavNode which is currently being displayed to the user.
	 * 
	 * @return The current node being displayed to the user.
	 */
	public NavNode getCurrentNode();
	
	/**
	 * Returns the index of the node which is currently being displayed.
	 * This method provides a less resource demanding way of checking which
	 * node is being displayed without using the actual NavNode to 
	 * verify.
	 * 
	 * @return The index of the current node based on it's location in the 
	 * array returned by the {@link #getContent()} method.
	 */
	public int getCurrentNodeIndex();
	
// Setters ----------------------------------------------------------------- //
	
	/**
	 * This method sets what the root navigation scheme considered the home
	 * or main page.
	 * 
	 * @param home The Navigation NavNode which will be considered the home page
	 * of this Navigation Root.
	 */
	public void setHome(NavNode home);
	
	/**
	 * Method which adds a Navigation NavNode to the array of navigation nodes 
	 * stored within this instance. This new node will be added at the given 
	 * index. <br><br>
	 * If the index if greater than the size of the content array then
	 * the node will be added to the end of the array. Likewise, if the index is
	 * less than 0 the node will be added at the very beginning of the content 
	 * array.<br><br>
	 * <b>NOTE: </b>It will be up to the implementing class to figure out how 
	 * to handle shifting the other nodes in the array.
	 * 
	 * @param index The index at which to insert the new NavNode.
	 * @param content The new navigation node to be added.
	 */
	public void addContent(int index, NavNode content);
	
	/**
	 * Method which is similar to {@link #addContent(int, NavNode)} but 
	 * adds the given node to the end of the content array, without having
	 * to specify it.
	 * 
	 * @param content The new Navigation NavNode to add to this instance of the
	 * Navigation Root.
	 */
	public void addContent(NavNode content);
	
	/**
	 * Method which adds an array of Navigation Nodes to the end of the 
	 * array of navigation nodes stored within this instance.
	 * 
	 * @param content An Array of new navigation nodes to add to this 
	 * Navigation root.
	 */
	public void addContent(ArrayList<NavNode> content);
	
// Removers ---------------------------------------------------------------- //
	
	/**
	 * Removes all nodes stored within the content array.
	 */
	public void removeAllContent();
	
	/**
	 * Removes the specific NavNode which is given. If the given node is not found
	 * within the content array, then this method doesn't do anything.
	 * 
	 * @param content The NavNode to remove.
	 * 
	 * @return <b>true</b> if the node is found and removed,
	 * <b>false</b> if the node is not found.
	 */
	public boolean removeContent(NavNode content);
	
	/**
	 * Removes the specified NavNode which is at the given index within the 
	 * content array.
	 * 
	 * @param index the index of the node to remove.
	 * 
	 * @throws IndexOutOfBoundsException if the index is less than 0 or if the
	 * index is greater than the length of the content array, given by 
	 * {@link #getContent()}.size.
	 */
	public void removeContent(int index) throws IndexOutOfBoundsException;
	
// Navigation -------------------------------------------------------------- //
	
	/**
	 * Method which changes the currently displayed node to the given node.
	 * If the node is not found within the content array, this method will not 
	 * change anything in what is being displayed but will return false.
	 * 
	 * @param node The node to display to the user.
	 * 
	 * @return <b>true</b> if the change was successful and <b>false</b> 
	 * if the node given was not found within the content array.
	 */
	public boolean nav(NavNode node);
	
	/**
	 * Method which changes the currently displayed node to the node within the
	 * content array which has the given index. If the given index is -1 then 
	 * this method will display the home page.
	 * 
	 * @param index The index of the node to display to the user based on
	 * the array returned by the {@link #getContent()} method.
	 * 
	 * @throws IndexOutOfBoundsException if the given index is less than -1 or
	 * if the given index is greater than {@link #getContent()}.length.
	 */
	public void nav(int index) throws IndexOutOfBoundsException;
	
	/**
	 * Method which displays the home page to the user.
	 */
	public void navHome();
	
	/**
	 * Method which displays the next Navigation node in the content array.
	 */
	public void navNext();
	
	/**
	 * Method which displays the previous Navigation NavNode in the content array.
	 */
	public void navPrev();
	
	/**
	 * Method which provides a way to implement an exit command.
	 */
	public void exit();
	
// Public Inner Classes ---------------------------------------------------- //
	
	/**
	 * An interface which suggest some useful methods for class which will be
	 * considered Nodes of a navigation scheme. This interface should be used 
	 * in combination with the {@link Navigatable} interface. 
	 * 
	 * @author David Boivin (Spit)
	 */
	public interface NavNode {
		
	// Getters ------------------------------------------------------------- //
		
		/**
		 * Method which returns the title of this NavNode.
		 * 
		 * @return The title of this node.
		 */
		public String getTitle();
		
		/**
		 * This method returns the icon which represents this node graphically,
		 * which is a more user friendly way of identifying a specific NavNode.
		 * 
		 * @return The image URI which represents this node graphically.
		 */
		public String getIcon();
		
	// Setters ------------------------------------------------------------- //
		
		/**
		 * Method which sets the title of this node. This title will be used
		 * to identify this node to the user, so this title should be 
		 * descriptive enough so that the user understands the general theme of
		 * the tab without reading a very long and redundant string, kind of 
		 * like this comment.
		 * 
		 * @param title The title of this node.
		 */
		public void setTitle(String title);
		
		/**
		 * Sets the icon of the node. The icon is an image which is simplistic 
		 * and descriptive enough for the user to understand what the node is 
		 * about without even having to read the title. This icon should be 
		 * minimalistic and simplistic while still conveying its message.
		 * 
		 * @param iconImage The String path of the image to display.
		 */
		public void setIcon(String iconImage);

	}
}
