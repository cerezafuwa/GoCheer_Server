package webAPP;

import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.filter.HTTPBasicAuthFilter;
import com.sun.jersey.core.util.MultivaluedMapImpl;
import dao.AchievementDAO;
import dao.UserDAO;
import entity.Achievement;
import entity.User;

import javax.ws.rs.core.MediaType;

/**
 * Created by orient on 2016/12/11.
 */
public class SendMail {

    public static void SendCongratulations(User user, Achievement achievement){
        // TODO: 新开线程来进行发邮件操作
        // TODO: 可以将固定不变的初始化部分缓存起来（比如client什么的）
        String alas=user.getAlias();
        String email=user.getEmail();
        String text_title="Dear "+alas+"\n.";
        String text_content="Congratulations on obtaining the "
                +(achievement.isHidden()?"hidden ":"")+"achievement "
                +achievement.getName()+":\n "+achievement.getDescription()+"\n";
        String text_end="Hope you make persistent efforts!\n";
        String text=text_title+text_content+text_end;
        
        Client client = Client.create();
        client.addFilter(new HTTPBasicAuthFilter("api",
                "key-77ed9a5a192dd6c5b90591b027c769b1"));
        WebResource webResource =
                client.resource("https://api.mailgun.net/v3/gocheer.donggu.me/messages");


        MultivaluedMapImpl formData = new MultivaluedMapImpl();
        formData.add("from", "GoCheer Achievements <postmaster@gocheer.donggu.me>");
        formData.add("to", email);
        formData.add("subject", "New GoCheer achievement: "+achievement.getName()+"\n.");
        formData.add("text",text);
        webResource.post(ClientResponse.class,formData);
    }
}
