package frontend;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

/**
 * Created by User on 4/3/2018.
 */
@Entity
@Table(name = "student")
public class StudentEntity {
    private int studentId;
    private String studentName;
    private String rollNumber;
    private String course;

    @Id
    @Column(name = "student_id")
    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    @Basic
    @Column(name = "student_name")
    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    @Basic
    @Column(name = "roll_number")
    public String getRollNumber() {
        return rollNumber;
    }

    public void setRollNumber(String rollNumber) {
        this.rollNumber = rollNumber;
    }

    @Basic
    @Column(name = "course")
    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        StudentEntity that = (StudentEntity) o;

        if (studentId != that.studentId) return false;
        if (studentName != null ? !studentName.equals(that.studentName) : that.studentName != null) return false;
        if (rollNumber != null ? !rollNumber.equals(that.rollNumber) : that.rollNumber != null) return false;
        if (course != null ? !course.equals(that.course) : that.course != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Student Details = Id: " + this.studentId + ", Name: " + this.studentName + ", Roll No.: " + this.rollNumber + ", Course: " + this.course;
    }



    @Override
    public int hashCode() {
        int result = studentId;
        result = 31 * result + (studentName != null ? studentName.hashCode() : 0);
        result = 31 * result + (rollNumber != null ? rollNumber.hashCode() : 0);
        result = 31 * result + (course != null ? course.hashCode() : 0);
        return result;
    }
}
