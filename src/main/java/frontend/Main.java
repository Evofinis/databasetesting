package frontend;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.sql.*;
import Utils.JPAUtil;
import org.apache.log4j.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.image.ImageView;
import java.sql.SQLException;

public class Main extends Application {
    //Initializes the primary stage
    Stage window;

    //*************DATABASE TABLE VARIABLES*************//
    //STUDENT
    TextField  idInput, nameInput, rollInput, courseInput;

    //*************VARIABLES FOR THE LOGIN SCREEN*************//
    Alert alert = new Alert (Alert.AlertType.ERROR);
    Button loginBTN = new Button("Login");
    TextField userName = new TextField();
    PasswordField passWord = new PasswordField();


    //SQL STATEMENTS
    String sql = "Select * from student";

    //*************DATABASE VARIABLES*************//
    Connection c;
    ResultSet rs;
    PreparedStatement pst;
    int t = 0; // This variable is used as an "on/off" variable to check if the database is open or closed
    ObservableList<ObservableList> databaseList = FXCollections.observableArrayList();

    //*************TABLEVIEW TO HOLD TABLE DATA*************//
    TableView<Student> table = new TableView<>();
    final ObservableList<Student> data = FXCollections.observableArrayList();
    final ObservableList<String> emptyData = FXCollections.observableArrayList();

    //*************SCENES AND RELATED DISPLAY VARIABLES*************//
    VBox dbButtons = new VBox();

    BorderPane loginBP = new BorderPane();
    BorderPane main = new BorderPane();
    BorderPane layout = new BorderPane(table);

    Scene mainScene = new Scene(main, 800, 550);
    Scene scene = new Scene(layout, 800, 550);
    Scene loginScene = new Scene(loginBP);

    public Main() {
    }

    public final static Logger logger = Logger.getLogger(Main.class);

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Front End");

        //logger.info(".......Hibernate Crud Operations Example.......\n");

        //ADD THIS TO MAIN ***ONCE****
        //JPAUtil.createEntityManager( JPAUtil.getEntityManagerFactory() );

        //CALL THIS WHEN YOU NEED IT
        //EntityManager entityManager = JPAUtil.getEntityManager();

        //STARTS THE TX - The TX must be started before queries can be executed
        //entityManager.getTransaction().begin();

        /**LOGIN SCREEN
         *
         */
        ImageView imageview = new ImageView();
        Image aLogo = new Image("amtraklogo2.png");
        imageview.setImage(aLogo);

        GridPane loginGP = new GridPane();
        userName.setMinWidth(200);
        userName.setPromptText("Database Username");


        passWord.setPromptText("Database Password");
        passWord.setMinWidth(200);
        passWord.setOnKeyPressed(ex -> {
            if (ex.getCode() == KeyCode.ENTER) {
                databaseLogin();
            }
        });

        /**LOGIN BUTTON ATTRIBUTES**/
        loginBTN.setMaxWidth(100);

        loginGP.add(userName, 0, 0);
        loginGP.add(passWord, 0, 1);
        loginGP.setMargin(loginBTN, new Insets(15,0,-370,65) );
        loginGP.setMargin(userName, new Insets(0,0,-240,15) );
        loginGP.setMargin(passWord, new Insets(3,0,-300,15) );
        loginGP.add(loginBTN, 0, 2);
        loginGP.setAlignment(Pos.CENTER_LEFT);

        loginBP.setPrefSize(600, 500);
        loginBP.setMargin(imageview, new Insets(100, 0, -550, 35));
        loginBP.setTop(imageview);
        loginBP.setCenter(loginGP);

        loginBTN.requestFocus();
        loginBTN.setOnAction(e -> databaseLogin());
        loginBTN.setOnKeyPressed(ex -> {
            if (ex.getCode() == KeyCode.ENTER) {
               databaseLogin();
            }
        });

        dbButtons.setStyle("-fx-font: 18 arial; -fx-base: silver");

        primaryStage.setScene(loginScene);
        primaryStage.show();





        /**BEGIN STUDENT TABLE
         * */
        // TODO: 4/18/2018 Setup methods to handle repetitive tasks
        //Table column defs - Creates the Student table columns
        TableColumn column1 = new TableColumn("ID");
        column1.setMinWidth(25);
        column1.setCellValueFactory(new PropertyValueFactory<>("studentID"));

