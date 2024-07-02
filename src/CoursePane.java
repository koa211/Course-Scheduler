// Assignment #: Arizona State University Spring 2023 CSE205 #6    
//         Name: Khoa Le
//    StudentID: 1225551002
//      Lecture: Tues-Thur 4:30pm in SCOB 210
//  Description: Generating the design and intricacies of a GUI window, holds handler classes that 
//  processes the users input. Holds all the course objects in an arraylist each being linked to 
//  a checkbox. 

//Note: when you submit on gradescope, you need to comment out the package line
//package yourPackageName;

import javafx.scene.control.Label;    
import javafx.scene.control.TextField;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;

import java.util.ArrayList;

public class CoursePane extends HBox {
    //GUI components
    private ArrayList<Course> courseList;
    private VBox checkboxContainer;

    //Step 1.1: declare any necessary instance variables here
    private ComboBox<String> courseButton = new ComboBox<>();
    private ComboBoxHandler courseHandler;
    private String coursePick;
    private TextField Num;
    private TextField Name;
    private Label courseStatus, courseEnroll, courseLabel, courseCounter;
    private int counter = 0;
    private Button addButton, dropButton;
    private ButtonHandler addHandler, dropHandler;
    private CheckBox tick;
    private CheckBoxHandler checkHandler;
    
    //constructor
    public CoursePane() {
        //step 1.2: initialize instance variables
        //set up the layout. Note: CoursePane is a HBox and contains
        //leftPane, centerPane and rightPane. Pick proper layout class
        //and use nested sub-pane if necessary, then add each GUI components inside.

        //step 1.3: create and set up the layout of the leftPane, leftPane contains a top label, the center sub-pane
        //and a label show at the bottom
        GridPane leftPane = new GridPane();
        leftPane.setStyle("-fx-border-color: black;");
         
        //Setting the padding  
        leftPane.setPadding(new Insets(10, 10, 10, 10)); 
        
        //Setting the vertical and horizontal gaps between the columns 
        leftPane.setVgap(5); 
        leftPane.setHgap(5);       
        
        //Setting the Grid alignment 
        leftPane.setAlignment(Pos.TOP_LEFT); 
        
        //Making all the labels
        courseLabel = new Label("Add Course(s)");
        courseLabel.setTextFill(Color.BLUE);
        courseLabel.setFont(Font.font(null, 14));       
        Label subject = new Label("Subject");        
        Label course = new Label("Course Num");        
        Label Prof = new Label("Instructor");             
        courseStatus = new Label("No course entered");
        
        //TextFields
        Num = new TextField();
        Name = new TextField();
        
        //Create subject button
        courseButton.getItems().addAll("CSE", "ACC", "AME", "BME", "CHM", "DAT", "EEE");
        courseButton.setValue("CSE");
        courseHandler = new ComboBoxHandler();
        courseButton.setOnAction(courseHandler);
        
        //Layout of leftplane        
        leftPane.add(courseLabel, 0, 0);
        leftPane.add(subject, 0, 10);
        leftPane.add(courseButton, 1, 10);
        leftPane.add(course, 0, 20);
        leftPane.add(Num, 1, 20);
        leftPane.add(Prof, 0, 30);
        leftPane.add(Name, 1, 30);
        leftPane.add(courseStatus, 0, 45);
        
        //step 1.4: create and set up the layout of the centerPane which holds the two buttons
        TilePane middlePane = new TilePane();  
        
        //Setting the orientation for the Tile Pane 
        middlePane.setOrientation(Orientation.VERTICAL); 
        						
        //Setting the alignment for the Tile Pane 
        middlePane.setTileAlignment(Pos.BOTTOM_CENTER); 
        middlePane.setVgap(50);
        
        //buttons
        addButton = new Button("Add =>");
        addHandler = new ButtonHandler();
        addButton.setOnAction(addHandler);
        
        dropButton = new Button("Drop <=");
        dropHandler = new ButtonHandler();
        dropButton.setOnAction(dropHandler);
        
        //middlePane adding the two buttons
        middlePane.getChildren().addAll(addButton, dropButton);

        //step 1.5: create and set up the layout of the rightPane, rightPane contains a top label,
        //checkboxContainer and a label show at the bottom    
        BorderPane rightPane = new BorderPane();
        rightPane.setMaxSize(400, 400); 
        rightPane.setStyle("-fx-border-color: black;");
        
        //labels
        courseEnroll = new Label("Course(s) Enrolled");
        courseEnroll.setTextFill(Color.BLUE);
        courseEnroll.setFont(Font.font(null, 14));
        courseCounter = new Label("Number of course is: " + counter);
        courseCounter.setTextFill(Color.BLUE);
        
        //instantiating vbox and arraylist
        checkboxContainer = new VBox();
        courseList = new ArrayList<Course>();
        
        //adding labels and checkboxcontainer to right pane
        rightPane.setTop(courseEnroll);
        rightPane.setCenter(checkboxContainer);
        rightPane.setBottom(courseCounter);
        
        //CoursePane is a HBox. Add leftPane, centerPane and rightPane inside
        this.setPadding(new Insets(10, 10, 10, 10));
        this.getChildren().add(leftPane);
        this.getChildren().add(middlePane);
        this.getChildren().add(rightPane);
    } //end of constructor

