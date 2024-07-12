package com.qa.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.search.FlagTerm;
import org.apache.commons.lang3.StringUtils;
import java.io.File;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import static com.qa.utils.Constants.FILE_PATH.FILE_DOWNLOADED;
public class EmailUtils {

	private static final Logger LOGGER = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());


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
