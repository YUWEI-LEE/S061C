package tw.com.firstbank.fcbcore.fir.service.example.application.out.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import tw.com.firstbank.fcbcore.fcbframework.core.application.out.repository.AggregateRepository;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;

public interface RefundTxnRepository extends AggregateRepository<RefundTxn, UUID> {

	Optional<RefundTxn> get(UUID uuid);


	Optional<RefundTxn> getS061BySeqNoAndAdviceBranch(String seqNo, String adviceBranch);

}
