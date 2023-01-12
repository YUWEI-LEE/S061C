package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.repository.refund.impl;

import java.util.Optional;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import tw.com.firstbank.fcbcore.fir.service.example.adapter.out.repository.refund.api.RefundJpaRepository;
import tw.com.firstbank.fcbcore.fir.service.example.application.out.repository.RefundTxnRepository;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;

@Repository
@RequiredArgsConstructor
public class RefundTxnRepositoryImpl implements RefundTxnRepository {


//	private final RefundJpaRepository repo;

	@Override
	public Optional<RefundTxn> get(UUID uuid) {
//		return repo.findByUuid(uuid);
		return Optional.empty();
	}

	@Override
	public Optional<RefundTxn> getS061BySeqNoAndAdviceBranch(String seqNo, String adviceBranch) {
//		return repo.findBySeqNoAndAdviceBranch(seqNo,adviceBranch);
		return Optional.empty();
	}

	@Override
	public RefundTxn save(RefundTxn data) {
		return null;
	}

	@Override
	public RefundTxn saveAndFlush(RefundTxn data) {
		return null;
	}
}
