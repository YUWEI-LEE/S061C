package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class FxRateRequest {

	private BigDecimal fxRate;
	private String currencyCode;
	private String processDate;
}
