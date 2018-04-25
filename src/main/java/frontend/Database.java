package frontend;

import java.sql.Connection;
import java.sql.ResultSet;

import javafx.beans.property.SimpleStringProperty;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;


/**
 * Created by User on 4/3/2018.

public class Database {

    private static ObservableList<ObservableList> data;
    private static TableView<ObservableList> tv;

    public static void buildData(String InsertSql) {
        Connection c;
        try {
            c = DBconnect.connect();
            System.out.println("Database Connection Successful");

            // SQL FOR SELECTING ALL OF CUSTOMER
            String SQL = InsertSql;
            System.out.println("SQL Statement Saved");

            // ResultSet
            ResultSet rs = c.createStatement().executeQuery(SQL);
            System.out.println("ResultSet Saved");

            data = FXCollections.observableArrayList();
            System.out.println("Arraylist Created");

            /**********************************
             * TABLE COLUMN ADDED DYNAMICALLY *
             *********************************
            for (int i = 0; i < rs.getMetaData().getColumnCount(); i++) {
                // We are using non property style for making dynamic table
                final int j = i;
                TableColumn<ObservableList<String>, String> col = new TableColumn(rs.getMetaData().getColumnName(i + 1));
                col.setCellValueFactory(param ->
                        new SimpleStringProperty(param.getValue().get(j).toString()));
                col.getColumns().addAll(col);
                System.out.println("Column: [" + rs.getMetaData().getColumnName(i + 1) + "] Added ");
            }



            /********************************
             * Data added to ObservableList *
             *******************************
            while (rs.next()) {
                // Iterate Row
                ObservableList<String> row = FXCollections.observableArrayList();
                for (int i = 1; i <= rs.getMetaData().getColumnCount(); i++) {
                    // Iterate Column
                    String notthere = "---";
                    rs.getString(i);
                    if (rs.wasNull()) {
                        row.add(notthere);
                    } else {
                        row.add(rs.getString(i));
                    }

                }
                System.out.println("Row [1] added " + row);
                data.add(row);

            }

            // FINALLY ADDED TO TableView
            tv.setItems(data);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error on Building Data");
        }
    }


}
 */