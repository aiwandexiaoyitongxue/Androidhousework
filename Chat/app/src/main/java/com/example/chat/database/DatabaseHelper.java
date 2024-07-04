package com.example.chat.database;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chat.Contact.Contact;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "user_db";
    private static final int DATABASE_VERSION = 1;
//登录注册的表
    private static final String TABLE_USERS = "users";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_USERNAME = "username";
    private static final String COLUMN_PASSWORD = "password";
//联系人的表
    private static final String TABLE_CONTACTS = "contacts";
    private static final String KEY_ID = "id";
    private static final String KEY_NAME = "name";
    private static final String KEY_PHONE = "phone";
//表示好友关系的表
    private static final String TABLE_FRIENDS = "friends";
    private static final String FRIENDS_ID = "friendship_id";
    private static final String KEY_CONTACT1_ID = "contact1_id";
    private static final String KEY_CONTACT2_ID = "contact2_id";
//消息的表
    public static final String TABLE_MESSAGES = "messages";
    public static final String COLUMN_MESSAGE_ID = "id";
    public static final String COLUMN_MESSAGE_CONTENT = "content";
    public static final String COLUMN_SENDER_ID = "sender_id";
    public static final String COLUMN_RECEIVER_ID = "receiver_id";
    public static final String COLUMN_TIMESTAMP = "timestamp";

    private static final String CREATE_TABLE_USERS = "CREATE TABLE " + TABLE_USERS + "("
            + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + COLUMN_USERNAME + " TEXT,"
            + COLUMN_PASSWORD + " TEXT"
            + ")";

    private static final String  CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE_CONTACTS + "("
            + KEY_ID + " INTEGER PRIMARY KEY,"
            + KEY_NAME + " TEXT,"
            + KEY_PHONE + " TEXT" + ")";

    private static final String CREATE_TABLE_MESSAGES = "CREATE TABLE " + TABLE_MESSAGES +
            "(" + COLUMN_MESSAGE_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
            COLUMN_MESSAGE_CONTENT + " TEXT," +
            COLUMN_SENDER_ID + " TEXT," +
            COLUMN_RECEIVER_ID + " TEXT," +
            COLUMN_TIMESTAMP + " INTEGER)";
