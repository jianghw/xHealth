package com.kaurihealth.kaurihealth.conversation_v;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.avos.avoscloud.im.v2.AVIMClient;
import com.avos.avoscloud.im.v2.AVIMConversation;
import com.avos.avoscloud.im.v2.AVIMException;
import com.avos.avoscloud.im.v2.callback.AVIMConversationCallback;
import com.kaurihealth.chatlib.LCChatKit;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.kaurihealth.base_v.BaseActivity;
import com.kaurihealth.utilslib.ValidatorUtils;
import com.kaurihealth.utilslib.constant.Global;
import com.mobsandgeeks.saripaar.ValidationError;
import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.NotEmpty;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;


/**
 * 医生个人信息界面
 */
public class ChangGroupChatTitleActivity extends BaseActivity implements Validator.ValidationListener {
    @Bind(R.id.tv_more)
    TextView tvMore;

    @NotEmpty(message = "群聊名不能为空")
    @Length(min = 1, max = 20, message = "最好不用超过20个字或不能为空")
    @Bind(R.id.edtCertificationNumber)
    EditText edtCertificationNumber;

    @Bind(R.id.ivDelete)
    ImageView ivDelete;

    private Validator mValidator;
    private String conversationId;

    @Override
    protected int getActivityLayoutID() {
        return R.layout.activity_group_chat_title;
    }

    @Override
    protected void initPresenterAndView(Bundle savedInstanceState) {
        initNewBackBtn("群聊名称");
        tvMore.setText(getString(R.string.title_save));

        mValidator = new Validator(this);
        mValidator.setValidationListener(this);
    }

    @Override
    protected void initDelayedData() {
        Bundle bundle = getBundle();
        if (bundle != null) {
            String name = bundle.getString(Global.Bundle.LEANCLOUD_GROUP_NAME);
            conversationId = bundle.getString(Global.Bundle.LEANCLOUD_GROUP_CONID);
            edtCertificationNumber.setText(name);
        }
    }

    @OnClick({R.id.tv_more, R.id.ivDelete})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_more:
                mValidator.validate();
                break;
            case R.id.ivDelete:
                deleteEditText();
                break;
            default:
                break;
        }
    }

    //清除执业证书编号
    private void deleteEditText() {
        clearTextView(edtCertificationNumber);
    }

    @Override
    public void onValidationSucceeded() {
        AVIMClient client = LCChatKit.getInstance().getClient();
        if (!TextUtils.isEmpty(conversationId) && client != null) {
            dataInteractionDialog();
            AVIMConversation conv = client.getConversation(conversationId);
            conv.setName(edtCertificationNumber.getText().toString().trim());
            conv.updateInfoInBackground(new AVIMConversationCallback() {
                @Override
                public void done(AVIMException e) {
                    if (e == null) {
                        Intent intent = new Intent();
                        intent.putExtra(Global.Bundle.LEANCLOUD_GROUP_NAME, edtCertificationNumber.getText().toString().trim());
                        setResult(RESULT_OK, intent);
                        showToast("修改成功");
                        dismissInteractionDialog();
                        finishCur();
                    } else {
                        displayErrorDialog(e.getMessage());
                    }
                }
            });
        } else {
            displayErrorDialog("conversationId加载出错,请退出当前页面");
        }
    }

    @Override
    public void onValidationFailed(List<ValidationError> errors) {
        String message = ValidatorUtils.validationErrorMessage(getApplicationContext(), errors);
        displayErrorDialog(message);
    }
}
