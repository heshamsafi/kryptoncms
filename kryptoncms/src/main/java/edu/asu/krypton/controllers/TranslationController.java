package edu.asu.krypton.controllers;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.memetix.mst.language.Language;
import com.memetix.mst.translate.Translate;

@Controller
@RequestMapping(value = "/translate")
public class TranslationController extends edu.asu.krypton.controllers.Controller {

	//private static final Logger logger = LoggerFactory.getLogger(TranslationController.class);

	private final String TRANSLATE_VIEW = "translate";
	
	private final String DEFAULT_BODIES_DIR = "bodies/";
	
	@RequestMapping(method = RequestMethod.GET)
	public String defaultView(ModelMap model,HttpServletRequest request)  {
		return appropriateView(request, DEFAULT_BODIES_DIR+TRANSLATE_VIEW, defaultView(model, TRANSLATE_VIEW));
	}
	

	@RequestMapping(value="tr",method = RequestMethod.POST, produces=MediaType.TEXT_PLAIN_VALUE)
	public @ResponseBody void home(String fromText,
			String fromLang,
			String toLang,
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Set your Windows Azure Marketplace client info - See
		// http://msdn.microsoft.com/en-us/library/hh454950.aspx
		//res.setContentType("text/plain; charset=UTF-8");
		int NUMBER_OF_TRIALS = 5;
		for(int i=0;i<NUMBER_OF_TRIALS;i++) {
			try {
				Translate.setClientId("javaprogram");
				Translate.setClientSecret("waledmeselhymohamedmeselhy");
				response.setCharacterEncoding("utf-8");
			    response.getWriter().write(Translate.execute(fromText, Language.valueOf(fromLang),Language.valueOf(toLang)));
			    return;
			} catch (Exception ex) {}
		}
		response.getWriter().write("ERROR : either connection is down or account expired !");
	}
}
