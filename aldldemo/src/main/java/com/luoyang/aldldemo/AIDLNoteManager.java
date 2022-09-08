package com.luoyang.aldldemo;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.luoyang.note.NoteBean;
import com.luoyang.note.NoteChange;

import java.util.ArrayList;
import java.util.List;

/**
 * @author luoyang
 * @date 2022/7/14
 */
public class AIDLNoteManager {
    private static final String TAG = "AIDLNoteManager";
    private NoteChange mRemote;
    private boolean mIsBound = false;
    private final Context mContext;
    private List<NoteBean> mNoteBeanList;
    /**
     * working memory 的变量信息立即直接写到main memory,以供其他线程调用
     * <p>
     * java 1.6加入volatile关键词,防止乱序执行
     * 当一个线程需要立刻读取到另外一个线程修改的变量值的时候，我们就可以使用volatile
     * Volatile关键字来强制将变量直接写到main memory，从而保证了不同线程读写到的是同一个变量。
     */
    private static volatile AIDLNoteManager mInstance;

    private AIDLNoteManager(Context paramContext) {
        mContext = paramContext;
//        mContext = MainApplication.getInstance();
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
    public static synchronized AIDLNoteManager getInstance(Context context) {
        if (mInstance == null) {
            synchronized (AIDLNoteManager.class) {
                if (mInstance == null) {
                    mInstance = new AIDLNoteManager(context);
                }
            }
        }
        return mInstance;
    }

    public void bindCloneRemoteService() {
        Log.d(TAG, "bindCloneRemoteService");
        try {
            Intent intent = new Intent();
            intent.setAction("com.luoyang.note.Service");
            ComponentName component = new ComponentName("com.luoyang.note", "com.luoyang.note.AIDLService");
            intent.setComponent(component);
            Log.d(TAG, "bindCloneRemoteService mContext " + mContext);
            Log.d(TAG, "bindCloneRemoteService serviceConnection " + serviceConnection);
            mIsBound = mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
            Log.d(TAG, "bindCloneRemoteService mIsBound " + mIsBound);
//            int count = 0;
//            while (mRemote == null) {
//                Intent intent = new Intent();
//                intent.setAction("com.luoyang.note.Service");
//                ComponentName component = new ComponentName("com.luoyang.note", "com.luoyang.note.AIDLService");
//                intent.setComponent(component);
//                Log.d(TAG, "bindCloneRemoteService mContext " + mContext);
//                Log.d(TAG, "bindCloneRemoteService MainApplication " + MainApplication.getInstance());
//                Log.d(TAG, "bindCloneRemoteService serviceConnection " + serviceConnection);
//                mIsBound = mContext.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE);
//                Log.d(TAG, "bindCloneRemoteService mIsBound " + mIsBound);
//                if (count == 3) {
//                    return;
//                }
//                count++;
//                Thread.sleep(500);
//
//            }
//

        } catch (Exception e) {
            Log.e(TAG, "bindRemoteService", e);
        }
    }

    public void unbindCloneRemoteService() {
        if (mIsBound) {
            mContext.unbindService(serviceConnection);
            mIsBound = false;
        }
    }

    public List<NoteBean> queryAllRemote() {
        List<NoteBean> noteBeanList = new ArrayList<>();

        Log.d(TAG, "queryAllRemote mRemote " + mRemote);
        if (mRemote != null) {
            try {
                noteBeanList = mRemote.queryAll();
            } catch (RemoteException e) {
                Log.e(TAG, "queryAll RemoteException:", e);
            }
        }
        Log.d(TAG, "queryAllRemote noteBeanList " + noteBeanList);
        return noteBeanList;
    }

    public void add(NoteBean noteBean) {
        if (mRemote != null) {
            try {
                mRemote.add(noteBean);
            } catch (RemoteException e) {
                Log.e(TAG, "add:", e);
            }
        }
    }

    public void deleteById(NoteBean noteBean) {
        if (mRemote != null) {
            try {
                mRemote.deleteById(noteBean);
            } catch (RemoteException e) {
                Log.e(TAG, "deleteById:", e);
            }
        }
    }

    public void update(NoteBean noteBean) {
        if (mRemote != null) {
            try {
                mRemote.update(noteBean);
            } catch (RemoteException e) {
                Log.e(TAG, "update:", e);
            }
        }
    }

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mRemote = NoteChange.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected");
            mNoteBeanList = queryAllRemote();
            RxBus.getDefault().post(new NoteList(mNoteBeanList));
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            //当这个远程service服务被异外销毁执行
            Log.d(TAG, "onServiceDisconnected: " + name.toString());
        }
    };
}
