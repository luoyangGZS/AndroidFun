// NoteChange.aidl
package com.luoyang.note;
import com.luoyang.note.NoteBean;

// Declare any non-default types here with import statements

interface NoteChange {

    void add(in NoteBean noteBean);

    void deleteById(inout NoteBean noteBean);

    void update(inout NoteBean noteBean);

    List<NoteBean> queryAll();
}