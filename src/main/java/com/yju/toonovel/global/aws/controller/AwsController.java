package com.yju.toonovel.global.aws.controller;

import java.io.Serializable;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.yju.toonovel.global.aws.service.S3Service;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/aws")
@RequiredArgsConstructor
public class AwsController {

	private final S3Service s3Service;

	@GetMapping
	@ResponseStatus(HttpStatus.OK)
	public void getHealthCheck() {
	}

	@GetMapping("/s3")
	@ResponseStatus(HttpStatus.OK)
	public Map<String, Serializable> getPreSignedUrl(@RequestParam String fileName) {
		return s3Service.getPreSignedUrl(fileName);
	}

}
