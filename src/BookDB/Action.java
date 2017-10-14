package BookDB;
//lab3 aaa
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;//ÕýÔòÌæ»»sqlÓï¾ä
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import org.apache.struts2.ServletActionContext;
public class Action {
    private Pattern p = Pattern.compile("\\?");
    /*private String sql1="insert into author(AuthorID,Name,Age,Country) values(?,?,?,?)"; 
    private String sql2="insert into book(ISBN,Title,AuthorID,Publisher,PublishDate,Price) values(?,?,?,?,?,?)";
    */
    private List<String> list = new LinkedList<String>();
    private List<String> list0 = new LinkedList<String>();
    ServletRequest request = ServletActionContext.getRequest();
    HttpServletRequest req = (HttpServletRequest) request;
    HttpSession session = req.getSession();
    private String username;
    private String title;
    private String isbn;
    private String name;
    private String publisher;
    private String publishdate;
    private String price;
    private String age;
    private String country;
    private String authorid;
    static int flag;
    public String getUsername(){
        return username;
    }
    public String getIsbn(){
        return isbn;
    }
    public String getTitle(){
        return title;
    }
    public String getName(){
        return name;
    }
    public String getAuthorid(){
        return authorid;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public void setAuthorid(String authorid) {
        this.authorid = authorid;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setName(String name) {
        this.name =name;
    }
    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }
    public void setPublishdate(String publishdate) {
        this.publishdate = publishdate;
    }
    public void setPrice(String price) {
        this.price = price;
    }
    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }
    public void setAge(String age) {
        this.age = age;
    }
    public void setCountry(String country) {
        this.country=country;
    }
    // all struts logic here
    public String execute() {
        String selID ="select AuthorID from Author where Name=?";
        Matcher m1 = p.matcher(selID);
        String _selID = m1.replaceFirst('"'+username+'"');
        DBConnection connect = new DBConnection();
        list0=connect.select(_selID);
        if(list0.size() == 0)
            return "FAILED";
        String selTitle ="select Title from Book where AuthorID=?";
        Matcher m2 = p.matcher(selTitle);
        String _selTitle = m2.replaceFirst('"'+list0.get(0)+'"');
        list=connect.select(_selTitle);
        session.setAttribute("list", list);
        session.setAttribute("username",username);
        if(list.size() == 0)
            return "FAILED";
        else
            return "SUCCESS";
    }
    public String addauthor(){
        String insAuthor=new String();
        insAuthor="insert into Author(AuthorID,Name,Age,Country) values(?,?,?,?)";
        Matcher m1 = p.matcher(insAuthor);
        insAuthor = m1.replaceFirst('"'+authorid+'"');
        m1 = p.matcher(insAuthor);
        insAuthor = m1.replaceFirst('"'+name+'"');
        m1 = p.matcher(insAuthor);
        insAuthor = m1.replaceFirst('"'+age+'"');
        m1 = p.matcher(insAuthor);
        insAuthor = m1.replaceFirst('"'+country+'"');
        DBConnection connect = new DBConnection();
        int signal=connect.insert(insAuthor);
        if(signal==1){
            if(flag==0)
                return "SUCCESS0";
            else
                return "SUCCESS1";
        }
        else
            return "FAILED";
    }
    public String addbook(){
        flag=1;
        String selAuthor="select * from Author where Name=?";
        Matcher m1 = p.matcher(selAuthor);
        String _selAuthor = m1.replaceFirst('"'+name+'"');
        DBConnection connect = new DBConnection();
        list=connect.select(_selAuthor);
        list0=connect.select("select AuthorID from Author");
        if(list.size()==0){
            if(list0.size()==0)
                authorid="1";
            else
                authorid=String.valueOf(Integer.parseInt(Collections.max(list0))+1);
        }
        else
            authorid=list.get(0);
        if(list.size()==0)
            return "ADD";
        String insBook="insert into Book(ISBN,Title,AuthorID,Publisher,PublishDate,Price) values(?,?,?,?,?,?)";
        m1 = p.matcher(insBook);
        insBook = m1.replaceFirst('"'+isbn+'"');
        m1 = p.matcher(insBook);
        insBook = m1.replaceFirst('"'+title+'"');
        m1 = p.matcher(insBook);
        insBook = m1.replaceFirst('"'+authorid+'"');
        m1 = p.matcher(insBook);
        insBook = m1.replaceFirst('"'+publisher+'"');
        m1 = p.matcher(insBook);
        insBook = m1.replaceFirst('"'+publishdate+'"');
        m1 = p.matcher(insBook);
        insBook = m1.replaceFirst('"'+price+'"');
        int signal=connect.insert(insBook);
        //System.out.println(insBook);
        if(signal==1)
            return "SUCCESS";
        else
            return "FAILED";
    }
    public String edit(){
        flag = 0;
        String updateBook=new String();
        updateBook="update Book set AuthorID=?,Publisher=?,PublishDate=?,Price=? where ISBN=?";
        String selAuthor="select * from Author where Name=?";
        Matcher m1 = p.matcher(selAuthor);
        String _selAuthor = m1.replaceFirst('"'+name+'"');
        DBConnection connect = new DBConnection();
        list=connect.select(_selAuthor);
        list0=connect.select("select AuthorID from Author");
        m1=p.matcher(updateBook);
        if(list.size()==0){
            if(list0.size()==0)
                authorid="1";
            else
                authorid=String.valueOf(Integer.parseInt(Collections.max(list0))+1);
        }
        else
            authorid=list.get(0);
        if(list.size()==0)
            return "ADD";
        updateBook = m1.replaceFirst('"'+authorid+'"');
        m1=p.matcher(updateBook);
        updateBook = m1.replaceFirst('"'+publisher+'"');
        m1=p.matcher(updateBook);
        updateBook = m1.replaceFirst('"'+publishdate+'"');
        m1=p.matcher(updateBook);
        updateBook = m1.replaceFirst('"'+price+'"');
        m1=p.matcher(updateBook);
        updateBook = m1.replaceFirst('"'+isbn+'"');
        int signal=connect.update(updateBook);
        if(signal==1)
            return "SUCCESS";
        else
            return "FAILED";
    }
    public String detail(){
        String selAuthor ="select * from Author where Name=?";
        Matcher m1 = p.matcher(selAuthor);
        String _selAuthor = m1.replaceFirst('"'+username+'"');
        DBConnection connect = new DBConnection();
        list0=connect.select(_selAuthor);
        session.setAttribute("list0", list0);
        String selBook ="select * from Book where Title=?";
        Matcher m2 = p.matcher(selBook);
        String _selBook = m2.replaceFirst('"'+title+'"');
        list=connect.select(_selBook);
        session.setAttribute("list", list);
        if(list.size()==0)
            return "FAILED";
        else
            return "SUCCESS";
    }
    public String gotoedit(){
        return "SUCCESS";
    }
    public String delete(){
        String delBook ="delete from Book where ISBN=?";
        Matcher m1 = p.matcher(delBook);
        String _delBook = m1.replaceFirst('"'+isbn+'"');
        DBConnection connect = new DBConnection();
        int signal=connect.delete(_delBook);
        if(signal==1)
            return "SUCCESS";
        else
            return "FAILED";
    }
    
}