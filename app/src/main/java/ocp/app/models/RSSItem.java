package ocp.app.models;

import android.content.ContentValues;

/**
 * This class handle RSS Item <item> node in rss xml
 * */
public class RSSItem {
	
	// All <item> node name
	public String _title;
    public String _link;
    public String _description;
    public String _pubdate;
    public String _guid;
    public String _image_url;

    public static final String TableName = "RssItems";
    public  static final String COL_ID = "_id";

    public  static final String COL__title = "_title";
    public  static final String COL__link = "_link";
    public  static final String COL__description = "_description";
    public  static final String COL__pubdate = "_pubdate";
    public  static final String COL_guiud = "_guid";
    public  static final String COL_image_url = "_image_url";
    public static final String[] FIELDS = { COL_ID, COL__title,COL__link,COL__description,COL__pubdate,COL_guiud,COL_image_url};

    public static final String CreateTable = "CREATE TABLE RssItems(_id INTEGER PRIMARY KEY AUTOINCREMENT,_title TEXT,_link TEXT,_description TEXT,_pubdate TEXT,_guid TEXT,_image_url TEXT)";


    // constructor
	public RSSItem(){
		
	}
	
	// constructor with parameters
	public RSSItem(String title, String link, String description, String pubdate, String guid,String ImageUrl){
		this._title = title;
		this._link = link;
		this._description = description;
		this._pubdate = pubdate;
		this._guid = guid;
        this._image_url = ImageUrl;
	}

    public ContentValues getContent() {
        final ContentValues values = new ContentValues();

        values.put(COL__title, this._title);
        values.put(COL__link, this._link);
        values.put(COL__description, this._description);
        values.put(COL__pubdate,this._pubdate);
        values.put(COL_guiud,this._guid);
        values.put(COL_image_url,this._image_url);
        return values;

    }
	
	/**
	 * All SET methods
	 * */
	public void setTitle(String title){
		this._title = title;
	}
	
	public void setLink(String link){
		this._link = link;
	}
	
	public void setDescription(String description){
		this._description = description;
	}
	
	public void setPubdate(String pubDate){
		this._pubdate = pubDate;
	}
	
	
	public void setGuid(String guid){
		this._guid = guid;
	}
	
	/**
	 * All GET methods
	 * */
	public String getTitle(){
		return this._title;
	}
	
	public String getLink(){
		return this._link;
	}
	
	public String getDescription(){
		return this._description;
	}
	
	public String getPubdate(){
		return this._pubdate;
	}
	
	public String getGuid(){
		return this._guid;
	}

    public String get_image_url() { return this._image_url;}
}
