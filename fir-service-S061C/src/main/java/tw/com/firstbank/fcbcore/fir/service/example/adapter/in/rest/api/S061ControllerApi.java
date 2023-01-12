package tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import javax.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import tw.com.firstbank.fcbcore.fcbframework.core.adapter.in.rest.api.RequestWrapper;
import tw.com.firstbank.fcbcore.fcbframework.core.adapter.in.rest.api.ResponseWrapper;

@RequestMapping("/na/fir/ir/s061")
@Validated
@Tag(name = "S061 API", description = "提供退匯相關API操作，可查詢、新增、修改、刪除退匯資訊")
public interface S061ControllerApi {


	/**
	 * Creates api information.
	 *
	 * @param requestWrapper the createUser json input with newCore format
	 * @return the createUser json output with newCore format
	 */
	@Operation(summary = "建立User", description = "檢查User資料，並建立User資料")
	@ApiResponses(value = {
		@ApiResponse(responseCode = "200", description = "OK"),
		@ApiResponse(responseCode = "400", description = "Validation Fail", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))}),
		@ApiResponse(responseCode = "500", description = "Internal Server Error", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = ResponseWrapper.class))})})
	ResponseEntity<ResponseWrapper<UpdateS061Response>> updateS061(
		@Valid @RequestBody final RequestWrapper<UpdateS061Request> requestWrapper);


}
