package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.AccountDAO;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.exception.InvalidAccountException;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.ExpenseType;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.ui.MainActivity;

/**
 * Created by Gihan bc on 11/18/2017.
 */

public class SqliteAccountDAO implements AccountDAO {
    //private final Map<String, Account> accounts;
    private Myhelper nMyHelper;


    public SqliteAccountDAO(Myhelper nMyHelper) {
        //this.accounts = new HashMap<>();
        this.nMyHelper=nMyHelper;

    }

    @Override
    public List<String> getAccountNumbersList() {
        //return new ArrayList<>(accounts.keySet());

        ArrayList<String> numbers = new ArrayList<>();
        Cursor c = nMyHelper.getAccountNumbersList();
        while(c.moveToNext()){
            numbers.add(c.getString(0));


        }
        return numbers;
    }

    @Override
    public List<Account> getAccountsList() {
        //return new ArrayList<>(accounts.values());

        ArrayList<Account> accounts = new ArrayList<Account>();
        Cursor c = nMyHelper.getAccountsList();
        while(c.moveToNext()){
            String accountNo=c.getString(0);
            String bankName=c.getString(1);
            String accountHolderName=c.getString(2);
            double balance= Double.parseDouble(c.getString(3));
            Account a=new Account(accountNo,bankName,accountHolderName,balance);

            accounts.add(a);


        }
        return accounts;
    }

    @Override
    public Account getAccount(String accNo) throws InvalidAccountException {

        Cursor c = nMyHelper.getAccount(accNo);
        if(c.moveToNext()){
            String accountNo=c.getString(0);
            String bankName=c.getString(1);
            String accountHolderName=c.getString(2);
            double balance= Double.parseDouble(c.getString(3));
            Account a=new Account(accountNo,bankName,accountHolderName,balance);
            return a;
        }
        else {String msg = "Account " + accNo + " is invalid.";
            throw new InvalidAccountException(msg);}


    }

    @Override
    public void addAccount(Account account) {

        nMyHelper.addAccount(account);
    }

    @Override
    public void removeAccount(String accountNo) throws InvalidAccountException {
        boolean isexist=nMyHelper.removeAccount(accountNo);
        if (!isexist) {
            String msg = "Account " + accountNo + " is invalid.";
            throw new InvalidAccountException(msg);
        }
    }

    @Override
    public void updateBalance(String accountNo, ExpenseType expenseType, double amount) throws InvalidAccountException {
        Account account = getAccount(accountNo);
        // specific implementation based on the transaction type
        switch (expenseType) {
            case EXPENSE:
                account.setBalance(account.getBalance() - amount);
                break;
            case INCOME:
                account.setBalance(account.getBalance() + amount);
                break;
        }
        boolean isexist=nMyHelper.updateBalance(account);
        if (!isexist) {
            String msg = "Cannot update balance of this account";
            throw new InvalidAccountException(msg);
        }
    }
}
