package com.luoyang.aldldemo;


import com.luoyang.note.NoteBean;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

/**
 * @author luoyang
 * @date 2022/7/14
 */
public class RxBus {
    private static volatile RxBus instance;
    private Subject<Object, Object> bus;

    private RxBus() {
        bus = new SerializedSubject<>(PublishSubject.create());
    }

    public static RxBus getDefault() {
        if (instance == null) {
            synchronized (RxBus.class) {
                instance = new RxBus();
            }
        }
        return instance;
    }

    /**
     * 发送事件
     * @param object
     */
    public void post(Object object) {
        bus.onNext(object);
    }

    /**
     * 根据类型接收相应类型事件
     * @param eventType
     * @param <T>
     * @return
     */
    public <T> Observable toObservable(Class<T> eventType) {
        return bus.ofType(eventType);
    }

}