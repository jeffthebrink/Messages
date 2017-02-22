import spark.ModelAndView;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    static User user;

    public static void main(String[] args) {
        Spark.init();

        ArrayList<Message> messages = new ArrayList<Message>();

        Spark.get("/",
                (request, response) -> {
                    HashMap m = new HashMap();
                    if(user == null) {
                        return new ModelAndView(m, "index.html");
                    }
                    else {
                        m.put("name", user.name);
                        m.put("messages", messages); //displays messages arraylist
                        return new ModelAndView(m, "messages.html");
                    }
                },
                new MustacheTemplateEngine()
        );
        Spark.post("/index", (request, response) -> {
            String name = request.queryParams("loginName");
            user = new User(name);
            response.redirect("/");
            return "";
        });

        Spark.post("/messages", (request, response) -> {
            Message m1 = new Message(request.queryParams("message"));
            if (!m1.message.isEmpty()) { // only adds message if it's not empty
                messages.add(m1);
            }
            response.redirect("/");
            return "";
        });
    }
}