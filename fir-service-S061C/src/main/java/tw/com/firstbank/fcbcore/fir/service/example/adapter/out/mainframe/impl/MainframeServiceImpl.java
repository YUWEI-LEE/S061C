package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.impl;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateRequest;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.FxRateResponse;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.mainframe.api.MainframeService;

public class MainframeServiceImpl implements MainframeService {

	@Autowired
	FxRateResponse fxRateResponse;

	@Override
	public FxRateResponse isReasonableFxRate(FxRateRequest fxRateRequest) {
		return null;
	}

	@Override
	public FxRateResponse getFxRate(FxRateRequest fxRateRequest) {
		fxRateResponse.setFxRate(BigDecimal.ZERO);
		return fxRateResponse;
	}
}
