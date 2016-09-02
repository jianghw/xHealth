package com.kaurihealth.kaurihealth.home.util.Abstract;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.PopupMenu;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.commonlibrary.widget.gallery.util.GalleryUtil;
import com.example.commonlibrary.widget.gallery.util.GetUrlsInterface;
import com.example.commonlibrary.widget.util.LoadingUtil;
import com.kaurihealth.utilslib.dialog.SelectDateDialog;
import com.kaurihealth.utilslib.dialog.SuccessInterfaceM;
import com.kaurihealth.datalib.request_bean.bean.MedicalRecordType;
import com.kaurihealth.datalib.request_bean.bean.RecordDocumentDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.NewLabTestPatientRecordDisplayBean;
import com.kaurihealth.datalib.request_bean.bean.PatientRecordDisplayBean;
import com.kaurihealth.kaurihealth.R;
import com.kaurihealth.datalib.request_bean.builder.NewDocumentDisplayBeanBuilder;
import com.kaurihealth.kaurihealth.common.activity.MedicaHistoryActivity;
import com.kaurihealth.kaurihealth.common.util.MedicalHistoryMode;
import com.kaurihealth.kaurihealth.common.util.RecordType;
import com.kaurihealth.kaurihealth.home.adapter.ImagAdapter;
import com.kaurihealth.kaurihealth.home.util.Interface.IEditMedicaHistoryNew;
import com.kaurihealth.kaurihealth.services.ServiceFactory;
import com.kaurihealth.kaurihealth.util.Enums.LoadingStatu;
import com.kaurihealth.kaurihealth.util.Interface.IMedicalRecordController;
import com.kaurihealth.kaurihealth.util.UploadFileUtil;
import com.kaurihealth.kaurihealth.util.Url;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import biz.kasual.materialnumberpicker.MaterialNumberPicker;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by 张磊 on 2016/6/20.
 * 介绍：
 */
public abstract class AbstractEditMedicaHistoryNew implements IEditMedicaHistoryNew {
    protected SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
    protected SimpleDateFormat formatDateToStr = new SimpleDateFormat("yyyy.MM.dd'T'HH:mm:ss");
    protected MedicalHistoryMode mode;
    private final Callback<PatientRecordDisplayBean> callback = new Callback<PatientRecordDisplayBean>() {
        @Override
        public void onResponse(Call<PatientRecordDisplayBean> call, Response<PatientRecordDisplayBean> response) {
            if (response.isSuccessful()) {
                getActivity().setResult(MedicaHistoryActivity.Update);
                commitDataSuccess(response.body());
            } else {
                commitDataFail();
            }
        }

        @Override
        public void onFailure(Call<PatientRecordDisplayBean> call, Throwable t) {
            commitDataFail(t);
        }
    };
    private final ServiceFactory serviceFactory;
    private Context context;
    protected ImagAdapter adapter;
    private LoadingUtil loadingUtil;
    private List<String> localImags = new ArrayList<>();

    public AbstractEditMedicaHistoryNew(Context context) {
        serviceFactory = new ServiceFactory(Url.prefix,context);
        this.context = context;
    }

    @Override
    public void setMode(MedicalHistoryMode mode) {
        switch (mode) {
            case Read:
                setReadMode();
                break;
            case Edit:
                setEditMode();
                break;
        }
    }

    @Override
    public void setEditMode() {
        this.mode = MedicalHistoryMode.Edit;
        getControlTv().setText("完成");
        setAllViewClickable(true);
        setAllEdittextEditable(true);
    }

    @Override
    public void setReadMode() {
        this.mode = MedicalHistoryMode.Read;
        getControlTv().setText("编辑");
        setAllViewClickable(false);
        setAllEdittextEditable(false);
    }

    @Override
    public MedicalHistoryMode getMode() {
        return mode;
    }

    @Override
    public void initUiWhenIsAbleEdit() {
        initUiWhenUnAbleEdit();
        getControlTv().setVisibility(View.VISIBLE);
        getControlTv().setText("编辑");
    }

    @Override
    public void initUiWhenUnAbleEdit() {
        getControlTv().setVisibility(View.GONE);
    }

    private Call<PatientRecordDisplayBean> call;
    NewDocumentDisplayBeanBuilder newDocumentDisplayBeanBuilder = new NewDocumentDisplayBeanBuilder();

