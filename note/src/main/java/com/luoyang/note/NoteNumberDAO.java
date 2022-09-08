package com.luoyang.note;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyang
 */
public class NoteNumberDAO {
    private static final String TAG = "NoteNumberDAO";
    private DBHelper dbHelper;

    /**
     * working memory 的变量信息立即直接写到main memory,以供其他线程调用
     * <p>
     * java 1.6加入volatile关键词,防止乱序执行
     * 当一个线程需要立刻读取到另外一个线程修改的变量值的时候，我们就可以使用volatile
     * Volatile关键字来强制将变量直接写到main memory，从而保证了不同线程读写到的是同一个变量。
     */
    private static volatile NoteNumberDAO mNoteNumberDAO;

    private NoteNumberDAO(Context paramContext) {
        dbHelper = new DBHelper(paramContext);
    }

    /**
     * 懒汉式单例,线程不安全->
     * 加锁,一把synchronized 线程安全->加锁可能影响效率->每次调用getInstance都会同步一次,浪费资源->
     * 加双检查锁, 可在实例域需要延迟初始化时使用->
     * 两把synchronized锁,要加volatile,防止JVM的指令重排
     *
     * @param context
     * @return
     */
    public static synchronized NoteNumberDAO getInstance(Context context) {
        if (mNoteNumberDAO == null) {
            synchronized (NoteNumberDAO.class) {
                if (mNoteNumberDAO == null) {
                    mNoteNumberDAO = new NoteNumberDAO(context);
                }
            }
        }
        return mNoteNumberDAO;
    }

    //添加数据
    public void add(NoteBean noteBean) {
        //获取连接
        SQLiteDatabase database = this.dbHelper.getReadableDatabase();
        ContentValues contentValues = new ContentValues();
        //添加数据
        contentValues.put("number", noteBean.getNumber());
        long id = database.insert(DBHelper.DB_TABLE, null, contentValues);
        Log.d(TAG, "添加数据 生成id= " + id);
        noteBean.setId((int) id);
        //关闭连接
        database.close();
    }

    //删除数据
    public void deleteById(int paramInt) {
        SQLiteDatabase database = this.dbHelper.getReadableDatabase();
        database.delete(DBHelper.DB_TABLE, "_id=?", new String[]{paramInt + ""});
        database.close();
    }
    //删除数据
    public void deleteByNumber(String number) {
        SQLiteDatabase database = this.dbHelper.getReadableDatabase();
        database.delete(DBHelper.DB_TABLE, "number=?", new String[]{number + ""});
        database.close();
    }

    //更新数据
    public void update(NoteBean noteBean) {
        SQLiteDatabase localSQLiteDatabase = this.dbHelper.getReadableDatabase();
        ContentValues localContentValues = new ContentValues();
        localContentValues.put("number", noteBean.getNumber());
        int i = localSQLiteDatabase.update(DBHelper.DB_TABLE, localContentValues, "_id=" + noteBean.getId(), null);
        Log.e(TAG, "update=" + i);
        localSQLiteDatabase.close();
    }


    //查询数据
    public List<NoteBean> queryAll() {
        SQLiteDatabase database = this.dbHelper.getReadableDatabase();
        Cursor cursor = database.query(DBHelper.DB_TABLE, null, null, null, null, null, "_id desc");
        ArrayList arrayList = new ArrayList();
        for (; ; ) {
            if (!cursor.moveToNext()) {
                cursor.close();
                database.close();
                return arrayList;
            }
            arrayList.add(new NoteBean(cursor.getInt(0), cursor.getString(1)));
        }
    }

}
