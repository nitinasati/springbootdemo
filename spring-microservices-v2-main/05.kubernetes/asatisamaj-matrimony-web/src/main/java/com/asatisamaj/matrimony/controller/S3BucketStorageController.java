package com.asatisamaj.matrimony.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.asatisamaj.matrimony.entities.MembersDetail;
import com.asatisamaj.matrimony.service.MemberDetailService;
import com.asatisamaj.matrimony.service.S3BucketStorageService;

import io.micrometer.core.instrument.util.StringUtils;

@Controller
@RequestMapping("matrimony/image")
public class S3BucketStorageController {

	@Value("${valid.file.extensions}")
	private String validFileExtensions;

	@Autowired
	S3BucketStorageService service;

	@Autowired
	public MemberDetailService memberDetailService;

	@Autowired
	private MessageSource messageSource;

	private static final Logger LOGGER = LogManager.getLogger(S3BucketStorageController.class);

	@PostMapping("/upload") // //new annotation since 4.3
	public ResponseEntity<String> singleFileUpload(@RequestParam("file") MultipartFile file,
			@RequestParam("fileName") String fileName, Authentication authentication) {

		if (!validateFile(file)) {
			LOGGER.info("File extension invalid ... ");
			return new ResponseEntity<>(
					messageSource.getMessage("profile.photo.valid.extensions", null, LocaleContextHolder.getLocale())
							+ " " + validFileExtensions,
					HttpStatus.BAD_REQUEST);
		}

		try {

			// Get the file and save it somewhere
			byte[] bytes = file.getBytes();
			Path path = Paths.get(file.getOriginalFilename());
			Files.write(path, bytes);
			String keyName = "matrimony/" + fileName + "/" + file.getOriginalFilename();
			String uploadMessage = service.uploadFile(keyName, file);
			LOGGER.info("Upload message {}", uploadMessage);
			MembersDetail membersDetail = memberDetailService.getMembersDetail(fileName, authentication);
			if (!StringUtils.isEmpty(membersDetail.getImagePath())
					&& !membersDetail.getImagePath().equalsIgnoreCase(keyName)) {
				service.deleteFile(membersDetail.getImagePath());
			}

			if (membersDetail.getMemberId() == null) {
				return new ResponseEntity<>(messageSource.getMessage("profile.photo.failure", null,
						LocaleContextHolder.getLocale()), HttpStatus.INTERNAL_SERVER_ERROR);
			}
			long millis = System.currentTimeMillis();
			membersDetail.setImagePath(keyName);
			membersDetail.setUpdateDate(new Date(millis));
			membersDetail.setUpdateProgram("website-update");
			membersDetail.setUpdateUser("update");
			memberDetailService.saveMember(membersDetail);

			return new ResponseEntity<>(messageSource.getMessage("profile.photo.success", null,
					LocaleContextHolder.getLocale()), HttpStatus.OK);

		} catch (IOException e) {
			LOGGER.error("Unexpected error while uploading photo ", e);
		}

		return new ResponseEntity<>(messageSource.getMessage("profile.photo.failure", null,
				LocaleContextHolder.getLocale()), HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@GetMapping(value = "/download")
	public ResponseEntity<byte[]> downloadFile(@RequestParam(value = "fileName") String filename) {
		ByteArrayOutputStream downloadInputStream = service.downloadFile(filename);

		return ResponseEntity.ok().contentType(contentType(filename))
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + filename + "\"")
				.body(downloadInputStream.toByteArray());
	}

	private MediaType contentType(String filename) {
		String[] fileArrSplit = filename.split("\\.");
		String fileExtension = fileArrSplit[fileArrSplit.length - 1];
		switch (fileExtension) {
		case "txt":
			return MediaType.TEXT_PLAIN;
		case "png":
			return MediaType.IMAGE_PNG;
		case "jpg":
			return MediaType.IMAGE_JPEG;
		default:
			return MediaType.APPLICATION_OCTET_STREAM;
		}
	}

	private boolean validateFile(MultipartFile file) {

		String fileName = file.getOriginalFilename();
		String[] fileArrSplit = fileName.split("\\.");
		String fileExtension = fileArrSplit[fileArrSplit.length - 1];

		return validFileExtensions.contains(fileExtension);
	}
}
