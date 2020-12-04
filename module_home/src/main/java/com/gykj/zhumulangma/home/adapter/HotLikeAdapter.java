package com.gykj.zhumulangma.home.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.gykj.zhumulangma.common.util.ZhumulangmaUtil;
import com.gykj.zhumulangma.home.R;
import com.ximalaya.ting.android.opensdk.model.album.Album;

/**
 * Created by 10719
 * on 2019/6/17
 */
public class HotLikeAdapter extends BaseQuickAdapter<Album, BaseViewHolder> {
    public HotLikeAdapter(int layoutResId) {
        super(layoutResId);
    }
    public HotLikeAdapter(Context context, int layoutResId) {
        super(layoutResId);
        mContext = context;
    }
    @Override
    protected void convert(BaseViewHolder helper, Album item) {
        Glide.with(mContext).load(item.getCoverUrlLarge()).into((ImageView) helper.getView(R.id.iv_cover));
        helper.setText(R.id.tv_playcount, ZhumulangmaUtil.toWanYi(item.getPlayCount()));
        helper.setText(R.id.tv_title,item.getAlbumTitle());
    }

    private static final String TAG = "HotLikeAdapter";
    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d(TAG, "onCreateViewHolder: ");
        return super.onCreateViewHolder(parent, viewType);
    }
}
