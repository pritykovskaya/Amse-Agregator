package ru.amse.agregator.gui.yalets;

import net.sf.xfresh.core.InternalRequest;
import net.sf.xfresh.core.InternalResponse;
import org.bson.types.ObjectId;
import ru.amse.agregator.storage.Database;
import ru.amse.agregator.storage.User;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Kirill Korgov (korgovk)
 * Date: 4/24/11
 */
public class LoginYalet extends AbstractAgregatorYalet {

    public void process(InternalRequest req, InternalResponse res) {

        Map<String, String> responseMap = new HashMap<String, String>();
        Map<String, String> forCookie = new HashMap<String, String>();
        Map<String, String> cookies = req.getCookies();
        final String emailCookie = cookies.get("email");
        final String pwdCookie = cookies.get("pwd");
        final String email = req.getParameter("email");
        final String pwd = req.getParameter("pwd");
        final String logout = req.getParameter("logout");

       if ("YES".equals(logout)) {
            final HashMap<String, String> result = new HashMap<String, String>();
            result.put("email", "");
            result.put("pwd", "");
            result.put("uid", "");
            res.setCookies(result);
            responseMap.put("AUTH", "-1");
            res.add(responseMap);
            return;
        }

        if (emailCookie != null && pwdCookie != null && !emailCookie.isEmpty() && !pwdCookie.isEmpty()) {
	        final User user = Database.getUser(emailCookie, pwdCookie);
            if (user != null) {
                //Already in the DB, everything OK
                forCookie.put("email", emailCookie);
                forCookie.put("pwd", pwdCookie);
                forCookie.put("uid", String.valueOf(user.getId()));
                res.setCookies(forCookie);
                responseMap.put("uid", String.valueOf(user.getId()));
                responseMap.put("AUTH", "2");
                responseMap.put("email", emailCookie);
                //res.redirectTo("index.xml");
            }
        } else if (email != null && pwd != null && !pwd.isEmpty() && !email.isEmpty()) {
            final User user = Database.getUser(email, pwd);
            if (user != null) {
                //Already in the DB, everything OK
                forCookie.put("email", email);
                forCookie.put("pwd", pwd);
                forCookie.put("uid", String.valueOf(user.getId()));
                res.setCookies(forCookie);
                responseMap.put("AUTH", "2");
                responseMap.put("uid", String.valueOf(user.getId()));
                responseMap.put("email", email);
                //res.redirectTo("index.xml");
            } else if ("YES".equals(req.getParameter("toRegister"))) {
                
                //No such user in the DB, User wanted to register?
                final User newUser = new User();
                newUser.setKeyValue(User.FIELD_LOGIN, email);
                newUser.setKeyValue(User.FIELD_PASSWORD, pwd);
                final ObjectId userId = Database.addUser(newUser);
                forCookie.put("email", email);
                forCookie.put("pwd", pwd);
                forCookie.put("uid", String.valueOf(userId));
                res.setCookies(forCookie);
                responseMap.put("AUTH", "1");
                responseMap.put("uid", String.valueOf(userId));
                responseMap.put("email", email);
            } else {
                //No such user in the DB, and don't want to register (bad pass possibly)
                responseMap.put("AUTH", "0");
            }
        } else {
            responseMap.put("AUTH", "-1");
        }

        res.add(responseMap);
    }
}
