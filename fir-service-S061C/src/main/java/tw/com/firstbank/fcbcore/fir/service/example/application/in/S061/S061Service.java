package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fcbframework.core.application.exception.BusinessException;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateResponse;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameResponse;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.mainframe.api.MainframeServiceApi;
import tw.com.firstbank.fcbcore.fir.service.example.application.exception.ServiceStatusCode;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.repository.RefundTxnRepository;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;
import tw.com.firstbank.fcbcore.fir.service.example.domain.S061Report;



@Service
@AllArgsConstructor
public class S061Service {

	private static final Logger log = LoggerFactory.getLogger(S061Service.class);

	private final RefundTxnRepository refundTxnRepository;

	private final MainframeServiceApi mainframeService;


	public RefundTxn getRefundTxn(String seqNo, String adviceBranch){
		RefundTxn refundTxn;
		Optional<RefundTxn> refundTxnOptional =  refundTxnRepository.getS061BySeqNoAndAdviceBranch(seqNo,adviceBranch);
		if(refundTxnOptional.isEmpty()){
			throw new BusinessException(ServiceStatusCode.DATA_NOT_FOUND.getCode(),ServiceStatusCode.DATA_NOT_FOUND.getMessage());
		}else{
			refundTxn = refundTxnOptional.get();
			return refundTxn;
		}
	}

	public boolean checkVersion(String refundTxnVersion,String commandVersion){

		if(!commandVersion.equals(refundTxnVersion)){
			throw new BusinessException(ServiceStatusCode.INVALID_VERSION.getCode(),ServiceStatusCode.INVALID_VERSION.getMessage());
		}

		return true;
	}

	public boolean checkDate(String refundTxnProcessDate,String commandProcessDate){

		if(!commandProcessDate.equals(refundTxnProcessDate)){
			throw new BusinessException(ServiceStatusCode.INVALID_DATE.getCode(),ServiceStatusCode.INVALID_DATE.getMessage());
		}

		return true;
	}

	public boolean checkReasonableFxRate(BigDecimal spotRate){

		FxRateRequest fxRateRequest = new FxRateRequest();
		fxRateRequest.setFxRate(spotRate);
		FxRateResponse fxRateResponse = mainframeService.isReasonableFxRate(fxRateRequest);
		if (fxRateResponse.getReturnCode().equals(ServiceStatusCode.SUCCESS.getCode())){
			return true;
		}else if(fxRateResponse.getReturnCode().equals(ServiceStatusCode.OVER_FXRATE.getCode())){
			return false;
		}else {
			return false;
		}
	}


	public BigDecimal getToUsdRate(FxRateRequest fxRateRequest){

		FxRateResponse fxRateResponse = mainframeService.getToUsdRate(fxRateRequest);
		if (fxRateResponse.getReturnCode().equals(ServiceStatusCode.SUCCESS.getCode())){
			return fxRateResponse.getToUsdRate();
		}else {
			return BigDecimal.ZERO;
		}
	}

	public MainFrameResponse mainframeIO(MainFrameRequest mainFrameRequest) throws Exception {
		MainFrameResponse mainFrameResponse = mainframeService.mainframeIO(mainFrameRequest);
		return mainFrameResponse;
	}

	public void updateRefundTxn(RefundTxn refundTxn) throws Exception {
		try{
			RefundTxn refundTxnOutput = refundTxnRepository.save(refundTxn);
		}catch (Exception ex){
			log.error("儲存資料庫失敗",ex);
			throw new RuntimeException("DataBaseError");

		}

	}

	public S061Report print(){
		S061Report s061Report = new S061Report();
		Date date = new Date();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String sdate= dateFormat.format(date);
		s061Report.setReportDate(sdate);
		s061Report.setReportOperator("160474");

		return s061Report;
	}

}
