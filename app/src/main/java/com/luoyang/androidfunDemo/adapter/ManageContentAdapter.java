
package com.luoyang.androidfunDemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.luoyang.androidfunDemo.R;
import com.luoyang.androidfunDemo.bean.MineItemBean;
import java.util.List;


/**
 * @author lixiongjun
 * @date 2021/7/19
 */
public class ManageContentAdapter extends RecyclerView.Adapter<ManageContentAdapter.ViewHolder> {
    private static final String TAG = "ManageContentAdapter";
    private List<MineItemBean> mDataList;
    private Context mContext;

    public ManageContentAdapter(Context context, List<MineItemBean> data) {
        mContext = context;
        mDataList = data;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.mine_manage_item, parent, false);
        return new ViewHolder(view);
    }

    public MineItemBean getItem(int position) {
        if (mDataList != null && mDataList.size() > position && position >= 0) {
            return mDataList.get(position);
        }
        return null;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        final MineItemBean item = getItem(position);
        if (item != null) {
            viewHolder.mTitle.setText(item.getTitle());
            viewHolder.mIcon.setImageResource(item.getImageId());
            viewHolder.mParentLayout.setOnClickListener(v -> item.onClickItem());
        }
    }

    @Override
    public int getItemCount() {
        if (mDataList != null) {
            return mDataList.size();
        } else {
            return 0;
        }
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTitle;
        public ImageView mIcon;
        public LinearLayout mParentLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            mTitle = itemView.findViewById(R.id.gv_item_text);
            mIcon = itemView.findViewById(R.id.gv_item_icon);
            mParentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
