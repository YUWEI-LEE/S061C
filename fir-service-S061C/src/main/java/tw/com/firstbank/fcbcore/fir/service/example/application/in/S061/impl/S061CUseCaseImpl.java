package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.impl;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fcbframework.core.application.in.CommandHandler;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameResponse;
import tw.com.firstbank.fcbcore.fir.service.example.application.exception.ServiceStatusCode;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.S061Service;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.S061cUseCaseApi;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061RequestCommand;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061ResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;
import tw.com.firstbank.fcbcore.fir.service.example.domain.S061Report;

@Service
@AllArgsConstructor
public class S061CUseCaseImpl extends S061cUseCaseApi implements CommandHandler {

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

		//1.S061c get
		RefundTxn refundTxn = s061Service.getRefundTxn(requestCommand.getSeqNo(),requestCommand.getAdviceBranch());

		//2.compare  version
		boolean isVersion= s061Service.checkVersion(refundTxn.getVersion(),requestCommand.getVersion());

		//3.check date
		boolean isDate = s061Service.checkDate(refundTxn.getProcessDate(),requestCommand.getProcessDate());

		//4.compare reasonable Rate
		boolean isReasonableRate = s061Service.checkReasonableFxRate(requestCommand.getSpotRate());

		FxRateRequest fxRateRequest = new FxRateRequest();
		fxRateRequest.setCurrencyCode(requestCommand.getCurrencyCode());
		fxRateRequest.setProcessDate(requestCommand.getProcessDate());
		fxRateRequest.setFxRate(requestCommand.getSpotRate());
		//5.update charge fee -> call FxRateService
		s061Service.getToUsdRate(fxRateRequest);

		//6.compose form msg to mainframe  (FOSGLIF2、FOSTXLS1)
		//6.1 account no update (txn-no)
		MainFrameResponse mainFrameResponse = new MainFrameResponse();
		if(isVersion && isDate && isReasonableRate){
			try {
				MainFrameRequest mainFrameRequest = new MainFrameRequest();
				mainFrameRequest.setData(requestCommand.getSeqNo());
				mainFrameResponse = s061Service.mainframeIO(mainFrameRequest);
				if (mainFrameResponse.getReturnCode().equals(ServiceStatusCode.SUCCESS.getCode())){
					refundTxn.setTxnNo(mainFrameResponse.getTxnNo());
				}
			} catch (Exception e) {
				throw new RuntimeException(e);
			}
		}

		//7.S061 update
		try {
			s061Service.updateRefundTxn(refundTxn);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

		//8.response body
//		S061Report s061Report = new S061Report();

		try {
//			responseCommand.setReturnMessage(s061Service.print().toString());
			List<S061Report> s061ReportList = new ArrayList<>() ;
			s061ReportList.add(s061Service.print());
			responseCommand.setS016ReportList(s061ReportList);
		} catch (Exception e) {
			responseCommand.setReturnCode("9900");
			responseCommand.setReturnMessage(e.toString());
		}

		return responseCommand;
	}



}
