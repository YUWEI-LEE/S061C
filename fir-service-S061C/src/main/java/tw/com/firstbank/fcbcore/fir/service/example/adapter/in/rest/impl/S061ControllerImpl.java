package tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.impl;

import lombok.AllArgsConstructor;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import tw.com.firstbank.fcbcore.fcbframework.core.adapter.in.rest.api.RequestWrapper;
import tw.com.firstbank.fcbcore.fcbframework.core.adapter.in.rest.api.ResponseWrapper;
import tw.com.firstbank.fcbcore.fcbframework.core.adapter.in.rest.impl.BaseController;
import tw.com.firstbank.fcbcore.fcbframework.core.application.in.CommandBus;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api.S061ControllerApi;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api.UpdateS061Request;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api.UpdateS061Response;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.mapper.S061ControllerMapper;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061ResponseCommand;

@AllArgsConstructor
@RestController
public class S061ControllerImpl extends BaseController implements S061ControllerApi {

	private static final Logger log = LoggerFactory.getLogger(S061ControllerImpl.class);

	private static final S061ControllerMapper mapper = Mappers.getMapper(
		S061ControllerMapper.class);
	private final CommandBus commandBus;


	@Override
	@PostMapping("/update/v1")
	public ResponseEntity<ResponseWrapper<UpdateS061Response>> updateS061(
		RequestWrapper<UpdateS061Request> requestWrapper) {
		UpdateS061ResponseCommand responseCommand = commandBus.handle(requestWrapper.toCommand());
		return this.makeResponseEntity(responseCommand, mapper::toUpdateS061Response);
	}


}
