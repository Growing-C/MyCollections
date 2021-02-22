/**
 * Copyright 2016 bingoogolapple
 * <p/>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p/>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.cgy.mycollections.utils;


import org.jetbrains.annotations.NotNull;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxUtil {
    private RxUtil() {
    }

    /**
     * 线程中运行，ui线程中观察
     *
     * @param <T>
     * @return
     */
    public static <T> ObservableTransformer<T, T> applySchedulersJobUI() {
        return new ObservableTransformer<T, T>() {
            @NotNull
            @Override
            public ObservableSource<T> apply(@NotNull Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };

        //和以上的等效
//        return observable -> observable
//                .subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 主线程中运行
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Observable<T> runInUIThread(T t) {
        return Observable.just(t).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 延迟一定时间后主线程运行
     *
     * @param t
     * @param delayMillis
     * @param <T>
     * @return
     */
    public static <T> Observable<T> runInUIThreadDelay(T t, long delayMillis) {
        return Observable.just(t).delaySubscription(delayMillis, TimeUnit.MILLISECONDS, AndroidSchedulers.mainThread());
    }

    public static Observable<Void> runInUIThread() {
        return runInUIThread(null);
    }

    public static Observable<Void> runInUIThreadDelay(long delayMillis) {
        return runInUIThreadDelay(null, delayMillis);
    }


    /**
     * io线程运行
     *
     * @param t
     * @param <T>
     * @return
     */
    public static <T> Observable<T> runInIoThread(T t) {
        return Observable.just(t).observeOn(Schedulers.io());
    }

    public static Observable<Void> runInIoThread() {
        return runInIoThread(null);
    }

    public static <T> Observable<T> runInIoThreadDelay(T t, long delayMillis) {
        return Observable.just(t).delaySubscription(delayMillis, TimeUnit.MILLISECONDS, Schedulers.io());
    }

    public static Observable<Void> runInIoThreadDelay(long delayMillis) {
        return runInIoThreadDelay(null, delayMillis);
    }

//
//    /**
//     * 将RESTResult<T>转换为T
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<RESTResult<T>, T> transformationData() {
//        return restResultObservable -> restResultObservable.flatMap((Func1<RESTResult<T>, Observable<T>>) trestResult -> {
//            Logger.e(trestResult.toString());
//            if (trestResult.getErrCode() == 0) {
//                return Observable.just(trestResult.getResult());
//            } else {
//                return Observable.error(new ApiException(ErrorCode.getMsg(trestResult.getErrCode()), trestResult.getErrCode()));
//            }
//        });
//    }
//
//
//
//
//    /**
//     * 将RESTResult<T>转换为pageList<T>
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<Result<T>, List<T>> transtormationPageList() {
//        return new Observable.Transformer<Result<T>, List<T>>() {
//            @Override
//            public Observable<List<T>> call(Observable<Result<T>> resultObservable) {
//                return resultObservable.flatMap(new Func1<Result<T>, Observable<List<T>>>() {
//                    @Override
//                    public Observable<List<T>> call(Result<T> tResult) {
//                        if (tResult.getTotal() == 0) {
//                            return Observable.error(new NotFoundDataException());
//                        } else if (tResult.getCurrently() > tResult.getTotal()) {
//                            return Observable.error(new NoMoreDataException());
//                        } else {
//                            return Observable.just(tResult.getList());
//                        }
//
//                    }
//                });
//            }
//        };
//    }
//
//
//    /**
//     * 将RESTResult<T>转换为List<T>
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<Result<T>, List<T>> transtormationList() {
//        return new Observable.Transformer<Result<T>, List<T>>() {
//            @Override
//            public Observable<List<T>> call(Observable<Result<T>> resultObservable) {
//                return resultObservable.flatMap(new Func1<Result<T>, Observable<List<T>>>() {
//
//                    @Override
//                    public Observable<List<T>> call(Result<T> tResult) {
//                        if (tResult.getList() == null || tResult.getList().size() == 0) {
//                            return Observable.error(new NotFoundDataException());
//                        } else {
//                            return Observable.just(tResult.getList());
//                        }
//                    }
//                });
//            }
//        };
//    }
//
//
//    /**
//     * 将BaseResult<T>转换为T
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<BaseResult<T>, T> transformationResult() {
//        return restResultObservable -> restResultObservable.flatMap((Func1<BaseResult<T>, Observable<T>>) trestResult -> {
//            Logger.e(trestResult.toString());
//            if (trestResult.getCode() == 1) {
//                return Observable.just(trestResult.getResult());
//            } else {
//                return Observable.error(new ApiException(trestResult.getMsg(), trestResult.getCode()));
//            }
//        });
//    }
//
//
//    /**
//     * 将ListPageResult<T>转换为List<T>
//     *
//     * @param <T>
//     * @return
//     */
//    public static <T> Observable.Transformer<ListPageResult<T>, List<T>> transformationPageListResult() {
//        return new Observable.Transformer<ListPageResult<T>, List<T>>() {
//            @Override
//            public Observable<List<T>> call(Observable<ListPageResult<T>> resultObservable) {
//                return resultObservable.flatMap(new Func1<ListPageResult<T>, Observable<List<T>>>() {
//                    @Override
//                    public Observable<List<T>> call(ListPageResult<T> tResult) {
//                        if (tResult.getPage().getTotal() == 0) {
//                            return Observable.error(new NotFoundDataException());
//                        } else if (tResult.getPage().getPage() > tResult.getPage().getPages()) {
//                            return Observable.error(new NoMoreDataException());
//                        } else {
//                            return Observable.just(tResult.getList());
//                        }
//
//                    }
//                });
//            }
//        };
//    }


}