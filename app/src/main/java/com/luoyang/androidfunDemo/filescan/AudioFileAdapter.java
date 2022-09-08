package com.luoyang.androidfunDemo.filescan;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.luoyang.androidfunDemo.MainApplication;
import com.luoyang.androidfunDemo.R;

import java.io.File;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 扫描文件适配
 *
 * @author lixiongjun
 * @date 2022/10/21
 */
public class AudioFileAdapter extends RecyclerView.Adapter<AudioFileAdapter.ViewHolder> {

    private final List<File> mData = new ArrayList<>();
    private final List<File> mSelectData = new ArrayList<>();
    private final OnSelectAudioListener mOnSelectAudioListener;
    private Boolean mCanCheck = false;
    private final Context mContext;

    public interface OnSelectAudioListener {
        void selectAudioSize(int count);
    }

    public AudioFileAdapter(OnSelectAudioListener onSelectAudioListener, Context context) {
        mOnSelectAudioListener = onSelectAudioListener;
        mContext = context;
    }

    public void addData(File newItem) {
        int position = mData.size();
        mData.add(newItem);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, 1);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_audio_file_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        File file = mData.get(position);
        holder.tvName.setText(file.getName());
        holder.tvSize.setText(convertByte(file.length()));
        holder.tvLoc.setText(file.getAbsolutePath().replace(Environment.getExternalStorageDirectory().getAbsolutePath(), "存储空间"));
        holder.cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    mSelectData.add(mData.get(position));
                } else {
                    mSelectData.remove(mData.get(position));
                }
                if (mOnSelectAudioListener != null) {
                    mOnSelectAudioListener.selectAudioSize(mSelectData.size());
                }

            }
        });
        if (mCanCheck) {
            holder.cbSelect.setVisibility(View.VISIBLE);
            holder.llContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.cbSelect.setChecked(!holder.cbSelect.isChecked());
                }
            });
            holder.llContent.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    if (mContext != null) {
                        String content = String.format(mContext.getString(R.string.file_info)
                                , file.getName(), file.getAbsolutePath(), getTime(file.lastModified()));
                        DialogUtil.showDialog(mContext, mContext.getString(R.string.dialog_title_file), content);
                    }
                    return false;
                }
            });
        } else {
            holder.cbSelect.setVisibility(View.INVISIBLE);
            holder.llContent.setOnClickListener(null);
        }

    }

    public void setCanCheck(Boolean mCanCheck) {
        this.mCanCheck = mCanCheck;
        notifyDataSetChanged();
    }

    public List<File> getSelectData() {
        return mSelectData;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout llContent;
        TextView tvName;
        TextView tvSize;
        TextView tvLoc;
        CheckBox cbSelect;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            llContent = itemView.findViewById(R.id.ll_content);
            tvName = itemView.findViewById(R.id.tv_name);
            tvSize = itemView.findViewById(R.id.tv_size);
            cbSelect = itemView.findViewById(R.id.cb_select);
            tvLoc = itemView.findViewById(R.id.tv_loc);
        }
    }

    public static String convertByte(long size) {
        DecimalFormat df = new DecimalFormat("###.#");
        float format;
        if (size < 1024) {
            format = size;
            return (df.format(Float.valueOf(format).doubleValue()) + "B");
        } else if (size < 1024 * 1024) {
            format = ((float) size / (float) 1024);
            return (df.format(Float.valueOf(format).doubleValue()) + "KB");
        } else if (size < 1024 * 1024 * 1024) {
            format = ((float) size / (float) (1024 * 1024));
            return (df.format(Float.valueOf(format).doubleValue()) + "MB");
        } else {
            format = ((float) size / (float) (1024 * 1024 * 1024));
            return (df.format(Float.valueOf(format).doubleValue()) + "GB");
        }
    }

    public static String getTime(long currentTime) {
        try {
            //当前时间毫秒的时间戳转换为日期
            Date millisecondDate = new Date(currentTime);
            //格式化时间
            @SuppressLint("SimpleDateFormat")
            SimpleDateFormat formatter = new SimpleDateFormat(MainApplication.getInstance().getString(R.string.date_format));
            return formatter.format(millisecondDate);
        } catch (Exception e) {
            Log.e("AudioFileAdapter", "getTime Exception " + e.getMessage());
        }
        return "";
    }
}
