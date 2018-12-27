package com.code.lengjiye.retrofit.observer;

import android.os.Handler;
import android.os.Looper;

import com.code.lengjiye.app.AppMaster;
import com.code.lengjiye.mvp.view.MvpView;
import com.code.lengjiye.retrofit.ApiException;
import com.code.lengjiye.retrofit.R;
import com.code.lengjiye.retrofit.constant.RetrofitErr;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.lengjiye.tools.LogTool;
import com.lengjiye.tools.NetWorkTool;
import com.lengjiye.tools.ResTool;
import com.lengjiye.tools.StringTool;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.ResponseBody;
import retrofit2.HttpException;


/**
 * 请求回调统一处理
 * <p>
 * 显示默认dialog
 *
 * @param <T>
 */
public class LoadingObserver<T> implements Observer<T> {

    /**
     * true 显示load
     */
    protected boolean isShowLoad;
    protected MvpView mMvpView;
    protected ObserverOnNextListener<T> mOnNextListener;
    protected ObserverOnErrorListener mOnErrorListener;
    protected ObserverOnLoadingListener mOnLoadingListener;

    protected Disposable mDisposable;

    public LoadingObserver(MvpView mvpView, ObserverOnNextListener<T> onNextListener, ObserverOnErrorListener onErrorListener) {
        this.mMvpView = mvpView;
        this.mOnNextListener = onNextListener;
        this.mOnErrorListener = onErrorListener;
        isShowLoad = true;
    }

    public LoadingObserver(MvpView mvpView, ObserverOnNextListener<T> onNextListener, ObserverOnErrorListener onErrorListener, boolean isShowLoad) {
        this.mMvpView = mvpView;
        this.mOnNextListener = onNextListener;
        this.mOnErrorListener = onErrorListener;
        this.isShowLoad = isShowLoad;
    }

    /**
     * 设置loading监听
     *
     * @param onLoadingListener
     */
    public void setOnLoadingListener(ObserverOnLoadingListener onLoadingListener) {
        mOnLoadingListener = onLoadingListener;
    }

    @Override
    public void onSubscribe(Disposable d) {
        mDisposable = d;

        if (mMvpView != null && mMvpView.isAlived()) {

            if (!NetWorkTool.isConnected()) {
                String errorMsg = ResTool.getString(mMvpView.getContext().getApplicationContext(), R.string.a_0210);

                ApiException apiException = new ApiException(-10001, errorMsg, null);
                //如果设置了监听，通过监听处理
                if (mOnErrorListener != null) {
                    mOnErrorListener.observerOnError(apiException);
                } else {
                    mMvpView.showError(ResTool.getString(mMvpView.getContext().getApplicationContext(), R.string.a_0210));
                }

                mDisposable.dispose();
            } else {
                //显示loading
                if (mOnLoadingListener != null) {
                    mOnLoadingListener.onShowLoading();
                } else {
                    if (isShowLoad) {
                        mMvpView.showLoading();
                    }
                }
            }
        }
    }

    @Override
    public void onNext(T value) {
        if (mMvpView != null && mMvpView.isAlived()) {
            if (mOnLoadingListener != null) {
                mOnLoadingListener.onDismissLoading();
            } else {
                if (isShowLoad) {
                    mMvpView.dismissLoading();
                }
            }
        }
        if (mOnNextListener != null) {
            mOnNextListener.observerOnNext(value);
        }
    }

