package tw.com.firstbank.fcbcore.fir.service.example.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class S061Report {

	private String typeFormCode="C0";
	private String formName;
	private String typeHeaderCode ="H0";
	private String reportProcessMode;
	private String reportTime;
	private String reportDate;
	private String reportVersion;
	private String reportOperator;
	private String reportOperatorName;

}
