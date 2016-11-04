package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by jianghw on 2016/11/3.
 * <p/>
 * Describe:
 */

public class ConversationItemBean<T> {

    private final long time;
    private final CharSequence message;
    private final int num;
    private final T bean;
    private final String conversationId;

    public ConversationItemBean(T bean, long time, CharSequence message, int num, String conversationId) {
        this.bean = bean;
        this.time = time;
        this.message = message;
        this.num = num;
        this.conversationId = conversationId;
    }

    public long getTime() {
        return time;
    }

    public CharSequence getMessage() {
        return message;
    }

    public int getNum() {
        return num;
    }

    public T getBean() {
        return bean;
    }

    public String getConversationId() {
        return conversationId;
    }
}
