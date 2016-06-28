package controllers;

import play.*;
import play.cache.Cache;
import play.libs.Images;
import play.mvc.*;
import java.util.*;
import com.sendgrid.*;

import javax.mail.Message;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import models.*;

public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void index(String exito) {
		render(exito);
	}

	public static void captcha(String id) {
		Images.Captcha captcha = Images.captcha();
		String code = captcha.getText("#be0411");
		Cache.set(id, code, "10mn");
		renderBinary(captcha);
	}

	public static void hacerReserva(String fecha, String nomCompleto, String telefono){
		SendGrid sendgrid = new SendGrid("app51353483@heroku.com", "vunprhty3457");

		SendGrid.Email email = new SendGrid.Email();
		email.addTo("marperuanosm@gmail.com");
		email.setFrom("marperuanosm@gmail.com");
		email.setSubject("Reserva para la fecha: "+fecha);
		email.setText("El señor(a) "+nomCompleto+" ha solicitado hacer una reserva en el restaurante para la fecha "+fecha+". Este es su número de contacto: "+telefono);

		try {
			SendGrid.Response response = sendgrid.send(email);
		}catch (SendGridException e) {
			System.out.println(e);
		}
	}

	//	public static void hacerReserva(String fecha, String nomCompleto, String telefono){
	//		final String email="marperuanosm@gmail.com";
	//		final String password="marperuano2015";
	//		
	//		Properties props = new Properties();
	//		props.put("mail.smtp.auth", "true");
	//		props.put("mail.smtp.starttls.enable", "true");
	//		props.put("mail.smtp.host", "smtp.gmail.com");
	//		props.put("mail.smtp.socketFactory.port", "465");
	//		props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
	//		props.put("mail.smtp.ssl.enable", "true");
	//		
	//		Session session = Session.getInstance(props,
	//				new javax.mail.Authenticator(){
	//			protected PasswordAuthentication getPasswordAuthentication(){
	//				return new PasswordAuthentication(email, password);
	//			}
	//		});
	//		
	//		try{
	//			Message message = new MimeMessage(session);
	//			message.setFrom(new InternetAddress("marperuanosm@gmail.com"));
	//			message.addRecipients(Message.RecipientType.TO, InternetAddress.parse("marperuanosm@gmail.com"));
	//			message.setSubject("Reserva para la fecha: "+fecha);
	//			message.setText("El señor(a) "+nomCompleto+" ha solicitado hacer una reserva en el restaurante para la fecha "+fecha+". Este es su número de contacto: "+telefono);
	//			
	//			Transport.send(message);
	//			
	//			index("si");
	//			
	//		}catch(Exception e){
	//			e.printStackTrace();
	//			index("no");
	//		}
	//	}

}