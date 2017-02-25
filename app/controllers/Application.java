package controllers;

import play.cache.Cache;
import play.libs.Images;
import play.mvc.*;

import java.io.File;

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
		SendGrid sendgrid = new SendGrid("app51353483@heroku.com", "vunprhty3457");

		SendGrid.Email email = new SendGrid.Email();
		email.addTo("marperuanosm@gmail.com");
		email.setFrom("marperuanosm@gmail.com");
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
	
	public static void pdf(){
		renderBinary(new File("public/pdf/carta-mar-peruano.pdf"));
	}

}