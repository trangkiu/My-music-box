
package musiccenter;


public class userFav {
    private String userName;
    private String musicId;
    
    public userFav(String userName, String musicId){
        this.userName = userName;
        this.musicId =  musicId;              
    }

    public String getUserName() {
        return userName;
    }

    public String getMusicId() {
        return musicId;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }
}
