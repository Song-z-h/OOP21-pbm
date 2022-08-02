package main.view;

import java.util.Collection;

import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.Pane;

/**
 * This interface models various GUI components in javafx being used repetitively,
 * so i gathered everything into a factory with Builder. Builder is because there will be a lot 
 * of configurations to set up, so i decided to use this pattern to avoid multiple parameters in one 
 * constructor. However, I should set up those configurations from an xml file, instead of doing 
 * tedious calculations. So let's open it up for pull request :) .
 *
 */
public interface GUIFactory {
	
	/**
	 * create a HBox in javafx.
	 * @return HBox
	 */
	Pane createHorizontalPanel();

	/**
	 * create a VBox in javafx.
	 * @return VBox
	 */
	Pane createVerticalPanel();

	/**
	 * create a big button in javafx.
	 * @param name the display on the button
	 * @return Button
	 */
   
	Button createButton(String name);

	/**
	 * create a scene in javafx.
	 * @param panel the default panel to attach to the scene.
	 * @return	Scene
	 */
	Scene createScene(Pane panel);

	/**
	 * create a option panel to show message box.
	 * @param message message to display.
	 * @return message box
	 */
	Alert createInformationBox(String message);

	/**
	 * BlockScheda is a component module that creates a data sheet, to use this class you need to pass
	 * a title in String, A collection of descriptions in row, that will be the header for this data sheet, then
	 * actual data in columns.
	 * Suggestion: provide the size of descriptions as same as the number of columns. 
	 * @param title A title for the block
	 * @param descriptions	first row as description in any Collection
	 * @param fields several columns in any Collection
	 * @return a scrollable pane that contains a plain data sheet.
	 */
	ScrollPane createBlockScheda(String title, Collection<? extends Node> descriptions, Collection<? extends Node>... fields);
}
