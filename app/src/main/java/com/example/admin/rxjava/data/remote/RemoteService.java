package com.example.admin.rxjava.data.remote;

import com.example.admin.rxjava.model.Repo;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by Admin on 10/12/2017.
 */

public interface RemoteService {

    @GET("users/{user}/repos")
    Observable<List<Repo>> getRepos(@Path("user") String username);
}
