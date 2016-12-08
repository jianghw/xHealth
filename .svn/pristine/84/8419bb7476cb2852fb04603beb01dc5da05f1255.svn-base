package com.kaurihealth.kaurihealth.dagger.module;

import android.content.Context;

import com.kaurihealth.datalib.local.ILocalSource;
import com.kaurihealth.datalib.local.LocalData;
import com.kaurihealth.datalib.remote.IRemoteSource;
import com.kaurihealth.datalib.remote.RemoteData;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：类里面的方法专门提供依赖
 */
@Module
public class ApplicationModule {
    private final Context mContext;

    public ApplicationModule(Context context) {
        mContext = context;
    }

    @Provides
    Context provideContext() {
        return mContext;
    }

    @Provides
    @Singleton
    IRemoteSource provideRemoteSource(){
        return new RemoteData();
    }

    @Provides
    @Singleton
    ILocalSource provideLocalSource(){
        return new LocalData(mContext);
    }
}
