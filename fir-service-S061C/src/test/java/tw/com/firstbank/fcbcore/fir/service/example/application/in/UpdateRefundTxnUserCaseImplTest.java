package tw.com.firstbank.fcbcore.fir.service.example.application.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tw.com.firstbank.fcbcore.fcbframework.core.application.exception.ValidationException;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061RequestCommand;

public class UpdateRefundTxnUserCaseImplTest {


	private UpdateS061RequestCommand updateS061RequestCommand;
//	private S061Repository mock;

	@BeforeEach
//	void setUp() {
//		mock = Mockito.mock(S061Repository.class);
//	}


	@Test
	void updateS061_WillReturn_printForm() throws ValidationException{
		//arrange
		updateS061RequestCommand = new UpdateS061RequestCommand();
		updateS061RequestCommand.setSeqNo("1234567");
		updateS061RequestCommand.setAdviceBranch("091");
		//act


		//assert


	}
}
