package com.kaurihealth.mvplib.search_p;

import com.kaurihealth.datalib.repository.IDataSource;

import javax.inject.Inject;

import rx.subscriptions.CompositeSubscription;

/**
 * Created by Garnet_Wu on 2016/8/30.
 */
public class BlankMedicinePresenter<V> implements  IBlankMedicinePresenter<V> {

  private  final IDataSource mRepository;
  private final CompositeSubscription mSubscriptions;
  private  IBlankMedicineView mFragment;
 @Inject
  public BlankMedicinePresenter(IDataSource repository){
    mRepository =  repository;
    mSubscriptions = new CompositeSubscription();
  }


 //IMvpPresenter
   @Override
   public void setPresenter(V view) {
    mFragment = (IBlankMedicineView)view;
 }

 //IMvpPresenter
    @Override
    public void onSubscribe() {

    }
    //IMvpPresenter
    @Override
    public void unSubscribe() {

    }
}
