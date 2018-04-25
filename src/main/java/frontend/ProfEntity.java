package frontend;

import javax.persistence.*;

/**
 * Created by User on 4/3/2018.
 */
@Entity
@Table(name = "prof")
public class ProfEntity {
    private int profId;
    private String profName;

    @Id
    @Column(name = "prof_id")
    public int getProfId() {
        return profId;
    }

    public void setProfId(int profId) {
        this.profId = profId;
    }

    @Basic
    @Column(name = "prof_name")
    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProfEntity that = (ProfEntity) o;

        if (profId != that.profId) return false;
        if (profName != null ? !profName.equals(that.profName) : that.profName != null) return false;

        return true;
    }

    @Override
    public String toString() {
        return "Professor Details = Id: " + this.profId + ", Name: " + this.profName;
    }

    @Override
    public int hashCode() {
        int result = profId;
        result = 31 * result + (profName != null ? profName.hashCode() : 0);
        return result;
    }
}
