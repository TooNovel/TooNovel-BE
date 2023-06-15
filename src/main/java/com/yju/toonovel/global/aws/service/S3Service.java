package com.yju.toonovel.global.aws.service;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.yju.toonovel.global.config.AwsConfig;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class S3Service {

	private final AwsConfig awsConfig;

	@Value("${cloud.aws.s3.bucket}")
	private String bucket;

	public Map<String, Serializable> getPreSignedUrl() {
		String encodedFileName = UUID.randomUUID().toString();
		String objectKey = "image/" + encodedFileName;

		Date expiration = new Date();
		long expTimeMillis = expiration.getTime();
		expTimeMillis += (2 * 60 * 1000);
		expiration.setTime(expTimeMillis);

		GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, objectKey)
			.withMethod(HttpMethod.PUT)
			.withExpiration(expiration);

		Map<String, Serializable> result = new HashMap<>();
		result.put("preSignedUrl", awsConfig.amazonS3().generatePresignedUrl(generatePresignedUrlRequest));
		result.put("encodedFileName", encodedFileName);

		return result;
	}
}
