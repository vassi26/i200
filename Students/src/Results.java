import java.util.*;

public class Results {
    List<Student> results;
    Integer size;

    public List<Student> getResults() {
        return results;
    }

    public Results(Integer qty){
        this.results = new ArrayList<Student>(qty);
    }

    public Student studentMax() {
        Comparator<Student> comparator = (s1, s2) -> s1.getGrade() - s2.getGrade();
        Student studentMax = Collections.max(this.results, comparator);
        //Student studentMin = Collections.min(students, comparator);
        Collections.sort(this.results);

        //DEBUG
        /*
        int i=0;
        for(Student temp: this.results){
            System.out.println("student " + ++i + " : " + temp.getName() +
                    ", Quantity : " + temp.getGrade());
        }
        */
        return studentMax;
    }
}
