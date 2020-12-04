package com.gykj.zhumulangma.home.activity;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gykj.zhumulangma.common.Constants;
import com.gykj.zhumulangma.common.databinding.CommonLayoutRefreshListBinding;
import com.gykj.zhumulangma.common.event.KeyCode;
import com.gykj.zhumulangma.common.mvvm.view.BaseRefreshActivity;
import com.gykj.zhumulangma.common.mvvm.view.status.ListSkeleton;
import com.gykj.zhumulangma.home.R;
import com.gykj.zhumulangma.home.adapter.AnnouncerTrackAdapter;
import com.gykj.zhumulangma.home.mvvm.ViewModelFactory;
import com.gykj.zhumulangma.home.mvvm.viewmodel.TrackListViewModel;
import com.kingja.loadsir.callback.Callback;
import com.ximalaya.ting.android.opensdk.model.track.Track;

/**
 * Author: Thomas.
 * <br/>Date: 2019/9/11 11:40
 * <br/>Email: 1071931588@qq.com
 * <br/>Description:声音列表
 */

@Route(path = Constants.Router.Home.F_TRACK_LIST)
public class TrackListActivity extends BaseRefreshActivity<CommonLayoutRefreshListBinding, TrackListViewModel, Track> implements
        BaseQuickAdapter.OnItemClickListener {

    @Autowired(name = KeyCode.Home.ANNOUNCER_ID)
    public long mAnnouncerId;
    @Autowired(name = KeyCode.Home.TITLE)
    public String mTitle;
    private AnnouncerTrackAdapter mAnnouncerTrackAdapter;

    @Override
    public int onBindLayout() {
        return R.layout.common_layout_refresh_list;
    }

    @Override
    public void initView() {
        super.initView();
        mBinding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
        mBinding.recyclerview.setHasFixedSize(true);
        mAnnouncerTrackAdapter = new AnnouncerTrackAdapter(R.layout.home_item_announcer_track);
        mAnnouncerTrackAdapter.bindToRecyclerView(mBinding.recyclerview);
        setTitle(new String[]{mTitle});
    }

    @Override
    public void initListener() {
        super.initListener();
        mAnnouncerTrackAdapter.setOnItemClickListener(this);
        mBinding.refreshLayout.setOnLoadMoreListener(this);
    }

    @NonNull
    @Override
    protected WrapRefresh onBindWrapRefresh() {
        return new WrapRefresh(mBinding.refreshLayout, mAnnouncerTrackAdapter);
    }

    @Override
    public void initData() {
        mViewModel.setAnnouncerId(mAnnouncerId);
        mViewModel.init();
    }

    @Override
    public void initViewObservable() {
        mViewModel.getInitTrackListEvent().observe(this, tracks -> mAnnouncerTrackAdapter.setNewData(tracks));
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        Track track = mAnnouncerTrackAdapter.getItem(position);
        mViewModel.play(track.getAlbum().getAlbumId(), track.getDataId());
    }

    @Override
    public Class<TrackListViewModel> onBindViewModel() {
        return TrackListViewModel.class;
    }

    @Override
    public ViewModelProvider.Factory onBindViewModelFactory() {
        return ViewModelFactory.getInstance(getApplication());
    }

    @Override
    public Callback getInitStatus() {
        return new ListSkeleton();
    }
}
