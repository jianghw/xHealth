package com.kaurihealth.chatlib.utils;

/**
 * Created by wli on 16/2/29.
 * 所有常量值均放到此类里边
 */
public class LCIMConstants {

    public static final String APP_ID_DEBUG = "IzQV1fXOQ4zXOhKLU9bUCwuu-gzGzoHsz";
    public static final String APP_KEY_DEBUG = "6xHXLQP3JAgfhean6jINUSWK";

    public static final String APP_ID_PREVIEW = "syrS01Qb3GwHqOgKk96VdEB9-gzGzoHsz";
    public static final String APP_KEY_PREVIEW = "4vfho7gjd6Di68mP7KRtwuoA";

    private static final String LEANMESSAGE_CONSTANTS_PREFIX = "com.kaurihealth.chatlib.";

    private static String getPrefixConstant(String str) {
        return LEANMESSAGE_CONSTANTS_PREFIX + str;
    }

    /**
     * 参数传递的 key 值，表示对方的 id，跳转到 LCIMConversationActivity 时可以设置
     */
    public static final String PEER_ID = getPrefixConstant("peer_id");

    public static final String PEER_ID_GROUP = getPrefixConstant("peer_id_group");

    /**
     * 参数传递的 key 值，表示回话 id，跳转到 LCIMConversationActivity 时可以设置
     */
    public static final String CONVERSATION_ID = getPrefixConstant("conversation_id");

    public static final String CONVERSATION_ID_GROUP = getPrefixConstant("conversation_id_group");

    public static final String CONVERSATION_ID_DETAIL_GROUP = getPrefixConstant("conversation_id_detail_group");

    public static final String CONVERSATION_ID_DETAIL_DIALOGUE = getPrefixConstant("conversation_id_detail_dialogue");

    public static final String CONVERSATION_ID_REFERRAL_PATIENT = getPrefixConstant("conversation_id_referral_patient");

    /**
     * LCIMConversationActivity 中头像点击事件发送的 action
     */
    public static final String AVATAR_CLICK_ACTION = getPrefixConstant("avatar_click_action");

    /**
     * LCIMConversationListFragment item 点击事件
     * 如果开发者不想跳转到 LCIMConversationActivity，可以在 Mainfest 里接管该事件
     */
    public static final String CONVERSATION_ITEM_CLICK_ACTION = getPrefixConstant("conversation_item_click_action");

    public static final String CONVERSATION_ITEM_CLICK_ACTION_GROUP = getPrefixConstant("conversation_item_click_action_group");//群聊界面

    public static final String CONVERSATION_ITEM_DETAIL_ACTION_GROUP = getPrefixConstant("conversation_item_detail_action_group");//详情页面
    public static final String CONVERSATION_ITEM_DETAIL_ACTION_DIALOGUE = getPrefixConstant("conversation_item_detail_action_dialogue");//会话详情页面


    public static final String CONVERSATION_ITEM_DETAIL_ACTION_REFERRAL_DOCTOR = getPrefixConstant("conversation_item_detail_action_referral_doctor");//会话详情页面
    public static final String CONVERSATION_ITEM_DETAIL_ACTION_REFERRAL_PATIENT = getPrefixConstant("conversation_item_detail_action_referral_Patient");//会话详情页面

    public static final String LCIM_LOG_TAG = getPrefixConstant("lcim_log_tag");


    // LCIMImageActivity
    public static final String IMAGE_LOCAL_PATH = getPrefixConstant("image_local_path");
    public static final String IMAGE_URL = getPrefixConstant("image_url");

    public static final String CHAT_NOTIFICATION_ACTION = getPrefixConstant("chat_notification_action");
}
