package tw.com.firstbank.fcbcore.fir.service.example.application.out.mainframe.api;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateResponse;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameResponse;

public interface MainframeServiceApi {

	public FxRateResponse isReasonableFxRate (FxRateRequest fxRateRequest);

	public MainFrameResponse mainframeIO (MainFrameRequest mainFrameRequest);


	public FxRateResponse getToUsdRate (FxRateRequest fxRateRequest);
}
