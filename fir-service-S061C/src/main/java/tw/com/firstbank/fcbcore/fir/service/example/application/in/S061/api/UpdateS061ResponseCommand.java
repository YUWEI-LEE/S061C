package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tw.com.firstbank.fcbcore.fcbframework.core.application.in.ResponseCommand;

@ToString
@Getter
@Setter
public class UpdateS061ResponseCommand implements ResponseCommand {

	private String returnCode;
	private String returnMessage;


}
