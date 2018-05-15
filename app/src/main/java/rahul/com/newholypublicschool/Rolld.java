package rahul.com.newholypublicschool;

/**
 * Created by Siddharth on 12/27/2017.
 */

class Rolld {

    String STU_ID;
    String ADMNO;

    public String getSTU_ID() {
        return STU_ID;
    }

    public String getADMNO() {
        return ADMNO;
    }

    public String getStudentName() {
        return StudentName;
    }

    public String getROLL() {
        return ROLL;
    }

    String StudentName;

    public Rolld(String STU_ID, String ADMNO, String studentName, String ROLL) {
        this.STU_ID = STU_ID;
        this.ADMNO = ADMNO;
        StudentName = studentName;
        this.ROLL = ROLL;
    }

    String ROLL;
}
