package tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.autoconfigure.amqp.RabbitAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.cloud.openfeign.FeignAutoConfiguration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import tw.com.firstbank.fcbcore.fcbframework.core.application.in.CommandBus;
import tw.com.firstbank.fcbcore.fcbframework.core.spring.config.CoreExceptionHandler;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api.UpdateS061Request;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061RequestCommand;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.impl.S061ControllerImpl;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061ResponseCommand;

@WebMvcTest
@ImportAutoConfiguration({RabbitAutoConfiguration.class, FeignAutoConfiguration.class})
public class RefundTxnControllerTest {

	@Mock
	private CommandBus commandBus;

	@Autowired
	private ObjectMapper objectMapper;

	private MockMvc mockMvc;
	private HttpHeaders httpHeaders = new HttpHeaders();

	@BeforeEach
	public void setup() {
		httpHeaders.add("x-core-channel", "channel");
		httpHeaders.add("x-core-traceid", "traceId");
		httpHeaders.add("x-core-uid", "uid");
		httpHeaders.add("x-core-txid", "txid");
		httpHeaders.add("x-core-timestamp", "2022-09-01T10:01:400480");
		httpHeaders.add("x-core-msg-type", "");
		httpHeaders.add("Accept-Language", "zh-TW");
		httpHeaders.add("Accept-Charset", "utf-8");

		S061ControllerImpl s061ControllerImpl = new S061ControllerImpl(commandBus);
		mockMvc = MockMvcBuilders.standaloneSetup(s061ControllerImpl)
			.setControllerAdvice(CoreExceptionHandler.class)
			.build();
	}
	@Test
	void updateS061_WillUpdateS061_ReturnSuccess() throws Exception{
		//arrange
		UpdateS061Request s061UpdateRequest = new UpdateS061Request();
		s061UpdateRequest.setVersion("01");
		s061UpdateRequest.setAdviceBranch("091");
		s061UpdateRequest.setSeqNo("1234567");
		s061UpdateRequest.setProcessDate("20221201");
		s061UpdateRequest.setCurrency("USD");
		s061UpdateRequest.setCurrencyCode("01");
		s061UpdateRequest.setAmount(new BigDecimal(1000));
		s061UpdateRequest.setDepositBankBIC("CITIUS33XXX");
		s061UpdateRequest.setReturnReason("AC01");
		s061UpdateRequest.setChargeCurrency("USD");
		s061UpdateRequest.setChargeCurrencyCode("01");
		s061UpdateRequest.setChargeFee(new BigDecimal(10));
		s061UpdateRequest.setChargeTwdAmount(new BigDecimal(305));
		s061UpdateRequest.setSpotRate(new BigDecimal(30.5));
		s061UpdateRequest.setTransferCurrency("USD");
		s061UpdateRequest.setTransferCurrencyCode("01");
		s061UpdateRequest.setTransferFee(new BigDecimal(990));

		// Request client Header
		Map<String, Object> requestMap = new HashMap<>();
		requestMap.put("terminalId", "terminalId");
		requestMap.put("empId", "empId");
		requestMap.put("txId", "txId");
		requestMap.put("txSeqNo", "txSeqNo");
		requestMap.put("businessDate", "businessDate");
		requestMap.put("requestTimeStamp", "123456");
		requestMap.put("clientRequest", s061UpdateRequest);

		String requestJson = objectMapper.writeValueAsString(requestMap);

		// Response Client Body
		UpdateS061ResponseCommand updateS061ResponseCommand = new UpdateS061ResponseCommand();
		updateS061ResponseCommand.setReturnCode("0000");
		updateS061ResponseCommand.setReturnMessage("success");

		//act
		when(commandBus.handle(any())).thenReturn(updateS061ResponseCommand);
		mockMvc.perform(post("/na/fir/ir/s061/update/v1")
				.headers(httpHeaders)
				.contentType(MediaType.APPLICATION_JSON)
				.content(requestJson)
				.characterEncoding("utf-8")
				.accept(MediaType.APPLICATION_JSON)).andReturn();
			//.andExpect(status().isOk());
			// 驗證client Body
//			.andExpect(jsonPath("$.clientResponse.returnCode").value(s061UpdateResponseCommand.getReturnCode()))
//			.andExpect(jsonPath("$.clientResponse.returnMessage").value(s061UpdateResponseCommand.getReturnMessage()));

		// validate
		verify(commandBus).handle(any(UpdateS061RequestCommand.class));

	}
}
