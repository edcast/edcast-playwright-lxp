package com.qa.testcases;

	
	import com.twilio.Twilio;
	import com.twilio.rest.api.v2010.account.Message;
	import com.twilio.type.PhoneNumber;

	public class OTPSender {
	    public static final String ACCOUNT_SID = "ACe45971e064fb144a42e78fda20ff8848";
	    public static final String AUTH_TOKEN = "b79267fe5402e53bbd57b6446eaf6d74";

	    public static void sendSMS(String to, String message) {
	        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

	        Message sms = Message.creator(
	                new PhoneNumber(to), // to
	                new PhoneNumber("+918076131669"), // from
	                message)
	            .create();

	        System.out.println("SMS sent successfully: " + sms.getSid());
	    }

	    public static void main(String[] args) {
	        String to = "+919717467417"; // recipient's phone number
	        String otp = OTPGenerator.generateOTP(6);
	        String message = "Your OTP code is: " + otp;

	        sendSMS(to, message);
	    }
	}


