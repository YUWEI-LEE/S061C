package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.openMocks;


import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
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

	@Autowired
	private S061Service s061Service;

	private UpdateS061RequestCommand updateS061RequestCommand;

	@BeforeEach
	public void setup() {
		openMocks(this);
		s061Service = new S061Service(refundTxnRepository);
		updateS061RequestCommand = new UpdateS061RequestCommand();
	}

	@Test
	void checkVersion_WillCallRefundTxn_ToGetVersion(){
		//arrange
//		RefundTxnDto refundTxnDto = new RefundTxnDto();
//		refundTxnDto.setSeqNo("1234567");
//		refundTxnDto.setAdviceBranch("091");

//		Optional<RefundTxn> refundTxnOptional = Optional.of(new RefundTxn());

		updateS061RequestCommand.setVersion("01");
		updateS061RequestCommand.setAdviceBranch("091");
		updateS061RequestCommand.setSeqNo("1234567");

		//DataBase
		RefundTxn refundTxn=  new RefundTxn();
		refundTxn.setSeqNo("1234567");
		refundTxn.setAdviceBranch("091");
		refundTxn.setVersion("01");


		Mockito.when(refundTxnRepository.getS061BySeqNoAndAdviceBranch(anyString(),anyString())).thenReturn(refundTxn);
		//act
	    Boolean isCheckVersion= s061Service.checkVersion(updateS061RequestCommand);

		//assert
		Assertions.assertEquals(true,isCheckVersion);
		Mockito.verify(refundTxnRepository).getS061BySeqNoAndAdviceBranch(anyString(),anyString());

	}
}
