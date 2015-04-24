import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import java.io.File;
import java.util.List;
import java.util.Properties;

public class SendMailSSL {

	/**
	 * This is a util method to send an e-mail from gmail via SSL
	 * @param from email from
	 * @param to email to
	 * @param subject subject
	 * @param text body of the email
	 */
	public void sendGMail(final String username, final String password, String from, String to, String subject, String text, List<String> adjuntos) {
		Properties props = new Properties();
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.socketFactory.port", "465");
		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.port", "465");

		Session session = Session.getDefaultInstance(props,
				new javax.mail.Authenticator() {
					protected PasswordAuthentication getPasswordAuthentication() {
						return new PasswordAuthentication(username,password);
					}
				});

		try {
			Message message = new MimeMessage(session);
			message.setFrom(new InternetAddress(from));
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
			message.setSubject(subject);
//			message.setText(text);

			//Se seteo el mensaje del e-mail
			MimeBodyPart messageBodyPart = new MimeBodyPart();
			messageBodyPart.setContent(message,"text/html");
			messageBodyPart.setText(text);

			Multipart multipart = new MimeMultipart();
			multipart.addBodyPart(messageBodyPart);

			//Se adjuntan los archivos al correo
			if( adjuntos!=null && adjuntos.size()>0 ){
				for( String rutaAdjunto : adjuntos ){
					messageBodyPart = new MimeBodyPart();
					File f = new File(rutaAdjunto);
					if( f.exists() ){
						DataSource source = new FileDataSource( rutaAdjunto );
						messageBodyPart.setDataHandler( new DataHandler(source) );
						messageBodyPart.setFileName( f.getName() );
						multipart.addBodyPart(messageBodyPart);
						}
					}
				}

			//Se junta el mensaje y los archivos adjuntos
			message.setContent(multipart);
			
			Transport.send(message);
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}
}