package com.dream.one.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.dream.one.R;
import com.dream.one.adapter.RecycleViewAdapter;
import com.dream.one.common.Common;
import com.dream.one.model.ArticleItem;
import com.dream.one.service.HttpHelper;
import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.florent37.materialviewpager.adapter.RecyclerViewMaterialAdapter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.Response;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by florentchampigny on 24/04/15.
 */
public class RecyclerViewFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    Handler mHandler;

    private List<ArticleItem> mContentItems = new ArrayList<>();

    private String typeID;

    public RecyclerViewFragment() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public RecyclerViewFragment(int position) {
        this.typeID = Common.classifies[position].id + "";
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static RecyclerViewFragment newInstance(int position) {
        return new RecyclerViewFragment(position);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_recyclerview, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(false);
        final String url = Common.ARTICLE_CLASSIFY_URL + "keyword="
                + "&page=1&showapi_appid=" + Common.APP_ID
                + "&showapi_timestamp=" + new Date().getTime()
                + "&typeId=" + typeID
                + "&showapi_sign=" + Common.SECRET;
        final HttpHelper helper = HttpHelper.getInstance();
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Response response = helper.get(url);
                    String res = response.body().string();
                    JSONObject jsonObject = new JSONObject(res);
                    JSONObject obj1 = jsonObject.getJSONObject("showapi_res_body");
                    JSONObject obj2 = obj1.getJSONObject("pagebean");
                    JSONArray array = obj2.getJSONArray("contentlist");

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<ArticleItem>>() {}.getType();
                    final List<ArticleItem> items = gson.fromJson(array.toString(), type);
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mContentItems = items;
                            mAdapter = new RecyclerViewMaterialAdapter(new RecycleViewAdapter(mContentItems));
                            mRecyclerView.setAdapter(mAdapter);
                            mAdapter.notifyDataSetChanged();
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        MaterialViewPagerHelper.registerRecyclerView(getActivity(), mRecyclerView, null);
    }
}
