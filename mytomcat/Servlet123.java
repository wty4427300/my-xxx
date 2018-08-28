package mytomcat;

public interface Servlet123 {
    String doGet(myHttpRequest myhttpRequest, myResponse myresponse);
    void doPost(myHttpRequest myhttpRequest, myResponse myresponse);
}
