package com.way2sms.controllers;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.way2sms.pojo.SmsResponse;

@Controller
public class SmsController {

	@RequestMapping(value = "/sendSMS", method = RequestMethod.POST)
	public String sendSms(Model model, @RequestParam("mobile") String mobile, @RequestParam("text") String textMessage) {
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
		
		System.out.println("status code is : " + result.getStatusCode());
		System.out.println("Response from sms gateway api : " + result.getBody());
		String body = result.getBody();

		Gson gson = new Gson();
		SmsResponse smsResponse = gson.fromJson(body, SmsResponse.class);
		
		System.out.println(smsResponse.getErrorCode());
		System.out.println(smsResponse.getErrorMessage());

		if(smsResponse.getErrorCode().equals("000")) {
			model.addAttribute("successMsg", "Your message sent successful!!");
		} else {
			model.addAttribute("errorMsg", smsResponse.getErrorMessage());			
		}
		
		return "way2sms";
	}
}