    //step 2.1: Whenever a new Course is added or one or several courses are dropped/removed, this method will
    //1) clear the original checkboxContainer;
    //2) create a CheckBox for each Course object inside the courseList, and also add it inside the checkboxContainer;
    //3) register the CheckBox with the CheckBoxHandler.
    public void updateCheckBoxContainer() {
    	checkboxContainer.getChildren().clear();
    	for (int i = 0; i < courseList.size(); i++) {
    		tick = new CheckBox(courseList.get(i).toString());
    		checkboxContainer.getChildren().addAll(tick);
    		checkHandler = new CheckBoxHandler(courseList.get(i));
    		tick.setOnAction(checkHandler);
    	}
    }

    //Step 2.2: Create a ButtonHandler class
    private class ButtonHandler implements EventHandler<ActionEvent> {
        public void handle(ActionEvent e) {
            Object check = e.getSource();
            String subject = coursePick;
            String input = Num.getText();
            String prof = Name.getText();
            boolean flag = false;
            try {
					if (check == addButton && prof.length() > 0) {
                	    //need to check whether the course already exist inside the courseList or not
						int num = Integer.parseInt(input);
						for (int i = 0; i < courseList.size(); i++) {
							String curCourse = courseList.get(i).getSubject();
							int curNum = courseList.get(i).getCourseNum();
							if (curCourse.equals(subject) && curNum == num) {
								flag = true;
							}
						}

						if (flag == false) { //it's a new course   
							Course obj = new Course(subject, num, prof);
							courseList.add(obj);
						    updateCheckBoxContainer();
						    courseStatus.setText("Course added successfully");
						    counter = courseList.size();
						    courseCounter.setText("Number of course is: " + counter);
					    }
                	   
					    else { //a duplicated one
						   courseStatus.setTextFill(Color.RED);
						   courseStatus.setText("Duplicated course - not added");
					    }
						//clear textfields
						Name.clear();
					    Num.clear();
                    }
					
					else if (check == addButton && (Name.getText().trim().isEmpty() || Num.getText().trim().isEmpty())) { //add button is pressed but one field is empty
						courseStatus.setTextFill(Color.RED);
						courseStatus.setText("At least one field is empty. Fill all fields");
               	    }

                else if (check == dropButton) { //when drop button is clicked it calls updateCheckBoxContainer
                    updateCheckBoxContainer();
                    counter = courseList.size();
				    courseCounter.setText("Number of course is: " + counter);
                }
					
                else { //  for all other invalid actions, thrown an exception and it will be caught             
					throw new Exception ("Exception in code");
				}
            } //end of try

            catch(NumberFormatException ex) {
            	courseStatus.setTextFill(Color.RED);
				courseStatus.setText("Error! Course number must be an integer");
            }
            
            catch(Exception exception) {
            	courseStatus.setTextFill(Color.RED);
				courseStatus.setText("Error! Something went wrong with input");
            }
        } //end of handle() method
    } //end of ButtonHandler class

    //Step 2.3: A ComboBoxHandler
    private class ComboBoxHandler implements EventHandler<ActionEvent> {
       public void handle(ActionEvent e) {
    	   coursePick = courseButton.getValue();
       }
    }//end of ComboBoxHandler

    //Step 2.4: A CheckBoxHandler
    private class CheckBoxHandler implements EventHandler<ActionEvent> {
        // Pass in a Course object so that we know which course is associated with which CheckBox
        private Course oneCourse;

        //constructor
        public CheckBoxHandler(Course oneCourse) {
        	this.oneCourse = oneCourse;
        }
        
        //handle method for checkbox to remove course when ticked
        public void handle(ActionEvent e) {
        	CheckBox eSource = (CheckBox)e.getSource();
            if (eSource.isSelected()) {
            	courseList.remove(oneCourse);
            }
        }
    }//end of CheckBoxHandler
} //end of CoursePane class