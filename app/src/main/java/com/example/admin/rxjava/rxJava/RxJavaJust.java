package com.example.admin.rxjava.rxJava;


import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Admin on 10/12/2017.
 */

public class RxJavaJust {

    public static void main(String[] args) {

        Observable<String> stringObservable = Observable.just("first", "second", "third", "fourth");

        Observer<String> stringObserver = new Observer<String>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe: " + " " + Thread.currentThread() + " " + d.toString());
            }

            @Override
            public void onNext(@NonNull String s) {
                System.out.println("onNext: " + s.toString() + Thread.currentThread());
            }

            @Override
            public void onError(@NonNull Throwable e) {
                System.out.println("onError: " + e.toString());
            }

            @Override
            public void onComplete() {
                System.out.println("onComplete: " + Thread.currentThread());
            }
        };

        stringObservable.observeOn(Schedulers/*.io()*/.newThread())
                .subscribe(stringObserver);
    }
}
