package tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api;

import java.math.BigDecimal;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.mapstruct.factory.Mappers;
import tw.com.firstbank.fcbcore.fcbframework.core.adapter.in.rest.api.RequestBody;
import tw.com.firstbank.fcbcore.fcbframework.core.application.in.RequestCommand;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.mapper.S061ControllerMapper;

@Getter
@Setter
@ToString
public class UpdateS061Request implements RequestBody {

	private static final S061ControllerMapper mapper = Mappers.getMapper(
		S061ControllerMapper.class);

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


	@Override
	public RequestCommand toCommand() {
		return mapper.toUpdateS061RequestCommand(this);
	}
}
