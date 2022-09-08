package com.luoyang.androidfunDemo.retrofit.show;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.Downsampler;
import com.bumptech.glide.request.RequestOptions;
import com.luoyang.androidfunDemo.R;
import com.luoyang.androidfunDemo.bean.DetailBean;
import com.luoyang.androidfunDemo.bean.ItemBean;
import com.luoyang.androidfunDemo.bean.SearchSuggestBean;
import com.luoyang.androidfunDemo.retrofit.show.okhttp.OkhttpActivity;
import com.luoyang.androidfunDemo.util.Utils;
import com.luoyang.base.BaseConstant;
import com.luoyang.base.base.BaseActivity;
import com.luoyang.base.exposure.ExposureHelperKt;
import com.luoyang.base.exposure.ViewHelperKt;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索建议词适配
 */
public class SearchSuggestAdapter extends BaseAdapter {
    private static final String TAG = "SearchResultAdapter";
    protected final List<ItemBean> list;
    private final BaseActivity mContext;
    private RequestOptions mRequestOptions = new RequestOptions().set(Downsampler.ALLOW_HARDWARE_CONFIG,
            true);

    public SearchSuggestAdapter(BaseActivity mAct, List<ItemBean> list) {
        this.mContext = mAct;
        if (list != null && list.size() > 0) {
            this.list = list;
        } else {
            this.list = new ArrayList<>();
        }
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if ((list == null) || (list.size() == 0)) {
            return convertView;
        }
        if (position >= list.size()) {
            return convertView;
        }
        if (list.get(position) instanceof DetailBean) {
            final DetailBean detailBean = (DetailBean) list.get(position);
            convertView = Utils.inflate(mContext, R.layout.common_app_item_layout);
            ImageView icon = convertView.findViewById(R.id.iv_icon);
            TextView title = convertView.findViewById(R.id.tv_title);
            TextView apkSize = convertView.findViewById(R.id.apk_size);
            TextView count = convertView.findViewById(R.id.tv_count);
            TextView description = convertView.findViewById(R.id.tv_content);
            RecyclerView mRvTag = convertView.findViewById(R.id.rv_app_item_tag);
            Button buttonSmall = convertView.findViewById(R.id.download_button);

            title.setText(detailBean.getAppName());


            Utils.setContentText(description, detailBean.getEditorIntro(), detailBean.getCompatibleMsg(), detailBean.getWarningTips());
            Glide.with(icon).load(detailBean.getIcon()).error(R.mipmap.ic_launcher)
                    .centerCrop().apply(mRequestOptions).into(icon);
            mRvTag.setFocusable(false);
            long size = 0;
            try {
                size = Long.parseLong(detailBean.getSize());
            } catch (Exception e) {
                Log.e(TAG, "getView Exception: ", e);
            }
            String finalNumber = detailBean.getDownloadDesc();


            if (!TextUtils.isEmpty(detailBean.getSize())) {
                apkSize.setText(Utils.formatFileSize(size, false));
            }
            if (!TextUtils.isEmpty(detailBean.getDownloadDesc())) {
                count.setText(detailBean.getDownloadDesc());
            } else {
                count.setText(finalNumber);
            }

            //这里两个曝光，会执行后一个曝光
            ViewHelperKt.setEventOnExposure(convertView, detailBean.getAppName().hashCode(), 30000, 1, 1000,
                    () -> {
                        Log.e(BaseConstant.EXPOSURE, "setEventOnExposure " + detailBean.getAppName());
                        return null;
                    }
            );
            ExposureHelperKt.setOnExposureListener(convertView, detailBean.getPackageName().hashCode(), 30000, 1, 1000,
                    () -> {
                        Log.e(BaseConstant.EXPOSURE, "setOnExposureListener " + detailBean.getPackageName());
                        return null;
                    }
            );

            return convertView;
        }

        Holder holder = null;
        if (convertView == null || convertView.getTag() == null) {
            holder = new Holder();
            convertView = Utils.inflate(mContext, R.layout.suggest_item);
            holder.title = (TextView) convertView.findViewById(R.id.title);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (list.get(position) instanceof SearchSuggestBean) {
            String text = ((SearchSuggestBean) list.get(position)).getSug();
            holder.title.setText(text);

            convertView.setOnClickListener(v -> {
                if (mContext instanceof HttpURLActivity) {
                    ((HttpURLActivity) mContext).search(text);
                }
                if (mContext instanceof OkhttpActivity) {
                    ((OkhttpActivity) mContext).search(text);
                }
            });

            //这里两个曝光，会执行后一个曝光
            ExposureHelperKt.setOnExposureListener(convertView, text.hashCode(), 30000, 1, 1000,
                    () -> {
                        Log.e(BaseConstant.EXPOSURE, "setOnExposureListener sug" + text);
                        return null;
                    }
            );
            ViewHelperKt.setEventOnExposure(convertView, text.hashCode(), 30000, 1, 1000,
                    () -> {
                        Log.e(BaseConstant.EXPOSURE, "setEventOnExposure sug  " + text);
                        return null;
                    }
            );
        }


        return convertView;

    }


    class Holder {
        TextView title;
    }
}
