package doc.table.doctable.testia;

public class TestMain {

    public static void main(String[] args) {
        Thor thor = new Thor();
        int x = 1;
        thor.fight();
        thor.hit(x);
        System.out.println(x);

   }
}


abstract class Avengers{
    abstract void fight();
    void hit(int x){

    }
}

interface Avenger {
    void fight();
    void protect();
}