package com.example.chatlibrary.chat_v;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.example.chatlibrary.R;

/**
 * Created by jianghw on 2016/8/11.
 * <p/>
 * 描述：
 */
public class SingleChatActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_single_chat);

        getExternalCorresponding();
    }

    private void getExternalCorresponding() {
//        ChatUtils chatUtils = new ChatUtils();
//        chatUtils.setChatInterface(new SingleChat(ChatUtils.avimClient, ChatUtils.control));
//        ChatInterface chatInterface = chatUtils.getChatInterface();
//
//        chatInterface.createConversation(self, other, new AVIMConversationCreatedCallback() {
//            @Override
//            public void done(AVIMConversation avimConversation, AVIMException e) {
//                if (e == null) {
//                    logUtil.i(avimConversation.getConversationId());
//                    SingleChatActivity.this.avimConversation = avimConversation;
//                    logUtil.i("对话创建成功");
//                    messageReceive(SingleChatActivity.this.avimConversation);
//                    refreshData();
//                } else {
//                    logUtil.e(e.getMessage());
//                    Bugtags.sendException(e);
//                }
//            }
//        });
    }


}
