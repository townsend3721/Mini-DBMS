package db61b;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class TestTable {
    @Test
    public void testTables() {
        String[] Col_names = {"name", "condition", "power"};
        String[] Col_names2 = {"name", "rival"};
        String[] fouth = {"Jane", "one"};
        String[] fifth = {"Greg", "two"};
        String[] sixth = {"Dane", "three"};
        ArrayList<String> test_names = new ArrayList<>();
        test_names.add("name");
        test_names.add("condition");
        test_names.add("rival");

        String[] my_vals = {"Jane", "blind", "sight"};
        String[] other = {"Greg", "footless", "fast"};
        String[] third = {"Dane", "belimic", "endless appetite"};
        Table mine = new Table(Col_names);
        Table mine2 = new Table(Col_names2);
        mine2.add(fouth);
        mine2.add(fifth);
        mine2.add(sixth);
        mine.add(my_vals);
        mine.add(other);
        mine.add(third);
        Column first = new Column("name", mine);
        ArrayList<Condition> use = new ArrayList<>();
        use.add(new Condition(first, ">", "Greg"));
        assertEquals(mine.get(1, 0), "Greg");
        assertEquals(mine.size(), 3);
        assertEquals(mine.findColumn("name"), 0);
        assertEquals(mine.getTitle(2), "power");
        assertEquals(mine.add(other), false);
        Table hope = mine.select(mine2, test_names, use);
        mine.print();
        hope.print();
    }

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(TestTable.class));
    }
}