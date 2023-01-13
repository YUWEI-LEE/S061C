package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.repository.mainframe.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class MainFrameResponse {


	private String returnCode;

	private int txnNo;
}
