package MainAssignment.registrationUtils;

import org.json.JSONObject;

public class userClass {

    public  String name,email,password;
    public int age;

    public userClass(String name, String email, String password, int age) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.age = age;
    }

    //taking json data from here for registration

//    {
//        "name": "Muhammad Nur Ali",
//            "email": "muh.nurali43@gmail.com",
//            "password": "12345678",
//            "age": 20
//    }

    //this api creates an object for registered user
    public JSONObject getJsonForRegistration(){
        JSONObject object=new JSONObject();
        object.put("name",this.name);
        object.put("email",this.email);
        object.put("password",this.password);
        object.put("age",this.age);

        return object;
    }

    //taking json data for login
    public JSONObject getJsonForLogin(){
        JSONObject object=new JSONObject();
        object.put("email",this.email);
        object.put("password",this.password);
        return object;
    }

    @Override
    public String toString() {
        return "userClass{" +
                "name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", age=" + age +
                '}';
    }
}
