package lk.ac.mrt.cse.dbs.simpleexpensemanager.data.impl;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.Settings;
import android.widget.Toast;

import java.util.Date;

import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Account;
import lk.ac.mrt.cse.dbs.simpleexpensemanager.data.model.Transaction;

/**
 * Created by Gihan bc on 11/18/2017.
 */

public class Myhelper extends SQLiteOpenHelper {
    public static final String myDB = "150178X";
    public Myhelper(Context context) {
        super(context, myDB, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {


        db.execSQL("create table  account(account_no text primary key, bank_name text, account_holder_name text, balance double)");
        db.execSQL("create table transactions(transaction_date int, account_no text, expense_type text, amount double)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("DROP TABLE IF EXISTS account");
        db.execSQL("DROP TABLE IF EXISTS transactions");
        onCreate(db);
    }

    public void logTransaction(Transaction transaction) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put("transaction_date", transaction.getDate().getTime());
        contentValues.put("account_no", transaction.getAccountNo());
        contentValues.put("expense_type", transaction.getExpenseType().toString());
        contentValues.put("amount", transaction.getAmount());
        db.insert("transactions", null, contentValues);
    }

    public Cursor getAllTransactionLogs() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM transactions", null);
    }


    public boolean addAccount(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        String accNo=account.getAccountNo();
        String BankName=account.getBankName();
        String Holder=account.getAccountHolderName();
        cv.put("account_no", accNo);
        cv.put("bank_name", BankName);
        cv.put("account_holder_name", Holder);
        cv.put("balance", account.getBalance());
        return db.insert("account", null, cv) != -1;
    }

    public boolean removeAccount(String accountNo) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.delete("account", "account_no = ?", new String[]{accountNo}) > 0;
    }

    public boolean updateBalance(Account account) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        String accNo=account.getAccountNo();
        String BankName=account.getBankName();
        String Holder=account.getAccountHolderName();
        cv.put("account_no", accNo);
        cv.put("bank_name",BankName );
        cv.put("account_holder_name", Holder);
        cv.put("balance", account.getBalance());
        return db.update("account", cv, "account_no = ?", new String[]{account.getAccountNo()}) > 0;
    }

    public Cursor getAccountNumbersList() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT account_no FROM account", null);
    }

    public Cursor getAccountsList() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM account", null);
    }

    public Cursor getAccount(String accountNo) {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM account WHERE account_no = ?", new String[]{accountNo});
    }



}