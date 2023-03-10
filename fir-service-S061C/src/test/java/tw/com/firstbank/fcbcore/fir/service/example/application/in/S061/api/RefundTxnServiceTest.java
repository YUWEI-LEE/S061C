package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;


import java.math.BigDecimal;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import tw.com.firstbank.fcbcore.fcbframework.core.application.exception.BusinessException;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateResponse;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameResponse;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.mainframe.api.MainframeServiceApi;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateRequest;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.S061Service;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.repository.RefundTxnRepository;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;
import tw.com.firstbank.fcbcore.fir.service.example.domain.S061Report;

//1.S061c get
//2.compare  version
//3.check date
//4.compare reasonable Rate
//5.update charge fee -> call FxRateService
//6.compose form msg to mainframe  (FOSGLIF2、FOSTXLS1)
//6.1 account no update (txn-no)
//7.S061 update
//8.response body

public class RefundTxnServiceTest {

	@Mock
	private RefundTxnRepository refundTxnRepository;

	@Mock
	private MainframeServiceApi mainframeService;

	@Autowired
	private S061Service s061Service;


	private UpdateS061RequestCommand updateS061RequestCommand;
	private RefundTxn refundTxn;

	@BeforeEach
	public void setup() {
		openMocks(this);
		s061Service = new S061Service(refundTxnRepository,mainframeService);
		updateS061RequestCommand = new UpdateS061RequestCommand();
		updateS061RequestCommand.setVersion("01");
		updateS061RequestCommand.setAdviceBranch("091");
		updateS061RequestCommand.setSeqNo("1234567");
		updateS061RequestCommand.setProcessDate("20230115");
		updateS061RequestCommand.setCurrencyCode("USD");
		refundTxn = new RefundTxn();
		refundTxn.setSeqNo("1234567");
		refundTxn.setAdviceBranch("091");
		refundTxn.setProcessDate("20230115");
		refundTxn.setCurrencyCode("USD");
		refundTxn.setVersion("01");
	}

	//test 1-1 get s061service (Pass)
	@Test
	void getS061Info_WillQueryRefundTxn_getPass(){
		//arrange
		RefundTxn refundTxnExpect = new RefundTxn();
		RefundTxn refundDB = new RefundTxn();
		refundDB.setSeqNo("1234567");
		refundDB.setAdviceBranch("091");
		refundDB.setProcessDate("20230115");
		refundDB.setCurrencyCode("USD");
		refundDB.setVersion("01");
		Optional<RefundTxn> refundTxnDBOptional= Optional.of(refundDB);
		Mockito.when(refundTxnRepository.getS061BySeqNoAndAdviceBranch(anyString(),anyString())).thenReturn(refundTxnDBOptional);
		//act
		refundTxnExpect = s061Service.getRefundTxn(updateS061RequestCommand.getSeqNo(),updateS061RequestCommand.getAdviceBranch());
		//assert
		Assertions.assertEquals(refundTxn,refundTxnExpect);

	}

	//test 1-2 get s061service (Data Not Found)(No Pass)
	@Test
	void getS061Info_WillQueryRefundTxnDataNotFound_NoPass(){
		//arrange
		Optional<RefundTxn> refundTxnDBOptional= Optional.empty();
		Mockito.when(refundTxnRepository.getS061BySeqNoAndAdviceBranch(anyString(),anyString())).thenReturn(refundTxnDBOptional);
		//act
		//assert
		var ex = Assertions.assertThrows(BusinessException.class ,() -> {s061Service.getRefundTxn(
			updateS061RequestCommand.getSeqNo(),updateS061RequestCommand.getAdviceBranch());} );

		assertEquals("查無資料",ex.getMessage());
		Mockito.verify(refundTxnRepository).getS061BySeqNoAndAdviceBranch(anyString(),anyString());

	}

	//test 2-1 Compare Version (Pass)
	@Test
	void checkVersion_WillCallRefundTxn_VersionEqualPass(){
		//arrange
		refundTxn.setVersion("01");

		//act
	    Boolean isCheckVersion= s061Service.checkVersion(refundTxn.getVersion(),updateS061RequestCommand.getVersion());

		//assert
		Assertions.assertEquals(true,isCheckVersion);

	}

