package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FxRateResponse {

	private String returnCode;

	private BigDecimal toUsdRate;

}