    @Override
    public void commitDataToServer(final MedicalRecordType type, final PatientRecordDisplayBean bean) {
        if (!checkDataComplete()) {
            return;
        }
        getLoalImages();
        loadingUtil = LoadingUtil.getInstance(getActivity());
        loadingUtil.show();
        if (localImags.size() != 0) {
            UploadFileUtil uploadFile = getUploadFile();
            uploadFile.startUpload(localImags, new SuccessInterfaceM<String>() {
                @Override
                public void success(String s) {
                    if (!(TextUtils.isEmpty(s))) {
                        List<RecordDocumentDisplayBean> recordDocumentDisplayBeen = null;
                        if (bean.recordDocuments != null) {
                            bean.recordDocuments.addAll(recordDocumentDisplayBeen);
                        } else {
                            bean.recordDocuments = recordDocumentDisplayBeen;
                        }
                    }
                    commitData(type, bean);
                }
            });
        } else {
            commitData(type, bean);
        }
    }

    protected List<String> getLoalImages() {
        localImags.clear();
        Iterator<Object> iterator = imags.iterator();
        while (iterator.hasNext()) {
            Object next = iterator.next();
            if (next instanceof String) {
                localImags.add((String) next);
                iterator.remove();
            }
        }
        return localImags;
    }

    protected boolean check(String iteam, Object object) {
        boolean result = true;
        String hint = iteam + "尚未填写";
        if (iteam != null) {
            if (object instanceof String) {
                if (TextUtils.isEmpty((String) object)) {
                    result = false;
                }
            } else if (object instanceof Integer) {
                if (((Integer) object) == NewLabTestPatientRecordDisplayBean.errorDefault) {
                    result = false;
                }
            } else if (object instanceof List) {
                if (((List) object).size() == 0) {
                    result = false;
                }
            } else {
                if (object == null) {
                    result = false;
                }
            }
        } else {
            result = false;
        }
        if (result == false) {
            showToast(hint);
        }
        return result;
    }

    protected void showToast(String hint) {
        Toast.makeText(getActivity().getApplicationContext(), hint, Toast.LENGTH_SHORT).show();
    }

    private void commitData(MedicalRecordType type, PatientRecordDisplayBean bean) {
        switch (type) {
            case ClinicalDiagnosisAndTreatment:
                call = serviceFactory.getIRecordService().UpdatePatientRecord(bean);
                break;
            case SupplementaryTest:
                call = serviceFactory.getISupplementaryTestService().UpdateSupplementaryTest(bean);
                break;
            case LabTest:
                call = serviceFactory.getILabTestService().UpdateLabTest(bean);
                break;
            case Pathology:
                call = serviceFactory.getIPathologyRecordService().UpdatePathologyRecord(bean);
                break;
        }
        call.enqueue(callback);
    }

    @Override
    public void commitDataSuccess(PatientRecordDisplayBean backData) {
        loadingUtil.dismiss("更新医疗记录成功");
    }

    @Override
    public void commitDataFail() {
        loadingUtil.dismiss("更新医疗记录失败");
    }

    @Override
    public void commitDataFail(Throwable t) {
        loadingUtil.dismiss(LoadingStatu.NetError.value);
    }


    protected List<Object> imags = new ArrayList<>();

