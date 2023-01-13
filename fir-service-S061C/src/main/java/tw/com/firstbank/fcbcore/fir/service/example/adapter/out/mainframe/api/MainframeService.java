package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.repository.mainframe.api;

public interface MainframeService {

	public FxRateResponse isReasonableFxRate (FxRateRequest fxRateRequest);

	public MainFrameResponse mainframeIO (MainFrameRequest mainFrameRequest);


	public FxRateResponse getFxRate (FxRateRequest fxRateRequest);
}