        TableColumn<Student, String> column2 = new TableColumn("Student Name");
        column2.setMinWidth(100);
        column2.setCellValueFactory(new PropertyValueFactory<>("studentName"));
        column2.setCellFactory(EditCell.forTableColumn());
        column2.setOnEditCommit(e ->{
            Student stu = e.getRowValue();
            stu.setStudentName(e.getNewValue());
            updateData("student_name", e.getNewValue(), stu.getStudentID());
        });

        TableColumn<Student, String> column3 = new TableColumn("Roll");
        column3.setMinWidth(100);
        column3.setCellValueFactory(new PropertyValueFactory<>("rollNumber"));
        column3.setCellFactory(EditCell.forTableColumn());

        TableColumn<Student, String> column4 = new TableColumn("Course Number");
        column4.setMinWidth(100);
        column4.setCellValueFactory(new PropertyValueFactory<>("course"));
        column4.setCellFactory(EditCell.forTableColumn());


        //Add new student defs - Creates text-fields for entering student data
        idInput = new TextField();
        idInput.setStyle("-fx-control-inner-background: #e3e6e9");
        idInput.setEditable(false);

        nameInput = new TextField();
        nameInput.setPromptText("Student Name");

        rollInput = new TextField();
        rollInput.setPromptText("Student Roll");

        courseInput = new TextField();
        courseInput.setPromptText("Student Course");
        courseInput.setOnKeyPressed(e ->{
            if (e.getCode() == KeyCode.ENTER){
                submitEntry();
            }});

        //This is the button used to add the new entry
        Button add = new Button("Add");
        add.setMinWidth(150);
        add.setOnKeyPressed(e ->{
            if (e.getCode() == KeyCode.ENTER){
                submitEntry();
            }
        });
        add.setOnAction(e -> submitEntry());
        add.setFont(Font.font("SanSerif", 15));

        table.setEditable(true);
        table.getSelectionModel().cellSelectionEnabledProperty().set(true);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.setOnKeyPressed(e ->{
            if (e.getCode().isLetterKey() || e.getCode().isDigitKey()) {
                editFocusedCell();
            } else if (e.getCode() == KeyCode.RIGHT
                    || e.getCode() == KeyCode.TAB) {
                table.getSelectionModel().selectNext();
                e.consume();
            } else if (e.getCode() == KeyCode.LEFT) {
                selectPrevious();
                e.consume();
            }
        });
        table.getColumns().addAll(column1, column2, column3, column4);

        /** THIS SECTION WILL HOLD THE CODE FOR VARIOUS SCENES
         *
         */


        Button loadStudents = new Button("Students");
        loadStudents.setFont(Font.font("SanSerif", 15));
        loadStudents.setOnAction(e ->{
            try{
                idInput.setText(DBconnect.getID());
                rs = c.createStatement().executeQuery(sql);

                while(rs.next()){
                    data.add(new Student(
                            rs.getString("student_id"),
                            rs.getString("student_name"),
                            rs.getString("roll_number"),
                            rs.getString("course")
                    ));
                    emptyData.add("");
                    table.setItems(data);
                    primaryStage.setScene(scene);
                    nameInput.requestFocus();
                }
            }catch(Exception e2){
                System.err.println(e2);
            }
        });

        HBox sBox = new HBox(5);
        sBox.setPadding(new Insets(10,10,10,10));
        sBox.setSpacing(10);
        sBox.getChildren().addAll(loadStudents);

        GridPane gridPane = new GridPane();
        gridPane.add(idInput, 0, 1);
        gridPane.add(nameInput, 1, 1);
        gridPane.add(rollInput, 2, 1);
        gridPane.add(courseInput, 3, 1);
        gridPane.add(add, 4, 1);

        main.setCenter(sBox);
        layout.setBottom(gridPane);
        scene.getStylesheets().add("databasecss.css");
        //primaryStage.setScene(mainScene);


        //FINISH QUERY
        //entityManager.getTransaction().commit();

        //CLOSE CONNECTION
        JPAUtil.shutdown();
    }

    //Add button
    public void addButtonClicked() throws SQLException {

        final String sID = idInput.getText();
        final String sName = nameInput.getText();
        final String roll = rollInput.getText();
        final String course = courseInput.getText();

        String insert = "INSERT INTO student (student_id, student_name, roll_number, course) VALUES (?,?,?,?)";

        pst = c.prepareStatement(insert);
        pst.setString(1, idInput.getText());
        pst.setString(2, nameInput.getText());
        pst.setString(3, rollInput.getText());
        pst.setString(4, courseInput.getText());
        pst.execute();

        table.getItems().add(new Student(sID, sName, roll, course));

        idInput.setText(DBconnect.getID());
        nameInput.clear();
        rollInput.clear();
        courseInput.clear();
        pst.close();
    }

    public void submitEntry(){
        try{
            addButtonClicked();
            nameInput.requestFocus();
        }catch (SQLException se){
            se.printStackTrace();
        }
    }
