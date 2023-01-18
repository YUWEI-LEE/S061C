package tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api;

import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tw.com.firstbank.fcbcore.fcbframework.core.application.in.ResponseCommand;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api.UpdateS061Response.S061DTO;
import tw.com.firstbank.fcbcore.fir.service.example.domain.S061Report;

@ToString
@Getter
@Setter
public class UpdateS061ResponseCommand implements ResponseCommand {

	private String returnCode;
	private String returnMessage;


}
