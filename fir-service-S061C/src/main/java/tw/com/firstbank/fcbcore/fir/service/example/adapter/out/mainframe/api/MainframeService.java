package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api;

public interface MainframeService {

	public FxRateResponse isReasonableFxRate (FxRateRequest fxRateRequest);

	public FxRateResponse getFxRate (FxRateRequest fxRateRequest);
}
