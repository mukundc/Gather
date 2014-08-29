package com.example.gather;
import java.io.Serializable;
import java.util.List;

import com.parse.ParseClassName;
import com.parse.ParseObject;

public class GatheringObject implements Serializable {

	
	private String name;
	private String description;
	private String location;
	private List<String> friends;
	private String admin;
	
	public GatheringObject()
	{
		name = "null";
		description = "null";
		location = "null";
		friends = null;
	}
	public GatheringObject(String n, String d, String l, List<String> f, String a)
	{
		name = n;
		description = d;
		location = l;
		friends = f;
		admin = a;
	}
	
	public String getName()
	{
		return name;
	}
	public void setAdmin(String newAdmin)
	{
		admin = newAdmin;
	}
	public String getAdmin()
	{
		return admin;
	}
	
	public String getDescription()
	{
		return description;
	}
	
	public String getLocation()
	{
		return location;
	}
	
	public List<String> getFriends()
	{
		return friends;
	}
	
	public void setFriends(List<String> f)
	{
		friends = f;
	}
	

}