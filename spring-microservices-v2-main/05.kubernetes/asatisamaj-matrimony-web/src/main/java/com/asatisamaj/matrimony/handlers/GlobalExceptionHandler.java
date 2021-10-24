package com.asatisamaj.matrimony.handlers;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class GlobalExceptionHandler {

    @Value("${spring.servlet.multipart.max-file-size}")
    private String fileSize;
	
    @Autowired
    private MessageSource messageSource;
    
	private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
	
    //https://jira.spring.io/browse/SPR-14651
    //Spring 4.3.5 supports RedirectAttributes
    @ExceptionHandler(MultipartException.class)
    public ResponseEntity<String> handleError1(MultipartException e) {
    	LOGGER.error(e.getMessage());
    	
    	if(null != e.getMessage() && e.getMessage().contains("Maximum upload size exceeded"))
    	{
    		
    		return new ResponseEntity<>(messageSource.getMessage("profile.photo.exceed.file.size", null,LocaleContextHolder.getLocale()) + fileSize, HttpStatus.INTERNAL_SERVER_ERROR);
    	}
    	
        return new ResponseEntity<>("\"Server error has occured\"", HttpStatus.INTERNAL_SERVER_ERROR);

    }

    /* Spring < 4.3.5
	@ExceptionHandler(MultipartException.class)
    public String handleError2(MultipartException e) {

        return "redirect:/errorPage";

    }*/

}