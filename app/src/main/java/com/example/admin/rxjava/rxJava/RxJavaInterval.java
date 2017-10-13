package com.example.admin.rxjava.rxJava;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;

/**
 * Created by Admin on 10/12/2017.
 */

public class RxJavaInterval {

    public static void main(String[] args) throws InterruptedException{
        Observable<Long> longObservable = Observable.interval(1, TimeUnit.SECONDS);


        Observer<Long> longobserver = new Observer<Long>() {
            @Override
            public void onSubscribe(@NonNull Disposable d) {
                System.out.println("onSubscribe: " + " " + Thread.currentThread() + " " + d.toString());
            }

            @Override
            public void onNext(@NonNull Long aLong) {
                System.out.println("onNext: " + " " + Thread.currentThread() + " " + aLong);
            }

            @Override
            public void onError(@NonNull Throwable e) {

            }

            @Override
            public void onComplete() {
                System.out.println("onNext: " + " " + Thread.currentThread());
            }
        };

        longObservable.subscribe(longobserver);
        Thread.sleep(5000);
    }
}