	//test 2-2 Compare Version (No Pass) BusinessException INVALID_VERSION
	@Test
	void checkVersion_WillCallRefundTxn_VersionNotEqualNoPass(){
		//arrange
		refundTxn.setVersion("02");
		Optional<RefundTxn> refundTxnOptional= Optional.of(refundTxn);

		Mockito.when(refundTxnRepository.getS061BySeqNoAndAdviceBranch(anyString(),anyString())).thenReturn(refundTxnOptional);
		//act
		//assert
		var ex = Assertions.assertThrows(BusinessException.class, ()->{s061Service.checkVersion(
			refundTxn.getVersion(),updateS061RequestCommand.getVersion());});

		assertEquals("版本別錯誤", ex.getMessage());
		Mockito.verify(refundTxnRepository,times(0)).getS061BySeqNoAndAdviceBranch(anyString(),anyString());

	}

	//test 3-1 Compare Date (Pass)
	@Test
	void checkDate_WillCallRefundTxn_DateEqualPass(){
		//arrange
		refundTxn.setProcessDate("20230115");
		//act
		Boolean isCheckDate= s061Service.checkDate(refundTxn.getProcessDate(),updateS061RequestCommand.getProcessDate());
		//assert
		Assertions.assertEquals(true,isCheckDate);
	}

	//test 3-2 Compare Date (No Pass) BusinessException INVALID_DATE
	@Test
	void checkDate_WillCallRefundTxn_DateNotEqualNoPass(){
		//arrange
		refundTxn.setProcessDate("20230116");
		Optional<RefundTxn> refundTxnOptional= Optional.of(refundTxn);

		Mockito.when(refundTxnRepository.getS061BySeqNoAndAdviceBranch(anyString(),anyString())).thenReturn(refundTxnOptional);
		//act
		//assert
		var ex = Assertions.assertThrows(BusinessException.class, ()->{s061Service.checkDate(
			refundTxn.getProcessDate(),updateS061RequestCommand.getProcessDate());});

		assertEquals("案件日期錯誤", ex.getMessage());
		//Mockito.verify(refundTxnRepository).getS061BySeqNoAndAdviceBranch(anyString(),anyString());
	}


	//test 4-1 compare reasonable Rate (Pass)
	@Test
	void checkReasonableFxRate_WillCallMainframeService_ToCheckPass(){
		//arrange
		updateS061RequestCommand.setSpotRate(new BigDecimal(30.5));
		FxRateResponse fxRateResponse = new FxRateResponse();
		fxRateResponse.setReturnCode("0000");
		Mockito.when(mainframeService.isReasonableFxRate(any())).thenReturn(fxRateResponse);
		//act
		Boolean isPass= s061Service.checkReasonableFxRate(updateS061RequestCommand.getSpotRate());

		//assert
		Assertions.assertEquals(true,isPass);
		Mockito.verify(mainframeService,atLeastOnce()).isReasonableFxRate(any());

	}

	//test 4-2 compare reasonable Rate (No Pass)
	@Test
	void checkReasonableFxRate_WillCallMainframeService_ToCheckNoPass(){
		//arrange
		updateS061RequestCommand.setSpotRate(new BigDecimal(29.5));
		FxRateResponse fxRateResponse = new FxRateResponse();
		fxRateResponse.setReturnCode("ED85");
		Mockito.when(mainframeService.isReasonableFxRate(any())).thenReturn(fxRateResponse);
		//act
		Boolean isPass= s061Service.checkReasonableFxRate(updateS061RequestCommand.getSpotRate());

		//assert
		Assertions.assertEquals(false,isPass);
		Mockito.verify(mainframeService,atLeastOnce()).isReasonableFxRate(any());
	}

	//test 5-1 get charge fee -> call FxRateService (Pass)
	@Test
	void givenCurrencyCode_WillCallMainframeService_ToGetFxRatePass(){
		//arrange
		FxRateRequest request = new FxRateRequest();
		request.setCurrencyCode(updateS061RequestCommand.getCurrencyCode());
		request.setProcessDate(updateS061RequestCommand.getProcessDate());
		FxRateResponse response = new FxRateResponse();
		response.setToUsdRate(new BigDecimal(30.5));
		response.setReturnCode("0000");
		Mockito.when(mainframeService.getToUsdRate(any())).thenReturn(response);
		//act
		BigDecimal fxRate= s061Service.getToUsdRate(request);
		//assert
		Mockito.verify(mainframeService,atLeastOnce()).getToUsdRate(any());
		Assertions.assertEquals(new BigDecimal(30.5), fxRate);
	}

