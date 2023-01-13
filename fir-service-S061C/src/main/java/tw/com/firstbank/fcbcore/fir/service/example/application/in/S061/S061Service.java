package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061;

import java.math.BigDecimal;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fcbframework.core.application.exception.BusinessException;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateResponse;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainframeService;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.impl.MainframeServiceImpl;
import tw.com.firstbank.fcbcore.fir.service.example.application.exception.ServiceStatusCode;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061RequestCommand;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.repository.RefundTxnRepository;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;



@Service
@AllArgsConstructor
public class S061Service {

	private static final Logger log = LoggerFactory.getLogger(S061Service.class);

	private final RefundTxnRepository refundTxnRepository;

	private final MainframeService mainframeService;




	public RefundTxn getRefundTxn(String seqNo, String adviceBranch){
		Optional<RefundTxn> refundTxnOptional =  refundTxnRepository.getS061BySeqNoAndAdviceBranch(seqNo,adviceBranch);
		RefundTxn refundTxn= null;
		if(refundTxnOptional.isEmpty()){
			throw new BusinessException(ServiceStatusCode.DATA_NOT_FOUND.getCode(),ServiceStatusCode.DATA_NOT_FOUND.getMessage());
		}else{
			refundTxn = refundTxnOptional.get();
		}
		return refundTxn;
	}

	public boolean checkVersion(UpdateS061RequestCommand updateS061RequestCommand){

		RefundTxn refundTxn= getRefundTxn(updateS061RequestCommand.getSeqNo(),updateS061RequestCommand.getAdviceBranch());
		if(!updateS061RequestCommand.getVersion().equals(refundTxn.getVersion())){
			throw new BusinessException(ServiceStatusCode.INVALID_VERSION.getCode(),ServiceStatusCode.INVALID_VERSION.getMessage());
		}

		return true;
	}

	public boolean checkDate(UpdateS061RequestCommand updateS061RequestCommand){

		RefundTxn refundTxn= getRefundTxn(updateS061RequestCommand.getSeqNo(),updateS061RequestCommand.getAdviceBranch());
		if(!updateS061RequestCommand.getProcessDate().equals(refundTxn.getProcessDate())){
			throw new BusinessException(ServiceStatusCode.INVALID_DATE.getCode(),ServiceStatusCode.INVALID_DATE.getMessage());
		}

		return true;
	}

	public boolean checkReasonableFxRate(UpdateS061RequestCommand updateS061RequestCommand){

		FxRateRequest fxRateRequest = new FxRateRequest();
		fxRateRequest.setFxRate(updateS061RequestCommand.getSpotRate());
		FxRateResponse fxRateResponse = mainframeService.isReasonableFxRate(fxRateRequest);
		if (fxRateResponse.getReturnCode().equals(ServiceStatusCode.SUCCESS.getCode())){
			return true;
		}else if(fxRateResponse.getReturnCode().equals(ServiceStatusCode.OVER_FXRATE.getCode())){
			return false;
		}else {
			return false;
		}
	}


	public boolean updateRefundTxn(RefundTxn refundTxn) throws Exception {

		boolean isSaveSuccess = false;
		try{
			RefundTxn refundTxnOutput = refundTxnRepository.save(refundTxn);
			isSaveSuccess = true;
		}catch (Exception ex){
			log.error("儲存資料庫失敗",ex);
			throw new RuntimeException("DataBaseError");

		}

		return isSaveSuccess;
	}

	public BigDecimal getFxRate(UpdateS061RequestCommand updateS061RequestCommand){
		FxRateRequest fxRateRequest = new FxRateRequest();
		fxRateRequest.setCurrencyCode(updateS061RequestCommand.getCurrencyCode());
		FxRateResponse fxRateResponse = mainframeService.getFxRate(fxRateRequest);
		if (fxRateResponse.getReturnCode().equals(ServiceStatusCode.SUCCESS.getCode())){
			return fxRateResponse.getFxRate();
		}else {
			return BigDecimal.ZERO;
		}
	}
}
