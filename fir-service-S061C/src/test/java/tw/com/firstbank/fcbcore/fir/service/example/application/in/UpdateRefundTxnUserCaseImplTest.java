package tw.com.firstbank.fcbcore.fir.service.example.application.in;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Optional;
import org.hibernate.mapping.Any;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import tw.com.firstbank.fcbcore.fcbframework.core.application.exception.ValidationException;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateResponse;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainframeService;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.S061Service;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061RequestCommand;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061ResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.impl.S061CUseCaseImpl;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.repository.RefundTxnRepository;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;

public class UpdateRefundTxnUserCaseImplTest {


	private UpdateS061RequestCommand updateS061RequestCommand;
	private S061CUseCaseImpl s061CUseCase;

	@Autowired
	private S061Service s061Service;
	@Mock
	private RefundTxnRepository refundTxnRepository;

	@Mock
	private MainframeService mainframeService;

	private RefundTxn refundTxn;

	private FxRateResponse fxRateResponse;

	@BeforeEach
	void setUp() {
		openMocks(this);
		s061Service = new S061Service(refundTxnRepository,mainframeService,refundTxn);
		s061CUseCase = new S061CUseCaseImpl(s061Service);
		refundTxn = new RefundTxn();
		refundTxn.setSeqNo("1234567");
		refundTxn.setAdviceBranch("091");
		refundTxn.setProcessDate("20230115");
		refundTxn.setCurrencyCode("USD");
		refundTxn.setVersion("01");
	}


	@Test
	void updateS061_WillReturn_printForm() throws ValidationException{
		//arrange
		updateS061RequestCommand = new UpdateS061RequestCommand();
		updateS061RequestCommand.setSeqNo("1234567");
		updateS061RequestCommand.setAdviceBranch("091");
		updateS061RequestCommand.setVersion("01");
		updateS061RequestCommand.setProcessDate("20230115");

		Optional<RefundTxn> refundTxnOptional= Optional.of(refundTxn);
		fxRateResponse = new FxRateResponse();
		fxRateResponse.setReturnCode("0000");


		Mockito.when(refundTxnRepository.getS061BySeqNoAndAdviceBranch(anyString(), anyString())).thenReturn(refundTxnOptional);
		Mockito.when(mainframeService.isReasonableFxRate(any())).thenReturn(fxRateResponse);

		//act
		UpdateS061ResponseCommand responseCommand = s061CUseCase.execute(updateS061RequestCommand);

		//assert
		Assertions.assertEquals("0000",responseCommand.getReturnCode());

	}
}
