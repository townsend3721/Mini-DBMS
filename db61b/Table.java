// This is a SUGGESTED skeleton for a class that represents a single
// Table.  You can throw this away if you want, but it is a good
// idea to try to understand it first.  Our solution changes or adds
// about 100 lines in this skeleton.

// Comments that start with "//" are intended to be removed from your
// solutions.
package db61b;



import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static db61b.Utils.*;
import java.util.Arrays;


/** A single table in a database.
 *  @author P. N. Hilfinger
 */
class Table {
    /**
     * A new Table whose columns are given by COLUMNTITLES, which may
     * not contain duplicate names.
     */

    Table(String[] columnTitles) {
        if (columnTitles.length == 0) {
            throw error("table must have at least one column");
        }
        _size = 0;
        _rowSize = columnTitles.length;

        for (int i = columnTitles.length - 1; i >= 1; i -= 1) {
            for (int j = i - 1; j >= 0; j -= 1) {
                if (columnTitles[i].equals(columnTitles[j])) {
                    throw error("duplicate column name: %s",
                            columnTitles[i]);
                }
            }
        }

        // FIXME
        _titles = columnTitles;
        _columns = new ValueList[_rowSize];
        for (int i = 0; i < _rowSize; i++) {
            _columns[i] = new ValueList();
        }
        _rows = new ArrayList<>();


    }

    /**
     * A new Table whose columns are give by COLUMNTITLES.
     */
    Table(List<String> columnTitles) {
        this(columnTitles.toArray(new String[columnTitles.size()]));
    }

    /**
     * Return the number of columns in this table.
     */
    public int columns() {
        return _titles.length;  // REPLACE WITH SOLUTION
    }

    /** Return the title of the Kth column.  Requires 0 <= K < columns(). */
    public String getTitle(int k) {
        return _titles[k];  // REPLACE WITH SOLUTION
    }

    public void rows() {

    }

    /** Return the number of the column whose title is TITLE, or -1 if
     *  there isn't one. */
    public int findColumn(String title) {
        for (int i = 0; i < columns(); i++){
            if (_titles[i].contains(title)) {
                return i;
            }
        }
        return -1;

          // REPLACE WITH SOLUTION
    }

    /** Return the number of rows in this table. */
    public int size() {

        return _size;  // REPLACE WITH SOLUTION
    }

    /** Return the value of column number COL (0 <= COL < columns())
     *  of row number ROW (0 <= ROW < size()). */
    public String get(int row, int col) {
        try {
            return _columns[col].get(row); // REPLACE WITH SOLUTION
        } catch (IndexOutOfBoundsException e) {
            throw error("invalid row or column");
        }
    }

    /** Add a new row whose column values are VALUES to me if no equal
     *  row already exists.  Return true if anything was added,
     *  false otherwise. */
    public boolean add(String[] values) {
        int num_rows = _columns[0].size();
        /** checks if any of the rows are equal to values **/
        for (int i = 0; i < size(); i++) {
            ArrayList<String> row= new ArrayList<>();
            ArrayList<String> trues = new ArrayList<>();
            for (int j = 0; j < columns(); j++) {
                row.add(_columns[j].get(i));
            }
            List<String> array_values = Arrays.asList(values);
            for (String val : values) {
                if (row.get(array_values.indexOf(val)).equals(val)) {
                    trues.add("true");

                } else {
                    trues.add("false");
                }
            }

            if (!trues.contains("false")) {
                return false;
            }
        }
        for (int k = 0; k < _rowSize; k++) {
            _columns[k].add(values[k]);

        }


        _size += 1;


        return true;
    }


    /** Add a new row whose column values are extracted by COLUMNS from
     *  the rows indexed by ROWS, if no equal row already exists.
     *  Return true if anything was added, false otherwise. See
     *  Column.getFrom(Integer...) for a description of how Columns
     *  extract values. */
    public boolean add(List<Column> columns, Integer... rows) {
        String[] _newRow = new String[columns.size()];
        for (int i = 0; i < columns.size(); i++) {
            String val = columns.get(i).getFrom(rows);
            _newRow[i] = val;
        }
        return add(_newRow);
    }

