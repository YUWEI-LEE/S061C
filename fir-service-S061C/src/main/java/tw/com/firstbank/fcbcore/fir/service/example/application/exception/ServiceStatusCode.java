package tw.com.firstbank.fcbcore.fir.service.example.application.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum ServiceStatusCode {

//	INVALID_USER_NAME("0001",),
//	INVALID_USER_CREDIT("0002"),
//	INVALID_USER_EMAIL("0003"),
//	INVALID_USER_DATA("0004"),
//	INVALID_HOST_TRANSACTION("0005"),


	OVER_FXRATE("ED85","承作匯率超過合理範圍"),
	DATA_NOT_FOUND("9998","查無資料"),
	INVALID_VERSION("9999","版本別錯誤"),
	INVALID_DATE("9999","案件日期錯誤"),

	SUCCESS("0000","交易成功");


	private final String code;
	private final String message;

}
