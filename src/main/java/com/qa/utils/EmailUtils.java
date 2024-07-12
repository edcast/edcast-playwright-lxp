package com.qa.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMultipart;
import javax.mail.search.FlagTerm;
import org.apache.commons.lang3.StringUtils;
import java.io.FileOutputStream;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import static com.qa.utils.Constants.FILE_PATH.FILE_DOWNLOADED;
public class EmailUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

//	public static String getConfirmationLink(String email, String password) {
//		try {
//			Thread.sleep(5000);
//			PropertiesEmail propertiesEmail = new PropertiesEmail(email, password);
//			Properties props = propertiesEmail.setServerProperties();
//			Session session = Session.getDefaultInstance(props);
//			Store store = session.getStore();
//			store.connect(propertiesEmail.host, email, password);
//			Folder folder = store.getFolder("Inbox");
//			folder.open(Folder.READ_WRITE);
//			LOGGER.info("Total messages Inbox: " + folder.getMessageCount());
//			LOGGER.info("Unread messages: " + folder.getUnreadMessageCount());
//			FlagTerm flag = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
//			Message[] unreadMessage = folder.search(flag);
//			LOGGER.info("Unread indox messages : ");
//			for (int i = 0, n = unreadMessage.length; i < n; i++) {
//				Message message = unreadMessage[i];
//				LOGGER.info("--------------");
//				LOGGER.info("Subject: " + message.getSubject());
//			}
//			for (int i = 0, n = unreadMessage.length; i < n; i++) {
//				Message message = unreadMessage[i];
//				if (message.getSubject().contains("EdCast")) {
//					String result = "";
//					result = getTextFromMimeMultipart((MimeMultipart) message.getContent());
//					result = result.replace("\"", "");
//					result = result.substring(result.indexOf("https://mandrillapp"));
//					result = result.substring(result.indexOf("https://mandrillapp"), result.indexOf("style=") - 1);
//					LOGGER.info("Confirmation link is : " + result);
//					return result;
//				}
//			}
//			folder.close(false);
//			store.close();
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}

//	public static String getConfirmationEmailSubject(String emailSearchQuery) {
//		try {
//			Thread.sleep(5000);
//			PropertiesEmail propertiesEmail = new PropertiesEmail("akshaykblera@gmail.com", "gan123@ram.COM");
//			Properties props = propertiesEmail.setServerProperties();
//			Session session = Session.getDefaultInstance(props);
//			Store store = session.getStore();
//			store.connect(propertiesEmail.host, "akshaykblera@gmail.com", "gan123@ram.COM");
//			Folder folder = store.getFolder("Inbox");
//			folder.open(Folder.READ_WRITE);
//			LOGGER.info("Total messages Inbox: " + folder.getMessageCount());
//			LOGGER.info("Unread messages: " + folder.getUnreadMessageCount());
//			FlagTerm flag = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
//			Message[] unreadMessage = folder.search(flag);
//			LOGGER.info("Unread indox messages : ");
//			for (int i = 0, n = unreadMessage.length; i < n; i++) {
//				Message message = unreadMessage[i];
//				LOGGER.info("--------------");
//				LOGGER.info("Subject: " + message.getSubject());
//			}
//
//			for (int i = 0, n = unreadMessage.length; i < n; i++) {
//				Message message = unreadMessage[i];
//				if (message.getSubject().contains(emailSearchQuery)) {
//					return message.getSubject();
//				}
//			}
//			folder.close(false);
//			store.close();
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//
//	private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
//		String result = "";
//		int partCount = mimeMultipart.getCount();
//		for (int i = 0; i < partCount; i++) {
//			BodyPart bodyPart = mimeMultipart.getBodyPart(i);
//
//			if (bodyPart.isMimeType("text/html")) {
//				String html = (String) bodyPart.getContent();
//				// result = result + "\n" + org.jsoup.Jsoup.parse(html).text();
//				result = html;
//			} else if (bodyPart.getContent() instanceof MimeMultipart) {
//				result = result + getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent());
//			}
//		}
//		return result;
//	}

	public enum FolderName {

		INBOX("inbox"), SPAM("[Gmail]/Spam");

		private String folderName;

		FolderName(String folderName) {
			this.folderName = folderName;
		}

		public String getFolderName() {
			return folderName;
		}
	}

	public String getEmailInfo(String user, String password,String mailSubjectLine)  {
		waitUntilEmailPresent(user, password, mailSubjectLine, null);
		Object object = readLastMessage(user, password);
		String url = StringUtils.substringBetween(object.toString(), "Start Now <", ">");
				LOGGER.info("URL from gmail: " + url);
		return url;
	}

