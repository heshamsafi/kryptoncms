package edu.asu.krypton.controllers.webservices;

import java.math.BigInteger;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import edu.asu.krypton.exceptions.CustomRuntimeException;
import edu.asu.krypton.model.repository.ServerKeyRepository;
import edu.asu.krypton.service.crypto.aes.AESCryptoSystem;
import edu.asu.krypton.service.crypto.ecc.ECCryptoSystem;
import edu.asu.krypton.service.crypto.ecc.ECKey;
import edu.asu.krypton.service.crypto.ecc.ECPoint;
import edu.asu.krypton.service.crypto.rsa.RSACryptoSystem;

@Controller
@RequestMapping(value="/newApp")
public class CryptoWebService extends edu.asu.krypton.controllers.Controller{

	@Autowired(required=true)
	private AESCryptoSystem aes;
	@Autowired(required=true)
	private ECCryptoSystem ecc;
	@Autowired(required=true)
	private RSACryptoSystem rsa;
	@Autowired(required=true)
	private ServerKeyRepository serverKeyRepository;
	
	private final String CREATE_APP_VIEW = "createApp";
	private final String DEFAULT_BODIES_DIR = "bodies/";
	private final String CREATE_APP_ERROR = "app_creation_error";
	private final String CREATE_APP_SUCCESS = "app_created_successfully";
	
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public String createApp(ModelMap model,HttpServletRequest request) {
		return appropriateView(request, DEFAULT_BODIES_DIR+CREATE_APP_VIEW, defaultView(model,CREATE_APP_VIEW));
	}
	
	@RequestMapping(value = "/submit", method = RequestMethod.GET)
	public String submitAppKeys(@RequestParam("appName") String appName, @RequestParam("publicKey_x") String publicKey_x, @RequestParam("publicKey_y") String publicKey_y, @RequestParam("n") String n, @RequestParam("e") String e, Model model, HttpServletRequest request) throws Exception{
		try{
			System.out.println("reached");
			if(appName==null||publicKey_x==null||publicKey_y==null||n==null||e==null)
				throw new CustomRuntimeException("You must fill all input fields");
			if(ecc.createApp(appName, new BigInteger(publicKey_x), new BigInteger(publicKey_y)) && rsa.createApp(appName, new BigInteger(n), new BigInteger(e))){
				
				BigInteger e_server = serverKeyRepository.getServerKey().getE();
				BigInteger n_server = serverKeyRepository.getServerKey().getN();
				ECPoint ec_public_server = new ECKey(ecc.getEc(), serverKeyRepository.getServerKey().getSk(), true).getPublic().beta;
				BigInteger ec_public_x_server = ec_public_server.getx();
				BigInteger ec_public_y_server = ec_public_server.gety();
				
				model.addAttribute("hint", "While calculating the symmetric secret key using Diffie Hellman algorithm, use the first 16 bytes from the X part only resulting from the Diffie Hellman process as the AES secret symmetric key");
				model.addAttribute("ec_base_x", ecc.getEc().getGenerator().getx());
				model.addAttribute("ec_base_y", ecc.getEc().getGenerator().gety());
				model.addAttribute("ec_order", ecc.getEc().getOrder());
				model.addAttribute("ec_a", ecc.getEc().geta());
				model.addAttribute("ec_b", ecc.getEc().getb());
				model.addAttribute("ec_p", ecc.getEc().getp());
				model.addAttribute("e_server", e_server);
				model.addAttribute("n_server", n_server);
				model.addAttribute("ec_public_x_server", ec_public_x_server);
				model.addAttribute("ec_public_y_server", ec_public_y_server);
				
				return appropriateView(request, DEFAULT_BODIES_DIR+CREATE_APP_SUCCESS, defaultView(model,CREATE_APP_SUCCESS));
			}
			// unreachable by the way
			return null;
		} catch(CustomRuntimeException ex){
			System.out.println("reached");
			model.addAttribute("message", ex.getMessage());
			return appropriateView(request, DEFAULT_BODIES_DIR+CREATE_APP_ERROR, defaultView(model,CREATE_APP_ERROR));
		}
	}

	public AESCryptoSystem getAes() {
		return aes;
	}

	public void setAes(AESCryptoSystem aes) {
		this.aes = aes;
	}

	public ECCryptoSystem getEcc() {
		return ecc;
	}

	public void setEcc(ECCryptoSystem ecc) {
		this.ecc = ecc;
	}

	public RSACryptoSystem getRsa() {
		return rsa;
	}

	public void setRsa(RSACryptoSystem rsa) {
		this.rsa = rsa;
	}

	public ServerKeyRepository getServerKeyRepository() {
		return serverKeyRepository;
	}

	public void setServerKeyRepository(ServerKeyRepository serverKeyRepository) {
		this.serverKeyRepository = serverKeyRepository;
	}
}