//表示好友关系
    String CREATE_FRIENDS_TABLE = "CREATE TABLE " + TABLE_FRIENDS + "("
        + KEY_ID + " INTEGER PRIMARY KEY,"
        + KEY_CONTACT1_ID + " INTEGER,"
        + KEY_CONTACT2_ID + " INTEGER,"
        + "FOREIGN KEY(" + KEY_CONTACT1_ID + ") REFERENCES contacts(id),"
        + "FOREIGN KEY(" + KEY_CONTACT2_ID + ") REFERENCES contacts(id)"
        + ")";
    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_MESSAGES);
        db.execSQL(CREATE_TABLE_USERS);
        db.execSQL(CREATE_CONTACTS_TABLE);
        db.execSQL(CREATE_FRIENDS_TABLE);
    }


    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MESSAGES);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FRIENDS);
        onCreate(db);
    }

    // 添加用户到数据库
    public long addUser(String username, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_USERNAME, username);
        values.put(COLUMN_PASSWORD, password);
        long id = db.insert(TABLE_USERS, null, values);
        db.close();
        return id;
    }

    // 检查用户是否存在
    public boolean checkUser(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_ID };
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();
        cursor.close();
        db.close();

        if (count > 0) {
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("SuspiciousIndentation")
    public int getUserIdByName(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_ID };
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);
        int count = cursor.getCount();

        int result=0;
        if(count>0){
            cursor.moveToFirst();
            int i=cursor.getColumnIndex(COLUMN_ID);
            if(i>0)
            result= cursor.getInt(i);
        }
        cursor.close();
        db.close();

       return result;
    }
    //将联系人添加到数据库
    public long addContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        long rowId = db.insert(TABLE_CONTACTS, null, values);
        db.close();
        return rowId;
    }
    // 检查上一次插入操作是否成功的方法
    public boolean wasInsertSuccessful(long rowId) {
        return rowId != -1;
    }

    // Getting single contact
    public Contact getContact(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_CONTACTS, new String[]{KEY_ID,
                        KEY_NAME, KEY_PHONE}, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        Contact contact = new Contact(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1), cursor.getString(2));
                cursor.close();
                return contact;
    }
    @SuppressLint("Range")
    public int findContactIdByNameAndPhone(String name, String phone) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { KEY_ID }; // 我们只需要查询ID列
        String selection = KEY_NAME + "=?" + " AND " + KEY_PHONE + "=?";
        String[] selectionArgs = { name, phone }; // 查询条件的参数

        Cursor cursor = db.query(TABLE_CONTACTS, columns, selection, selectionArgs, null, null, null);
        int contactId = -1; // 默认返回-1表示未找到

        if (cursor.moveToFirst()) {
            contactId = cursor.getInt(cursor.getColumnIndex(KEY_ID)); // 获取查询结果中的ID
        }
        cursor.close();
        db.close();

        return contactId;
    }
    // Getting all contacts
    public List<Contact> getAllContacts() {
        List<Contact> contactList = new ArrayList<>();
        String selectQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {
                Contact contact = new Contact();
                contact.setId(Integer.parseInt(cursor.getString(0)));
                contact.setName(cursor.getString(1));
                contact.setPhone(cursor.getString(2));
                contactList.add(contact);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return contactList;
    }
    // Updating single contact
    public int updateContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(KEY_NAME, contact.getName());
        values.put(KEY_PHONE, contact.getPhone());
        return db.update(TABLE_CONTACTS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
    }
    // Deleting single contact
    public void deleteContact(Contact contact) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACTS, KEY_ID + " = ?",
                new String[]{String.valueOf(contact.getId())});
        db.close();
    }
    // Getting contacts count
    public int getContactsCount() {
        String countQuery = "SELECT  * FROM " + TABLE_CONTACTS;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        int count = cursor.getCount();
        cursor.close();
        return count;
    }
    //添加消息
    public long addMessage(int senderId, int receiverId, String content) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_SENDER_ID, senderId);
        values.put(COLUMN_RECEIVER_ID, receiverId);
        values.put(COLUMN_MESSAGE_CONTENT, content);
        // 记录当前时间的时间戳
        values.put(COLUMN_TIMESTAMP, System.currentTimeMillis());
        long messageId = db.insert(TABLE_MESSAGES, null, values);
        db.close();
        return messageId;
    }
    //聊天记录
    public Cursor getAllMessages(int senderId, int receiverId) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_MESSAGES +
                " WHERE (" + COLUMN_SENDER_ID + " = " + senderId + " AND " +
                COLUMN_RECEIVER_ID + " = " + receiverId + ") OR (" +
                COLUMN_SENDER_ID + " = " + receiverId + " AND " +
                COLUMN_RECEIVER_ID + " = " + senderId + ")" +
                " ORDER BY " + COLUMN_TIMESTAMP;
        return db.rawQuery(query, null);
    }
    //删除
    public void deleteMessage(long messageId) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MESSAGES, COLUMN_MESSAGE_ID+ " = ?",
                new String[]{String.valueOf(messageId)});
        db.close();
    }
    public int updateMessage(long messageId, String newContent) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MESSAGE_CONTENT, newContent);
        return db.update(TABLE_MESSAGES, values, COLUMN_MESSAGE_ID + " = ?",
                new String[]{String.valueOf(messageId)});
    }
    @SuppressLint("Range")
    public int getCurrentUserId(String username) {
        SQLiteDatabase db = this.getReadableDatabase();
        String[] columns = { COLUMN_ID };
        String selection = COLUMN_USERNAME + "=?";
        String[] selectionArgs = { username };
        Cursor cursor = db.query(TABLE_USERS, columns, selection, selectionArgs,
                null, null, null);
        int userId = -1; // Default to -1 if user not found or not logged in
        if (cursor.moveToFirst()) {
            userId = cursor.getInt(cursor.getColumnIndex(COLUMN_ID));
        }
        cursor.close();
        db.close();
        return userId;
    }

}
