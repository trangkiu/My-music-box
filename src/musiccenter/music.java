
package musiccenter;

import java.io.File;
import javafx.scene.control.Hyperlink;

enum GENRES{
    ROCK,
    FOLK,
    COUNTRY,
    POP,
    CLASSICAL,
    ORCHESTA;
}
public class music {
     private String id ;
     private String name;
     private String singer;
     private GENRES genres;
     private File poster;
     private String link;
     private String uploader;
     
     public music(String id,String name,String singer,GENRES genres,File poster,String link,String uploader ){
         this.id=id;
         this.name=name;
         this.genres= genres;         
         this.poster = poster;
         this.uploader = uploader;
         if(singer == null){
             this.singer="unknown";
         }else 
             this.singer = singer;
         if(link == null){
             this.link= null;
         }else 
             this.link = link;                
     }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getUploader() {
        return uploader;
    }

    public String getName() {
        return name;
    }

    public String getSinger() {
        return singer;
    }

    public GENRES getGenres() {
        return genres;
    }

    public File getPoster() {
        return poster;
    }

    public String getLink() {
        return link;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public void setGenres(GENRES genres) {
        this.genres = genres;
    }

    public void setPoster(File poster) {
        this.poster = poster;
    }

    public void setLink(String link) {
        this.link = link;
    }
     
     
}
