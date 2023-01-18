package tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api.UpdateS061Request;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api.UpdateS061Response;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061RequestCommand;
import tw.com.firstbank.fcbcore.fir.service.example.application.in.S061.api.UpdateS061ResponseCommand;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = "spring")
public interface S061ControllerMapper {


	UpdateS061RequestCommand toUpdateS061RequestCommand(UpdateS061Request source);

	@Mapping(source = "returnMessage", target = "message")
	UpdateS061Response toUpdateS061Response(UpdateS061ResponseCommand source);

}
