package com.dp.fravega.sucursal.controller;

import com.dp.fravega.sucursal.model.BranchOffice;
import com.dp.fravega.sucursal.model.Node;
import com.dp.fravega.sucursal.model.WithdrawalPoint;
import com.dp.fravega.sucursal.repository.NodeRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/api")
public class NodeController {

	@Autowired
	NodeRepository nodeRepository;

	@Operation(summary = "Get a node by its id.")
	@GetMapping("/node/{id}")
	public ResponseEntity<Node> getNodeById(@PathVariable("id") long id) {
		Optional<Node> nodoData = nodeRepository.findById(id);

		if (nodoData.isPresent()) {
			return new ResponseEntity<>(nodoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Create a node (Branch office).")
	@PostMapping("/node/branchOffice")
	public ResponseEntity<BranchOffice> createBranchOffice(@RequestBody BranchOffice node) {
		try {
			Node _node = nodeRepository.save(node);
			return new ResponseEntity<>((BranchOffice) _node, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Create a node (Withdrawal point).")
	@PostMapping("/node/withdrawalPoint")
	public ResponseEntity<WithdrawalPoint> createWithdrawalPoint(@RequestBody WithdrawalPoint node) {
		try {
			Node _node = nodeRepository.save(node);
			return new ResponseEntity<>((WithdrawalPoint) _node, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Update a node (Branch office).")
	@PutMapping("/nodo/branchOffice/{id}")
	public ResponseEntity<BranchOffice> updateBranchOffice(@PathVariable("id") long id, @RequestBody BranchOffice node) {
		Optional<Node> nodoData = nodeRepository.findById(id);

		if (nodoData.isPresent()) {
			BranchOffice _node = (BranchOffice)nodoData.get();
			_node.setLat(node.getLat());
			_node.setLng(node.getLng());
			_node.setAddress(node.getAddress());
			return new ResponseEntity<>(nodeRepository.save(_node), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Update a node (Withdrawal point).")
	@PutMapping("/nodo/withdrawalPoint/{id}")
	public ResponseEntity<WithdrawalPoint> updateWithdrawalPoint(@PathVariable("id") long id, @RequestBody WithdrawalPoint node) {
		Optional<Node> nodoData = nodeRepository.findById(id);

		if (nodoData.isPresent()) {
			WithdrawalPoint _node = (WithdrawalPoint)nodoData.get();
			_node.setLat(node.getLat());
			_node.setLng(node.getLng());
			_node.setCapacity(node.getCapacity());
			return new ResponseEntity<>(nodeRepository.save(_node), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@Operation(summary = "Delete a node by its id.")
	@DeleteMapping("/node/{id}")
	public ResponseEntity<HttpStatus> deleteNode(@PathVariable("id") long id) {
		try {
			nodeRepository.deleteById(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@Operation(summary = "Get nearest node by lat, lng.")
	@GetMapping("/nearestNode")
	public ResponseEntity<Node> nearestNode(@RequestParam(name="lat") double lat, @RequestParam(name="lng") double lng) {
		Optional<Node> nodoData = nodeRepository.nearest(lat, lng);
		if (nodoData.isPresent()) {
			return new ResponseEntity<>(nodoData.get(), HttpStatus.OK);
		} else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
