package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061;

import java.util.Optional;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fcbframework.core.application.exception.BusinessException;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api.UpdateS061Request;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api.UpdateS061Response;
import tw.com.firstbank.fcbcore.fir.service.example.application.exception.ServiceStatusCode;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061RequestCommand;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.repository.RefundTxnRepository;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;



@Service
@AllArgsConstructor
public class S061Service {

	private static final Logger log = LoggerFactory.getLogger(S061Service.class);

	private final RefundTxnRepository refundTxnRepository;


	public RefundTxn getRefundTxn(String seqNo, String adviceBranch){
		RefundTxn res=null;
		RefundTxn refundTxn =  refundTxnRepository.getS061BySeqNoAndAdviceBranch(seqNo,adviceBranch);
//		if(!refundTxn.isEmpty()){
//			res = refundTxn.get();
//		}
		return refundTxn;
	}

	public Boolean checkVersion(UpdateS061RequestCommand updateS061RequestCommand){

		RefundTxn refundTxn= getRefundTxn(updateS061RequestCommand.getSeqNo(),updateS061RequestCommand.getAdviceBranch());
		if(!updateS061RequestCommand.getVersion().equals(refundTxn.getVersion())){
			throw new BusinessException(ServiceStatusCode.INVALID_VERSION.getCode(),"版本別錯誤" );
		}

		return true;
	}





}
