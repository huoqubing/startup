package com.sql.project.startup.dto;

import org.apache.solr.client.solrj.beans.Field;

/**
 * @ClassName:     SearchEntity.java
 * @Description:   TODO
 * @author         FrankWong
 * @version        V1.0  
 * @Date           2014-2-2 下午3:50:31 
 */
public class SearchEntity implements SearchableEntity {

	@Field(ID_FIELD)
	private String id;
	
	@Field(NAME_FIELD)
	private String name;
	
	@Field(TYPE_FIELD)
	private String type;
	
	@Field(DB_ID_FIELD)
	private int _id;
	
	@Field(COUNT_FIELD)
	private int count = 0;

	
	public SearchEntity(){
		
	}
	
	public SearchEntity(String id,String name,String type,int _id,int count){
		this.id = id;
		this.name = name;
		this.type = type;
		this._id = _id;
		this.count = count;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}

	/**
	 * @param _id the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}

	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}

	/**
	 * @param count the count to set
	 */
	public void setCount(int count) {
		this.count = count;
	}
	
	public String toString(){
		return "id:"+id+" name:"+name+" type:"+type+" _id:"+_id+" count:"+count;
	}
}
