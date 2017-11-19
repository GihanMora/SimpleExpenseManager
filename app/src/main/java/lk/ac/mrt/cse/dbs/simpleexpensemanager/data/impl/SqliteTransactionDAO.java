package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.TransactionDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

import static android.support.design.R.id.text;

/**
 * Created by Gihan bc on 11/18/2017.
 */

public class SqliteTransactionDAO implements TransactionDAO {
   //private final List<Transaction> transactions;

    private Myhelper nMyHelper;
    public SqliteTransactionDAO(Myhelper nMyHelper) {
        //transactions = new LinkedList<>();
        this.nMyHelper=nMyHelper;


    }

    @Override
    public void logTransaction(Date date, String accountNo, ExpenseType expenseType, double amount) {

        Transaction transaction = new Transaction(date, accountNo, expenseType, amount);
        nMyHelper.logTransaction(transaction);


    }

    @Override
    public List<Transaction> getAllTransactionLogs(){

        ArrayList<Transaction> transactions = new ArrayList<>();

        Cursor c = nMyHelper.getAllTransactionLogs();
        while(c.moveToNext()){
            //SimpleDateFormat dt1 = new SimpleDateFormat("yyyy-MM-dd");
            Date date=new Date();
            date.setTime(c.getLong(0));
            String accountNo=c.getString(1);
            ExpenseType expenseType=ExpenseType.valueOf(c.getString(2));

            double amount= Double.parseDouble(c.getString(3));
            Transaction transaction = new Transaction(date, accountNo, expenseType, amount);

            transactions.add(transaction);
            //ExpenseType expenseType = c.getString(2).equals("EXPENSE") ? ExpenseType.EXPENSE : ExpenseType.INCOME;
            //transactions.add(new Transaction(date, c.getString(1), expenseType, c.getDouble(3)));


        }

        return transactions;

    }

    @Override
    public List<Transaction> getPaginatedTransactionLogs(int limit) {
        List<Transaction> transactions = getAllTransactionLogs();

        int size = transactions.size();
        if (size <= limit) {
            return transactions;
        }
        //return the last <code>limit</code> number of transaction logs
        return transactions.subList(size - limit, size);
    }


}
