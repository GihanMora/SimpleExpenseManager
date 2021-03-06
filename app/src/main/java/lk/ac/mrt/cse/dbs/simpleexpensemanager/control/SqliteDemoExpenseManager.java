package lk.ac.mrt.cse.dbs.simpleexpensemanager.control;

import android.content.Context;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.zip.DataFormatException;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryAccountDAO;
//import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.InMemoryTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.Myhelper;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.SqliteAccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl.SqliteTransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

/**
 * Created by Gihan bc on 11/18/2017.
 */

public class SqliteDemoExpenseManager  extends ExpenseManager{
    private Myhelper nMyHelper;
    public SqliteDemoExpenseManager(Myhelper nMyHelper)  {
        this.nMyHelper=nMyHelper;
        setup();
    }


    @Override
    public void setup() {
        /*** Begin generating dummy data for In-Memory implementation ***/

        TransactionDAO sqliteTransactionDAO = new SqliteTransactionDAO(nMyHelper);
        setTransactionsDAO(sqliteTransactionDAO);

        AccountDAO sqliteAccountDAO = new SqliteAccountDAO(nMyHelper);
        setAccountsDAO(sqliteAccountDAO);

        // dummy data
        Account dummyAcct1 = new Account("403029A", "Sampath Bank", "Gihan Gamage", 10000.0);
        Account dummyAcct2 = new Account("950317Z", "BOC", "Nadeesha Madhushani", 80000.0);

        getAccountsDAO().addAccount(dummyAcct1);
        getAccountsDAO().addAccount(dummyAcct2);



        /*** End ***/
    }
}