    @Override
    public void displayImages() {
        imags.clear();
        IMedicalRecordController controller = getController();
        List<RecordDocumentDisplayBean> images = controller.getImages();
        if (images != null) {
            for (RecordDocumentDisplayBean iteam : images) {
                if (!iteam.isDeleted) {
                    imags.add(iteam);
                }
            }
        }
        if (adapter == null) {
            adapter = new ImagAdapter(context, imags);
            getGridview().setAdapter(adapter);
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void setImageListener() {
        getGridview().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryUtil.toGallery(getActivity(), new GetUrlsInterface() {
                    @Override
                    public ArrayList<String> getUrls() {
                        ArrayList<String> result = new ArrayList();
                        for (Object iteam : imags) {
                            if (iteam instanceof String) {
                                result.add((String) iteam);
                            } else if (iteam instanceof RecordDocumentDisplayBean) {
                                if (!(((RecordDocumentDisplayBean) iteam).isDeleted)) {
                                    result.add(((RecordDocumentDisplayBean) iteam).documentUrl);
                                }
                            }
                        }
                        return result;
                    }
                }, position);
            }
        });
    }

    @Override
    public void setOnIteamLongClickListener() {
        getGridview().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                final PopupMenu popupMenu = new PopupMenu(getActivity(), view);
                Menu menu = popupMenu.getMenu();
                menu.add(Menu.NONE, Menu.FIRST, 0, "删除");
                popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        switch (item.getItemId()) {
                            case Menu.FIRST:
                                Object iteam = imags.get(position);
                                if (iteam instanceof RecordDocumentDisplayBean) {
                                    ((RecordDocumentDisplayBean) iteam).isDeleted = true;
                                }
                                imags.remove(position);
                                adapter.notifyDataSetChanged();
                                break;
                        }
                        return false;
                    }
                });
                popupMenu.show();
                return true;
            }
        });
    }

    @Override
    public void enableIteamLongClick(boolean enAble) {
        getGridview().setLongClickable(enAble);
    }

    @Override
    public boolean checkDataComplete() {
        boolean result = true;
        IMedicalRecordController controller = getController();
        result = result && check("类型", controller.getCategory()) && check("医生姓名", controller.getDoctor()) && check("科室", controller.getDepartmentName()) && check("机构", controller.getHospital());
        return result;
    }

    protected void setAllViewClickable(boolean clickable) {
        Field[] declaredFields = getActivity().getClass().getDeclaredFields();
        for (Field iteam : declaredFields) {
            iteam.setAccessible(true);
            try {
                Object o = iteam.get(getActivity());
                if (o instanceof TextView) {
                    TextView tvTemp = (TextView) o;
                    tvTemp.setClickable(clickable);

                    setBackground(iteam,getActivity(),clickable);

                } else if (o instanceof ImageView) {
                    ((ImageView) o).setClickable(clickable);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    private Drawable selectedDrawable;
    private Drawable unselectedDrawable;

    private void setBackground(Field field, Object object, boolean selectd) {
        boolean canSetBackground = true;
        if (selectedDrawable == null) {
            selectedDrawable = context.getResources().getDrawable(R.drawable.exclusive_et);
        }
        if (unselectedDrawable == null) {
            unselectedDrawable = new ColorDrawable(context.getResources().getColor(android.R.color.transparent));
        }
        field.setAccessible(true);
        RecordType annotation = field.getAnnotation(RecordType.class);
        if (annotation != null) {
            canSetBackground = annotation.cansetBackground();
        }
        if (canSetBackground) {
            try {
                View view = (View) field.get(object);
                if (selectd) {
                    view.setBackgroundResource(R.drawable.exclusive_et);
                } else {
                    view.setBackground(unselectedDrawable);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }

    protected void setAllEdittextEditable(boolean editable) {
        Field[] declaredFields = getActivity().getClass().getDeclaredFields();
        for (Field iteam : declaredFields) {
            iteam.setAccessible(true);
            try {
                Object iteamOne = iteam.get(getActivity());
                if (iteamOne instanceof EditText) {
                    setEdtEditable(((EditText) iteamOne), editable);
                }
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
    }


    protected void setEdtEditable(EditText editText, boolean editable) {
        editText.setFocusableInTouchMode(editable);
        editText.setFocusable(editable);
        if (editable) {
            editText.requestFocus();
        }

    }

    /**
     * 得到需要展示图片的List
     *
     * @return
     */
    protected List<Object> getIvListShow() {
        List<Object> imagShow = new ArrayList<>();
        for (Object object : imags) {
            if (object instanceof RecordDocumentDisplayBean) {
                if (!((RecordDocumentDisplayBean) object).isDeleted) {
                    imagShow.add(object);
                } else {
                    imagShow.add(object);
                }
            }
        }
        return imagShow;
    }

    protected void setNumberPicker(MaterialNumberPicker numberpicker, String[] content) {
        numberpicker.setDisplayedValues(null);
        numberpicker.setMaxValue(content.length - 1);
        numberpicker.setMinValue(0);
        numberpicker.setValue(1);
        numberpicker.setDisplayedValues(content);
    }

    /**
     * 获取时间选择器
     */
    public Dialog getTimeDialog(final SuccessInterfaceM<String> listener) {
        final SelectDateDialog selectDateDialog = new SelectDateDialog(getActivity(), new SelectDateDialog.DialogListener() {
            @Override
            public void onclick(String year, String month, String day) {
                Calendar instance = Calendar.getInstance();
                instance.set(Calendar.YEAR, Integer.valueOf(year));
                instance.set(Calendar.MONTH, Integer.valueOf(month) - 1);
                instance.set(Calendar.DAY_OF_MONTH, Integer.valueOf(day));
                instance.set(Calendar.HOUR_OF_DAY, 0);
                instance.set(Calendar.MINUTE, 0);
                instance.set(Calendar.SECOND, 0);
                getController().editRecordDate(formatDateToStr.format(instance.getTime()));
                listener.success(format.format(instance.getTime()));
            }
        });
        return selectDateDialog.getDataDialog();
    }
}
