package com.dp.fravega.sucursal.repository;

import com.dp.fravega.sucursal.model.BranchOffice;
import com.dp.fravega.sucursal.model.CustomerServiceHour;
import com.dp.fravega.sucursal.model.Node;
import com.dp.fravega.sucursal.model.WithdrawalPoint;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@AutoConfigureTestDatabase
@DataJpaTest
class NodeRepositoryTest {
	Logger logger = LoggerFactory.getLogger(NodeRepositoryTest.class);
	
	@Autowired
	private NodeRepository nodeRepository;
	
	@Test
	void testNearest() {
		CustomerServiceHour csh = CustomerServiceHour.builder().startService(Time.valueOf("09:00:00")).endService(Time.valueOf("18:00:00")).build();
		List<Node> nodes = new ArrayList<>(4);
		nodes.add(BranchOffice.builder().address("Av. Rivadavia 11626, C1408 CABA").lat(-34.649456).lng(-58.5195477).customerServiceHour(csh).build());
		nodes.add(BranchOffice.builder().address("Av. Rivadavia 6502, C1405 CABA").lat(-34.6439358).lng(-58.4398426).customerServiceHour(csh).build());
		nodes.add(BranchOffice.builder().address("Av. Sáenz 997, C1437 CABA").lat(-34.6883711).lng(-58.5022494).customerServiceHour(csh).build());
		nodes.add(BranchOffice.builder().address("Av. Corrientes 756, C1043 CABA").lat(-34.6439358).lng(-58.4398426).customerServiceHour(csh).build());
		nodes.add(BranchOffice.builder().address("Av. Corrientes 756, C1043 CABA").lat(-34.6439358).lng(-58.4398426).customerServiceHour(csh).build());
		
		nodes.stream().forEach(n->nodeRepository.save(n));
		
		Optional<Node> nearest = nodeRepository.nearest(-34.6079963, -58.3836569);
		Assert.assertTrue(nearest.isPresent());
		logger.info("Nearest to (-34.6079963, -58.3836569) is: {}", nearest.get());
	}
	
	@Test
	void testCrud() {
		assertThat(nodeRepository).isNotNull();
		CustomerServiceHour csh = CustomerServiceHour.builder().startService(Time.valueOf("09:00:00")).endService(Time.valueOf("18:00:00")).build();
		BranchOffice b = BranchOffice.builder().address("Av. Rivadavia 11626, C1408 CABA").lat(-34.649456).lng(-58.5195477).customerServiceHour(csh).build();
		Node nb = nodeRepository.save(b);
		Assert.assertNotNull(nb);
		assertThat((BranchOffice)nb).isEqualTo(b);
		logger.info("Node: {}", nb);
		
		Optional<Node> nodeData = nodeRepository.findById(nb.getId());
		Assert.assertTrue(nodeData.isPresent());
		assertThat(nodeData.get()).isEqualTo(nb);
		logger.info("Get: {}", nodeData.get());
		
		nodeRepository.deleteById(nb.getId());
		Optional<Node> nodeDel = nodeRepository.findById(nb.getId());
		Assert.assertFalse(nodeDel.isPresent());

		WithdrawalPoint wp = WithdrawalPoint.builder().lat(-34.6883711).lng(-58.5022494).capacity(200l).build();
		Node nw = nodeRepository.save(wp);
		Assert.assertNotNull(nw);
		assertThat((WithdrawalPoint)nw).isEqualTo(wp);
		logger.info("Node: {}", nw);

		Optional<Node> nodeData2 = nodeRepository.findById(nw.getId());
		Assert.assertTrue(nodeData2.isPresent());
		assertThat(nodeData2.get()).isEqualTo(nw);
		logger.info("Get: {}",nodeData2.get());

		nodeRepository.deleteById(nw.getId());
		Optional<Node> nodeDel2 = nodeRepository.findById(nw.getId());
		Assert.assertFalse(nodeDel2.isPresent());
	}
}
