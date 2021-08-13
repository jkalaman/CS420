
This project fulfills the requirements for the final project for CS 420. 

The code contained in this repository is meant to build a news reader(RSS). A GUI built using javaFX will display the headline for each news story
and when the mouse is hovered over a headline, a short description of the story will pop up. Double-clicking on the headline in the list view
should open the news story's URL in a web browser.

CLASS: App extends Application
	Method: main	
		launches whole program.
	Method: start
		This method takes the URL for an RSS (hardcoded as The Guardian) and creates an RSSFeedParser to read the URL and return a Feed.
		The Feed is then used to create objects of type Item to be used to populate the ListView.
		This method creates the stage for the GUI, builds scenese and ListView
		The method uses various lambda expression to control functionality of various featurs in the GUI e.g. double-click, hovering etc.
	Method: openLink
		This method take a URL as a string and then launches then launches a web browser to open the URL associated with that String
		
	Inner Class: Item
		The start method creates the ListView and adds elements of type Item to it. The ListView *could* have been created as type <FeedMessage>
		but this structure leaves flexibility to do further transformations or limith the fields being passed to the ListView without making 
		edits to the FeedMessage class. For example, the Inner Class Item, in the getDescription method, cleans up html tags from the description
		before displaying it in the ListView. Preserving the html coding in the FeedMessage might serve useful funcitonality in the future, 
		so best to preserve the option to do so.
	
	
CLASS: RSSFeedParser
	This class does the heavy lifting of reading the RSS
	Method: readFeed 
		Takes URL as a string and passes it to subsequent methods to create a Document to be parsed. This method then parses the Document.
		The parsed fields as strings are set into a FeedMessage object and that object is added to the ArrayList of FeedMessage, Feed
	Method: cleanUpURL
		This method takes the URL as a string, opens an Inputstream to the URL and then uses a BufferedReader to read the RSS as a String.
		It then parses the string to get fields for the Object FeedMessage.
	Method: stringToDom
		This method take a String and converts it back into and XML like document to be parsed in the readFeed method.
		
	This method returns an ArrayList<Feed>
		

CLASS: Feed
	This class create an interable ArrayList of type FeedMessage i.e. an ArrayList of FeedMessage objects.
	Improvment Idea: incorporate FeedMessage class as an innerclass of Feed, but FeedMessage is used in App. 
	

CLASS: FeedMessage
	This class create an object of type FeedMessage. It has getters and setters for the fields of interest from the RSS
	
CLASS: ConfirmBox
	This creates a stage, scene, and a couple of buttons that pops up when the user of the GUI initiates and exit. It promts the user to 
	confirm if he/she/they wish to exit. 