    @Override
    public void onError(Throwable e) {
        if (mMvpView != null && mMvpView.isAlived()) {
            if (mOnLoadingListener != null) {
                mOnLoadingListener.onDismissLoading();
            } else {
                if (isShowLoad) {
                    mMvpView.dismissLoading();
                }
            }
        }

        ApiException apiException = null;

        int errorCode = 0;
        String errorMsg = "";
        if (e instanceof NullPointerException) {
            if (mOnNextListener != null) {
                mOnNextListener.observerOnNext(null);
                return;
            }
        } else if (e instanceof SocketTimeoutException) {  //请求超时
            if (null != AppMaster.getInstance().getAppContext()) {
                errorMsg = AppMaster.getInstance().getAppContext().getString(R.string.a_0977);
            }
            apiException = new ApiException(-10001, errorMsg, null);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {     //网络连接出错
            if (null != AppMaster.getInstance().getAppContext()) {
                errorMsg = AppMaster.getInstance().getAppContext().getString(R.string.a_1439);
            }
            apiException = new ApiException(-10002, errorMsg, null);
        } else if (e instanceof HttpException) {
            String reponseStr = null;
            if (((HttpException) e).response() != null && ((HttpException) e).response().errorBody() != null) {
                ResponseBody responseBody = ((HttpException) e).response().errorBody();
                if (responseBody != null) {
                    try {
                        reponseStr = responseBody.string();
                    } catch (IOException e1) {
                    }
                }
            }
            apiException = interceptStatusCode(reponseStr);
            if (apiException == null) {
                errorMsg = e.getMessage();
                apiException = new ApiException(-10000, e.getMessage(), null);
            } else {
                errorMsg = apiException.getErrorMsg();
            }
        } else if (e instanceof JsonSyntaxException) {
            errorMsg = e.getMessage();
            apiException = new ApiException(-10003, errorMsg, null);
            errorCode = apiException.getErrorCode();
        } else if (e instanceof ApiException) {
            apiException = ((ApiException) e);
            errorCode = apiException.getErrorCode();
            errorMsg = apiException.getErrorMsg();
            String reponseStr = null;

            if (apiException.getData() != null) {
                reponseStr = apiException.getData().toString();
            }
            apiException = interceptStatusCode(reponseStr);
        } else {
            if ("product".equals(AppMaster.getInstance().getBuildType())) {
                if (null != AppMaster.getInstance().getAppContext()) {
                    errorMsg = AppMaster.getInstance().getAppContext().getString(R.string.a_0014);
                    apiException = new ApiException(-10000, errorMsg, null);
                }
            } else {
                errorMsg = e.getMessage();
                apiException = new ApiException(-10000, errorMsg, null);
            }
        }
        // 报错错误信息，方便以后找错
//        saveErrorLog(errorCode, e.getMessage(), e);

        //如果设置了监听，通过监听处理
        if (apiException != null && mOnErrorListener != null) {
            mOnErrorListener.observerOnError(apiException);
        }

        /**
         * 如果设置了错误监听，通过错误监听处理，否则按照默认处理
         */
        if (mMvpView != null && mMvpView.isAlived()) {
            if (errorCode == RetrofitErr.TOKEN_OVERDUE || errorCode == RetrofitErr.TOKEN_FAIL || errorCode == RetrofitErr.SENSITIVE_WORD_ERROR) {
                LogTool.e("token 失效");
                return;
            }
            mMvpView.showError(errorMsg);
        }
    }

    @Override
    public void onComplete() {
        if (mMvpView != null && mMvpView.isAlived()) {
            if (mOnLoadingListener != null) {
                mOnLoadingListener.onDismissLoading();
            } else {
                if (isShowLoad) {
                    mMvpView.dismissLoading();
                }
            }
        }
    }


    /**
     * <p>
     * 目的是为了拦截服务器返回的错误code来做事情
     * <p>
     * 注意：
     * 注意：
     * 注意：
     * 注意：
     * 注意：
     * 所有拦截服务器错误码的地方都在这里拦截
     * <p>
     * 应该有一个统一的地方回调错误码，此处可以不用
     *
     * @param reponseStr
     */
    private synchronized ApiException interceptStatusCode(String reponseStr) {

        if (StringTool.isEmpty(reponseStr)) {
            return null;
        }
        JsonObject json = null;
        try {
            json = new JsonParser().parse(reponseStr).getAsJsonObject();
        } catch (Exception e1) {
        }
        if (json == null) {
            return null;
        }
        int statusCode;
        String errorMsg = null;
        if (json.get("statusCode") != null) {
            statusCode = json.get("statusCode").getAsInt();
            if (json.get("msg") != null) {
                String msg = json.get("msg").getAsString();
                if (!StringTool.isEmpty(msg)) {
                    errorMsg = msg;
                }
            }


            switch (statusCode) {
                // Token失效
                case RetrofitErr.TOKEN_OVERDUE:
                    new Handler(Looper.getMainLooper()).post(() -> {
//                        BroadcastCenter.getInstance().action(ActionHolder.ACTION_TOKEN_OVERDUE).broadcast();
//                        IntentManager.get().target(AppMaster.getInstance().getAppContext(), IntentTargetMapKey.TARGET_REGISTER_AND_LOGIN)
//                                .startActivity(AppMaster.getInstance().getAppContext());
                    });
                    break;
                // 异地登录
                case RetrofitErr.TOKEN_FAIL:
                    new Handler(Looper.getMainLooper()).post(() -> {
//                        BroadcastCenter.getInstance().action(ActionHolder.ACTION_TOKEN_FAIL).broadcast();
//                        IntentManager.get().target(AppMaster.getInstance().getAppContext(), IntentTargetMapKey.TARGET_REGISTER_AND_LOGIN)
//                                .startActivity(AppMaster.getInstance().getAppContext());
                    });
                    break;

                // 发了敏感词被删号
                case RetrofitErr.SENSITIVE_WORD_ERROR:
                    new Handler(Looper.getMainLooper()).post(() -> {
//                        BroadcastCenter.getInstance().action(ActionHolder.ACTION_KICK_OFF).broadcast();
                    });
                    break;

                default:
                    if (StringTool.isEmpty(errorMsg)) {
                        return null;
                    }
                    return new ApiException(statusCode, errorMsg, null);
            }

        }
        return null;
    }

    public void cancelRequest() {
        if (mMvpView != null && mMvpView.isAlived()) {
            if (mOnLoadingListener != null) {
                mOnLoadingListener.onDismissLoading();
            } else {
                if (isShowLoad) {
                    mMvpView.dismissLoading();
                }
            }
        }

        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
    }

    public interface ObserverOnNextListener<T> {
        void observerOnNext(T value);
    }

    public interface ObserverOnErrorListener {
        void observerOnError(ApiException e);
    }

    public interface ObserverOnLoadingListener {
        void onShowLoading();

        void onDismissLoading();
    }

//    /**
//     * 报错错误日志
//     */
//    private void saveErrorLog(int errorCode, String errorMessage, Throwable throwable) {
//        String defaultDir;
//        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
//                && ContextHolder.getContext().getExternalCacheDir() != null)
//            defaultDir = ContextHolder.getContext().getExternalCacheDir() + File.separator + "http" + File.separator;
//        else {
//            defaultDir = ContextHolder.getContext().getCacheDir() + File.separator + "http" + File.separator;
//        }
//
//        String filePath = defaultDir + DateUtil.getCurrTime("MM-dd HH-mm-ss") + ".txt";
//        if (!createOrExistsFile(filePath)) return;
//        new Thread(() -> {
//            PrintWriter pw = null;
//            try {
//                pw = new PrintWriter(new FileWriter(filePath, false));
//                String log = " 【" + DateUtil.getSystemData() + "】 errorCode:" + errorCode +
//                        "\r\n" + "errorMessage:" + errorMessage + "\r\n" + "throwable:" + throwable.toString();
//                pw.write(log);
//            } catch (IOException e) {
//                e.printStackTrace();
//            } finally {
//                if (pw != null) {
//                    pw.close();
//                }
//            }
//        }).start();
//    }
}
