package tw.com.firstbank.fcbcore.fir.service.example.adapter.out.repository.refund.api;

import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tw.com.firstbank.fcbcore.fcbframework.core.spring.config.DataSourceMain;
import tw.com.firstbank.fcbcore.fir.service.example.domain.RefundTxn;
//@Repository
//@DataSourceMain
public interface RefundJpaRepository extends JpaRepository<RefundTxn, Long> {


	Optional<RefundTxn> findByUuid(UUID uuid);

	Optional<RefundTxn> findBySeqNoAndAdviceBranch(String seqNo, String adviceBranch);


}
