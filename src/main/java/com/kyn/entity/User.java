package com.kyn.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;


@Entity
public class User {
    @Id
    @Column(name="user_id")
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(name="first_name")
    private String firstName;
    
    @Column(name="last_name")
    private String lastName;

    @Column(name="user_name")
    private String userName;
    
    private String password;
    
    private String city;
    
    private String country;
    
    private String bio;
    
    @Column(name="profile_picture_url")
    private String pictureUrl;
    
    private String email;
   
    @Column(name="provider")
    private String provider;
    
    @Column(name="provider_id")
    private String providerId;
    
    @Lob
    @Column(name = "profile_picture")
	private byte[] profileImage;
    

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "role_id")
    private Role role;
 
    public String getProvider() {
        return provider;
    }
 
    public void setProvider(String provider) {
        this.provider = provider;
    }

    public User() {

    }

    public User(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }


    public User(String firstName,String lastName, String password, String userName) {
		// TODO Auto-generated constructor stub
    	this.firstName = firstName;
		this.lastName = lastName;
         this.password = password;
         this.userName = userName;
        
	}
    
    

	public User(Long id, String firstName, String lastName) {
		super();
		this.id = id;
		this.firstName = firstName;
		this.lastName = lastName;
	}


	public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getPictureUrl() {
		return pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}

	public byte[] getProfileImage() {
		return profileImage;
	}

	public void setProfileImage(byte[] profileImage) {
		this.profileImage = profileImage;
	}
	


    
    
    
}