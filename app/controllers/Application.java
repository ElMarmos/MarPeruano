package controllers;

import play.cache.Cache;
import play.libs.Images;
import play.mvc.*;
import com.sendgrid.*;

public class Application extends Controller {

	public static void index() {
		render();
	}

	public static void captcha(String id) {
		Images.Captcha captcha = Images.captcha();
		String code = captcha.getText("#be0411");
		Cache.set(id, code, "10mn");
		renderBinary(captcha);
	}

	public static void hacerReserva(String fecha, String nomCompleto, String telefono){
		SendGrid sendgrid = new SendGrid("<correoaqui>", "<contraqui>");

		SendGrid.Email email = new SendGrid.Email();
		email.addTo("<correorecibe>");
		email.setFrom("<correoenvia>");
		email.setSubject("Reserva para la fecha: "+fecha);
		email.setText("El señor(a) "+nomCompleto+" ha solicitado hacer una reserva en el restaurante para la fecha "+fecha+". Este es su número de contacto: "+telefono);

		try {
			SendGrid.Response response = sendgrid.send(email);
			
			renderArgs.put("exito","si");
			renderTemplate("Application/index.html");
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