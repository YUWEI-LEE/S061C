package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fcbframework.core.application.in.CommandHandler;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateRequest;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.S061Service;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.S061cUserCaseApi;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061RequestCommand;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061ResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.repository.RefundTxnRepository;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;

@Service
@AllArgsConstructor
public class S061CUseCaseImpl extends S061cUserCaseApi implements CommandHandler {

	private final S061Service s061Service;


	//1.S061c get
	//2.compare  version
	//3.check date
	//4.compare reasonable Rate
	//5.update charge fee -> call FxRateService
	//6.compose form msg to mainframe  (FOSGLIF2、FOSTXLS1)
	//6.1 account no update (txn-no)
	//7.S061 update
	//8.response body

	@Override
	public UpdateS061ResponseCommand execute(UpdateS061RequestCommand requestCommand) {

		UpdateS061ResponseCommand responseCommand = new UpdateS061ResponseCommand();
		responseCommand.setReturnCode("0000");
		String txId = requestCommand.getClientHeader().getTxId();
		requestCommand.getClientHeader().setTxId(txId + "*");

		String channel = requestCommand.getCoreHeader().getXCoreChannel();
		requestCommand.getCoreHeader().setXCoreChannel(channel + "*");
		FxRateRequest request = new FxRateRequest();

		//1.S061c get
		RefundTxn refundTxn = s061Service.getRefundTxn(requestCommand.getSeqNo(),requestCommand.getAdviceBranch());

		//2.compare  version
		boolean isVersion= s061Service.checkVersion(refundTxn.getVersion(),requestCommand.getVersion());

		//3.check date
		boolean isDate = s061Service.checkDate(refundTxn.getProcessDate(),requestCommand.getProcessDate());

		//4.compare reasonable Rate
		boolean isReasonableRate = s061Service.checkReasonableFxRate(requestCommand.getSpotRate());

		//5.update charge fee -> call FxRateService
		s061Service.getToUsdRate(request);

		if(isVersion && isDate && isReasonableRate){

		}

		return responseCommand;
	}



}
