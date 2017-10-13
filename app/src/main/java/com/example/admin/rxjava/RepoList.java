package com.example.admin.rxjava;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.example.admin.rxjava.data.remote.RemoteDataSource;
import com.example.admin.rxjava.model.Repo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RepoList extends AppCompatActivity {

    RecyclerView rvRepo;
    RecyclerView.LayoutManager layoutManager;
    RepoListAdapter repoListAdapter;
    public static final String TAG = "mainActivity";
    List<Repo> repoList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rvRepo = findViewById(R.id.rvRepo);
        layoutManager = new LinearLayoutManager(this);

        RemoteDataSource.getRepos("myers831")
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .flatMap(new Function<List<Repo>, Observable<Repo>>() {
                    @Override
                    public Observable<Repo> apply(@NonNull final List<Repo> repos) throws Exception {
                        return Observable.create(new ObservableOnSubscribe<Repo>() {
                            @Override
                            public void subscribe(@NonNull ObservableEmitter<Repo> e) throws Exception {

                                for (Repo repo : repos) {
                                    e.onNext(repo);
                                }
                                e.onComplete();

                            }
                        });
                    }
                })
                .map(new Function<Repo, Repo>() {
                    @Override
                    public Repo apply(@NonNull Repo repo) throws Exception {

                        String repoName = repo.getName();
                        repo.setName("My "+ repoName);

                        return repo;
                    }
                })
                .subscribe(new Observer<Repo>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onNext(@NonNull Repo repo) {
                        Log.d(TAG, "onNext: " + repo.getName());
                        repoList.add(repo);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.d(TAG, "onError: " + e.toString());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                        repoListAdapter = new RepoListAdapter(repoList);

                        rvRepo.setLayoutManager(layoutManager);
                        rvRepo.setAdapter(repoListAdapter);
                    }
                });
    }
}