//	public String getConfirmLinkFromEmail1(String user, String password) throws InterruptedException {
//		waitUntilEmailPresent(user, password, "Demo, your PDF Transcript", null);
//		;
//		Object object = readLastMessage(user, password);
//		String url = StringUtils.substringBetween(object.toString(), "Start Now <", ">");
//		if (url == null) {
//			object = readLastMessage(user, password, 0, FolderName.SPAM);
//			url = StringUtils.substringBetween(object.toString(), "Email Address\r\n<", ">");
//		}
//		LOGGER.info("URL from gmail: " + url);
//		return url;
//	}

	public Object readLastMessage(String user, String password) {
		return readLastMessage(user, password, 0, FolderName.INBOX);
	}

	public void waitUntilEmailPresent(String user, String password, String subject, String recipientEmail)
		 {
		boolean isEmailPresent;
		int attempt = 30;
		do {
			isEmailPresent = isMessagePresent(user, password, subject, recipientEmail);
			try {
			Thread.sleep(5000);
			}
			catch (Exception e) {
				// TODO: handle exception
			}
		} while (!isEmailPresent && attempt-- >= 0);
	}

	public boolean isMessagePresent(String user, String password, String subject, String recipientEmailAddress) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		props.setProperty("mail.imaps.ssl.enable", "true");
		props.setProperty("mail.imaps.starttls.enable", "true");
		props.setProperty("mail.imaps.ssl.protocols", "TLSv1.2");
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", user, password);

			Folder inbox = store.getFolder(FolderName.INBOX.getFolderName());
			inbox.open(Folder.READ_ONLY);
			LOGGER.info("Total messages" + FolderName.INBOX.getFolderName() + ": " + inbox.getMessageCount());
			LOGGER.info("Unread messages: " + inbox.getUnreadMessageCount());
			FlagTerm flag = new FlagTerm(new Flags(Flags.Flag.SEEN), false);
			Message[] unreadMessage = inbox.search(flag);
			LOGGER.info("Unread inbox messages : ");
			for (int i = 0, n = unreadMessage.length; i < n; i++) {
				Message message = unreadMessage[i];
				LOGGER.info("--------------");
				LOGGER.info("Subject: " + message.getSubject());
			}

			for (int i = 0, n = unreadMessage.length; i < n; i++) {
				Message message = unreadMessage[i];
				InternetAddress[] addresses = (InternetAddress[]) message.getAllRecipients();
				if (recipientEmailAddress != null) {
					String recipientEmail = addresses[0].getAddress();
					if (message.getSubject().contains(subject) && recipientEmail.equals(recipientEmailAddress)) {
						return true;
					}
				} else if (message.getSubject().contains(subject)) {
					return true;
				}
			}
			inbox.close(false);
			store.close();

		} catch (NoSuchProviderException e) {
			LOGGER.error("Error:" + e);
		} catch (MessagingException e) {
			LOGGER.error("Error:" + e);
			;
		}
		return false;
	}
//	public void  getFileDownloadFromEmail1(String user, String password) throws InterruptedException {
//		waitUntilEmailPresent(user, password, "Demo, your PDF Transcript", null);
//		downloadFileFromMail(user,password,1,FolderName.INBOX);
//		
//	}
//	public void downloadFileFromMail(String user, String password, int bodyPartNumber, FolderName folderName) {
//		Object object = readLastMessage(user, password, bodyPartNumber, folderName);
//		if (object instanceof Multipart) {
//			Multipart multipart = (Multipart) object;
//			try {
//            BodyPart bodyPart = multipart.getBodyPart(bodyPartNumber);
//			
//
//				object = bodyPart.getContent();
//
//				for (int i = 0; i < multipart.getCount(); i++) {
//					BodyPart part = multipart.getBodyPart(i);
//					if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
//						String fileName = part.getFileName();
//						File file = new File(File.separator + fileName);
//						try (FileOutputStream outputStream = new FileOutputStream(file)) {
//							((MimeBodyPart) part).saveFile("download");
//							LOGGER.info("Attachment saved successfully: " + file.getAbsolutePath());
//
//						}
//					}
//				}
//			} catch (MessagingException | IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//
//			}
//		}
//	}

	public Object readLastMessage(String user, String password, int bodyPartNumber, FolderName folderName) {
		Properties props = System.getProperties();
		props.setProperty("mail.store.protocol", "imaps");
		props.setProperty("mail.imaps.ssl.enable", "true");
		props.setProperty("mail.imaps.starttls.enable", "true");
		props.setProperty("mail.imaps.ssl.protocols", "TLSv1.2");
		Object object = null;
		try {
			Session session = Session.getDefaultInstance(props, null);
			Store store = session.getStore("imaps");
			store.connect("imap.gmail.com", user, password);

			Folder inbox = store.getFolder(folderName.getFolderName());
			inbox.open(Folder.READ_ONLY);
			int messageCount = inbox.getMessageCount();
			LOGGER.info("Total Messages:- " + messageCount);
			Message lastMessage = inbox.getMessage(messageCount);
			LOGGER.info("------------------------------");
			object = lastMessage.getContent();
			if (object instanceof Multipart) {
				Multipart multipart = (Multipart) object;
				try {
	            BodyPart bodyPart = multipart.getBodyPart(bodyPartNumber);	
					object = bodyPart.getContent();

					for (int i = 0; i < multipart.getCount(); i++) {
						BodyPart part = multipart.getBodyPart(i);
						if (Part.ATTACHMENT.equalsIgnoreCase(part.getDisposition())) {
							String fileName = part.getFileName();
							File file = new File(FILE_DOWNLOADED+ fileName);
	                        ((MimeBodyPart) part).saveFile(file);
								LOGGER.info("Attachment saved successfully: " + file.getAbsolutePath());
							}
						}
					
				} catch (MessagingException | IOException e) {
					LOGGER.info("Attachment not saved successfully");
					e.printStackTrace();

				}
			}	
			
			inbox.close(true);
			store.close();

		} catch (IOException e) {
			LOGGER.error("Error:" + e);
		} catch (NoSuchProviderException e) {
			LOGGER.error("Error:" + e);
		} catch (MessagingException e) {
			LOGGER.error("Error:" + e);
		}
		return object;
	}

}
