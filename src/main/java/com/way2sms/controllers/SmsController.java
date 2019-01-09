package com.way2sms.controllers;

import javax.swing.text.html.FormSubmitEvent.MethodType;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class SmsController {

	@RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
	public String sendSms(@RequestParam("mobile") String mobile, @RequestParam("text") String textMessage) {
		System.out.println(mobile);
		System.out.println(textMessage);
		// Hit sms gateway api from here to send sms

		StringBuffer requestUrl = new StringBuffer("https://www.smsgatewayhub.com");
		requestUrl.append("/api/mt/SendSMS?");
		requestUrl.append("APIKey=").append("wc4VKZTMqk6fEuC7zA5pJg");
		requestUrl.append("&senderid=").append("SMSTST");
		requestUrl.append("&channel=").append("2");
		requestUrl.append("&DCS=").append("0");
		requestUrl.append("&flashsms=").append("0");
		requestUrl.append("&number=").append(mobile);
		requestUrl.append("&text=").append(textMessage);
		requestUrl.append("&route=").append("13");
		
		HttpHeaders headers = new HttpHeaders();
		
		HttpEntity<String> entity = new HttpEntity<String>("",headers);
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> result = restTemplate.exchange(requestUrl.toString(), HttpMethod.POST, entity, String.class);
		String response = result.getBody();
		System.out.println(response);

		return "way2sms";
	}
}
