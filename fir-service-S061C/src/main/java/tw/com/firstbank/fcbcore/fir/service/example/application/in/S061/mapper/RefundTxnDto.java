package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.mapper;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RefundTxnDto {

	private String seqNo;
	private String adviceBranch;
	private String version;
	private String processDate;
	private String currency;
	private String currencyCode;
	private BigDecimal amount;
	private String depositBankbic;
	private String returnReason;
	private String chargeCurrency;
	private String chargeCurrencyCode;
	private BigDecimal chargeFee;
	private BigDecimal chargeTwdAomunt;
	private BigDecimal spotRate;
	private String transferCurrency;
	private String transferCurrencyCode;
	private BigDecimal transferFee;

}
