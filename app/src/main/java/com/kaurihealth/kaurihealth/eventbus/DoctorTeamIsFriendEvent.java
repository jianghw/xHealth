package com.kaurihealth.kaurihealth.eventbus;

/**
 * Created by Garnet_Wu on 2016/11/7.
 */
public class DoctorTeamIsFriendEvent {
    private   boolean isFriend;

    public DoctorTeamIsFriendEvent(boolean isFriend){
        this.isFriend = isFriend;
    }

    public boolean  getisFriend(){
        return  isFriend;
    }
}

