package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.atLeastOnce;
import static org.mockito.Mockito.doThrow;
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
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.repository.mainframe.api.FxRateResponse;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.repository.mainframe.api.MainframeService;
import tw.com.firstbank.fcbcore.fir.service.example.application.exception.ServiceStatusCode;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.S061Service;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.mapper.RefundTxnDto;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.repository.RefundTxnRepository;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;

//1.S061c get
//2.compare  version
//3.check date
//4.compare reasonable Rate
//5.s061 update
//6.update charge fee
//7.compose form msg to mainframe
//8.mainframeservice
//9.s061 get(seq)
//10. account no update (txn-no)
//11.s061 update
//12.response body
//13.print

public class RefundTxnServiceTest {

	@Mock
	private RefundTxnRepository refundTxnRepository;

	@Mock
	private MainframeService mainframeService;

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
		updateS061RequestCommand.setProcessDate("20230113");
		refundTxn = new RefundTxn();
		refundTxn.setSeqNo("1234567");
		refundTxn.setAdviceBranch("091");
		refundTxn.setProcessDate("20230113");
	}

	//test 2-1 Compare Version (Pass)
	@Test
	void checkVersion_WillCallRefundTxn_VersionEqualPass(){
		//arrange
		refundTxn.setVersion("01");
		Optional<RefundTxn> refundTxnOptional= Optional.of(refundTxn);


		Mockito.when(refundTxnRepository.getS061BySeqNoAndAdviceBranch(anyString(),anyString())).thenReturn(refundTxnOptional);
		//act
	    Boolean isCheckVersion= s061Service.checkVersion(updateS061RequestCommand);

		//assert
		Assertions.assertEquals(true,isCheckVersion);
		Mockito.verify(refundTxnRepository).getS061BySeqNoAndAdviceBranch(anyString(),anyString());

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
			updateS061RequestCommand);});

		assertEquals("版本別錯誤", ex.getMessage());
		Mockito.verify(refundTxnRepository).getS061BySeqNoAndAdviceBranch(anyString(),anyString());

	}

	@Test
	void checkVersion_WillCallRefundTxn_NoDataNoPass(){
		//arrange
		refundTxn = null;
		Optional<RefundTxn> refundTxnOptional= Optional.ofNullable(refundTxn);
		Mockito.when(refundTxnRepository.getS061BySeqNoAndAdviceBranch(anyString(),anyString())).thenReturn(refundTxnOptional);
		//act
		//assert
		var ex = Assertions.assertThrows(BusinessException.class, ()->{s061Service.checkVersion(
			updateS061RequestCommand);});

		assertEquals("查無資料", ex.getMessage());
		Mockito.verify(refundTxnRepository).getS061BySeqNoAndAdviceBranch(anyString(),anyString());

	}

	//test 3-1 Compare Date (Pass)
	@Test
	void checkDate_WillCallRefundTxn_DateEqualPass(){
		//arrange
		Optional<RefundTxn> refundTxnOptional= Optional.of(refundTxn);

		Mockito.when(refundTxnRepository.getS061BySeqNoAndAdviceBranch(anyString(),anyString())).thenReturn(refundTxnOptional);
		//act
		Boolean isCheckDate= s061Service.checkDate(updateS061RequestCommand);

		//assert
		Assertions.assertEquals(true,isCheckDate);
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
		Boolean isPass= s061Service.checkResonableFxRate(updateS061RequestCommand);

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
		Boolean isPass= s061Service.checkResonableFxRate(updateS061RequestCommand);

		//assert
		Assertions.assertEquals(false,isPass);
		Mockito.verify(mainframeService,atLeastOnce()).isReasonableFxRate(any());
	}

	//test 5-1 update DB Success (Pass)
	@Test
	void UpdateRefundTxn_WillSaveRefundTxn_UpdateSuccess() throws Exception {
		//arrange
		refundTxn.setReturnReason("AC02");
		Mockito.when(refundTxnRepository.save(any())).thenReturn(refundTxn);
		//act
		boolean isSaveSuccess = s061Service.updateRefundTxn(refundTxn);
		//assert
		Assertions.assertEquals(true,isSaveSuccess);
		Mockito.verify(refundTxnRepository).save(any());
	}

	//test 5-2 update DB fail (No Pass)
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

}
