package controllers;

import play.*;
import play.cache.Cache;
import play.libs.Images;
import play.mvc.*;

import java.util.*;

import models.*;

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

}