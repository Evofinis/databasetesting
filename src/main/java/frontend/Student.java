package frontend;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.css.SimpleStyleableStringProperty;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

/**
 * Created by User on 4/4/2018.
 */
public class Student {

    private SimpleStringProperty studentID;
    private SimpleStringProperty studentName;
    private SimpleStringProperty rollNumber;
    private SimpleStringProperty course;

    public Student(){
    }

    public Student (String id, String sName, String rNum, String sCourse){
        this.studentID = new SimpleStringProperty(id);
        this.studentName = new SimpleStringProperty(sName);
        this.rollNumber = new SimpleStringProperty(rNum);
        this.course = new SimpleStringProperty(sCourse);

    }

    public String getStudentID(){
        return studentID.get();
    }

    public String getStudentName(){
        return studentName.get();
    }

    public String getRollNumber(){
        return rollNumber.get();
    }

    public String getCourse(){
        return course.get();
    }

    public void setStudentId(final String id){
        studentID.set(id);
    }

    public void setStudentName(final String sName){
        this.studentName.set (sName);
    }

    public void setRollNumber(final String rNum){
        this.rollNumber.set(rNum);
    }

    public void setCourse(final String sCourse){
        this.course.set(sCourse);
    }
}


/**
public class Student {

        private int studentID;
        private String studentName;
        private String rollNumber;
        private String course;

        public Student(){
            this.studentID = 0;
            this.studentName = "";
            this.rollNumber = "";
            this.course = "";
        }

        public Student (int id, String sName, String rNum, String sCourse){
            this.studentID = id;
            this.studentName = sName;
            this.rollNumber = rNum;
            this.course = sCourse;

        }

        public int getStudentID(){
            return studentID;
        }

        public String getStudentName(){
            return studentName;
        }

        public String getRollNumber(){
            return rollNumber;
        }

        public String getCourse(){
            return course;
        }

        public void setStudentId(int id){
            this.studentID = id;
        }
        public void setStudentName(String sName){
            this.studentName = sName;
        }

        public void setRollNumber(String rNum){
            this.rollNumber = rNum;
        }

        public void setCourse(String sCourse){
            this.course = sCourse;
        }
}
*/

/**
 package frontend;

 import javafx.beans.property.SimpleIntegerProperty;
 import javafx.beans.property.SimpleStringProperty;
 import javafx.css.SimpleStyleableStringProperty;

 import javax.persistence.Basic;
 import javax.persistence.Column;
 import javax.persistence.Id;


public class Student {

    private SimpleIntegerProperty studentID;
    private SimpleStringProperty studentName;
    private SimpleStringProperty rollNumber;
    private SimpleStringProperty course;

    public Student(){
    }

    public Student (int id, String sName, String rNum, String sCourse){
        this.studentID = new SimpleIntegerProperty(getStudentID());
        this.studentName = new SimpleStringProperty(getStudentName());
        this.rollNumber = new SimpleStringProperty(getRollNumber());
        this.course = new SimpleStringProperty(getCourse());

    }

    public int getStudentID(){
        return studentID.get();
    }

    public String getStudentName(){
        return studentName.get();
    }

    public String getRollNumber(){
        return rollNumber.get();
    }

    public String getCourse(){
        return course.get();
    }

    public void setStudentId(int id){
        this.studentID.set(id);
    }

    public void setStudentName(String sName){
        this.studentName.set (sName);
    }

    public void setRollNumber(String rNum){
        this.rollNumber.set(rNum);
    }

    public void setCourse(String sCourse){
        this.course.set(sCourse);
    }
}
**/

/**

 private SimpleIntegerProperty studentID;
 private SimpleStringProperty studentName;
 private SimpleStringProperty rollNumber;
 private SimpleStringProperty course;



 public Student (SimpleIntegerProperty id, SimpleStringProperty sName, SimpleStringProperty rNum, SimpleStringProperty sCourse){
 this.studentID = id;
 this.studentName = sName;
 this.rollNumber = rNum;
 this.course = sCourse;

 }

 public SimpleIntegerProperty getStudentID(){
 return studentID;
 }

 public SimpleStringProperty getStudentName(){
 return studentName;
 }

 public SimpleStringProperty getRollNumber(){
 return rollNumber;
 }

 public SimpleStringProperty getCourse(){
 return course;
 }

 public void setStudentId(SimpleIntegerProperty id){
 this.studentID = id;
 }
 public void setStudentName(SimpleStringProperty sName){
 this.studentName = sName;
 }

 public void setRollNumber(SimpleStringProperty rNum){
 this.rollNumber = rNum;
 }

 public void setCourse(SimpleStringProperty sCourse){
 this.course = sCourse;
 }
 }
 */