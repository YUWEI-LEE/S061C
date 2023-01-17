package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.impl;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fcbframework.core.application.in.CommandHandler;
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

		String txId = requestCommand.getClientHeader().getTxId();
		requestCommand.getClientHeader().setTxId(txId + "*");

		String channel = requestCommand.getCoreHeader().getXCoreChannel();
		requestCommand.getCoreHeader().setXCoreChannel(channel + "*");

		//1.S061c get
		RefundTxn refundTxn = s061Service.getRefundTxn(requestCommand.getSeqNo(),requestCommand.getAdviceBranch());

		//2.compare  version
		boolean isPass= s061Service.checkVersion(refundTxn.getVersion(),requestCommand.getVersion());

		return null;
	}



}
