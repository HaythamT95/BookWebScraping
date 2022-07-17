package com.example.demo.model;

public class Book implements Comparable<Book>{
	private int ID;
	private String name;
	private String author;
	private byte[] image;
	private String imageBase64;
	private byte[] pdf;

	public Book() {
	}

	public Book(int ID, String name, String author, String imageBase64) {
		this.ID = ID;
		this.name = name;
		this.author = author;
		this.imageBase64 = imageBase64;
	}
	
	public Book(int iD, String name, String author, String imageBase64, byte[] pdf) {
		this.ID = iD;
		this.name = name;
		this.author = author;
		this.imageBase64 = imageBase64;
		this.pdf = pdf;
	}
	
	public String getImageBase64() {
		return imageBase64;
	}

	public void setImageBase64(String imageBase64) {
		this.imageBase64 = imageBase64;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Book && this.ID==(((Book) obj).ID)
				&& this.name.equals(((Book) obj).name)
				&& this.author == ((Book) obj).author)
			return true;
		else
			return false;
	}

	public byte[] getPdf() {
		return pdf;
	}

	public void setPdf(byte[] pdf) {
		this.pdf = pdf;
	}

	public byte[] getImage() {
		return image;
	}

	public void setImage(byte[] image) {
		this.image = image;
	}

	public int getID() {
		return ID;
	}

	public void setID(int iD) {
		this.ID = iD;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	@Override
	public String toString() {
		return "Book [ID=" + ID + ", name=" + name + ", author=" + author + "]";
	}

	@Override
	public int compareTo(Book o) {
		return this.ID-o.getID();
	}

}
