import java.util.Comparator;

public class Student implements Comparable<Student> {
    String name;
    Integer grade;

    public String getName() {
        return name;
    }

    public Integer getGrade() {
        return grade;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    // Constructor
    public Student(String name, Integer grade) {

        this.name = name;
        this.grade = grade;
    }

    public int compareTo(Student compareStudent) {

        int compareGrade = ((Student) compareStudent).getGrade();

        //ascending order
        return this.grade - compareGrade;

        //descending order
        //return compareGrade - this.grade;

    }

    public static Comparator<Student> StudentNameComparator = new Comparator<Student>() {

        public int compare(Student st1, Student st2) {

            String studentName1 = st1.getName().toUpperCase();
            String studentName2 = st2.getName().toUpperCase();

            //ascending order
            return studentName1.compareTo(studentName2);

            //descending order
            //return studentName2.compareTo(studentName1);
        }

    };
}
