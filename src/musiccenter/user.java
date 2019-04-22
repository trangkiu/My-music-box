
package musiccenter;
/**
 *
 * @author Trang Nguyen
 */
public class user {
    private String userId;
    private String passwd;
 
    public user(String userId, String passwd ){
        this.userId = userId;
        this.passwd = passwd;

    }

    public String getUserId() {
        return userId;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }
    
    
    
}
