package tw.com.firstbank.fcbcore.fir.service.example.domain;


import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;
import tw.com.firstbank.fcbcore.fcbframework.core.domain.BaseAggregate;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@Entity
@Table(name ="RefundTxn")
public class RefundTxn extends BaseAggregate<RefundTxn, UUID> implements Serializable {



	@Column(name = "uuid", nullable = false, updatable = false, unique = true)
	@Type(type = "uuid-char")
	private UUID uuid;

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
	public String getAggregateId() {
		return getUuid().toString();
	}




}
