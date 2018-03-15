package com.example.vadym.starwars.activities;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.vadym.starwars.R;
import com.example.vadym.starwars.model.StarWar;
import com.example.vadym.starwars.recycler.StarWarRecyclerAdapter;
import com.example.vadym.starwars.retrofit.StarWarRetrofit;
import com.example.vadym.starwars.util.BottomSheet;
import com.example.vadym.starwars.util.OnStarWarClickListener;

import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Completable;
import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class MainActivity extends AppCompatActivity implements OnStarWarClickListener {

    @BindView(R.id.recycler)
    RecyclerView recyclerView;
    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    private ActionBar actionbar;
    private TextView textview;
    private LinearLayout.LayoutParams layoutparams;
    private StarWarRecyclerAdapter adapter;
    private CompositeDisposable compositeDisposable;
    private int page = 1;
    private boolean isLoading = true;
    private BottomSheet bottomSheet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSettingsToolbar();

        compositeDisposable = new CompositeDisposable();
        bottomSheet = new BottomSheet();

        LinearLayoutManager manager = new LinearLayoutManager(this);
        manager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(manager);
        adapter = new StarWarRecyclerAdapter();
        adapter.onStarWarClickListener(this);
        recyclerView.setAdapter(adapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                if (isLoading) return;

                int lastVisibleItemCount = manager.findLastVisibleItemPosition();
                int allLoadedItemCount = adapter.getItemCount();
                int loadShouldStartPosition = (int) (allLoadedItemCount * 0.8);

                if (loadShouldStartPosition <= lastVisibleItemCount && page < 9) {
                    page++;
                    isLoading = true;
                }
                if (isLoading)
                    getLoadData(page);
            }
        });

        getLoadData(page);
    }

    private void setSettingsToolbar() {
        actionbar = getSupportActionBar();
        textview = new TextView(getApplicationContext());
        layoutparams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);
        textview.setLayoutParams(layoutparams);
        if (actionbar.getTitle() == null)
            return;

        String title = actionbar.getTitle().toString();
        textview.setText(title);
        textview.setTextSize(20);
        textview.setTextColor(Color.WHITE);
        textview.setGravity(Gravity.CENTER);
        actionbar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionbar.setCustomView(textview);
    }

    @Override
    protected void onDestroy() {
        if (compositeDisposable != null)
            compositeDisposable.clear();

        super.onDestroy();
    }

    private void getLoadData(int page) {

        progressBar.setVisibility(View.VISIBLE);
        Flowable flowable = StarWarRetrofit.getRetrofit().getResponce(page)
                .flatMap(starWarResponce -> Flowable.just(starWarResponce.getListStar()))
                .filter(list -> !list.isEmpty())
                .delay(1, TimeUnit.SECONDS, AndroidSchedulers.mainThread())
                .flatMapCompletable(this::addToAdapter).toFlowable();


        Disposable disposable = flowable.subscribe();

        compositeDisposable.add(disposable);
    }

    private Completable addToAdapter(List<StarWar> list) {
        return Completable.fromAction(() -> {
            adapter.addAllStarWar(list);
            isLoading = false;
            setProgressBarGone();
        });
    }

    private void setProgressBarGone() {
        Completable.timer(1, TimeUnit.SECONDS,
                AndroidSchedulers.mainThread()).subscribe(() -> progressBar.setVisibility(View.GONE));
    }

    @Override
    public void onClick(int position) {

        StarWar starWar = adapter.getStarWar(position);
        if (starWar == null)
            return;

        Bundle bundle = new Bundle();
        bundle.putString(BottomSheet.NAME, starWar.getName());
        bundle.putString(BottomSheet.HEIGHT, starWar.getHeight());
        bundle.putString(BottomSheet.MASS, starWar.getMass());
        bundle.putString(BottomSheet.GENDER, starWar.getGender());
        bundle.putString(BottomSheet.STARSHIPS, String.valueOf(starWar.getStarships().size()));
        bundle.putString(BottomSheet.FILMS, String.valueOf(starWar.getFilms().size()));
        bundle.putString(BottomSheet.VEHICLES, String.valueOf(starWar.getVehicles().size()));
        bundle.putString(BottomSheet.URL, starWar.getUrl());

        bottomSheet.setArguments(bundle);
        bottomSheet.show(getSupportFragmentManager(), bottomSheet.getTag());

    }
}