/*
    public void delButtonClicked() throws SQLException{
        /*
        ObservableList<Student> studentSelected, allStudents;
        allStudents = table.getItems();
        studentSelected = table.getSelectionModel().getSelectedItems();

        studentSelected.forEach(allStudents::remove);
        //

        String insert = "INSERT INTO student (student_name, roll_number, course) VALUES (?,?,?)";

        pst = c.prepareStatement(insert);
        pst.setString(1, "");
        pst.setString(2, "");
        pst.setString(3, "");
        pst.execute();

        table.getItems().add(new Student("","","",""));
        table.refresh();
    }*/

    @SuppressWarnings("unchecked")
    private void editFocusedCell() {
        final TablePosition<Student, ?> focusedCell = table.getFocusModel().getFocusedCell();
        table.edit(focusedCell.getRow(), focusedCell.getTableColumn());
    }

    /**
     * This is working as intended
     */
    @SuppressWarnings("unchecked")
    private void selectPrevious() {
        if (table.getSelectionModel().isCellSelectionEnabled()) {
            TablePosition<Student, ?> pos = table.getFocusModel()
                    .getFocusedCell();
            if (pos.getColumn() - 1 >= 0) {
                table.getSelectionModel().select(pos.getRow(),
                        getTableColumn(pos.getTableColumn(), -1));
            } else if (pos.getRow() < table.getItems().size()) {
                table.getSelectionModel().select(pos.getRow() - 1,
                        table.getVisibleLeafColumn(
                                table.getVisibleLeafColumns().size() - 1));
            }
        } else {
            int focusIndex = table.getFocusModel().getFocusedIndex();
            if (focusIndex == -1) {
                table.getSelectionModel().select(table.getItems().size() - 1);
            } else if (focusIndex > 0) {
                table.getSelectionModel().select(focusIndex - 1);
            }
        }
    }

    private TableColumn<Student, ?> getTableColumn(
            final TableColumn<Student, ?> column, int offset) {
        int columnIndex = table.getVisibleLeafIndex(column);
        int newColumnIndex = columnIndex + offset;
        return table.getVisibleLeafColumn(newColumnIndex);
    }

    private void updateData (String column, String newValue, String id) {

        try(
            PreparedStatement stmt = c.prepareStatement("UPDATE student SET "+column+" = ? WHERE student_id = ? ")
        ){
            stmt.setString(1, newValue);
            stmt.setString(2, id);
            stmt.execute();
        } catch (SQLException ex) {
            System.err.println("Error");
            ex.printStackTrace(System.err);
        }
    }

    private void databaseLogin(){
        final String uName = userName.getText();
        final String pWord = passWord.getText();

        alert.setTitle("Login Unsuccessful");
        alert.setHeaderText(null);
        alert.setGraphic(null);

        if((uName != null && !uName.isEmpty()) && (pWord != null && !pWord.isEmpty())) {
            try {
                c = DBconnect.connect(uName, pWord);
                DBconnect.getDatabaseNames(databaseList);
                t = DBconnect.isClosed();

                Button databaseNames[] = new Button[databaseList.size()];
                for (int i=0; i<databaseList.size(); i++) {
                    String dbName = databaseList.get(i).toString();
                    databaseNames[i] = new Button(dbName.substring(1, dbName.length() - 1));
                    databaseNames[i].setAlignment(Pos.CENTER);
                    databaseNames[i].setMinSize(250, 25);
                    dbButtons.getChildren().add(databaseNames[i]);
                    loginBP.setRight(dbButtons);
                    loginBP.setMargin(dbButtons, new Insets(400,20,0,0) );
                    if (t == 1) {
                        loginBTN.setVisible(false);
                    }
                }
            } catch (SQLException | NullPointerException e1) {
                alert.setContentText("Please verify that your credentials are correct.");
                alert.showAndWait();
            }
        }else{
            alert.setContentText("Please enter both Username and Password");
            alert.showAndWait();
        }
    }

/*
================================================== DON'T NEED THIS, KEEPING FOR REFERENCE ==========================================================
    private String getID() throws SQLException{
      /*  int id;

        ResultSet r = c.createStatement().executeQuery("SELECT student_id FROM student ORDER BY student_id DESC limit 1");

        r.next();
        id = r.getInt("student_id");*

        return Integer.toString(1);
    }*/

    public static void main(String[] args) {launch(args); }
}
