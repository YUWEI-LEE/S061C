package tw.com.firstbank.fcbcore.fir.service.example.adapter.in.rest.api;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import tw.com.firstbank.fcbcore.fcbframework.core.adapter.in.rest.api.ResponseBody;

@Setter
@ToString
public class UpdateS061Response implements ResponseBody {

	/**
	 * The api List.
	 */

//	@JsonProperty(value = "message")
//	private  String message;




	@JsonProperty(value = "list")
	List<S061DTO> list = new ArrayList<>();

	@ToString
	@Setter
	public static class S061DTO{

		@Schema(description = "UUID", example = "0136a826-82a4-493c-b0f2-de51b68ee4e4")
		@JsonProperty(value = "uuid")
		private String uuid;

		@Schema(description = "印表處理方式", example = "")
		@JsonProperty(value = "reportProcessMode")
		private String reportProcessMode;

		@Schema(description = "印表時間", example = "")
		@JsonProperty(value = "reportTime")
		private String reportTime;

		@Schema(description = "印表日期", example = "")
		@JsonProperty(value = "reportDate")
		private String reportDate;

		@Schema(description = "版本", example = "")
		@JsonProperty(value = "version")
		private String version;

		@Schema(description = "操作員", example = "")
		@JsonProperty(value = "operator")
		private String operator;

	}

}
