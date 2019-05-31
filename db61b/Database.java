// This is a SUGGESTED skeleton for a class that contains the Tables your
// program manipulates.  You can throw this away if you want, but it is a good
// idea to try to understand it first.  Our solution changes about 6
// lines in this skeleton.

// Comments that start with "//" are intended to be removed from your
// solutions.
package db61b;

// FILL IN (WITH IMPORTS)?

import java.util.ArrayList;
import java.util.HashMap;

/** A collection of Tables, indexed by name.
 *  @author */





class Database {
    /** An empty database. */

    public Database() {
        HashMap_ = new HashMap<>();

    }

    /** Return the Table whose name is NAME stored in this database, or null
     *  if there is no such table. */
    public Table get(String name) {

        return HashMap_.get(name);


    }

    /** Set or replace the table named NAME in THIS to TABLE.  TABLE and
     *  NAME must not be null, and NAME must be a valid name for a table. */
    public void put(String name, Table table) {
        if (name == null || table == null) {
            throw new IllegalArgumentException("null argument");
        }
        HashMap_.put(name, table);
    }

    private  final HashMap<String, Table> HashMap_;
}
