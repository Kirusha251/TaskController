package by.achramionok.controller;

import by.achramionok.email.VerificationToken;
import by.achramionok.model.User;
import by.achramionok.repository.UserRepository;
import by.achramionok.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Kirill on 10.04.2017.
 */
@RestController
public class RegistrationCompleteController {
    @Autowired
    private IUserService service;

    @Autowired
    private UserRepository userRepository;
    @RequestMapping(value = "/regitrationConfirm", method = RequestMethod.GET)
    public ResponseEntity confirmRegistration(HttpServletRequest request, @RequestParam("token") String token){
        Locale locale = request.getLocale();
        VerificationToken verificationToken = service.getVerificationToken(token);
        if(verificationToken == null){
            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        User user = verificationToken.getUser();
        Calendar cal = Calendar.getInstance();
        if ((verificationToken.getExpiryDate().getTime() - cal.getTime().getTime()) <= 0) {

            return new ResponseEntity(HttpStatus.BAD_REQUEST);
        }
        //user.setEnabled(true);
        User user1 = userRepository.findByEmail(user.getEmail());
        user1.setEnabled(true);
        userRepository.save(user1);
        return new ResponseEntity(HttpStatus.OK);
    }

}
