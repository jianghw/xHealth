package com.kaurihealth.kaurihealth.dagger.module;

import com.kaurihealth.datalib.repository.IDataSource;
import com.kaurihealth.datalib.repository.Repository;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by jianghw on 2016/8/12.
 * <p/>
 * 描述：此类中的方法专门提供依赖
 */
@Module
public class RepositoryModule {

    @Provides
    @Singleton
    IDataSource provideSource(Repository repository) {
        return repository;
    }
}
