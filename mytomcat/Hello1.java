package mytomcat;

class Hello1 implements Servlet123  {

    @Override
    public String doGet(myHttpRequest myhttprequest, myResponse myresponse) {
        return myresponse.write("gun  dan!!!!!");
    }

    @Override
    public void doPost(myHttpRequest myhttpRequest, myResponse myrsponse) {

    }


}
