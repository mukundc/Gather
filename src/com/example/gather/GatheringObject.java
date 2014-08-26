package com.example.gather;
import java.io.Serializable;
import java.util.List;


public class GatheringObject implements Serializable{

	private String name;
	private String description;
	private String location;
	private List<String> friends;
	
	public GatheringObject(String n, String d, String l, List<String> f)
	{
		name = n;
		description = d;
		location = l;
		friends = f;
	}
	
	public String getName()
	{
		return name;
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