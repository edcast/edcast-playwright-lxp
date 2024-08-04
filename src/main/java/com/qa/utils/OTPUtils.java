package com.qa.utils;

import java.util.Random;

import org.testng.annotations.Listeners;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

import io.qameta.allure.testng.AllureTestNg;
public class OTPUtils {


	public  String generateOTP(int length) {
		String numbers = "0123456789";
		Random random = new Random();
		StringBuilder otp = new StringBuilder(length);
		for (int i = 0; i < length; i++) {
			otp.append(numbers.charAt(random.nextInt(numbers.length())));
		}

		return otp.toString();
	}

	public  void sendSMS(String to, String message) {
		String ACCOUNT_SID = "ACe45971e064fb144a42e78fda20ff8848";
		String AUTH_TOKEN = "b79267fe5402e53bbd57b6446eaf6d74";
		Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
		Message sms = Message.creator(new PhoneNumber(to), 
				new PhoneNumber("+918076131669"), //Sample phone number, not registered
				message).create();

		//LOGGER.error("SMS sent successfully: " + sms.getSid());
	}

}