	//test 5-2 get charge fee -> call FxRateService (NO Pass)
	@Test
	void givenCurrencyCode_WillCallMainframeService_ToGetFxRateNoPass(){
		//arrange
		FxRateRequest request = new FxRateRequest();
		request.setCurrencyCode(updateS061RequestCommand.getCurrencyCode());
		request.setProcessDate(updateS061RequestCommand.getProcessDate());
		FxRateResponse response = new FxRateResponse();
		//response.setFxRate(new BigDecimal(30.5));
		response.setReturnCode("0000");
		Mockito.when(mainframeService.getToUsdRate(any())).thenReturn(response);
		//act
		BigDecimal fxRate= s061Service.getToUsdRate(request);
		//assert
		Mockito.verify(mainframeService,atLeastOnce()).getToUsdRate(any());
		Assertions.assertEquals(null, fxRate);
	}

	//test 6-1 compose form msg to mainframe  (FOSGLIF2、FOSTXLS1) (Pass)
	@Test
	void ioMainFrame_WillCallMainFrameService_IOSuccess() throws Exception {

		//arrange
		Optional<RefundTxn> refundTxnOptional= Optional.of(refundTxn);

		MainFrameResponse mainFrameResponse = new MainFrameResponse();
		mainFrameResponse.setReturnCode("0000");
		mainFrameResponse.setTxnNo(12345);
		Mockito.when(mainframeService.mainframeIO(any())).thenReturn(mainFrameResponse);
		//act
		MainFrameRequest mainFrameRequest = new MainFrameRequest();
		mainFrameRequest.setData(updateS061RequestCommand.getSeqNo());
		mainFrameResponse = s061Service.mainframeIO(mainFrameRequest);
		//assert
		Assertions.assertEquals("0000",mainFrameResponse.getReturnCode());
		Mockito.verify(mainframeService).mainframeIO(any());
	}

	//test 6-2 compose form msg to mainframe  (FOSGLIF2、FOSTXLS1) (No Pass)
	@Test
	void ioMainFrame_WillCallMainFrameService_IOFail() throws Exception {

		//arrange
		MainFrameResponse mainFrameResponse = new MainFrameResponse();
		mainFrameResponse.setReturnCode("error");
		Mockito.when(mainframeService.mainframeIO(any())).thenReturn(mainFrameResponse);
		//act
		MainFrameRequest mainFrameRequest = new MainFrameRequest();
		mainFrameRequest.setData(updateS061RequestCommand.getSeqNo());
		mainFrameResponse = s061Service.mainframeIO(mainFrameRequest);
		//assert
		Assertions.assertEquals("error",mainFrameResponse.getReturnCode());
		Mockito.verify(mainframeService,times(1)).mainframeIO(any());
	}

	//test 7-1 update DB Success (Pass)
	@Test
	void UpdateRefundTxn_WillSaveRefundTxn_UpdateSuccess() throws Exception {
		//arrange
		refundTxn.setReturnReason("AC02");
		Mockito.when(refundTxnRepository.save(any())).thenReturn(refundTxn);
		//act
		s061Service.updateRefundTxn(refundTxn);
		//assert
		Mockito.verify(refundTxnRepository).save(any());
	}

	//test 7-2 update DB fail (No Pass)
	@Test
	void UpdateRefundTxn_WillSaveRefundTxn_UpdateFail() {
		//arrange
		refundTxn.setReturnReason("AC02");
		doThrow(RuntimeException.class).when(refundTxnRepository).save(any());
//		Mockito.when(refundTxnRepository.save(any())).thenReturn(refundTxn);
		//act
		//assert
		Assertions.assertThrows(RuntimeException.class,()->s061Service.updateRefundTxn(refundTxn));
		Mockito.verify(refundTxnRepository).save(any());
	}

	//test 8.response body print
	@Test
	void printForm_WillPrintReponse_PrintSuccess() throws Exception {
		//arrange

		//act
		S061Report s061Report = s061Service.print();
		//assert
		Assertions.assertNotNull(s061Report);
//		Mockito.verify(refundTxnRepository).save(any());
	}

}
