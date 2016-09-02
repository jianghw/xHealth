package com.kaurihealth.kaurihealth.services;

import com.kaurihealth.datalib.service.IChangePasswordService;
import com.kaurihealth.datalib.service.ICheckVersionService;
import com.kaurihealth.datalib.service.ICreditTransactionService;
import com.kaurihealth.datalib.service.IDepartmentService;
import com.kaurihealth.datalib.service.IDoctorPatientRelationshipService;
import com.kaurihealth.datalib.service.IDoctorRelationshipService;
import com.kaurihealth.datalib.service.IDoctorService;
import com.kaurihealth.datalib.service.IHealthConditionService;
import com.kaurihealth.datalib.service.IHospitalsService;
import com.kaurihealth.datalib.service.ILabTestService;
import com.kaurihealth.datalib.service.ILiteratureCommentService;
import com.kaurihealth.datalib.service.ILiteratureReplyService;
import com.kaurihealth.datalib.service.ILoginService;
import com.kaurihealth.datalib.service.IMedicalLiteratureLikeService;
import com.kaurihealth.datalib.service.IMedicalLiteratureService;
import com.kaurihealth.datalib.service.IPathologyRecordService;
import com.kaurihealth.datalib.service.IPatientRecordService;
import com.kaurihealth.datalib.service.IPatientRequestService;
import com.kaurihealth.datalib.service.IPatientService;
import com.kaurihealth.datalib.service.IPrescriptionService;
import com.kaurihealth.datalib.service.IPriceService;
import com.kaurihealth.datalib.service.IRecordService;
import com.kaurihealth.datalib.service.IRegisterService;
import com.kaurihealth.datalib.service.ISearchService;
import com.kaurihealth.datalib.service.ISupplementaryTestService;
import com.kaurihealth.datalib.service.IUserCashOutAccountService;

/**
 * Created by Nick on 21/04/2016.
 */
public interface IServiceFactory {
    ILoginService getLoginService(String apiEndpoint);

    IHospitalsService getLoadAllHospitalsService();

    IPriceService getUpdateDoctorProductPrice();

    ICreditTransactionService getCreditTransactions();

    IRecordService getAddNewPatientRecord();

    IPrescriptionService getInsertPrescription();

    ICheckVersionService getCheckVersionService();

    IPatientService getPatientService();

    IDoctorPatientRelationshipService getDoctorPatientRelationshipService();

    IHealthConditionService getHealthConditionService();

    IPrescriptionService getPrescriptionService();

    IPatientRequestService getPatientRequestService();

    IRegisterService getRegisterService();

    IDoctorService getDoctorService();

    IDepartmentService getDepartmentService();

    IChangePasswordService getChangePasswordService();

    IPriceService getPriceService();

    ISearchService getSearchService();

    IPatientRecordService getPatientRecordService();

    IRecordService getIRecordService();

    ILabTestService getILabTestService();

    IPathologyRecordService getIPathologyRecordService();

    ISupplementaryTestService getISupplementaryTestService();

    IMedicalLiteratureService getMedicalLiteratureService();

    IMedicalLiteratureLikeService getMedicalLiteratureLikeService();

    ILiteratureReplyService getLiteratureReplyService();

    ILiteratureCommentService getILiteratureCommentService();

    IUserCashOutAccountService getIUserCashOutAccountService();

    IDoctorRelationshipService getIDoctorRelationshipService();
}
