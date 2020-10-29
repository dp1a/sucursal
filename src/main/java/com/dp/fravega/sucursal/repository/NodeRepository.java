package com.dp.fravega.sucursal.repository;

import com.dp.fravega.sucursal.model.Node;
import com.harium.storage.kdtree.KDTree;
import com.harium.storage.kdtree.exception.KeyDuplicateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NodeRepository extends JpaRepository<Node, Long> {
	Logger logger = LoggerFactory.getLogger(NodeRepository.class);
	default Optional<Node> nearest(final double lat, final double lng) {
		KDTree<Node> tree = new KDTree<>(2);
		findAll().forEach(n -> {
			try {
				tree.insert(n.getPos(), n);
			} catch (KeyDuplicateException kde) {
				logger.info("Duplicated Key: {}", n);
				// Do Nothing
			}
		});
		return Optional.of(tree.nearest(new double[]{lat, lng}));
	}
}