    /** Read the contents of the file NAME.db, and return as a Table.
     *  Format errors in the .db file cause a DBException. */
    static Table readTable(String name) {
        BufferedReader input;
        Table table;
        input = null;
        table = null;
        try {
            input = new BufferedReader(new FileReader(name + ".db"));
            String header = input.readLine();
            if (header == null) {
                throw error("missing header in DB file");
            }
            String[] columnNames = header.split(",");
            table = new Table(columnNames);
            String newRow = input.readLine();
            while (newRow != null) {

                String[] _newRow = newRow.split(",");
                table.add(_newRow);
                newRow = input.readLine();
            }

        } catch (FileNotFoundException e) {
            throw error("could not find %s.db", name);
        } catch (IOException e) {
            throw error("problem reading from %s.db", name);
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    /* Ignore IOException */
                }
            }
        }
        return table;
    }

    /** Write the contents of TABLE into the file NAME.db. Any I/O errors
     *  cause a DBException. */
    void writeTable(String name) {
        PrintStream output;
        output = null;
        try {
            String sep;
            sep = "";
            output = new PrintStream(name + ".db");
            String[] header = _titles;
            String columnNames = "";
            /** locate column titles and store them in columnNames **/
            for (int i = 0; i < _rowSize; i++) {
                if (i == _rowSize - 1) {
                    columnNames = columnNames + getTitle(_columns.length - 1);
                }
                else {
                    columnNames = columnNames + getTitle(i) + ",";
                }
            }
            /** Print columnNames in the standard output and create a Value list of rows from our table **/
            output.println(columnNames);
            /** Print each row, line by line, in rows **/
            ArrayList<ArrayList> rows = new ArrayList<>();
            for (int i = 0; i < size(); i++) {
                ArrayList<String> row = new ArrayList<>();
                for (int j = 0; j < columns(); j++) {
                    row.add(_columns[j].get(i));
                }
                rows.add(row);
            }

            for (ArrayList row : rows) {
                String EachRow = "";
                for ( Object value: row) {
                    EachRow = EachRow + value + ",";
                }
                output.println(EachRow);
            }

        } catch (IOException e) {
            throw error("trouble writing to %s.db", name);
        } finally {
            if (output != null) {
                output.close();
            }
        }
    }

    /** Print my contents on the standard output, separated by spaces
     *  and indented by two spaces. */
    void print() {
        ArrayList<ArrayList> rows = new ArrayList<>();
        for (int i = 0; i < size(); i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < columns(); j++) {
                row.add(_columns[j].get(i));
            }
            rows.add(row);
        }
        for (ArrayList row__ : rows) {
            String _print = "  ";
            for (Object _val : row__) {
                if (row__.indexOf(_val) == row__.size() - 1) {
                    _print = _print +_val;
                }
                else {
                    _print = _print + _val + ", ";
                }
            }
            System.out.println(_print);
        }
    }

    /** Return a new Table whose columns are COLUMNNAMES, selected from
     *  rows of this table that satisfy CONDITIONS. */
    Table select(List<String> columnNames, List<Condition> conditions) {
        Table result = new Table(columnNames);
        ArrayList<ArrayList<String>> rows = new ArrayList<>();

        /** initiate a variable called rows which contains my rows **/
        for (int i = 0; i < size(); i++) {
            ArrayList<String> row = new ArrayList<>();
            for (int j = 0; j < columns(); j++) {
                row.add(_columns[j].get(i));
            }
            rows.add(row);
        }
        /** Select my new table by adding each row**/
        for (Condition _condition : conditions) {
            for (int row = 0; row < rows.size(); row++) {
                String[] _newRows = new String[columnNames.size()];
                if (_condition.test(row)) {
                    for (int i = 0; i < columnNames.size(); i++) {
                        int col_index = this.findColumn(columnNames.get(i));
                        _newRows[i] = rows.get(row).get(col_index);
                    }
                    result.add(_newRows);
                }
            }
        }
        return result;
        }

    /** Return a new Table whose columns are COLUMNNAMES, selected
     *  from pairs of rows from this table and from TABLE2 that match
     *  on all columns with identical names and satisfy CONDITIONS. */
    Table select(Table table2, List<String> columnNames,
                 List<Condition> conditions) {
        List<Column> column1 = new ArrayList<>();
        Table result = new Table(columnNames);

        for (int i = 0; i < columnNames.size(); i +=1){
            column1.add(new Column(columnNames.get(i), this, table2));
        }
        List<Column> columnList_1 = new ArrayList<>();
        List<Column> columnList_2 = new ArrayList<>();
        for (int k = 0; k < columns(); k+=1) {
            String string_ = this.getTitle(k);
            if (table2.findColumn(string_) != -1) {
                Column column_1 = new Column(string_, this);
                columnList_1.add(column_1);
                Column column_2 = new Column(string_, table2);
                columnList_2.add(column_2);
            }
        }
        for (int i = 0; i < _size; i +=1) {
            for (int j = 0; j <table2.size(); j +=1){
                if (conditions == null){
                    result.add(columnList_1, i,j);
                }
                else{
                    if (equijoin(columnList_1, columnList_2, i, j)) {
                        if (Condition.test(conditions, i, j)) {
                            result.add(column1, i,j);
                        }
                    }
                }
            }
        }

        return result;
    }
    /** Return <0, 0, or >0 depending on whether the row formed from
     *  the elements _columns[0].get(K0), _columns[1].get(K0), ...
     *  is less than, equal to, or greater than that formed from elememts
     *  _columns[0].get(K1), _columns[1].get(K1), ....  This method ignores
     *  the _index. */
    private int compareRows(int k0, int k1) {
        for (int i = 0; i < _columns.length; i += 1) {
            int c = _columns[i].get(k0).compareTo(_columns[i].get(k1));
            if (c != 0) {
                return c;
            }
        }
        return 0;
    }

    /** Return true if the columns COMMON1 from ROW1 and COMMON2 from
     *  ROW2 all have identical values.  Assumes that COMMON1 and
     *  COMMON2 have the same number of elements and the same names,
     *  that the columns in COMMON1 apply to this table, those in
     *  COMMON2 to another, and that ROW1 and ROW2 are indices, respectively,
     *  into those tables. */
    private static boolean equijoin(List<Column> common1, List<Column> common2,
                                    int row1, int row2) {
        ValueList row1_ = new ValueList();
        ValueList row2_ = new ValueList();
        for (int i = 0; i < common1.size(); i++) {
            row1_.add(common1.get(i).getFrom(row1));
            row2_.add(common2.get(i).getFrom(row2));
        }
        if (row1_ == row2_) {
            return true;
        }
        else {
            return false;
        }
    }


    /** A class that is essentially ArrayList<String>.  For technical reasons,
     *  we need to encapsulate ArrayList<String> like this because the
     *  underlying design of Java does not properly distinguish between
     *  different kinds of ArrayList at runtime (e.g., if you have a
     *  variable of type Object that was created from an ArrayList, there is
     *  no way to determine in general whether it is an ArrayList<String>,
     *  ArrayList<Integer>, or ArrayList<Object>).  This leads to annoying
     *  compiler warnings.  The trick of defining a new type avoids this
     *  issue. */
    private static class ValueList extends ArrayList<String> {
    }

    /** My column titles. */
    private final String[] _titles;
    /** My columns. Row i consists of _columns[k].get(i) for all k. */
    private final ValueList[] _columns;

    /** Rows in the database are supposed to be sorted. To do so, we
     *  have a list whose kth element is the index in each column
     *  of the value of that column for the kth row in lexicographic order.
     *  That is, the first row (smallest in lexicographic order)
     *  is at position _index.get(0) in _columns[0], _columns[1], ...
     *  and the kth row in lexicographic order in at position _index.get(k).
     *  When a new row is inserted, insert its index at the appropriate
     *  place in this list.
     *  (Alternatively, we could simply keep each column in the proper order
     *  so that we would not need _index.  But that would mean that inserting
     *  a new row would require rearranging _rowSize lists (each list in
     *  _columns) rather than just one. */
    private final ArrayList<Integer> _index = new ArrayList<>();
   private final ArrayList<ArrayList> _rows;

    /** My number of rows (redundant, but convenient). */
    private int _size;
    /** My number of columns (redundant, but convenient). */
    private final int _rowSize;
}
