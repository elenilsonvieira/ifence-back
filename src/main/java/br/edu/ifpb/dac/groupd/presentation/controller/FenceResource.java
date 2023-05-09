package br.edu.ifpb.dac.groupd.presentation.controller;


import java.net.URI;
import java.security.Principal;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import br.edu.ifpb.dac.groupd.business.exception.FenceEmptyException;
import br.edu.ifpb.dac.groupd.business.exception.FenceNotFoundException;
import br.edu.ifpb.dac.groupd.business.exception.NoBraceletAvailableException;
import br.edu.ifpb.dac.groupd.business.exception.UserNotFoundException;
import br.edu.ifpb.dac.groupd.business.service.FenceService;
import br.edu.ifpb.dac.groupd.business.service.converter.FenceConverterService;
import br.edu.ifpb.dac.groupd.model.entities.Fence;
import br.edu.ifpb.dac.groupd.presentation.dto.FenceRequest;
import br.edu.ifpb.dac.groupd.presentation.dto.FenceResponse;

@RestController
@RequestMapping({"/fences","/users/fences"})
public class FenceResource {
	// User Fence
	@Autowired
	private FenceService fenceService;
	
	@Autowired
	private FenceConverterService converter;
	
	
	@PostMapping
	public ResponseEntity<?> createFence(
			Principal principal,
			@Valid
			@RequestBody
			FenceRequest postDto) throws UserNotFoundException{
		Fence fence = fenceService.createFence(getPrincipalId(principal), postDto);
		
		FenceResponse dto = converter.fenceToResponse(fence);
		
		return ResponseEntity.status(HttpStatus.CREATED).location(toUri(fence)).body(dto);

	}
	@GetMapping
	public ResponseEntity<?> getAllFences(
			Principal principal, Pageable pageable
			) throws UserNotFoundException{
		Page<Fence> pageFences = fenceService.getAllFences(getPrincipalId(principal), pageable);
		Page<FenceResponse> dtos = pageFences
				.map(converter::fenceToResponse);
		
		return ResponseEntity.ok(dtos);

	}
	
	@GetMapping("/{fenceId}")
	public ResponseEntity<?> getAllFences(
			Principal principal,
			@PathVariable("fenceId") Long fenceId) throws UserNotFoundException, FenceNotFoundException{
		Fence bracelet = fenceService.findFenceById(getPrincipalId(principal), fenceId);
		
		FenceResponse dto = converter.fenceToResponse(bracelet);
		
		return ResponseEntity.ok(dto);

	}
	@GetMapping("/search")
	public ResponseEntity<?> searchFenceByName(
			Principal principal,
			@RequestParam("name") String name,
			Pageable pageable) throws UserNotFoundException{
		Page<Fence> bracelets = fenceService.searchFencesByName(getPrincipalId(principal), name, pageable);
		
		Page<FenceResponse> dto = bracelets.map(converter::fenceToResponse);
		
		return ResponseEntity.ok(dto);
	}
	
	@PutMapping("/{fenceId}")
	public ResponseEntity<?> updateFence(
			Principal principal,
			@PathVariable("fenceId") Long fenceId,
			@Valid
			@RequestBody
			FenceRequest postDto) throws UserNotFoundException, FenceNotFoundException{
		Fence fence = fenceService.updateFence(getPrincipalId(principal), fenceId, postDto);
		
		FenceResponse dto = converter.fenceToResponse(fence);
		
		return ResponseEntity.ok(dto);

	}
	@PatchMapping("/{fenceId}/setStatus")
	public ResponseEntity<?> setStatus(Principal principal,
			@PathVariable("fenceId") Long fenceId,
			@RequestParam(name="active", required=true) boolean active) throws FenceEmptyException, 
		FenceNotFoundException, 
		UserNotFoundException, 
		NoBraceletAvailableException{
		System.out.println(active);
		Fence fence = fenceService.setActive(getPrincipalId(principal), fenceId, active);

		for(Bracelet e:fence.getBracelets()){
			for(Fence j:e.getFences()){
				if(j.isActive()){
					return ResponseEntity.badRequest().body("A pulseira está ativa em outra cerca");
				}
			}
		}

		FenceResponse dto = converter.fenceToResponse(fence);
		
		return ResponseEntity.ok(dto);

	}
	
	@DeleteMapping("/{fenceId}")
	public ResponseEntity<?> deleteFence(
			Principal principal,
			@PathVariable("fenceId") Long fenceId) throws UserNotFoundException, FenceNotFoundException{
		fenceService.deleteFence(getPrincipalId(principal), fenceId);
		
		return ResponseEntity.noContent().build();

	}
	private URI toUri (Fence fence) {
		return ServletUriComponentsBuilder
				.fromCurrentRequestUri()
				.path("/{id}")
				.buildAndExpand(fence.getId())
				.toUri();
	}
	
	private Long getPrincipalId(Principal principal) {
		return Long.parseLong(principal.getName());
	}
	
	
}
