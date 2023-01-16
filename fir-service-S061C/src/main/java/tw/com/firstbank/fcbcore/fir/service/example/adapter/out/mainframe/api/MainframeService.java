package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateResponse;

public interface MainframeService {

	public FxRateResponse isReasonableFxRate (FxRateRequest fxRateRequest);

	public MainFrameResponse mainframeIO (MainFrameRequest mainFrameRequest);


	public FxRateResponse getToUsdRate (FxRateRequest fxRateRequest);
}
