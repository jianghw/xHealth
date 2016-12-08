package com.kaurihealth.datalib.request_bean.bean;

/**
 * Created by jianghw on 2016/11/3.
 * <p/>
 * Describe:
 */

public class ConversationItemBean<T> {

    private final long time;
    private final int num;
    private final T bean;
    private final CharSequence message;
    private final String conversationId;

    public ConversationItemBean(String conversationId, T bean) {
        this(conversationId, "", 0, 0, bean);
    }

    public ConversationItemBean(String conversationId, CharSequence message, long time, int num, T bean) {
        this.conversationId = conversationId;
        this.message = message;
        this.time = time;
        this.num = num;
        this.bean = bean;
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
