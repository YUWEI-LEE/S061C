package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tw.com.firstbank.fcbcore.fcbframework.core.application.in.BaseRequestCommand;

@ToString
@Getter
@Setter
public class UpdateS061RequestCommand extends BaseRequestCommand {

	private String version;
	private String adviceBranch;
	private String seqNo;

	private String processDate;
	private String currency;
	private String currencyCode;
	private BigDecimal Amount;
	private String DepositBankBIC;
	private String ReturnReason;
	private String ChargeCurrency;
	private String ChargeCurrencyCode;
	private BigDecimal ChargeFee;
	private BigDecimal chargeTwdAmount;
	private BigDecimal spotRate;
	private String transferCurrency;
	private String transferCurrencyCode;
	private BigDecimal transferFee;
}
