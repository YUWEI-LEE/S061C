package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.impl;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateResponse;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainFrameResponse;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainframeService;

@Service
public class MainframeServiceImpl implements MainframeService {



	@Override
	public FxRateResponse isReasonableFxRate(FxRateRequest fxRateRequest) {
		return null;
	}

	@Override
	public MainFrameResponse mainframeIO(MainFrameRequest mainFrameRequest) {
		return null;
	}

	@Override
	public FxRateResponse getToUsdRate(FxRateRequest fxRateRequest) {
		FxRateResponse fxRateResponse = new FxRateResponse();
		fxRateResponse.setToUsdRate(BigDecimal.ZERO);
		return fxRateResponse;
	}
}
